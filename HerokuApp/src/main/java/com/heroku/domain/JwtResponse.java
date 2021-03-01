package com.heroku.domain;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	
	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;
	private final String name;
	
	public JwtResponse(String jwttoken,String name) {
		this.token = jwttoken;	
		this.name=name;
	}

	public String getToken() {
		return this.token;
	}

	public String getName() {
		return name;
	}

	

}
