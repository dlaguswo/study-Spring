package com.anno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/main.action") // main.action으로 들어오는 모든 요청에 대한 처리를 해당 클래스에 함
public class MainController {

	@RequestMapping(method = RequestMethod.GET) // main.action GET 요청에 대한 처리를 해당 클래스에 함 
	public String method() {		
		return "/main";
	}
	
}
