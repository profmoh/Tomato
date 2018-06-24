package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.destination.DestinationCategories;

import reactor.core.publisher.Flux;

@Repository
public interface DestinationCategoriesRepository extends ReactiveMongoRepository<DestinationCategories, String> {

	Flux<DestinationCategories> findByLanguageId(String languageId);
}
