package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.destination.DestinationColor;
import com.datazord.model.destination.DestinationSize;

import reactor.core.publisher.Flux;

@Repository
public interface DestinationSizeRepository extends ReactiveMongoRepository<DestinationSize,String>{

	public Flux<DestinationSize> findByLanguageId(String languageId);
}

