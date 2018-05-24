package com.datazord.service.Impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.datazord.model.TomatoCategories;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.TomatoCategoriesRepository;
import com.datazord.service.TomatoCategoriesService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TomatoCategoriesServiceImpl implements TomatoCategoriesService {

	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private TomatoCategoriesRepository tomatoCategoriesRepository;

	@Override
	public Flux<TomatoCategories> findAll() {
		return tomatoCategoriesRepository.findAll();
	}

	@Override
	public Flux<TomatoCategories> findAll(Sort sort) {
		return tomatoCategoriesRepository.findAll(sort);
	}

	@Override
	public Mono<TomatoCategories> findById(String id) {
		return tomatoCategoriesRepository.findById(id);
	}

	@Override
	public Mono<TomatoCategories> create(TomatoCategories t) {
		t.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));

		return tomatoCategoriesRepository.save(t);
	}

	@Override
	public Mono<TomatoCategories> update(TomatoCategories t) {
		return tomatoCategoriesRepository.save(t);
	}

	@Override
	public Mono<Void> delete(TomatoCategories t) {
		return tomatoCategoriesRepository.delete(t);

////	Mono<TomatoCategories> tomatoCategory = findById(id);
//
////	if (tomatoCategory != null)
//	return tomatoCategoriesRepository.delete(tomatoCategory);
//
////	return tomatoCategory;
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return tomatoCategoriesRepository.deleteById(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return tomatoCategoriesRepository.deleteAll()/*.subscribe()*/;
	}

	@Override
	public Flux<TomatoCategories> createAll(Collection<TomatoCategories> tCollection) {
		tCollection.forEach(c -> c.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY)));

		return tomatoCategoriesRepository.saveAll(tCollection);
	}

	@Override
	public Flux<TomatoCategories> updateAll(Collection<TomatoCategories> tCollection) {
		return tomatoCategoriesRepository.saveAll(tCollection);
	}

}
