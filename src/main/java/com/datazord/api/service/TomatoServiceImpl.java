package com.datazord.api.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.datazord.dto.CompanyConfigurationDto;
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
import com.datazord.model.TomatoProduct;
import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;
import com.datazord.service.CategoriesService;
import com.datazord.service.CompanyConfigurationService;
import com.datazord.service.MappingService;
import com.datazord.service.ProductOptionsService;
import com.datazord.service.ProductService;
import com.datazord.service.TomatoProductService;
import com.datazord.utils.ApiUtils;
import com.datazord.utils.FileUtils;
import com.datazord.utils.ImageUtils;
import com.datazord.utils.JsonUtils;
import com.datazord.utils.UrlUtils;
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

	@Autowired
	private CompanyConfigurationService companyConfigurationService;
	
	@Autowired
	private TomatoProductService tomatoProductService; 

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

	@Value("${xml.pathKey}")
	private String fullPathKey;

	@Value("${api.categories.maxLevel}")
	private Integer categoriesMaxLevel;

	public List<Category> findCategories() {
		logger.info("Getting Catigories ...");

		String categoriesUrl = baseUrl.concat("/rest_admin/categories/level/" + categoriesMaxLevel);

		try {
			ResponseEntity<Categories> categoriesResult = ApiUtils.doRequest(
					headerName, this.authorization, null, null, categoriesUrl, HttpMethod.GET, Categories.class);

			logger.info("Categories : " + categoriesResult.getBody().getData().getCategoriesMap().keySet());

			List<Category> categoriesList = new ArrayList<>();

			//level 1
			categoriesResult.getBody().getData().getCategoriesMap().values().forEach(level1CategoryList -> {
				level1CategoryList.forEach(level1Category -> {
					String level1CategoryName = level1Category.getName();

					categoriesList.add(level1Category);

					// level 2
					if(level1Category.getCategories().getCategoriesMap().size() > 0) {
						level1Category.getCategories().getCategoriesMap().values().forEach(level2CategoryList -> {
							level2CategoryList.forEach(level2Category -> {
								String level2CategoryName = level1CategoryName + "." + level2Category.getName();

								level2Category.setName(level2CategoryName);

								categoriesList.add(level2Category);

								// level 3
								if(level2Category.getCategories().getCategoriesMap().size() > 0) {
									level2Category.getCategories().getCategoriesMap().values().forEach(level3CategoryList -> {
										level3CategoryList.forEach(level3Category -> {
											String level3CategoryName = level2CategoryName + "." + level3Category.getName();

											level3Category.setName(level3CategoryName);

											categoriesList.add(level3Category);
										});
									});
								}
								// level 3
							});
						});
					}
					// level 2
				});
			});
			//level 1

//			List<Category> categoriesList = categoriesResult.getBody().getData().getCategoriesMap().values()
//					.stream()
//					.flatMap(List::stream)
//					.collect(Collectors.toList());

			return categoriesList;
		} catch (Exception e) {
			e.printStackTrace();
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

			String addOrUpdateproductUrl;
			boolean inDB=false;
			
			Long productIDFromDB = checkIfProductInDB(product.getSku());
			if (Utils.isNotEmpty(productIDFromDB)){
				addOrUpdateproductUrl = baseUrl.concat("/rest_admin/products/" + productIDFromDB);
				inDB=true;
			}
			else {
				inDB=false;
				
				Long productIDFromApi = checkIfProductInApi(product.getSku());
				if (Utils.isNotEmpty(productIDFromApi)){
					addOrUpdateproductUrl = baseUrl.concat("/rest_admin/products/" + productIDFromApi);
					
				}else
					addOrUpdateproductUrl = baseUrl.concat("/rest_admin/products");
			}

			String bodyJson;
			API_Reply api_Reply = new API_Reply();
			ObjectMapper mapper = new ObjectMapper();

			bodyJson = mapper.writeValueAsString(product);

			ResponseEntity<API_Reply> responseEntity =
					ApiUtils.doRequest(headerName, this.authorization, bodyJson, null, addOrUpdateproductUrl, HttpMethod.POST, API_Reply.class);

			api_Reply = responseEntity.getBody();
			api_Reply.setSuccess(0);
			if(api_Reply.getSuccess().equals(0) && !inDB){
				
				Long productId=checkIfProductInApi(product.getSku());
				if(Utils.isNotEmpty(productId)){
					
					TomatoProduct tomatoProduct=new TomatoProduct();
					tomatoProduct.setProductId(productId);
					tomatoProduct.setSku(product.getSku());
					
					tomatoProductService.saveTomatoProduct(tomatoProduct);
				}
				
			}

			return api_Reply;
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException : ", e);
			return null;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			return null;
		}
	}

	public List<String> saveSourceCategories(String inputPath, boolean isUrl) throws MissedMappingException, IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
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
			jsonObjectList = FileUtils.readJsonObjectsFormXML(inputPath, isUrl, fullPathKey);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

		Set<String> categoriesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList, categorySource.getName());

		if (!Utils.isEmptyCollection(categoriesList))
			categoriesService.saveSourceCategories(new ArrayList<>(categoriesList));

		return new ArrayList<>(categoriesList);
	}

	public List<String> saveSourceProductOptionColors(String inputPath, boolean isUrl) throws MissedMappingException {
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
			jsonObjectList = FileUtils.readJsonObjectsFormXML(inputPath, isUrl, fullPathKey);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

		Set<String> colorsList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList, colorSource.getName());

		if (! Utils.isEmptyCollection(colorsList))
			try {
				productOptionsService.saveSourceProductOptionColors(new ArrayList<>(colorsList));
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}

		return new ArrayList<>(colorsList);
	}

	public List<String> saveSourceProductOptionSizes(String inputPath, boolean isUrl) throws MissedMappingException {
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
			jsonObjectList = FileUtils.readJsonObjectsFormXML(inputPath, isUrl, fullPathKey);
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}

		Set<String> sizesList = Utils.getDistinctFieldByFieldNameInJson(jsonObjectList, sizeSource.getName());

		if (! Utils.isEmptyCollection(sizesList))
			try {
				productOptionsService.saveSourceProductOptionSizes(new ArrayList<>(sizesList));
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}

		return new ArrayList<>(sizesList);
	}

	public void saveProductListToAPI(String xmlPath, String xmlUrl) throws MissedMappingException {
		logger.info("calling Source product option sizes");

		boolean isUrl = true;
		String inputPath = xmlUrl;

		if(StringUtils.isNotBlank(xmlPath)) {
			isUrl = false;
			inputPath = xmlPath;
		}

		try {
			productService.saveSourceProductPath(inputPath, isUrl);
			saveSourceCategories(inputPath, isUrl);
			saveSourceProductOptionSizes(inputPath, isUrl);
			saveSourceProductOptionColors(inputPath, isUrl);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e1) {
			e1.printStackTrace();
			return;
		}
		
		Map<MappingType, Map<String, String>> mappingMap = null;

		try {
			mappingMap = mappingService.getMappingMap();
		} catch (Exception e) {
			throw new MissedMappingException(e.getMessage());
		}

		if(Utils.isEmptyMap(mappingMap) || ! isMappingMapValid(mappingMap))
			throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage + "missed Product mapping");

		List<JsonObject> jsonObjectList = null;

		try {
			jsonObjectList = FileUtils.readJsonObjectsFormXML(inputPath, isUrl, fullPathKey);
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
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage + ", missed Size mapping: " + sourceSize);

			String sourceColor = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_COLOR_JSON_PATH));

			String destinationColor = mappingMap.get(MappingType.color).get(sourceColor);

			if(StringUtils.isBlank(destinationColor))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage + ", missed Color mapping: " + sourceColor);

			String sourceCategory = JsonUtils.getStringValueFromJsonByPath(
					jsonObject, mappingMap.get(MappingType.productPath).get(TomatoConstants.DESTINATION_CATEGORY_JSON_PATH));

			String destinationCategory = mappingMap.get(MappingType.category).get(sourceCategory);

			if(StringUtils.isBlank(destinationCategory))
				throw new MissedMappingException(TomatoConstants.MissedMappingExceptionMessage + ", missed Category mapping: " + sourceCategory);

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

						try {
							JsonUtils.setObjectByValueAndPath(product, destinationPath, value);
						} catch (NoSuchMethodException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}

					sizeCounter++;
				}

				colorCounter++;
			}

			// handle language and download image
			for(ProductDescription productDescription : product.getProduct_description())
				if(StringUtils.isBlank(productDescription.getName()))
					productDescription.setName(product.getModel());

			CompanyConfigurationDto companyConfig = companyConfigurationService.getCompanyConfig();

			if(companyConfig != null && StringUtils.isNotBlank(companyConfig.getImagePath())) {
				if(StringUtils.isNotBlank(product.getImage()) && Utils.isValidUrl(product.getImage())) {
					String imageName = UrlUtils.getNameFromUrl(product.getImage());

					try {
						String imagePath = ImageUtils.downloadImage(product.getImage(), imageName, companyConfig.getImagePath());
						product.setImage(imagePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				List<String> otherImagesList = new ArrayList<>();

				for(String imageUrl : product.getOther_images()) {
					if(StringUtils.isBlank(product.getImage()) || ! Utils.isValidUrl(product.getImage()))
						continue;

					String imageName = UrlUtils.getNameFromUrl(imageUrl);

					try {
						String imagePath = ImageUtils.downloadImage(imageUrl, imageName, companyConfig.getImagePath());
						otherImagesList.add(imagePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				product.setOther_images(otherImagesList);

				for(productCustomOption productCustomOption : product.getProduct_custom_option()) {
					String imageUrl = productCustomOption.getImage();

					if(StringUtils.isBlank(imageUrl) || ! Utils.isValidUrl(imageUrl))
						continue;

					String imageName = UrlUtils.getNameFromUrl(imageUrl);

					try {
						String imagePath = ImageUtils.downloadImage(imageUrl, imageName, companyConfig.getImagePath());
						productCustomOption.setImage(imagePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			resultedProductMap.put(destinationCategoryFromMap, product);
		}
	
		for (Map.Entry<String, Product> entry : resultedProductMap.entrySet()) {
			Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().create();
			System.out.println(gson.toJson(entry));
		}

		// call the API
		for (Map.Entry<String, Product> entry : resultedProductMap.entrySet()) {
			System.out.println(entry.getKey());
			
			System.out.println("sku : " + entry.getValue().getSku() + ", " + "quantity : " + entry.getValue().getQuantity() + ", " + "price : " + entry.getValue().getPrice());

			for(productCustomOption p : entry.getValue().getProduct_custom_option()) {
				System.out.println("\t\t\tcolor : " + p.getColor_id() + " --> ");

				for(Child c : p.getChild()) {
					System.out.println("\t\t\t\t\t\tsize : " + c.getSize_id() + ", " + "quantity : " + c.getQuantity() + ", " + "price : " + c.getPrice());
				}
			}

			API_Reply api_Reply = addProduct(entry.getValue());

			logger.debug("DestinationCategory:" + entry.getKey() +
					">>> Success:" + api_Reply.getSuccess() + " , Error:" + api_Reply.getError() + " , Data:" + api_Reply.getData());
		}
		
	}
	
	public Long checkIfProductInApi(String productSKU){
		try {
			String getproductSKU_Url = baseUrl.concat("/rest_admin/products/getproductbysku/").concat(productSKU);
			API_Reply api_Reply = new API_Reply();
			ResponseEntity<API_Reply> responseEntity = ApiUtils.doRequest(headerName, this.authorization, null, null,
					getproductSKU_Url, HttpMethod.GET, API_Reply.class);
			api_Reply = responseEntity.getBody();

			logger.debug("productSKU:" + productSKU + ">>> Success:" + api_Reply.getSuccess() + " , Error:"
					+ api_Reply.getError());

			if (api_Reply.getSuccess().equals(1))
				return api_Reply.getData().getId();
			else
				return null;

		} catch (Exception e) {
			logger.error("", e);
			return null;
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
	
	public Long checkIfProductInDB(String productSKU){
		try {
			TomatoProduct product=tomatoProductService.getProductBySku(productSKU);
			if(Utils.isNotEmpty(product))
				return product.getProductId();
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
}
