package com.datazord.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.api.tomatocart.TomatoServiceImpl;
import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.service.Service;
import com.datazord.service.Impl.ServiceImpl;

@RestController
@RequestMapping({"/api"})
public class Controller {

	@Autowired
	private Service service;

	@GetMapping
	public Collection<Category> findCategories(Model model) {
		return null;
//		return apiService.findCategories();
	}
}
