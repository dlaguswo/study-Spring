package com.anno;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("anno.TestController") // 해당 이름으로 객체 생성, 다른 TestController와 다른 객체
public class TestController {

	@RequestMapping(value="/demo/write.action", method = {RequestMethod.GET}) // value: 해당 url로 요청시 매서드 수행, method: 요청 method 명시
	public String write() throws Exception{
		return "anno/created";
	}
	
	@RequestMapping(value="/demo/write.action", method = {RequestMethod.POST})
	public String write_ok(TestCommand command, HttpServletRequest request) throws Exception{
		
		String message = "이름: " + command.getUserName();
		message += ", 아이디: " + command.getUserId();
		request.setAttribute("message", message);
		
		return "anno/result";
		
	}
	
	@RequestMapping(value="/demo/save.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String save(TestCommand command, HttpServletRequest request) throws Exception{
		if(command == null || command.getMode() == null || command.getMode().equals("")) {
			return "anno/test";
		}
		
		String message = "이름: " + command.getUserName();
		message += ", 아이디: " + command.getUserId();
		
		request.setAttribute("message", message);
		
		return "anno/result";
	}
	
	@RequestMapping(value="/demo/demo.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String demo(String userId, String userName, String mode, HttpServletRequest request) throws Exception{
		if(mode == null || mode.equals("")) {
			return "anno/demo";
		}
		
		String message = "이름: " + userName;
		message += ", 아이디: " + userId;
		
		request.setAttribute("message", message);
		
		return "anno/result";
	}
}
