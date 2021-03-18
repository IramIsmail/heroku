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
import com.heroku.domain.User;
import com.heroku.domain.UserContacts;
import com.heroku.service.UserContactService;

@RestController
@RequestMapping("/contacts")
public class UserContactsController {

	@Autowired
	UserContactService userContactService;

	/************************* Add Contact *********************************/

	@RequestMapping(value = "/{userId}/{contactId}", method = RequestMethod.POST)
	public ResponseEntity<?> save_contacts(@PathVariable("userId") String userId,
			@PathVariable("contactId") String contactId, HttpServletRequest req) {
		try {

			UserContacts userContact = new UserContacts();
			userContact.setContactsId(contactId);
			userContactService.saveUserContacts(userContact, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	/*****************************
	 * Delete particular Contact in a contact List
	 *******************************/

	@RequestMapping(value = "/deletecontact/", method = RequestMethod.POST)
	public ResponseEntity<?> delete_contact(@RequestBody UserContacts userContact) {
		if (userContactService.delete(userContact))
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		else
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}

	/*************************
	 * Getting List Of Contacts
	 **************************/

	@RequestMapping(value = "/contactlist/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> contact_list(@PathVariable("userId") String userId) {
		List<User> usrs = new ArrayList<>();
		try {
			usrs = userContactService.getContactList(userId);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<User>>(usrs, HttpStatus.OK);

	}

}
