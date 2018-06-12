package com.datazord.service;

import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;

public interface ProductOptionsService {

	static final String PRODUCT_OPTIONS_SEQ_KEY="product_options_seq";
	
	public void saveProductOptions(ProductOptions productOptions);
}
