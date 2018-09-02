package com.datazord.service.Impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.datazord.dto.ReconciliationHolder;
import com.datazord.enums.Language;
import com.datazord.enums.MappingType;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.model.destination.DestinationCategories;
import com.datazord.model.source.SourceCategories;
import com.datazord.repositories.DestinationCategoriesRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.SourceCategoriesRepository;
import com.datazord.service.CategoriesService;
import com.datazord.service.MappingService;
import com.datazord.utils.Reconciliation;

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

	@Autowired
	private MappingService mappingService;

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
	public void saveDestinationCategories(List<Category> categories) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		Map<String, DestinationCategories> categoryMapResult = new HashMap<>();

		for (Category category : categories) {
			DestinationCategories destinationCategory = new DestinationCategories();

			destinationCategory.setName(category.getName());
			destinationCategory.setLanguageId(Language.valueOf(category.getLanguageId()).name());
			destinationCategory.setId(sequenceRepositorys.getNextSequenceId(TOMATO_CATEGORIES_SEQ_KEY));

			categoryMapResult.put(destinationCategory.getName() + "." + destinationCategory.getLanguageId(), destinationCategory);
		}

		List<ReconciliationHolder> disSizeListReconciliation =
				categoryMapResult.values()
				.stream()
				.map(p -> new ReconciliationHolder(p.getId(), p.getName(), p.getLanguageId()))
				.collect(Collectors.toList());

		List<DestinationCategories> mainCategoryList = destinationCategoriesRepository.findAll().collectList().block();

		List<ReconciliationHolder> mainCategoryListReconciliation =
				mainCategoryList
				.stream()
				.map(c -> new ReconciliationHolder(c.getId(), c.getName(), c.getLanguageId()))
				.collect(Collectors.toList());

		List<ReconciliationHolder> reconciliationResult =
				Reconciliation.compare(mainCategoryListReconciliation, disSizeListReconciliation, Arrays.asList("name", "language"));

		for (ReconciliationHolder reconciliationHolder : reconciliationResult) {
			switch (reconciliationHolder.reconciliationResult) {
			case added:
				destinationCategoriesRepository.save(categoryMapResult.get(reconciliationHolder.name + "." + reconciliationHolder.language)).subscribe();
				break;
			case removed:
				destinationCategoriesRepository.deleteById(reconciliationHolder.mainId).subscribe();
				mappingService.deleteMappingResult(
						Arrays.asList(mappingService.findMappingResultByDestinationIdAndMappingType(reconciliationHolder.id, MappingType.category)));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public List<DestinationCategories> getDestinationCategoryList() {
		try {
			Flux<DestinationCategories>flux = destinationCategoriesRepository.findByLanguageId("en");
			List<DestinationCategories> destinationCategories = flux.collectList().block();

			return destinationCategories;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public List<SourceCategories> getSourceCategoryList(){ 
		try {
			Flux<SourceCategories> flux = sourceCategoriesRepository.findAll();
			List<SourceCategories> sourceCategories = flux.collectList().block();

			return sourceCategories;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public void saveSourceCategories(List<String> categoriesList) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		Map<String, SourceCategories> sourceCategoryMap = categoriesList
				.stream()
				.collect(Collectors.toMap(c -> c + "." + Language.en.name(),
						c -> new SourceCategories(sequenceRepositorys.getNextSequenceId(SOURCE_CATEGORIES_SEQ_KEY), c, Language.en.name())));

		List<ReconciliationHolder> disCategoryListReconciliation =
				sourceCategoryMap.values()
				.stream()
				.map(s -> new ReconciliationHolder(s.getId(), s.getName(), s.getLanguageId()))
				.collect(Collectors.toList());

		List<SourceCategories> mainCategoryList = sourceCategoriesRepository.findAll().collectList().block();

		List<ReconciliationHolder> mainCategoryListReconciliation =
				mainCategoryList.stream().map(s -> new ReconciliationHolder(s.getId(), s.getName(), s.getLanguageId())).collect(Collectors.toList());

		List<ReconciliationHolder> reconciliationResult =
				Reconciliation.compare(mainCategoryListReconciliation, disCategoryListReconciliation, Arrays.asList("name", "language"));

		for (ReconciliationHolder reconciliationHolder : reconciliationResult) {
			switch (reconciliationHolder.reconciliationResult) {
			case added:
				sourceCategoriesRepository.save(sourceCategoryMap.get(reconciliationHolder.name + "." + reconciliationHolder.language)).subscribe();
				break;
			case removed:
				sourceCategoriesRepository.deleteById(reconciliationHolder.mainId).subscribe();
				mappingService.deleteMappingResult(
						mappingService.findMappingResultBySourceIdAndMappingType(reconciliationHolder.id, MappingType.category));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Map<String, String> getSourceCategoriesMap() {
		try {
			List<SourceCategories> sourceCategoriesList = getSourceCategoryList();

			Map<String, String> sourceCategoriesMap = sourceCategoriesList
					.stream()
					.collect(Collectors.toMap(SourceCategories::getId, SourceCategories::getName));

			return sourceCategoriesMap;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public Map<String, String> getDestinationCategoriesMap() {
		try {
			List<DestinationCategories> destinationCategoriesList = getDestinationCategoryList();

			Map<String, String> destinationCategoriesMap = destinationCategoriesList
					.stream()
					.collect(Collectors.toMap(DestinationCategories::getId, DestinationCategories::getName));

			return destinationCategoriesMap;
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	@Override
	public DestinationCategories getDestinationCategoryById(String id) {
		try {
			Mono<DestinationCategories>mono=destinationCategoriesRepository.findById(id);
			DestinationCategories destinationCategory= new DestinationCategories();
			destinationCategory=mono.block();
			return destinationCategory;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
