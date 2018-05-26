package com.datazord.service.Impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.datazord.model.User;
import com.datazord.repositories.SequenceRepository;
import com.datazord.repositories.UserRepository;
import com.datazord.service.UsersService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserServiceImpl implements UsersService {

	@Autowired
	private SequenceRepository sequenceRepositorys;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Flux<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public Mono<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Mono<User> create(User t) {
		t.setId(sequenceRepositorys.getNextSequenceId(ORDERS_HISTORY_SEQ_KEY));

		return userRepository.save(t);
	}

	@Override
	public Mono<User> update(User t) {
		return userRepository.save(t);
	}

	@Override
	public Mono<Void> delete(User t) {
		return userRepository.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return userRepository.deleteById(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return userRepository.deleteAll();
	}

	@Override
	public Flux<User> createAll(Collection<User> tCollection) {
		tCollection.forEach(c -> c.setId(sequenceRepositorys.getNextSequenceId(ORDERS_HISTORY_SEQ_KEY)));

		return userRepository.saveAll(tCollection);
	}

	@Override
	public Flux<User> updateAll(Collection<User> tCollection) {
		return userRepository.saveAll(tCollection);
	}

}
