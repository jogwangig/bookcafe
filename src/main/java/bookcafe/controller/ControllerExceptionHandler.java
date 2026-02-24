package bookcafe.controller;

import java.lang.reflect.InaccessibleObjectException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;


import bookcafe.exception.InaccessibleItemException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ModelAttribute("currentURI")
	public String addCurrentURI(HttpServletRequest req) {
		return req.getRequestURI();
	}
	
	@ExceptionHandler(InaccessibleItemException.class)
	public String catchInaccessibleItemException(Model model, InaccessibleItemException e) {
		model.addAttribute("msg",e.getMessage());
		
		return "/error";
	}
	
	@ExceptionHandler(Exception.class)
	public String catchException(Model model, Exception e) {
		
		model.addAttribute("msg",e.getMessage());
		
		return "/error";
	}
}
