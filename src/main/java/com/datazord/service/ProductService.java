package com.datazord.service;

import java.util.List;

import com.datazord.model.destination.DestinationProduct;

public interface ProductService {

	static final String DESTINATION_PRODUCT_SEQ_KEY="destination_product_seq";
	
	public void getParamterPathFromObject();
	
	public List<DestinationProduct> getDestinationProducts();
}