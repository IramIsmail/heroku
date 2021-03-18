package com.heroku.domain;

import java.util.List;

public class UserContacts {
	
	Long id;
	
	String contactsId;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContactsId() {
		return contactsId;
	}
	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}
	@Override
	public String toString() {
		return "UserContacts [contactsId=" + contactsId + "]";
	}
	
	
	
	

}
