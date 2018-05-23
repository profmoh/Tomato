package com.datazord.api.tomatocart;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.json.tomato.pojo.categories.Category;

@RestController
@RequestMapping({"/categories"})
public class TomatoController {

	@Autowired
	private TomatoServiceImpl apiService;

	@GetMapping
	public Collection<Category> findCategories(Model model) {
		return apiService.findCategories();
	}
}
