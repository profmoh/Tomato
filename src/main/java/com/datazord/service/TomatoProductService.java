package com.datazord.service;

import com.datazord.model.TomatoProduct;

public interface TomatoProductService {

	static final String TOMATO_PRODUCT_SQU="tomato_product_squ";
	
	TomatoProduct getProductBySku(String sku);
	
	void saveTomatoProduct(TomatoProduct product);
}
