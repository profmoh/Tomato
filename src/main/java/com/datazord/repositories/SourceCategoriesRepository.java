package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.datazord.model.source.SourceCategories;

import reactor.core.publisher.Flux;

public interface SourceCategoriesRepository extends ReactiveMongoRepository<SourceCategories, String> {

	Flux<SourceCategories> findByLanguageId(String languageId);
}
