package com.datazord.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.datazord.model.TomatoCategories;

public interface TomatoCategoriesRepository extends ReactiveMongoRepository<TomatoCategories, String> {
	
	
	
//    Flux<Product> findByName(String name);
//
//    Flux<Product> findByName(Mono<String> name);
//
//    Mono<Product> findByNameAndImageUrl(Mono<String> name, String imageUrl);
//
//    @Query("{ 'name': ?0, 'imageUrl': ?1}")
//    Mono<Product> findByNameAndImageUrl(String name, String imageUrl);
}
