package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.MappingResult;

@Repository
public interface MappingResultRepository extends ReactiveMongoRepository<MappingResult,String> {

}
