package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.destination.DestinationColor;

import reactor.core.publisher.Flux;

@Repository
public interface DestinationColorRepository extends ReactiveMongoRepository<DestinationColor, String> {

	public Flux<DestinationColor> findByLanguageId(String languageId);
}
