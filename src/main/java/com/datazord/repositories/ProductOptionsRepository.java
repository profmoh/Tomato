package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.ProductOptionsModel;

@Repository
public interface ProductOptionsRepository extends ReactiveMongoRepository<ProductOptionsModel, String> {

}
