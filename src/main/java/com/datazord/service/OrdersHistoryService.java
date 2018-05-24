package com.datazord.service;

import org.springframework.data.domain.Pageable;

import com.datazord.model.OrdersHistory;

import reactor.core.publisher.Flux;

public interface OrdersHistoryService extends BaseService<OrdersHistory> {

	static final String ORDERS_HISTORY_SEQ_KEY = "orders_history_seq";

	Flux<OrdersHistory> findAllPageable(Pageable pageable);

}
