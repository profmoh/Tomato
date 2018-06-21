package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.datazord.model.source.SourceSize;

import reactor.core.publisher.Flux;

public interface SourceSizeRepository extends ReactiveMongoRepository<SourceSize, String> {

	Flux<SourceSize> findByLanguageId(String languageId);
}
