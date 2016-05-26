package com.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;

import com.example.common.Constant;
import com.example.modal.UserCreateForm;
import com.example.modal.UserDetail;
import com.example.user.imlement.UserImplement;

@Service
public class UserService implements UserImplement {
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
    private DataSource datasource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void updateUser (String currentPassword, String newPassword) throws AuthenticationException {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
		userDetailsService.setDataSource(datasource);
		userDetailsService.changePassword(currentPassword, newPassword);
	}
	
	public UserDetails getUserByUsername(String username) throws UsernameNotFoundException {
		JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
		jdbcDaoImpl.setDataSource(datasource);
		return jdbcDaoImpl.loadUserByUsername(username);
	}
	
	public void rollbackUserByUsername (String username) throws UsernameNotFoundException {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
		userDetailsService.setDataSource(datasource);

		UserDetails userDetail = getUserByUsername(username);
        
		User user = new User (userDetail.getUsername(),userDetail.getPassword(), true, true, true, true, userDetail.getAuthorities());
		/* Get UserDetail from username */
		userDetailsService.updateUser(user);
	}
	
	public void deleteUserByUsername (String username) throws UsernameNotFoundException {
		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
		userDetailsService.setDataSource(datasource);
		
		JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
		jdbcDaoImpl.setDataSource(datasource);
		UserDetails userDetail = jdbcDaoImpl.loadUserByUsername(username);
        
		User user = new User (userDetail.getUsername(),userDetail.getPassword(), false, false, false, false, userDetail.getAuthorities());
		/* Get UserDetail from username */
		userDetailsService.updateUser(user);
	}
	
	public void createNewUser(UserCreateForm userForm) throws Exception {
		log.info("Insert new User.");

		JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(datasource);
        if(!userDetailsService.userExists(userForm.getUsername().trim())) {
        	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(userForm.getRole()));
            User userDetails = new User(userForm.getUsername(), encoder.encode(userForm.getPassword()), authorities);

            userDetailsService.createUser(userDetails);
        }
	}
	
	public long countingUser () throws Exception {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM users", Long.class);
	}
	
	public Collection<UserDetail> getAllUsers(int currentPage) throws Exception {
		log.info("Find all accounts follow page: " + currentPage);
		int start = currentPage*Constant.PAGING.SIZE_ON_PAGE;
		int offset = Constant.PAGING.SIZE_ON_PAGE;
		List<UserDetail> allUsers = jdbcTemplate.query("SELECT username, password, enabled FROM users ORDER BY username LIMIT ?, ?", 
				new Object[]{start, offset}, new RowMapper<UserDetail>() {
			public UserDetail mapRow(ResultSet result, int arg1) throws SQLException {
				UserDetail userDetail = new UserDetail();
				userDetail.setUsername(result.getString("username"));
				userDetail.setPassword(result.getString("password"));
				userDetail.setEnabled(result.getBoolean("enabled"));
				userDetail.setRoles(getAuthorities(userDetail.getUsername()));
				return userDetail;
			}
		});
		return allUsers;
	};
	
	public Collection<UserDetail> getAllUsers() throws Exception {
		log.info("Find all accounts");
		
		List<UserDetail> allUsers = jdbcTemplate.query("SELECT username, password, enabled FROM users ORDER BY username", new RowMapper<UserDetail>() {
			public UserDetail mapRow(ResultSet result, int arg1) throws SQLException {
				UserDetail userDetail = new UserDetail();
				userDetail.setUsername(result.getString("username"));
				userDetail.setPassword(result.getString("password"));
				userDetail.setEnabled(result.getBoolean("enabled"));
				userDetail.setRoles(getAuthorities(userDetail.getUsername()));
				return userDetail;
			}
		});
		return allUsers;
	}
	
	public Collection<String> getAuthorities (String username) {
		log.info("Get user's role from user: " + username);
		List<String> roles = jdbcTemplate.query("SELECT authority FROM authorities WHERE username = ?", new Object[] {username}, new RowMapper<String>(){
			public String mapRow (ResultSet result, int arg1) throws SQLException {
				return result.getString("authority");			
			}
		});
		
		return roles;
	}
}
