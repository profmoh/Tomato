package com.datazord.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datazord.model.TomatoProduct;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.TomatoProductRepository;
import com.datazord.service.TomatoProductService;

import reactor.core.publisher.Flux;

@Component
public class TomatoProductServiceImpl implements TomatoProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(TomatoProductServiceImpl.class);
			
	@Autowired
	private TomatoProductRepository tomatoProductRepository;
	
	@Autowired
	private SequenceRepository sequenceRepository;
	
	@Override
	public TomatoProduct getProductBySku(String sku) {
		try {
			Flux<TomatoProduct> flux=tomatoProductRepository.findBySku(sku);
			TomatoProduct tomatoProduct=flux.blockFirst();
			return tomatoProduct;
		} catch (Exception e) {
			logger.error("",e); 
			return null;
		}
		
	}

	@Override
	public void saveTomatoProduct(TomatoProduct product) {
		try {
			product.setId(sequenceRepository.getNextSequenceId(TOMATO_PRODUCT_SQU));
			tomatoProductRepository.save(product).subscribe();
		} catch (Exception e) {
			logger.error("",e); 
		}
	}

}
