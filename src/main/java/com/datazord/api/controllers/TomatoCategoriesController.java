package com.datazord.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.service.CategoriesService;
import com.datazord.utils.Utils;

@RestController
@RequestMapping({ "/api/categories" })
public class TomatoCategoriesController {

	@Autowired
	private TomatoServiceImpl apiService;

	@Autowired
	private CategoriesService categoriesService;

	@GetMapping
	public String findCategories(Model model) {
		List<Category> categories = apiService.findCategories();

		if (!Utils.isEmptyCollection(categories))
			categoriesService.saveDestinationCategories(categories);

		return "Success";
	}

	@GetMapping("/Source")
	public String saveSourceCategories(Model model) {
		apiService.saveSourceCategories();

		return "Success";
	}
}
