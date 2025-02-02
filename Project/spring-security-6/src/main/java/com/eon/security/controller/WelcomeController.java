package com.eon.security.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/security")

//http://localhost:8080/security/

public class WelcomeController {
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome:";
	}
	
	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
		// hit logout page and go to source in inspect mode
		// <input name="_csrf" type="hidden" value="iVoDMEAagy_HVGA7vIEOqAykHKTn7avDXAUpJ9XCPwX5r4zz6mthA3ki4RzqNVIP2aw6m23FMZ3T3J_uZDMcEuDwXTSbnriR" />
	}

}
