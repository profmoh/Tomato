package com.datazord.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.json.tomato.pojo.ProductOptions.OptionValue;
import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.model.ProductOptionsModel;
import com.datazord.repositories.ProductOptionsRepository;
import com.datazord.service.ProductOptionsService;

@Component
public class ProductOptionsServiceImpl implements ProductOptionsService{

	private static final Logger logger = LoggerFactory.getLogger(ProductOptionsServiceImpl.class);
	
	@Autowired
	ProductOptionsRepository productOptionsRepository;
	
	@Override
	public void saveProductOptions(ProductOptions productOptions) {
		ProductOptionsModel optionsModel;
		logger.info("Insert product option to DB  with id ="+productOptions.getData().getOption_id());
		for (OptionValue optionValue : productOptions.getData().getOption_values()) {
			optionsModel=new ProductOptionsModel();
			optionsModel.setOption_id(productOptions.getData().getOption_id());
			optionsModel.setOption_value_id(optionValue.getOption_value_id());
			optionsModel.getOptionValueDescriptions().addAll((optionValue.getOptionValueDescription().values()));
			optionsModel.setImage(optionValue.getImage());
			optionsModel.setThumb(optionValue.getThumb());
			optionsModel.setSort_order(optionValue.getSort_order());
		
			productOptionsRepository.save(optionsModel).subscribe();
		}
	}

}
