package com.example.modal;

import java.util.Collection;

public class UserDetail {
	private String username;
	private String password;
	private boolean enabled;
	private Collection<String> roles;
	
	public UserDetail () {
		
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
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Collection<String> getRoles() {
		return roles;
	}
	
	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

}
