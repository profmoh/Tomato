package com.datazord.service;

import java.util.List;

import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;

public interface ProductService {

	static final String DESTINATION_PRODUCT_SEQ_KEY="destination_product_seq";
	
	public void saveDestinationProduct();
	
	public List<DestinationProduct> getDestinationProductList();
	
	public List<SourceProduct> getSourceProductList();
		
}