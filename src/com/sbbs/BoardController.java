package com.sbbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.common.MyUtil;
import com.spring.dao.CommonDAO;

// @Service: 해당 클래스가 Service임을 명시하는 어노테이션
@Controller("sbbs.BoardController") // Controller: 해당 메소드가 특정 value에 맞는 URL 요청이 왔을 시 처리하는 메소드
public class BoardController {

	@Resource(name = "dao")
	private CommonDAO dao;
	
	@Resource(name = "myUtil")
	private MyUtil myUtil;
	
	@RequestMapping(value="/sbbs/created.action", method = {RequestMethod.GET, RequestMethod.POST}) // @RequestMapping: 매핑정보, value = url-pattern 명시, method = RESTful API 명시
	public String created(BoardCommand command, HttpServletRequest request) throws Exception{
		
		if(command == null || command.getMode() == null || command.getMode().equals("")) {
			request.setAttribute("mode", "insert");			
			return "board/created";
		}
		
		int boardNumMax = dao.getIntValue("sbbs.boardNumMax"); // 게시글 max 번호 가져 오기
		command.setBoardNum(boardNumMax + 1);
		command.setIpAddr(request.getRemoteAddr());
		
		dao.insertData("sbbs.insertData", command); // 게시글 입력
		
		return "redirect:/sbbs/list.action";
	}
	
	@RequestMapping(value="/sbbs/list.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, HttpSession session) throws Exception{
	
		String cp = request.getContextPath();
		
		int numPerPage = 3; // 한 페이지당 보여주는 게시글 개수
		int totalPage = 0; // 총 페이지
		int totalDataCount = 0; // 총 게시물 개수
		int currentPage = 1; // 현재 페이지
		
		String pageNum = request.getParameter("pageNum");
		
		// 수정 메서드(updateSubmit)에서 보낸 pageNum을 받음
		if(pageNum == null) {
			pageNum = (String)session.getAttribute("pageNum");
		}
		session.removeAttribute("pageNum");
		
		if(pageNum != null && !pageNum.equals("")) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue == null) {
			searchKey = "subject";
			searchValue = "";
		} else {
			if(request.getMethod().equals("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("searchKey", searchKey);
		hMap.put("searchValue", searchValue);
		
		totalDataCount = dao.getIntValue("sbbs.dataCount", hMap);
		
		if(totalDataCount != 0) {
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);
		}
		
		if(currentPage > totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		
		hMap.put("start", start);
		hMap.put("end", end);
		
		List<Object> lists = (List<Object>)dao.getListData("sbbs.listData", hMap);
		int listNum, n = 0;
		ListIterator<Object> it = lists.listIterator();
		while(it.hasNext()) {
			BoardCommand dto = (BoardCommand) it.next();
			listNum = totalDataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
		}
		
		String params = "";
		String urlList = ""; // 게시물 리스트 주소
		String urlArticle = ""; // 게시물 보기 주소
		
		if(!searchValue.equals("")) {
			params = "searchKey=" + searchKey;
			params += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		if(params.equals("")) {
			urlList = cp + "/sbbs/list.action";
			urlArticle = cp + "/sbbs/article.action?pageNum=" + currentPage;
		} else {
			urlList = cp + "/sbbs/list.action?" + params;
			urlArticle = cp + "/sbbs/article.action?pageNum=" + currentPage + "&" + params;
		}
		
		request.setAttribute("lists", lists);
		request.setAttribute("urlList", urlList);
		request.setAttribute("urlArticle", urlArticle);
		request.setAttribute("totalDataCount", totalDataCount);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		
		return "board/list";
	}
	
	@RequestMapping(value="/sbbs/article.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String article(HttpServletRequest request) throws Exception{
		
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue == null) {
			searchKey = "subject";
			searchValue = "";
		} else {
			if(request.getMethod().equals("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		dao.updateData("sbbs.updateHitCount", boardNum); // 조회수 증가
		
	 	BoardCommand dto = (BoardCommand)dao.getReadData("sbbs.readData", boardNum); // 게시글 가져오기
		if(dto == null) {
			return "redirect:/sbbs/list.action";
		}
		
		int lineSu = dto.getContent().split("\r\n").length;
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br>"));
		
		Map<String, Object> hMap = new HashMap<>();
		hMap.put("searchKey", searchKey);
		hMap.put("searchValue", searchValue);
		hMap.put("boardNum", boardNum);
		
		BoardCommand prereadData = (BoardCommand)dao.getReadData("sbbs.prereadData", hMap);
		int preBoardNum = 0;
		String preSubject = "";
		
		if(prereadData != null) {
			preBoardNum = prereadData.getBoardNum();
			preSubject = prereadData.getSubject();
		}
		
		BoardCommand nextreadData = (BoardCommand)dao.getReadData("sbbs.nextreadData", hMap);
		int nextBoardNum = 0;
		String nextSubject = "";
		
		if(nextreadData != null) {
			nextBoardNum = nextreadData.getBoardNum();
			nextSubject = nextreadData.getSubject();
		}
		
		String params = "pageNum=" + pageNum;
		if(!searchValue.equals("")) {
			params += "&searchKey=" + searchKey;
			params += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("preBoardNum", preBoardNum);
		request.setAttribute("preSubject", preSubject);
		request.setAttribute("nextBoardNum", nextBoardNum);
		request.setAttribute("nextSubject", nextSubject);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("params", params);
		
		return "/board/article";
	}
	
	@RequestMapping(value="/sbbs/updated.action", method = {RequestMethod.GET})
	public String updateForm(HttpServletRequest request) throws Exception{ 
		
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
		
		BoardCommand dto = (BoardCommand)dao.getReadData("sbbs.readData", boardNum);
		if(dto == null) {
			return "redirect:/sbbs/list.action?pageNum=" + pageNum;
		}
		
		request.setAttribute("mode", "update");
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("dto", dto);
		
		return "board/created";
	}
	
	@RequestMapping(value="/sbbs/updated.action", method = {RequestMethod.POST})
	public String updateSubmit(BoardCommand command, HttpSession session, HttpServletRequest request) throws Exception{ 
		
		dao.updateData("sbbs.updateData", command);
		
		session.setAttribute("pageNum", command.getPageNum());
			
		return "redirect:/sbbs/list.action";
		
		/*String pageNum = request.getParameter("pageNum");
		return "redirect:/sbbs/list.action?pageNum=" + pageNum;*/
	}

	@RequestMapping(value="/sbbs/deleted.action", method = {RequestMethod.GET})
	public String deleted(HttpServletRequest request) throws Exception{ 
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
		
		dao.deleteData("sbbs.deleteData", boardNum);
		return "redirect:/sbbs/list.action?pageNum=" + pageNum;
	}
}
