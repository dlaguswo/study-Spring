package com.test2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

public class MemController extends AbstractWizardFormController{

	public MemController() {
		setCommandClass(MemCommand.class);
		setCommandName("info");
	}
	
	
	
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page)
			throws Exception {
			
		MemCommand mem = (MemCommand)command; // 각 페이지에서 submit한 경우
		
		if(page == 0) {
			if(mem.getSsn().equals("1234")) {
				String str = mem.getName() + "님 이미 가입 되어있습니다";				
				errors.rejectValue("message", str);
				mem.setMessage(str);
			}
		} else if(page == 1) {
			
		}
		
		super.postProcessPage(request, command, errors, page);
	}



	@Override
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
			
		return new ModelAndView("test2/mem_cancel", errors.getModel()); // 취소	
	}


	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
	
		return new ModelAndView("test2/mem_ok");
	}
	

	@Override
	protected Map<String, List<String>> referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception { // 초기화 작업
		
		if(page == 1) {
			List<String> types = new ArrayList<>();
			types.add("일반회원");
			types.add("기업회원");
			types.add("특별회원");
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("types", types);
			
			return map;
		} else {
			return null;
		}
		
	}


}
