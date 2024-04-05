package com.in28minutes.rest.webservices.restwebservices.Hello;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	private MessageSource messageSource;
		
	
	public HelloController(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	@GetMapping("/hello")
	public String helloMessage() {
		return "Its working";
	}
	
	@GetMapping("/hello-i18n")
	public String helloMessagei18n() {

		Locale locale=LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message",locale);
		
	}
}
