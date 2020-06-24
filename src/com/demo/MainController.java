package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("demo.mainController")
public class MainController {

	@RequestMapping(value = "/start.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String method() {
		return "mainLayout"; // tile.xml의 mainLayout을 찾음
	}
}
