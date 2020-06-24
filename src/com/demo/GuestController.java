package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("demo.GuestController")
public class GuestController {

	@RequestMapping(value = "/demo/guest/guest.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String guest() throws Exception{
		
		return "guest.guestLayout"; // tile.xml의 guest.guestLayout을 찾음
	}
}
