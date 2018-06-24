package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.datazord.model.source.SourceColor;

import reactor.core.publisher.Flux;

public interface SourceColorRepository extends ReactiveMongoRepository<SourceColor, String> {

	Flux<SourceColor> findByLanguageId(String languageId);
}
