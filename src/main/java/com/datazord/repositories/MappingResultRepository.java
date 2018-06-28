package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.MappingResult;

import reactor.core.publisher.Flux;

@Repository
public interface MappingResultRepository extends ReactiveMongoRepository<MappingResult,String> {

	Flux<MappingResult> findBySourceId(String sourceId);
}
