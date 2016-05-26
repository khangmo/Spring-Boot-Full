package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class FrameworkApplication extends WebMvcConfigurerAdapter  {

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
}
