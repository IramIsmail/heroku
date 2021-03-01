package com.heroku.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.domain.Message;
import com.heroku.domain.UserContacts;
import com.heroku.service.UserContactService;

@RestController
@RequestMapping("/contacts")
public class UserContactsController {
	
	@Autowired
	UserContactService userContactService;


	
	
	
	
	@RequestMapping(value="/{userId}/{contactId}",method = RequestMethod.GET)
	public ResponseEntity<?> save_contacts(@PathVariable("userId") String userId,@PathVariable("contactId") String contactId, HttpServletRequest req) {
		try {
			
			UserContacts user=new UserContacts();
			user.setContactsId(Arrays.asList(contactId));
			userContactService.saveUserContacts(user,userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
