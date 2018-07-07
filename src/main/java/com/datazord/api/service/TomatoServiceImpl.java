package com.datazord.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.datazord.api.reply.API_Reply;
import com.datazord.constants.TomatoConstants;
import com.datazord.enums.Language;
import com.datazord.enums.MappingType;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.json.tomato.pojo.categories.Categories;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.json.tomato.pojo.product.Child;
import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.json.tomato.pojo.product.ProductDescription;
import com.datazord.json.tomato.pojo.product.productCustomOption;
import com.datazord.model.MappingResult;
import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;
import com.datazord.service.CategoriesService;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.utils.ApiUtils;
import com.datazord.utils.FileUtils;
import com.datazord.utils.JsonUtils;
import com.datazord.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Service
public class TomatoServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(TomatoServiceImpl.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductOptionsService productOptionsService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private MappingService mappingService;

	@Value("${resource.url}")
	private String baseUrl;

	@Value("${resource.username}")
	private String username;

	@Value("${resource.password}")
	private String password;

	@Value("${resource.authorization}")
	private String authorization;

	@Value("${resource.headerName}")
	private String headerName;

	public List<Category> findCategories() {
		logger.info("Getting Catigories ...");

		String categoriesUrl = baseUrl.concat("/rest_admin/categories");

		try {
			ResponseEntity<Categories> categoriesResult = ApiUtils.doRequest(
					headerName, this.authorization, null, null, categoriesUrl, HttpMethod.GET, Categories.class);

			logger.info("Categories : " + categoriesResult.getBody().getData().getCategoriesMap().keySet());

			List<Category> categoriesList = categoriesResult.getBody().getData().getCategoriesMap().values()
					.stream()
					.flatMap(List::stream)
					.collect(Collectors.toList());

			return categoriesList;
		} catch (Exception e) {
			logger.error("Categories Read Error");
			return null;
		}
	}

	public ProductOptions findProductOptionsValue(Integer optionID) {
		String productOptUrl = "";

		logger.info("calling production_Options with Id=" + optionID);

		if (optionID.equals(TomatoConstants.COLOR_PRODUCT_OPTION))
			productOptUrl = baseUrl.concat("/rest_admin/product_options/" + TomatoConstants.COLOR_PRODUCT_OPTION);
		else if (optionID.equals(TomatoConstants.SIZE_PRODUCT_OPTION))
			productOptUrl = baseUrl.concat("/rest_admin/product_options/" + TomatoConstants.SIZE_PRODUCT_OPTION);

		ProductOptions productOptions = new ProductOptions();

		ResponseEntity<ProductOptions> responseEntity = ApiUtils.doRequest(
				headerName, this.authorization, null, null, productOptUrl, HttpMethod.GET, ProductOptions.class);

		productOptions = responseEntity.getBody();

		return productOptions;
	}

	public API_Reply addProduct(Product product) {
		try {
			logger.info("Insert Product Into Tomato ");

			String addproductUrl = baseUrl.concat("/rest_admin/products");

			String bodyJson;
			API_Reply api_Reply = new API_Reply();
			ObjectMapper mapper = new ObjectMapper();

			bodyJson = mapper.writeValueAsString(product);

			ResponseEntity<API_Reply> responseEntity = ApiUtils.doRequest(
					headerName, this.authorization, bodyJson, null, addproductUrl, HttpMethod.POST, API_Reply.class);

			api_Reply = responseEntity.getBody();

			return api_Reply;
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException : ", e);
			return null;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			return null;
		}
	}

	public List<String> saveSourceCategories() throws MissedMappingException {
		logger.info("Getting Source Catigories ...");

		DestinationProduct categoryDestinationPath = productService.getDestinationProductByName(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH);

		if(categoryDestinationPath == null)
			throw new MissedMappingException("Category Path from destination is not defined");

		MappingResult catecoryDestinationMapping =
				mappingService.findMappingResultByDestinationIdAndMappingType(categoryDestinationPath.getId(), MappingType.productPath);

		if(catecoryDestinationMapping == null)
			throw new MissedMappingException("Category Path from destination is not mapped to any source path");

		SourceProduct categorySource = productService.getSourceProductById(catecoryDestinationMapping.getSourceId());

		if(categorySource == null)
			throw new MissedMappingException("Category Path from source is not found");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

// use the mapping to get the path or throw MissedMappingException
// "#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: item_name"
		Set<String> categoriesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList, categorySource.getName());

		if (!Utils.isEmptyCollection(categoriesList))
			categoriesService.saveSourceCategories(new ArrayList<>(categoriesList));

		return new ArrayList<>(categoriesList);
	}

	public List<String> saveSourceProductOptionColors() throws MissedMappingException {
		logger.info("calling Source product option colors");

		DestinationProduct colorDestinationPath = productService.getDestinationProductByName(TomatoConstants.DESTINATION_COLOR_JSON_PATH);

		if(colorDestinationPath == null)
			throw new MissedMappingException("Color Path from destination is not defined");

		MappingResult colorDestinationMapping =
				mappingService.findMappingResultByDestinationIdAndMappingType(colorDestinationPath.getId(), MappingType.productPath);

		if(colorDestinationMapping == null)
			throw new MissedMappingException("Color Path from destination is not mapped to any source path");

		SourceProduct colorSource = productService.getSourceProductById(colorDestinationMapping.getSourceId());

		if(colorSource == null)
			throw new MissedMappingException("Color Path from source is not found");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

// use the mapping to get the path or throw MissedMappingException
// "#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: color_name"
		Set<String> colorsList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList, colorSource.getName());

		if (!Utils.isEmptyCollection(colorsList))
			productOptionsService.saveSourceProductOptionColors(new ArrayList<>(colorsList));

		return new ArrayList<>(colorsList);
	}

	public List<String> saveSourceProductOptionSizes() throws MissedMappingException {
		logger.info("calling Source product option sizes");

		DestinationProduct sizeDestinationPath = productService.getDestinationProductByName(TomatoConstants.DESTINATION_SIZE_JSON_PATH);

		if(sizeDestinationPath == null)
			throw new MissedMappingException("Size Path from destination is not defined");

		MappingResult sizeDestinationMapping =
				mappingService.findMappingResultByDestinationIdAndMappingType(sizeDestinationPath.getId(), MappingType.productPath);

		if(sizeDestinationMapping == null)
			throw new MissedMappingException("Size Path from destination is not mapped to any source path");

		SourceProduct sizeSource = productService.getSourceProductById(sizeDestinationMapping.getSourceId());

		if(sizeSource == null)
			throw new MissedMappingException("Size Path from source is not found");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

// use the mapping to get the path or throw MissedMappingException
// "#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: SIZE_NAME"
		Set<String> sizesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList, sizeSource.getName());

		if (!Utils.isEmptyCollection(sizesList))
			productOptionsService.saveSourceProductOptionSizes(new ArrayList<>(sizesList));

		return new ArrayList<>(sizesList);
	}

	public void saveProductListToAPI() throws MissedMappingException {
		logger.info("calling Source product option sizes");

		Map<MappingType, Map<String, String>> mappingMap = null;

		try {
			mappingMap = mappingService.getMappingMap();
		} catch (Exception e) {
			throw new MissedMappingException(e.getMessage());
		}

		if(Utils.isEmptyMap(mappingMap) || ! isMappingMapValid(mappingMap))
			throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return;
		}

		Map<String, Map<String, Map<String, JsonObject>>> jsonObjectMap = new HashMap<>();

		for (JsonObject jsonObject : jsonObjectList) {
			String sourceSize = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_SIZE_JSON_PATH));

			String destinationSize = mappingMap.get(MappingType.size).get(sourceSize);

			if(StringUtils.isBlank(destinationSize))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			String sourceColor = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_COLOR_JSON_PATH));

			String destinationColor = mappingMap.get(MappingType.color).get(sourceColor);

			if(StringUtils.isBlank(destinationColor))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			String sourceCategory = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH));

			String destinationCategory = mappingMap.get(MappingType.category).get(sourceCategory);

			if(StringUtils.isBlank(destinationCategory))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			if(! jsonObjectMap.containsKey(destinationCategory))
				jsonObjectMap.put(destinationCategory, new HashMap<>());

			if(! jsonObjectMap.get(destinationCategory).containsKey(destinationColor))
				jsonObjectMap.get(destinationCategory).put(destinationColor, new HashMap<>());

			if(! jsonObjectMap.get(destinationCategory).get(destinationColor).containsKey(destinationSize))
				jsonObjectMap.get(destinationCategory).get(destinationColor).put(destinationSize, jsonObject);
		}

		//mapping json objects to product
		Map<String, Product> resultedProductMap = new HashMap<>();

		//categories
		for(String destinationCategoryFromMap : jsonObjectMap.keySet()) {
			int colorCounter = 0;
			JsonObject jsonObject = null;
			Product product = new Product();

			for(Language language : Language.values()) {
				ProductDescription productDescription = new ProductDescription();

				productDescription.setLanguage_id("" + language.getValue());

				product.getProduct_description().add(productDescription);
			}

			//colors
			for(String destinationColorFromMap : jsonObjectMap.get(destinationCategoryFromMap).keySet()) {
				int sizeCounter = 0;
				product.getProduct_custom_option().add(colorCounter, new productCustomOption());

				//sizes
				for(String destinationSizeFromMap : jsonObjectMap.get(destinationCategoryFromMap).get(destinationColorFromMap).keySet()) {
					product.getProduct_custom_option().get(colorCounter).getChild().add(sizeCounter, new Child());

					jsonObject = jsonObjectMap.get(destinationCategoryFromMap).get(destinationColorFromMap).get(destinationSizeFromMap);

					//path
					for(String destinationPath : mappingMap.get(MappingType.productPath).keySet()) {
						String sourcePath = mappingMap.get(MappingType.productPath).get(destinationPath);

						String value = null;

						try {
							if(destinationPath.equals(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH))
								value = destinationCategoryFromMap;
							else if(destinationPath.equals(TomatoConstants.DESTINATION_COLOR_JSON_PATH))
								value = destinationColorFromMap;
							else if(destinationPath.equals(TomatoConstants.DESTINATION_SIZE_JSON_PATH))
								value = destinationSizeFromMap;
							else
								value = JsonUtils.getStringValueFromJsonByPath(jsonObject, sourcePath);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}

						JsonUtils.setObjectByValueAndPath(product, destinationPath, value);
					}

					sizeCounter++;
				}

				colorCounter++;
			}

			for(ProductDescription productDescription : product.getProduct_description())
				if(StringUtils.isBlank(productDescription.getName()))
					productDescription.setName(product.getModel());

			resultedProductMap.put(destinationCategoryFromMap, product);
		}
	
		for (Map.Entry<String, Product> entry : resultedProductMap.entrySet()) {
			Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().create();
			System.out.println(gson.toJson(entry));
		}

		// call the API
		for (Map.Entry<String, Product> entry : resultedProductMap.entrySet()) {
			API_Reply api_Reply = addProduct(entry.getValue());

			logger.debug("DestinationCategory:" + entry.getKey() +
					">>> Success:" + api_Reply.getSuccess() + " , Error:" + api_Reply.getError() + " ,Data:" + api_Reply.getData());
		}
		
	}

	private boolean isMappingMapValid(Map<MappingType, Map<String, String>> mappingMap) {
		if(Utils.isEmptyMap(mappingMap.get(MappingType.size)))
			return false;

		if(Utils.isEmptyMap(mappingMap.get(MappingType.color)))
			return false;

		if(Utils.isEmptyMap(mappingMap.get(MappingType.category)))
			return false;

		if(Utils.isEmptyMap(mappingMap.get(MappingType.productPath)))
			return false;

		if(StringUtils.isAllBlank(mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_SIZE_JSON_PATH)))
			return false;

		if(StringUtils.isAllBlank(mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_COLOR_JSON_PATH)))
			return false;

		if(StringUtils.isAllBlank(mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH)))
			return false;

		return true;
	}
}
