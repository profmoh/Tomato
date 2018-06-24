package com.datazord.service;

import java.util.List;

import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.model.destination.DestinationColor;
import com.datazord.model.destination.DestinationSize;
import com.datazord.model.source.SourceColor;
import com.datazord.model.source.SourceSize;

public interface ProductOptionsService {

	static final String PRODUCT_OPTIONS_SEQ_KEY = "product_options_seq";

	static final String DESTINATION_SIZE_SEQ_KEY = "destination_size_seq";
	static final String DESTINATION_COLOR_SEQ_KEY = "destination_color_seq";

	static final String SOURCE_SIZE_SEQ_KEY = "source_size_seq";
	static final String SOURCE_COLOR_SEQ_KEY = "source_color_seq";

	public List<DestinationColor> getDestinationColorList();

	public void saveDestinationProductOptions(ProductOptions productOptions);

	public List<DestinationSize> getDestinationSizeList();

	public List<SourceColor> getSourceColorList();

	public List<SourceSize> getSourceSizeList();

	void saveSourceProductOptionColors(List<String> colorsList);

	void saveSourceProductOptionSizes(List<String> sizesList);
}
