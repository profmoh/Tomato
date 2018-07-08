package com.datazord.service;

import java.util.List;
import java.util.Map;

import com.datazord.model.destination.DestinationProduct;
import com.datazord.model.source.SourceProduct;

public interface ProductService {

	static final String DESTINATION_PRODUCT_SEQ_KEY = "destination_product_seq";

	static final String SOURCE_PRODUCT_PATH_SEQ_KEY = "source_product_path_seq";

	public void saveDestinationProduct();

	public List<DestinationProduct> getDestinationProductList();

	public List<SourceProduct> getSourceProductList();

	void saveSourceProductPath() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException;

	public Map<String, String> getSourceProductPathMap();

	public Map<String, String> getDestinationProductPathMap();
	
	public DestinationProduct getDestinationProductById(String id);

	public DestinationProduct getDestinationProductByName(String name);

	public SourceProduct getSourceProductById(String id);
}