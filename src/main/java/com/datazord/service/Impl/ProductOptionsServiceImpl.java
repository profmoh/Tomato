package com.datazord.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.json.tomato.pojo.ProductOptions.ProductOptions;
import com.datazord.model.ProductOptionsModel;
import com.datazord.model.TomatoCategories;
import com.datazord.repositories.ProductOptionsRepository;
import com.datazord.repositories.TomatoCategoriesRepository;
import com.datazord.service.ProductOptionsService;

import reactor.core.publisher.Flux;

@Component
public class ProductOptionsServiceImpl implements ProductOptionsService{

	@Autowired
	ProductOptionsRepository productOptionsRepository;
	
	@Autowired
	private TomatoCategoriesServiceImpl categoriesServiceImpl;
	
	@Override
	public void saveProductOptions(ProductOptions productOptions) {
		ProductOptionsModel optionsModel=new ProductOptionsModel();
		optionsModel.setOption_id(productOptions.getData().getOption_id());
		optionsModel.setName(productOptions.getData().getName());
		optionsModel.setOption_values(productOptions.getData().getOption_values());
		productOptionsRepository.save(optionsModel).subscribe();
	}

}
