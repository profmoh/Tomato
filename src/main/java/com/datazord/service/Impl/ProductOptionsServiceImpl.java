package com.datazord.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.constants.TomatoConstants;
import com.datazord.enums.Language;
import com.datazord.json.tomato.pojo.ProductOptions.OptionValue;
import com.datazord.json.tomato.pojo.ProductOptions.OptionValueDescription;
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.model.destination.DestinationColor;
import com.datazord.model.destination.DestinationSize;
import com.datazord.model.source.SourceColor;
import com.datazord.model.source.SourceSize;
import com.datazord.repositories.DestinationColorRepository;
import com.datazord.repositories.DestinationSizeRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.SourceColorRepository;
import com.datazord.repositories.SourceSizeRepository;
import com.datazord.service.ProductOptionsService;

import reactor.core.publisher.Flux;

@Component
public class ProductOptionsServiceImpl implements ProductOptionsService {

	private static final Logger logger = LoggerFactory.getLogger(ProductOptionsServiceImpl.class);
	
	@Autowired
	private TomatoServiceImpl apiService;
	
	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private DestinationSizeRepository destinationSizeRepository;

	@Autowired
	private DestinationColorRepository destinationColorRepository;

	@Autowired
	private SourceColorRepository sourceColorRepository;

	@Autowired
	private SourceSizeRepository sourceSizeRepository;

	@Override
	public void saveDestinationProductOptions(Integer optionID) {
		DestinationColor color;
		DestinationSize size;
		try {
			ProductOptions productOptions = findProductOptionsValue(optionID);
			logger.info("response from findProductOptionsValue >>Success=" + productOptions.getSuccess() + " >> error="
					+ productOptions.getError());
			if (productOptions.getSuccess() == 1) {
				for (OptionValue optionValue : productOptions.getData().getOption_values()) {
					for (Map.Entry<String, OptionValueDescription> entry : optionValue.getOptionValueDescription()
							.entrySet()) {
						if (productOptions.getData().getOption_id().equals(TomatoConstants.COLOR_PRODUCT_OPTION)) {
							color = new DestinationColor();
							Language language = null;
							color.setName(entry.getValue().getName());
							color.setLanguageId(
									language.valueOf(Integer.parseInt(entry.getValue().getLanguage_id())).name());
							color.setId(sequenceRepositorys.getNextSequenceId(DESTINATION_COLOR_SEQ_KEY));

							destinationColorRepository.save(color).subscribe();
						} else if (productOptions.getData().getOption_id()
								.equals(TomatoConstants.SIZE_PRODUCT_OPTION)) {
							size = new DestinationSize();
							size.setName(entry.getValue().getName());
							Language language = null;
							size.setLanguageId(
									language.valueOf(Integer.parseInt(entry.getValue().getLanguage_id())).name());
							size.setId(sequenceRepositorys.getNextSequenceId(DESTINATION_SIZE_SEQ_KEY));

							destinationSizeRepository.save(size).subscribe();
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("",e);
		}
	}
		

	@Override
	public List<DestinationColor> getDestinationColorList() {
		try {
			Flux<DestinationColor> flux = destinationColorRepository.findByLanguageId("en");
			List<DestinationColor> destinationColors = flux.collectList().block();

			return destinationColors;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public List<DestinationSize> getDestinationSizeList() {
		try {
			Flux<DestinationSize> flux = destinationSizeRepository.findByLanguageId("en");
			List<DestinationSize> destinationSizes = flux.collectList().block();

			return destinationSizes;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public List<SourceColor> getSourceColorList() {
		try {
			Flux<SourceColor> flux = sourceColorRepository.findByLanguageId("en");
			List<SourceColor> sourceColors = flux.collectList().block();

			return sourceColors;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public List<SourceSize> getSourceSizeList() {
		try {
			Flux<SourceSize> flux = sourceSizeRepository.findByLanguageId("en");
			List<SourceSize> sourceSizes = flux.collectList().block();
			return sourceSizes;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public void saveSourceProductOptionColors(List<String> colorsList) {
		List<SourceColor> sourceColorsList = colorsList
				.stream()
				.map(color -> new SourceColor(sequenceRepositorys.getNextSequenceId(SOURCE_SIZE_SEQ_KEY), color, "1"))
				.collect(Collectors.toList());	
	
		sourceColorRepository.deleteAll().subscribe();
		sourceColorRepository.saveAll(sourceColorsList).subscribe();
	}

	@Override
	public void saveSourceProductOptionSizes(List<String> sizesList) {
		List<SourceSize> sourceSizesList = sizesList
				.stream()
				.map(size -> new SourceSize(sequenceRepositorys.getNextSequenceId(SOURCE_SIZE_SEQ_KEY), size, "1"))
				.collect(Collectors.toList());

		sourceSizeRepository.deleteAll().subscribe();
		sourceSizeRepository.saveAll(sourceSizesList).subscribe();
	}

	private ProductOptions findProductOptionsValue(Integer optionID){
		return apiService.findProductOptionsValue(optionID);
	}

}
