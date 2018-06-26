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
import com.datazord.enums.MappingFlag;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.json.tomato.pojo.categories.Categories;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.service.CategoriesService;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.utils.ApiUtils;
import com.datazord.utils.FileUtils;
import com.datazord.utils.JsonUtils;
import com.datazord.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@Service
public class TomatoServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(TomatoServiceImpl.class);

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

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

// use the mapping to get the path or throw MissedMappingException
		Set<String> categoriesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList,
				"#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: item_name");

		if (!Utils.isEmptyCollection(categoriesList))
			categoriesService.saveSourceCategories(new ArrayList<>(categoriesList));

		return new ArrayList<>(categoriesList);
	}

	public List<String> saveSourceProductOptionColors() throws MissedMappingException {
		logger.info("calling Source product option colors");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

// use the mapping to get the path or throw MissedMappingException
		Set<String> colorsList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList,
				"#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: color_name");

		if (!Utils.isEmptyCollection(colorsList))
			productOptionsService.saveSourceProductOptionColors(new ArrayList<>(colorsList));

		return new ArrayList<>(colorsList);
	}

	public List<String> saveSourceProductOptionSizes() throws MissedMappingException {
		logger.info("calling Source product option sizes");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

// use the mapping to get the path or throw MissedMappingException
		Set<String> sizesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList,
				"#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: SIZE_NAME");

		if (!Utils.isEmptyCollection(sizesList))
			productOptionsService.saveSourceProductOptionSizes(new ArrayList<>(sizesList));

		return new ArrayList<>(sizesList);
	}

	public void saveProductListToAPI() throws MissedMappingException {
		logger.info("calling Source product option sizes");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return;
		}

		Map<MappingFlag, Map<String, String>> mappingMap = null;

		try {
			mappingMap = mappingService.getMappingMap();
		} catch (Exception e) {
			throw new MissedMappingException(e.getMessage());
		}

		if(Utils.isEmptyMap(mappingMap) || ! isMappingMapValid(mappingMap))
			throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

		Map<String, Product> resultedProductMap = new HashMap<>();

		for (JsonObject jsonObject : jsonObjectList) {
			String sourceSize = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingFlag.productPath).get(TomatoConstants.DESTINATION_SIZE_JSON_PATH));

			String destinationSize = mappingMap.get(MappingFlag.size).get(sourceSize);

			if(StringUtils.isBlank(destinationSize))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			String sourceColor = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingFlag.productPath).get(TomatoConstants.DESTINATION_COLOR_JSON_PATH));

			String destinationColor = mappingMap.get(MappingFlag.color).get(sourceColor);

			if(StringUtils.isBlank(destinationColor))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			String sourceCategory = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingFlag.productPath).get(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH));

			String destinationCategory = mappingMap.get(MappingFlag.category).get(sourceCategory);

			if(StringUtils.isBlank(destinationCategory))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage);

			Product product = resultedProductMap.get(destinationCategory);

			if(product == null)
				product = new Product();

			for(String destinationPath : mappingMap.get(MappingFlag.productPath).keySet()) {
				String sourcePath = mappingMap.get(MappingFlag.productPath).get(destinationPath);

				String value = null;

				try {
					value = JsonUtils.getStringValueFromJsonByPath(jsonObject, sourcePath);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				JsonUtils.setObjectByValueAndPath(product, destinationPath, value);
			}

			if(resultedProductMap.get(destinationCategory) != null)
				resultedProductMap.remove(destinationCategory);

			resultedProductMap.put(destinationCategory, product);
		}

		// call the API
	}

	private boolean isMappingMapValid(Map<MappingFlag, Map<String, String>> mappingMap) {
		if(Utils.isEmptyMap(mappingMap.get(MappingFlag.size)))
			return false;

		if(Utils.isEmptyMap(mappingMap.get(MappingFlag.color)))
			return false;

		if(Utils.isEmptyMap(mappingMap.get(MappingFlag.category)))
			return false;

		if(Utils.isEmptyMap(mappingMap.get(MappingFlag.productPath)))
			return false;

		if(StringUtils.isAllBlank(mappingMap.get(MappingFlag.productPath).get(TomatoConstants.DESTINATION_SIZE_JSON_PATH)))
			return false;

		if(StringUtils.isAllBlank(mappingMap.get(MappingFlag.productPath).get(TomatoConstants.DESTINATION_COLOR_JSON_PATH)))
			return false;

		if(StringUtils.isAllBlank(mappingMap.get(MappingFlag.productPath).get(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH)))
			return false;

		return true;
	}
}
