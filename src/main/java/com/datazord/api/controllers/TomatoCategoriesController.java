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
			try {
				categoriesService.saveDestinationCategories(categories);
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
				return "Failed";
			}

		return "Success";
	}

	@GetMapping("/Source")
	public String saveSourceCategories(Model model) {
//		try {
//			apiService.saveSourceCategories();
//		} catch (MissedMappingException | IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
//			e.printStackTrace();
//			return "Failed";
//		}

		return "Success";
	}

	@GetMapping("/addProduct")
	private String addProduct() {
//		apiService.saveProductListToAPI(TomatoConstants.xmlFilePath, null);
		return "Success";
	}
}
