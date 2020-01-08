package com.uc.lsy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
	@RequestMapping(value = "/main")
	public String main() { // 여기에 모델 써줘야 넘기기 가능
		return "redirect:/";

	}
	
}
