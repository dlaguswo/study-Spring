<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="<%=cp %>/test2/mem.action" method="post">
	이름:<input type = "text" name = "name" /><br>
	주민:<input type = "text" name = "ssn" /><br>
	<input type = "submit" name ="_target0" value = "다시입력" />
	<input type = "submit" name ="_target1" value = "다음단계" />
</form>

${info.message }
</body>
</html>