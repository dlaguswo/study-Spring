<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=cp%>/file/data/created.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/file/data/style.css" type="text/css">
<style>
#bbs_ {
	width: 600px;
	margin: 10px auto;
	text-align: left;
}

#bbsCreated_ {
	width: 600px;
	overflow: visible;
	border-bottom: 3px solid #DBDBDB;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<div id="bbs">
		<div id="bbs_title">이미지 게시판</div>
	</div>
	<table style="margin-left: auto; margin-right: auto;">
		<tr>
			<td width="530">Total ${dataCount } articles, ${totalPage } pages / Now page is ${currentPage }</td>
			<td align="right">
				<input type="button" value="게시물 등록" onclick="javscript:location.href = '<%=cp%>/file/write.action';" />
			</td>		
		</tr>
	</table>
	<div id="bbs_">
		<div id="bbsCreated_"></div>
		<table>
			<%
				int newLine = 0; // <tr> 개수
			%>
			<c:forEach var="dto" items="${lists }">
				<%
					if (newLine == 0) {
							out.print("<tr>");
						}
					newLine++; // <tr> 개수 세기
				%>
				<td align="center">				
					<img alt="" src="${imagePath }/${dto.saveFileName }" width="195" height="180">[${dto.num}] ${dto.subject } 
						<a href="${deletePath }?num=${dto.num}&pageNum=${pageNum}">삭제</a>
						<a href="<%=cp %>/file/download.action?num=${dto.num}">다운로드</a>						
				</td>
				<%
					if (newLine == 3) {
						out.print("</tr>");
						newLine = 0;
					}
				%>
			</c:forEach>
			<%
				while (newLine > 0 && newLine < 3) {
					out.print("<td></td>");
					newLine++;
				}
			%>
			<tr>
				<td align="center" colspan="3">
					<c:if test="${dataCount!=0 }">
						${pageIndexList }
					</c:if> 
					<c:if test="${dataCount==0 }">
						등록된 파일이 없습니다
					</c:if>
				</td>
			</tr>
		</table>
		<div id="bbsCreated_"></div>
	</div>
</body>
</html>