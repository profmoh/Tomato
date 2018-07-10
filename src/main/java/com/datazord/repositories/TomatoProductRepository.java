package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.TomatoProduct;

import reactor.core.publisher.Flux;

@Repository
public interface TomatoProductRepository extends ReactiveMongoRepository<TomatoProduct, String> {

	Flux<TomatoProduct> findBySku(String sku);
} 
