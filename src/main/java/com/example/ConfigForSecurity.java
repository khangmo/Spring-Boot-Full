package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.example.common.Constant;

import java.util.Arrays;

import javax.sql.DataSource;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ConfigForSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private DataSource datasource;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login*", "/share*", "/facebook*").permitAll()
                .antMatchers("/home","/home/*", "/greet", "/greet/*", "/user-manager", "/user-manager/*",
                		"/update-user", "/update-user/*", "create-user", "create-user/*", 
                		"/authen", "/authen/*").authenticated()
                .and()
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/home")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("remember-me")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe();
        http.httpBasic();
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(datasource);
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
        auth.jdbcAuthentication().dataSource(datasource);
    }
    
    /**
     * This function is creating an user. the user will be used to Unit Test for all controller.
     */
    @Bean
	public UserDetailsService userDetailsServiceRoleAdminTest(){
		GrantedAuthority authority = new SimpleGrantedAuthority(Constant.ROLES.ROLE_ADMIN);
		UserDetails userDetails = (UserDetails)new User(Constant.UNIT_TEST.USER_TEST, 
				Constant.UNIT_TEST.PASSWORD_TEST, Arrays.asList(authority));
		return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
	}
}
