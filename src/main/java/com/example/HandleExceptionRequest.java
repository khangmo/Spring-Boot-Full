package com.example;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HandleExceptionRequest implements EmbeddedServletContainerCustomizer {
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error-not-found"));
		container.addErrorPages(new ErrorPage(HttpStatus.CONFLICT, "/error-conflic"));
		container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error-forbidden"));
	}
}
