package bookcafe.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ModelAttribute("currentURI")
	public String addCurrentURI(HttpServletRequest req) {
		return req.getRequestURI();
	}
	
	@ExceptionHandler(Exception.class)
	public String catchException() {
		return "/error";
	}
}
