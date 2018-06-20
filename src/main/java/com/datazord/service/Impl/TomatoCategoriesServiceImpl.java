package com.datazord.service.Impl;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.datazord.enums.Language;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.model.destination.DestinationCategories;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.TomatoCategoriesRepository;
import com.datazord.service.TomatoCategoriesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TomatoCategoriesServiceImpl implements TomatoCategoriesService {
	
	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
	
	
	
    @Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private TomatoCategoriesRepository tomatoCategoriesRepository;

	@Override
	public Flux<DestinationCategories> findAll() {
		return tomatoCategoriesRepository.findAll();
	}

	@Override
	public Flux<DestinationCategories> findAll(Sort sort) {
		return tomatoCategoriesRepository.findAll(sort);
	}

	@Override
	public Mono<DestinationCategories> findById(String id) {
		return tomatoCategoriesRepository.findById(id);
	}

	@Override
	public Mono<DestinationCategories> create(DestinationCategories t) {
		t.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));

		return tomatoCategoriesRepository.save(t);
	}

	@Override
	public Mono<DestinationCategories> update(DestinationCategories t) {
		return tomatoCategoriesRepository.save(t);
	}

	@Override
	public Mono<Void> delete(DestinationCategories t) {
		return tomatoCategoriesRepository.delete(t);
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
	public Flux<DestinationCategories> createAll(Collection<DestinationCategories> tCollection) {
		tCollection.forEach(c -> c.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY)));

		return tomatoCategoriesRepository.saveAll(tCollection);
	}

	@Override
	public Flux<DestinationCategories> updateAll(Collection<DestinationCategories> tCollection) {
		return tomatoCategoriesRepository.saveAll(tCollection);
	}

	@Override
	public void saveCategories(List<Category> categories) {
		DestinationCategories destinationCategory;
		for (Category category : categories) {
			destinationCategory=new DestinationCategories();
			destinationCategory.setName(category.getName());
			Language language = null;
			destinationCategory.setLanguageId(language.valueOf(Integer.parseInt(category.getLanguageId())).name());
			destinationCategory.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));
			
			tomatoCategoriesRepository.save(destinationCategory).subscribe();
		}
	}

	@Override
	public List<DestinationCategories> getDestinationCategoryList() {
		try {
			Flux<DestinationCategories>flux=tomatoCategoriesRepository.findByLanguageId("en");
			List<DestinationCategories>destinationCategories=flux.collectList().block();
			return destinationCategories;
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
		
	}



}
