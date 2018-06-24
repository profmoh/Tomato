package com.datazord.service.Impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.datazord.json.tomato.pojo.product.Child;
import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.json.tomato.pojo.product.ProductAttribute;
import com.datazord.json.tomato.pojo.product.ProductAttributeDescription;
import com.datazord.json.tomato.pojo.product.ProductDescription;
import com.datazord.json.tomato.pojo.product.ProductDiscount;
import com.datazord.json.tomato.pojo.product.ProductOption;
import com.datazord.json.tomato.pojo.product.ProductOptionValue;
import com.datazord.json.tomato.pojo.product.ProductSeoUrl;
import com.datazord.json.tomato.pojo.product.ProductSpecial;
import com.datazord.json.tomato.pojo.product.productCustomOption;
import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;
import com.datazord.repositories.DestinationProductRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.SourceProductRepository;
import com.datazord.service.ProductService;
import com.fasterxml.jackson.annotation.JsonProperty;

import reactor.core.publisher.Flux;

@Component
public class ProductServiceImpl implements ProductService{

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private SequenceRepository sequenceRepositorys;
	
	@Autowired
	private DestinationProductRepository destinationProductRepository;
	
	@Autowired
	private SourceProductRepository sourceProductRepository;
	

	private List<String> getParamterPathFromObject(){
		try{
	    Field[] fields = Product.class.getDeclaredFields();
	    List<String>productParmaeterPath=new ArrayList<>();

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
	    
	    productParmaeterPath.forEach(param->System.out.println(param));
	    
	    return productParmaeterPath;
		}catch(Exception e){
			logger.error("",e);
			return null;
		}
	}
	
	@Override
	public void saveDestinationProduct(){
		try {

			List<String> productParmaeterPath = getParamterPathFromObject();

			DestinationProduct destinationProduct;
			for (String param : productParmaeterPath) {
				destinationProduct = new DestinationProduct();
				destinationProduct.setName(param);
				destinationProduct.setId(sequenceRepositorys.getNextSequenceId(DESTINATION_PRODUCT_SEQ_KEY));

				destinationProductRepository.save(destinationProduct).subscribe();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}


	@Override
	public List<DestinationProduct> getDestinationProductList() {
		try {
			Flux<DestinationProduct> flux=destinationProductRepository.findAll();
			List<DestinationProduct>destinationProducts=flux.collectList().block();
			return destinationProducts;
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return null;
	}

	@Override
	public List<SourceProduct> getSourceProductList() {
		try{
		Flux<SourceProduct>flux=sourceProductRepository.findAll();
		List<SourceProduct>sourceProducts=flux.collectList().block();
		return sourceProducts;
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	
}
