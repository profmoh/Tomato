package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.destination.DestinationProduct;

@Repository
public interface DestinationProductRepository extends ReactiveMongoRepository<DestinationProduct,String> {

}
