package com.datazord.service;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.datazord.model.BaseModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService <T extends BaseModel> {

	Flux<T> findAll();

	Flux<T> findAll(Sort sort);

	Mono<T> findById(String id);

	Mono<T> create(T t);

	Mono<T> update(T t);

	Mono<Void> delete(T t);

	Mono<Void> deleteAll();

	Mono<Void> deleteById(String id);

	Flux<T> createAll(Collection<T> tCollection);

	Flux<T> updateAll(Collection<T> tCollection);

}
