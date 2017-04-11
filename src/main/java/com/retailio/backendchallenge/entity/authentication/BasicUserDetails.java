package com.retailio.backendchallenge.entity.authentication;

import java.util.Arrays;

import com.retailio.backendchallenge.entity.AbstractEntity;

public class BasicUserDetails extends AbstractEntity {

	private String username;
	private String password;
	private String [] grantedAuthorities;
	
	public BasicUserDetails() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(String[] grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	@Override
	public String toString() {
		return "BasicUserDetails [username=" + username + ", password="
				+ password + ", grantedAuthorities="
				+ Arrays.toString(grantedAuthorities) + "]";
	}

}