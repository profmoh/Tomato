package com.datazord.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.datazord.service.Service;

public class ServiceImpl implements Service {
	
	@Autowired
	private TomatoCategoriesServiceImpl tomatoCategoriesServiceImpl;

	
}
