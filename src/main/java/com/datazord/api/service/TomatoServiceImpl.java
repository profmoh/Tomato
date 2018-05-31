package com.datazord.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.json.tomato.pojo.categories.Categories;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.utils.ApiUtils;

@Service
public class TomatoServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(TomatoServiceImpl.class);

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
			ResponseEntity<Categories> categoriesResult =
					ApiUtils.doRequest(headerName, this.authorization, null, null, categoriesUrl, HttpMethod.GET, Categories.class);

			logger.info("Categories : " + categoriesResult.getBody().getData().getCategoriesMap().keySet());

			List<Category> categoriesList = 
					categoriesResult.getBody().getData().getCategoriesMap()
					.values().stream()
				        .flatMap(List::stream)
				        .collect(Collectors.toList());

			return categoriesList;
		} catch (Exception e) {
			logger.error("Categories Read Error");
			//e.printStackTrace();
			return null;
		}
	}
	
	public ProductOptions findProductOptionsValue(String optionID){
		String productOptUrl="";
		logger.info("calling production_Options with Id="+optionID);
		if(optionID.equals("13"))
		    productOptUrl = baseUrl.concat("/rest_admin/product_options/13");
		else if(optionID.equals("11"))
			productOptUrl = baseUrl.concat("/rest_admin/product_options/11");
		
			ProductOptions productOptions=new ProductOptions();
			ResponseEntity<ProductOptions>responseEntity=ApiUtils.doRequest(headerName, this.authorization, null, null, productOptUrl, HttpMethod.GET, ProductOptions.class);
			productOptions=responseEntity.getBody();
			return productOptions;
	
	}
}
