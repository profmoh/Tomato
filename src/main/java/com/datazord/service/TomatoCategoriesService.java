package com.datazord.service;

import java.util.Collection;

import com.datazord.model.TomatoCategories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TomatoCategoriesService {

	Mono<TomatoCategories> create(TomatoCategories tomatoCategory);

	Mono<Void> delete(TomatoCategories tomatoCategory);

	Flux<TomatoCategories> findAll();

	Mono<Void> deleteAll();

	Mono<TomatoCategories> findById(long id);

	Mono<TomatoCategories> update(TomatoCategories tomatoCategory);

	Flux<TomatoCategories> createAll(Collection<TomatoCategories> tomatoCategoryCollection);

	Flux<TomatoCategories> updateAll(Collection<TomatoCategories> tomatoCategoryCollection);
}
