package com.datazord.service.Impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.datazord.model.OrdersHistory;
import com.datazord.repositories.OrdersHistoryRepository;
import com.datazord.repositories.SequenceRepository;
import com.datazord.service.OrdersHistoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OrdersHistoryServiceImpl implements OrdersHistoryService {

	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private OrdersHistoryRepository ordersHistoryRepository;

	@Override
	public Flux<OrdersHistory> findAll() {
		return ordersHistoryRepository.findAll();
	}

	@Override
	public Flux<OrdersHistory> findAll(Sort sort) {
		return ordersHistoryRepository.findAll(sort);
	}

	@Override
	public Mono<OrdersHistory> findById(String id) {
		return ordersHistoryRepository.findById(id);
	}

	@Override
	public Flux<OrdersHistory> findAllPageable(Pageable pageable) {
		return ordersHistoryRepository.findAllBy(pageable);
	}

	@Override
	public Mono<OrdersHistory> create(OrdersHistory t) {
		t.setId(sequenceRepositorys.getNextSequenceId(ORDERS_HISTORY_SEQ_KEY));

		return ordersHistoryRepository.save(t);
	}

	@Override
	public Mono<OrdersHistory> update(OrdersHistory t) {
		return ordersHistoryRepository.save(t);
	}

	@Override
	public Mono<Void> delete(OrdersHistory t) {
		return ordersHistoryRepository.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return ordersHistoryRepository.deleteById(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return ordersHistoryRepository.deleteAll();
	}

	@Override
	public Flux<OrdersHistory> createAll(Collection<OrdersHistory> tCollection) {
		tCollection.forEach(c -> c.setId(sequenceRepositorys.getNextSequenceId(ORDERS_HISTORY_SEQ_KEY)));

		return ordersHistoryRepository.saveAll(tCollection);
	}

	@Override
	public Flux<OrdersHistory> updateAll(Collection<OrdersHistory> tCollection) {
		return ordersHistoryRepository.saveAll(tCollection);
	}

}
