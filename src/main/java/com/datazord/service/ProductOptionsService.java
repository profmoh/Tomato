package com.datazord.service;

import java.util.List;

import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.model.destination.DestinationColor;
import com.datazord.model.destination.DestinationSize;

public interface ProductOptionsService {

	static final String PRODUCT_OPTIONS_SEQ_KEY="product_options_seq";
	
	static final String DESTINATION_COLOR_SEQ_KEY="destination_color_seq";
	
	static final String DESTINATION_SIZE_SEQ_KEY="destination_size_seq";
	
	public List<DestinationColor> getDestinationColorList();
	
	public List<DestinationSize>  getDestinationSizeList();
	
	public void saveProductOptions(ProductOptions productOptions);
}
