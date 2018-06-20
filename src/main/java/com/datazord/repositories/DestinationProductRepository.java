package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.DestinationProduct;

@Repository
public interface DestinationProductRepository extends ReactiveMongoRepository<DestinationProduct,String> {

}
