package com.datazord.service.Impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.datazord.enums.Language;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.model.destination.DestinationCategories;
import com.datazord.model.source.SourceCategories;
import com.datazord.repositories.DestinationCategoriesRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.SourceCategoriesRepository;
import com.datazord.service.CategoriesService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CategoriesServiceImpl implements CategoriesService {

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);

	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private DestinationCategoriesRepository destinationCategoriesRepository;

	@Autowired
	private SourceCategoriesRepository sourceCategoriesRepository;

	@Override
	public Flux<DestinationCategories> findAll() {
		return destinationCategoriesRepository.findAll();
	}

	@Override
	public Flux<DestinationCategories> findAll(Sort sort) {
		return destinationCategoriesRepository.findAll(sort);
	}

	@Override
	public Mono<DestinationCategories> findById(String id) {
		return destinationCategoriesRepository.findById(id);
	}

	@Override
	public Mono<DestinationCategories> create(DestinationCategories t) {
		t.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));

		return destinationCategoriesRepository.save(t);
	}

	@Override
	public Mono<DestinationCategories> update(DestinationCategories t) {
		return destinationCategoriesRepository.save(t);
	}

	@Override
	public Mono<Void> delete(DestinationCategories t) {
		return destinationCategoriesRepository.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return destinationCategoriesRepository.deleteById(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return destinationCategoriesRepository.deleteAll()/* .subscribe() */;
	}

	@Override
	public Flux<DestinationCategories> createAll(Collection<DestinationCategories> tCollection) {
		tCollection.forEach(c -> c.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY)));

		return destinationCategoriesRepository.saveAll(tCollection);
	}

	@Override
	public Flux<DestinationCategories> updateAll(Collection<DestinationCategories> tCollection) {
		return destinationCategoriesRepository.saveAll(tCollection);
	}

	@Override
	public void saveDestinationCategories(List<Category> categories) {
		DestinationCategories destinationCategory;

		for (Category category : categories) {
			destinationCategory = new DestinationCategories();

			destinationCategory.setName(category.getName());
			destinationCategory.setLanguageId(Language.valueOf(Integer.parseInt(category.getLanguageId())).name());
			destinationCategory.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));

			destinationCategoriesRepository.save(destinationCategory).subscribe();
		}
	}

	@Override
	public List<DestinationCategories> getDestinationCategoryList() {
		try {
			Flux<DestinationCategories> flux = destinationCategoriesRepository.findByLanguageId("en");
			List<DestinationCategories> destinationCategories = flux.collectList().block();

			return destinationCategories;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public List<SourceCategories> getSourceCategoryList() {
		try {
			Flux<SourceCategories> flux = sourceCategoriesRepository.findByLanguageId("en");
			List<SourceCategories> sourceCategories = flux.collectList().block();

			return sourceCategories;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public void saveSourceCategories(List<String> categoriesList) {
		List<SourceCategories> sourceCategoriesList = categoriesList
				.stream()
				.map(category -> new SourceCategories(sequenceRepositorys.getNextSequenceId(SOURCE_CATEGORIES_SEQ_KEY), category, "1"))
				.collect(Collectors.toList());

		sourceCategoriesRepository.deleteAll().subscribe();
		sourceCategoriesRepository.saveAll(sourceCategoriesList).subscribe();
	}
}
