package com.file;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.common.FileManager;
import com.spring.common.MyUtil;
import com.spring.dao.CommonDAO;

@Controller("file.FileController")
public class FileController {
	
	@Resource(name = "dao")
	private CommonDAO dao;
	
	@Resource(name = "myUtil")
	private MyUtil myUtil;
	
	@Resource(name = "fileManager")
	private FileManager fileManager;

	@RequestMapping(value = "/file/write.action", method = {RequestMethod.GET, RequestMethod.POST })
	public String writeForm(HttpServletRequest request, FileCommand command, HttpSession session) throws Exception{
		
		if(command == null || command.getMode() == null || command.getMode().equals("") ) {
			request.setAttribute("mode", "input");
			return "file/write";
		}
		
		int maxnum = dao.getIntValue("file.maxFileNum");
		
		MultipartFile file = command.getUpload();
		InputStream is = file.getInputStream();
		
		String root = session.getServletContext().getRealPath("/");
		String savePath = root + File.separator + "pds" + File.separator + "saveData";
		
		command.setNum(maxnum + 1); 
		command.setSaveFileName(fileManager.doFileUpload(is, command.getUpload().getOriginalFilename(), savePath));
		command.setOriginalFileName(command.getUpload().getOriginalFilename());
		
		dao.insertData("file.insertData", command);
		
		return "redirect:/file/list.action";
		
	}
	
	@RequestMapping(value = "/file/list.action", method = {RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		System.out.println("번호:" + pageNum);
		String cp = request.getContextPath();
		
		int numPerPage = 9;
		int totalPage = 0;
		int totalDataCount = 0;
		int currentPage = 1;
				
		// 처음 실행이 아니면
		if(pageNum != null && !pageNum.equals("")) {
			currentPage = Integer.parseInt(pageNum); // pageNum을 현재 페이지로 설정
		} else {
			pageNum = "1";
		} 
		
		// 전체 데이터 수
		totalDataCount = dao.getIntValue("file.dataCount");
		
		// 전체 페이지 수
		totalPage = myUtil.getPageCount(numPerPage, totalDataCount);
		
		// 마지막 페이지 삭제될때 처리
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		// 전체 list 가져오기
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;

		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("start", start);
		hMap.put("end", end);

		List<Object> lists = (List<Object>) dao.getListData("file.listData", hMap);
		String listUrl = "list.action";
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		String deletePath = cp + "/file/deleted.action";
		String imagePath = cp + "/pds/saveData";
		
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", totalDataCount);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("imagePath", imagePath);	
		request.setAttribute("deletePath", deletePath);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageNum", pageNum);
		
		return "file/view";
	}
	
	@RequestMapping(value = "/file/download.action", method = {RequestMethod.GET})
	public String download(HttpServletRequest request, HttpServletResponse response, FileCommand command, HttpSession session) throws Exception{
		int num = Integer.parseInt(request.getParameter("num"));
		command = (FileCommand)dao.getReadData("file.readData", num);
		String root = session.getServletContext().getRealPath("/");
		String savePath = root + File.separator + "pds" + File.separator + "saveData";
		
		fileManager.doFileDownload(command.getSaveFileName(), command.getOriginalFileName(), savePath, response);

		return "null";
	}
		
	@RequestMapping(value = "/file/deleted.action", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, FileCommand command, HttpSession session) throws Exception {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String root = session.getServletContext().getRealPath("/");
		String savePath = root + File.separator + "pds" + File.separator + "saveData";
		
		fileManager.doFileDelete(command.getSaveFileName(), savePath);
		dao.deleteData("file.deleteData", num);
		return "redirect:/file/list.action?pageNum=" + pageNum;
	}
	
}
