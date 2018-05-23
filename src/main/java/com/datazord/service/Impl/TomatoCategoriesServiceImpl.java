package com.datazord.service.Impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.datazord.model.TomatoCategories;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.TomatoCategoriesRepository;
import com.datazord.service.TomatoCategoriesService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TomatoCategoriesServiceImpl implements TomatoCategoriesService {

	private static final String TOMATO_CATEGORIES_SEQ_KEY = "tomato_categories_seq";

	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private TomatoCategoriesRepository tomatoCategoriesRepository;

	@Override
	public Mono<TomatoCategories> create(TomatoCategories tomatoCategory) {
		tomatoCategory.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));

		return tomatoCategoriesRepository.save(tomatoCategory);
	}

	@Override
	public Flux<TomatoCategories> createAll(Collection<TomatoCategories> tomatoCategoryCollection) {
		tomatoCategoryCollection.forEach(c -> c.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY)));

		return tomatoCategoriesRepository.saveAll(tomatoCategoryCollection);
	}

	@Override
	public Mono<Void> delete(TomatoCategories tomatoCategory) {
//		Mono<TomatoCategories> tomatoCategory = findById(id);

//		if (tomatoCategory != null)
		return tomatoCategoriesRepository.delete(tomatoCategory);

//		return tomatoCategory;
	}

	@Override
	public Mono<Void> deleteAll() {
		return tomatoCategoriesRepository.deleteAll()/*.subscribe()*/;
	}

	@Override
	public Flux<TomatoCategories> findAll() {
		return tomatoCategoriesRepository.findAll();
	}

	@Override
	public Mono<TomatoCategories> findById(long id) {
		return tomatoCategoriesRepository.findById("" + id);
	}

	@Override
	public Mono<TomatoCategories> update(TomatoCategories tomatoCategory) {
		return tomatoCategoriesRepository.save(tomatoCategory);
	}

	@Override
	public Flux<TomatoCategories> updateAll(Collection<TomatoCategories> tomatoCategoryCollection) {
		return tomatoCategoriesRepository.saveAll(tomatoCategoryCollection);
	}
}
