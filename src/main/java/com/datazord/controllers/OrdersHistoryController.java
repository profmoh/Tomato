package com.datazord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datazord.model.OrdersHistory;
import com.datazord.service.OrdersHistoryService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping({ "/api/ordershistory" })
public class OrdersHistoryController {

	private static final int RESULTS_PER_PAGE = 20;
	private static final Sort SORT_BY_CREATED_DATE = new Sort(Direction.DESC, "createdDate");

	@Autowired
	private OrdersHistoryService service;

	@GetMapping(value = "/findAll")
	public Flux<OrdersHistory> findAll() {
		return service.findAll(SORT_BY_CREATED_DATE);
	}

	@GetMapping
	Flux<OrdersHistory> findAllPaginning(@RequestParam(value = "page", defaultValue = "0", required = false) int page) {
		return service.findAllPageable(PageRequest.of(page, RESULTS_PER_PAGE, SORT_BY_CREATED_DATE));
	}
}
