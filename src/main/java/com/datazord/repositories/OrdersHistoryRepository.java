package com.datazord.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.datazord.model.OrdersHistory;

import reactor.core.publisher.Flux;

@Repository
public interface OrdersHistoryRepository extends ReactiveMongoRepository<OrdersHistory, String> {

	Flux<OrdersHistory> findAllBy(Pageable pageable);
}
