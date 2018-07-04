package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.CompanyConfiguration;

import reactor.core.publisher.Flux;

@Repository
public interface CompanyConfigurationRepository extends ReactiveMongoRepository<CompanyConfiguration, String> {

	Flux<CompanyConfiguration> findByCompanyId(String companyId);
}
