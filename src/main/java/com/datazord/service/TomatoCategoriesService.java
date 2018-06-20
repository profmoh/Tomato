package com.datazord.service;

import java.util.List;

import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.model.destination.DestinationCategories;

public interface TomatoCategoriesService extends BaseService<DestinationCategories> {

	static final String TOMATO_CATEGORIES_SEQ_KEY = "tomato_categories_seq";
	
    List<DestinationCategories>	getDestinationCategoryList();
	
	void saveCategories(List<Category> categories);
}
