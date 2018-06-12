package com.datazord.api.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.json.tomato.pojo.categories.Category;

@RestController
@RequestMapping({"/api/categories"})
public class TomatoCategoriesController {

	@Autowired
	private TomatoServiceImpl apiService;

	@GetMapping
	public Collection<Category> findCategories(Model model) {
		return apiService.findCategories();
	}
}
