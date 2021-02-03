package com.heroku.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.domain.ItemDetail;
import com.heroku.service.OperationService;
import com.heroku.utils.FirebaseService;

@RestController
@RequestMapping("/api/operations")
public class OperationController {
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	FirebaseService firebaseService;
	
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody ItemDetail itemDetail, HttpServletRequest req
			) {
		System.out.println(itemDetail.getName()+"hhhhhhhhhhhhhhhhtest");
		return null;
	}
	
	
}
