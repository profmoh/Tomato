package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.destination.DestinationProduct;

import reactor.core.publisher.Mono;

@Repository
public interface DestinationProductRepository extends ReactiveMongoRepository<DestinationProduct, String> {

	public Mono<DestinationProduct> findByName(String name);
}
