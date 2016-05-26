package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SecurityRequestPostProcessors {
	/**
     * Establish a security context for a user with the specified username. All
     * details are declarative and do not require that the user actually exists.
     * This means that the authorities or roles need to be specified too.
     */
    public static SecurityRequestPostProcessors.UserRequestPostProcessor user(String username) {
        return new SecurityRequestPostProcessors.UserRequestPostProcessor(username);
    }

    /**
     * Establish a security context for a user with the specified username. The
     * additional details are obtained from the {@link UserDetailsService}
     * declared in the {@link WebApplicationContext}.
     */
    public static SecurityRequestPostProcessors.UserDetailsRequestPostProcessor userDetailsService(String username) {
        return new SecurityRequestPostProcessors.UserDetailsRequestPostProcessor(username);
    }

    /**
     * Establish a security context with the given {@link SecurityContext} and
     * thus be authenticated with {@link SecurityContext#getAuthentication()}.
     * @param securityContext
     * @return 
     */
    public SecurityRequestPostProcessors.SecurityContextRequestPostProcessor securityContext(SecurityContext securityContext) {
        return new SecurityRequestPostProcessors.SecurityContextRequestPostProcessor(securityContext);
    }

    /**
     * Support class for {@link RequestPostProcessor}'s that establish a Spring
     * Security context
     */
    private static abstract class SecurityContextRequestPostProcessorSupport {

        private final SecurityContextRepository repository = new HttpSessionSecurityContextRepository();

        final void save(Authentication authentication, HttpServletRequest request) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            save(securityContext, request);
        }

        final void save(SecurityContext securityContext, HttpServletRequest request) {
            HttpServletResponse response = new MockHttpServletResponse();

            HttpRequestResponseHolder requestResponseHolder = new HttpRequestResponseHolder(request, response);
            this.repository.loadContext(requestResponseHolder);

            request = requestResponseHolder.getRequest();
            response = requestResponseHolder.getResponse();

            this.repository.saveContext(securityContext, request, response);
        }
    }

    public final static class SecurityContextRequestPostProcessor
            extends SecurityRequestPostProcessors.SecurityContextRequestPostProcessorSupport implements RequestPostProcessor {

        private final SecurityContext securityContext;

        private SecurityContextRequestPostProcessor(SecurityContext securityContext) {
            this.securityContext = securityContext;
        }

        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            save(this.securityContext, request);
            return request;
        }
    }

    public final static class UserRequestPostProcessor
            extends SecurityRequestPostProcessors.SecurityContextRequestPostProcessorSupport implements RequestPostProcessor {

        private final String username;
        private String rolePrefix = "ROLE_";
        private Object credentials;
        private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        private UserRequestPostProcessor(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
        }

        /**
         * Sets the prefix to append to each role if the role does not already
         * start with the prefix. If no prefix is desired, an empty String or
         * null can be used.
         */
        public SecurityRequestPostProcessors.UserRequestPostProcessor rolePrefix(String rolePrefix) {
            this.rolePrefix = rolePrefix;
            return this;
        }

        /**
         * Specify the roles of the user to authenticate as. This method is
         * similar to {@link #authorities(GrantedAuthority...)}, but just not as
         * flexible.
         *
         * @param roles The roles to populate. Note that if the role does not
         * start with {@link #rolePrefix(String)} it will automatically be
         * prepended. This means by default {@code roles("ROLE_USER")} and
         * {@code roles("USER")} are equivalent.
         * @see #authorities(GrantedAuthority...)
         * @see #rolePrefix(String)
         */
        public SecurityRequestPostProcessors.UserRequestPostProcessor roles(String... roles) {
            List<GrantedAuthority> _authorities = new ArrayList<GrantedAuthority>(roles.length);
            for (String role : roles) {
                if (this.rolePrefix == null || role.startsWith(this.rolePrefix)) {
                    _authorities.add(new SimpleGrantedAuthority(role));
                } else {
                    _authorities.add(new SimpleGrantedAuthority(this.rolePrefix + role));
                }
            }
            return this;
        }

        /**
         * Populates the user's {@link GrantedAuthority}'s.
         *
         * @param authorities
         * @see #roles(String...)
         */
        public SecurityRequestPostProcessors.UserRequestPostProcessor authorities(GrantedAuthority... authorities) {
            this.authorities = Arrays.asList(authorities);
            return this;
        }

        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(this.username, this.credentials, this.authorities);
            save(authentication, request);
            return request;
        }
    }

    public final static class UserDetailsRequestPostProcessor
            extends SecurityRequestPostProcessors.SecurityContextRequestPostProcessorSupport implements RequestPostProcessor {

        private final String username;
        private String userDetailsServiceBeanId;

        private UserDetailsRequestPostProcessor(String username) {
            this.username = username;
        }

        /**
         * Use this method to specify the bean id of the
         * {@link UserDetailsService} to use to look up the {@link UserDetails}.
         *
         * <p>By default a lookup of {@link UserDetailsService} is performed by
         * type. This can be problematic if multiple {@link UserDetailsService}
         * beans are declared.
         */
        public SecurityRequestPostProcessors.UserDetailsRequestPostProcessor userDetailsServiceBeanId(String userDetailsServiceBeanId) {
            this.userDetailsServiceBeanId = userDetailsServiceBeanId;
            return this;
        }

        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
            UsernamePasswordAuthenticationToken authentication = authentication(request.getServletContext());
            save(authentication, request);
            return request;
        }

        private UsernamePasswordAuthenticationToken authentication(ServletContext servletContext) {
            ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            UserDetailsService userDetailsService = userDetailsService(context);
            UserDetails userDetails = userDetailsService.loadUserByUsername(this.username);
            return new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }

        private UserDetailsService userDetailsService(ApplicationContext context) {
            if (this.userDetailsServiceBeanId == null) {
                return context.getBean(UserDetailsService.class);
            }
            return context.getBean(this.userDetailsServiceBeanId, UserDetailsService.class);
        }
    }

    private SecurityRequestPostProcessors() {
    }
}
