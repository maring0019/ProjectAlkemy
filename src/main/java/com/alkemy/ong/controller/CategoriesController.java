package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.service.impl.CategoriesServiceImpl;

@RestController
@RequestMapping(path = "/categories")
public class CategoriesController {

	@Autowired
	private CategoriesServiceImpl service;

}
