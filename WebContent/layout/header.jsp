<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<a href = "<%=cp %>/start.action">메인</a>
<a href = "<%=cp %>/demo/bbs/list.action">게시판</a>
<a href = "<%=cp %>/demo/bbs/write.action">글쓰기</a>
<a href = "<%=cp %>/demo/guest/guest.action">방명록</a>

