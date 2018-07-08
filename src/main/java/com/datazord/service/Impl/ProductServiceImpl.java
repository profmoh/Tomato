package com.datazord.service.Impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.datazord.dto.ReconciliationHolder;
import com.datazord.json.tomato.pojo.product.Child;
import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.json.tomato.pojo.product.ProductDescription;
import com.datazord.json.tomato.pojo.product.productCustomOption;
import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;
import com.datazord.repositories.DestinationProductRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.SourceProductRepository;
import com.datazord.service.ProductService;
import com.datazord.utils.FileUtils;
import com.datazord.utils.Reconciliation;
import com.datazord.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private DestinationProductRepository destinationProductRepository;

	@Autowired
	private SourceProductRepository sourceProductRepository;

	private List<String> getParamterPathFromObject() {
		try {
			Field[] fields = Product.class.getDeclaredFields();
			List<String> productParmaeterPath = new ArrayList<>();

	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {	
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_description") ||
	            	 annotationValue.equals("product_custom_option"))
	            	continue;
	            String parameterPath="Product."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    
	    fields = ProductDescription.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_description."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    
	    fields = productCustomOption.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("child"))
	            	continue;
	            String parameterPath="Product.product_custom_option."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = Child.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_custom_option.child."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	   // productParmaeterPath.forEach(param->System.out.println(param));
	    
	    return productParmaeterPath;
		}catch(Exception e){
			logger.error("",e);
			return null;
		}
	}

	@Override
	public void saveDestinationProduct() {
		try {
			List<String> productParmaeterPath = getParamterPathFromObject();

			List<ReconciliationHolder> disProductListReconciliation =
					productParmaeterPath
					.stream()
					.map(p -> new ReconciliationHolder(sequenceRepositorys.getNextSequenceId(DESTINATION_PRODUCT_SEQ_KEY), p))
					.collect(Collectors.toList());

			List<DestinationProduct> mainProductList = destinationProductRepository.findAll().collectList().block();

			List<ReconciliationHolder> mainProductListReconciliation =
					mainProductList.stream().map(s -> new ReconciliationHolder(s.getId(), s.getName())).collect(Collectors.toList());

			List<ReconciliationHolder> reconciliationResult =
					Reconciliation.compare(mainProductListReconciliation, disProductListReconciliation, Arrays.asList("name"));

			for (ReconciliationHolder reconciliationHolder : reconciliationResult) {
				switch (reconciliationHolder.reconciliationResult) {
				case added:
					destinationProductRepository.save(new DestinationProduct(reconciliationHolder.id, reconciliationHolder.name)).subscribe();
					break;
				case removed:
					destinationProductRepository.deleteById(reconciliationHolder.mainId).subscribe();
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public List<DestinationProduct> getDestinationProductList() {
		try {
			Flux<DestinationProduct> flux = destinationProductRepository.findAll();
			List<DestinationProduct> destinationProducts = flux.collectList().block();

			return destinationProducts;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public List<SourceProduct> getSourceProductList() {
		try {
			Flux<SourceProduct> flux = sourceProductRepository.findAll();
			List<SourceProduct> sourceProducts = flux.collectList().block();

			List<String> distinctPath = Utils.getDistinctFieldByGetterName(sourceProducts, "getName")
					.stream()
					.map(p -> (String) p)
					.collect(Collectors.toList());

			String intersectionPath = FileUtils.getXPathIntersection(distinctPath);

			sourceProducts = sourceProducts
					.stream()
					.map(p -> new SourceProduct(p.getId(), replaceIntersection(p.getName(), intersectionPath)))
					.collect(Collectors.toList());

			return sourceProducts;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	private String replaceIntersection(String path, String intersection) {
		path = path.replaceFirst(intersection, "");

		if(path.startsWith(" :: "))
			path = path.replaceFirst(" :: ", "");

		return path;
	}

	@Override
	public void saveSourceProductPath(String inputPath, boolean isUrl) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		logger.info("calling Source product option colors");

		List<String> xpathList = null;

		try {
			xpathList = FileUtils.extractXPathList(FileUtils.readXMLfileToDocument(inputPath, isUrl));
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return;
		}

		List<ReconciliationHolder> disProductListReconciliation =
				xpathList
				.stream()
				.map(p -> new ReconciliationHolder(sequenceRepositorys.getNextSequenceId(SOURCE_PRODUCT_PATH_SEQ_KEY), p))
				.collect(Collectors.toList());

		List<SourceProduct> mainProductList = sourceProductRepository.findAll().collectList().block();

		List<ReconciliationHolder> mainProductListReconciliation =
				mainProductList.stream().map(s -> new ReconciliationHolder(s.getId(), s.getName())).collect(Collectors.toList());

		List<ReconciliationHolder> reconciliationResult =
				Reconciliation.compare(mainProductListReconciliation, disProductListReconciliation, Arrays.asList("name"));

		for (ReconciliationHolder reconciliationHolder : reconciliationResult) {
			switch (reconciliationHolder.reconciliationResult) {
			case added:
				sourceProductRepository.save(new SourceProduct(reconciliationHolder.id, reconciliationHolder.name)).subscribe();
				break;
			case removed:
				sourceProductRepository.deleteById(reconciliationHolder.mainId).subscribe();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Map<String, String> getSourceProductPathMap() {
		try {
			List<SourceProduct> sourceProductPathList = sourceProductRepository.findAll().collectList().block();

			Map<String, String> sourceProductPathMap = sourceProductPathList
					.stream()
					.collect(Collectors.toMap(SourceProduct::getId, SourceProduct::getName));

			return sourceProductPathMap;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public Map<String, String> getDestinationProductPathMap() {
		try {
			List<DestinationProduct> destinationProductPathList = getDestinationProductList();

			Map<String, String> destinationProductPathMap = destinationProductPathList
					.stream()
					.collect(Collectors.toMap(DestinationProduct::getId, DestinationProduct::getName));

			return destinationProductPathMap;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public DestinationProduct getDestinationProductById(String id) {
		try {
			Mono<DestinationProduct> mono = destinationProductRepository.findById(id);
			return mono.block();
//			DestinationProduct destinationProduct = new DestinationProduct();
//			destinationProduct = mono.block();
//			return destinationProduct;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public DestinationProduct getDestinationProductByName(String name) {
		try {
			Mono<DestinationProduct> mono = destinationProductRepository.findByName(name);
			return mono.block();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public SourceProduct getSourceProductById(String id) {
		try {
			Mono<SourceProduct> mono = sourceProductRepository.findById(id);
			return mono.block();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
