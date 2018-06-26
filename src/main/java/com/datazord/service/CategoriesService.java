package com.datazord.service;

import java.util.List;
import java.util.Map;

import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.model.destination.DestinationCategories;
import com.datazord.model.source.SourceCategories;

public interface CategoriesService extends BaseService<DestinationCategories> {

	static final String TOMATO_CATEGORIES_SEQ_KEY = "tomato_categories_seq";

	static final String SOURCE_CATEGORIES_SEQ_KEY = "source_categories_seq";

	void saveDestinationCategories(List<Category> categories);

	List<DestinationCategories> getDestinationCategoryList();

	List<SourceCategories> getSourceCategoryList();

	void saveSourceCategories(List<String> categoriesList);

	public Map<String, String> getSourceCategoriesMap();

	public Map<String, String> getDestinationCategoriesMap();
}
