package com.test1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class LoginController extends SimpleFormController { // SimpleFormController: POST 요청이 들어오면 onSubmit() 메소드 호출

	private Authenticator authenticator;
	
	public void setAuthenticator(Authenticator authenticator ) { // 의존성 주입
		this.authenticator = authenticator;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception { // submit 버튼 누르면 자동 실행
		
		LoginCommand login = (LoginCommand)command;
		
		try {
			authenticator.authen(login.getUserId(), login.getUserPwd());
			
			String message = "id: " + login.getUserId();
			message += ",pwd: " + login.getUserPwd();
			message += ",type: " + login.getLoginType();
			
			request.setAttribute("message", message);
			return new ModelAndView("test1/login_ok");
		} catch (Exception e) {
			return showForm(request, response, errors); //입력창 다시 보여주기
		}
		
	}

	@Override
	protected Map<String, List<String>> referenceData(HttpServletRequest request) throws Exception { // login.jsp로 넘어감
		
		List<String> loginTypes = new ArrayList<>();
		loginTypes.add("일반회원");
		loginTypes.add("기업회원");
		loginTypes.add("특별회원");
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("loginTypes", loginTypes);
		return map;
	}

}
