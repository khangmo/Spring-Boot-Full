<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

# Spring-Boot-Full
**This Project will helps you all information and solution about Spring Boot (For all developers want develop Web Application)**

> Hi all 
>This is Project about Spring Boot
>In this project you will find all information relative Spring Boot(MicroServies).

account/password: demo/demo

###1. Config title.xml how to defined a page will be returned to client.
```
@Bean
public TilesConfigurer tilesConfigurer() {
    final TilesConfigurer configurer = new TilesConfigurer();
    configurer.setDefinitions(new String[] { "WEB-INF/views/layout/tiles.xml", 
            "WEB-INF/views/view/view.xml",
            "WEB-INF/views/error/view.xml"});
    configurer.setCheckRefresh(true);
    return configurer;
}
```
```   
<tiles-definitions>
    <!-- Templates -->
    <definition name="home" template="/WEB-INF/views/layout/home.jsp">
        <put-attribute name="title" value="Spring Web MVC Boot" />
        <put-attribute name="header" value="/WEB-INF/views/view/header.jsp" />
        <put-attribute name="body" value="" />
        <put-attribute name="footer" value="/WEB-INF/views/view/footer.jsp" />
    </definition>
</tiles-definitions>
```
###2. Config security in Spring Boot with all users have stored in Mysql Db
```
@Autowired
private DataSource datasource;
	
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/login*", "/share*", "/facebook*").permitAll()
        .antMatchers("/home", "/home/*", "/greet", "/greet/*", "/user-manager", "/user-manager/*",
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
```
###3. Config connect to Mysql Db
```
@Value("${spring.datasource.driverClassName}")
    protected String databaseDriverClassName;

    @Value("${spring.datasource.url}")
    protected String datasourceUrl;

    @Value("${spring.datasource.username}")
    protected String databaseUsername;

    @Value("${spring.datasource.password}")
    protected String databasePassword;
	
	public static void main(String[] args) {
		SpringApplication.run(FrameworkApplication.class, args);
	}

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(databaseDriverClassName);
		driverManagerDataSource.setUrl(datasourceUrl);
		driverManagerDataSource.setUsername(databaseUsername);
		driverManagerDataSource.setPassword(databasePassword);
		return driverManagerDataSource;
	}
```
###4. Config to support Multi Language in spring boot.
```
@Bean
public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/i18/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}
@Bean
public LocaleResolver localeResolver(){
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(new Locale("en"));
    resolver.setCookieName("myLocaleCookie");
    resolver.setCookieMaxAge(4800);
    return resolver;
}
@Override
public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang");
    registry.addInterceptor(interceptor);
}
```
##5. And some Example about Unit test in spring boot.
```
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
```
###6. This project used Bootstrap to design GUI for all page.

###7. How to control Error Exception in Spring boot.
```
@Component
public class HandleExceptionRequest implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
	container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error-not-found"));
	container.addErrorPages(new ErrorPage(HttpStatus.CONFLICT, "/error-conflic"));
	container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error-forbidden"));
    }
}
```
###8. Final, i want to introduce how to deploy this App liction on tomcat.
```
@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FrameworkApplication.class);
}  
```
