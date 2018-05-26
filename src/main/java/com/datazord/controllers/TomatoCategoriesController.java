package com.datazord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.model.TomatoCategories;
import com.datazord.service.TomatoCategoriesService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({ "/api/tomatocategories" })
public class TomatoCategoriesController {

	private static final Sort SORT_BY_CREATED_DATE = new Sort(Direction.DESC, "createdDate");

	@Autowired
	private TomatoCategoriesService service;

	@GetMapping(value = "/findAll")
	public Flux<TomatoCategories> findAll() {
		return service.findAll(SORT_BY_CREATED_DATE);
	}

	@GetMapping
	public Flux<TomatoCategories> findCategories(Model model) {
		return service.findAll(SORT_BY_CREATED_DATE);
	}

	@GetMapping("/{id}")
	public Mono<TomatoCategories> get(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@PutMapping("/{id}")
	public Mono<TomatoCategories> update(@PathVariable("id") String id, @RequestBody TomatoCategories t) {
		return service.findById(id).map(c -> {
			c.setName(t.getName());
			c.setDescription(t.getDescription());

			return c;
		}).flatMap(c -> service.update(c));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return service.deleteById(id);
	}

}
