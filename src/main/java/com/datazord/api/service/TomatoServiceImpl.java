package com.datazord.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

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
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.json.tomato.pojo.categories.Categories;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;
import com.datazord.service.CategoriesService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.utils.ApiUtils;
import com.datazord.utils.FileUtils;
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
	private ProductService productService;

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

	public List<String> saveSourceCategories() {
		logger.info("Getting Source Catigories ...");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		Set<String> categoriesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList,
				"#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: item_name");

		if (!Utils.isEmptyCollection(categoriesList))
			categoriesService.saveSourceCategories(new ArrayList<>(categoriesList));

		return new ArrayList<>(categoriesList);
	}

	public List<String> saveSourceProductOptionColors() {
		logger.info("calling Source product option colors");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		Set<String> colorsList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList,
				"#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: color_name");

		if (!Utils.isEmptyCollection(colorsList))
			productOptionsService.saveSourceProductOptionColors(new ArrayList<>(colorsList));

		return new ArrayList<>(colorsList);
	}

	public List<String> saveSourceProductOptionSizes() {
		logger.info("calling Source product option sizes");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		Set<String> sizesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList,
				"#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: SIZE_NAME");

		if (!Utils.isEmptyCollection(sizesList))
			productOptionsService.saveSourceProductOptionSizes(new ArrayList<>(sizesList));

		return new ArrayList<>(sizesList);
	}

	public void saveProductListToAPI() {
		logger.info("calling Source product option sizes");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(TomatoConstants.xmlFilePath);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return;
		}

		List<SourceProduct> xpathObjectList = productService.getSourceProductList();
		List<DestinationProduct> jsonPathObjectList = productService.getDestinationProductList();


		if(Utils.isEmptyCollection(xpathObjectList) || Utils.isEmptyCollection(jsonPathObjectList))
			return;

		Set<Object> xpathList = Utils.getDistinctFieldByFieldName(xpathObjectList, "name");
		Set<Object> jsonPathList = Utils.getDistinctFieldByFieldName(jsonPathObjectList, "name");


//		for (JsonObject jsonObject : jsonObjectList)
	}
}
