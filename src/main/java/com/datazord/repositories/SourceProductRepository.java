package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.datazord.model.source.SourceProduct;

public interface SourceProductRepository extends ReactiveMongoRepository<SourceProduct, String> {

}
