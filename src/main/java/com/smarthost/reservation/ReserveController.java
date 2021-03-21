package com.smarthost.reservation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReserveController {

	@GetMapping("/hello")
	String hello(@RequestParam(required = false) String name) {
		return "Hello " + name;
	}

}
