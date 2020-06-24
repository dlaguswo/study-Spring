package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("demo.BoardController")
public class BoardController {
	
	@RequestMapping(value = "/demo/bbs/list.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String list() throws Exception{
		
		return "list.bbsLayout"; // tile.xml의 list.bbsLayout을 찾음
	}
	
	@RequestMapping(value = "/demo/bbs/write.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String write() throws Exception{
		
		return "write.bbsLayout"; // tile.xml의 write.bbsLayout을 찾음
		
	}
}
