package com.example.user.imlement;

import java.util.Collection;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.modal.UserCreateForm;
import com.example.modal.UserDetail;

public interface UserImplement {
	public void createNewUser(UserCreateForm userCreateForm) throws Exception;
	
	public void deleteUserByUsername(String username) throws Exception;
	
	public void rollbackUserByUsername(String username) throws Exception;
	
	public Collection<UserDetail> getAllUsers() throws Exception;
	
	public Collection<UserDetail> getAllUsers(int currentPage) throws Exception;
	
	public long countingUser() throws Exception;
	
	public UserDetails getUserByUsername(String username) throws UsernameNotFoundException;
	
	public void updateUser(String currentPassword, String newPassword) throws AuthenticationException;
}
