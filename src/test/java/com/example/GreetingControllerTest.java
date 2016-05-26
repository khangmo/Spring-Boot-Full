package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.common.Constant;
import com.example.user.imlement.UserImplement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static com.example.SecurityRequestPostProcessors.userDetailsService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FrameworkApplication.class)
@WebAppConfiguration
public class GreetingControllerTest {
	
	@Value("${environments.root.url.test}")
	protected String rootUrl;
	
	@Autowired
	UserImplement userImplement;
	
	UserDetails user;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;
	
	@Before
    public void setup() {
		user = userImplement.getUserByUsername("khangnt");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.addFilter(springSecurityFilterChain).build();
    }
	
	@Test
	public void testHomePagesAsAnonymousUnauthorized() throws Exception {
		mockMvc.perform(get("/home"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl(rootUrl + "/login"));
	}
	
	@Test
	public void testRootPathAsAnonymousUnauthorized() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/home"));
	}
	
	@Test
    public void testHomePageAsAccountRoleManager() throws Exception {
        mockMvc.perform(get("/home")
                .with(userDetailsService(Constant.UNIT_TEST.USER_TEST)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/layout/home.jsp"));
    }
}
