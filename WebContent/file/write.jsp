<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판</title>
<link rel = "stylesheet" href="<%=cp %>/file/data/style.css" type = "text/css">
<link rel = "stylesheet" href="<%=cp %>/file/data/created.css" type = "text/css">
<script type="text/javascript">
function sendIt() {
    f = document.myForm;
    
    str = f.subject.value;
    str = str.trim();
    if(!str) {
        alert("제목을 입력해보세요 !!!");
        f.subject.focus();
        return;
    }
    f.subject.value = str;

    str = f.upload.value;
    if(!str) {
        alert("이미지 파일을 선택하세요 !!!");
        f.upload.focus();
        return;
    }
    
    f.action = "<%=cp %>/file/write.action";
    f.submit();
}
</script>
</head>
<body>
<div id = "bbs">
	<div id = "bbs_title">
		파일 게시판
	</div>	
	<form name = "myForm" method="post" enctype="multipart/form-data" >
		<div id = "bbsCreated">
			<div class= "bbsCreated_bottomLine">
				<div>
					<dl>
						<dt>제&nbsp;&nbsp;목</dt>
						<dd><input type = "text" name = "subject" size = "68" maxlength="100" class="boxTF"></dd>
					</dl>
				</div>
			</div>
			
			<div class= "bbsCreated_bottomLine">
				<div>
					<dl>
						<dt>파&nbsp;&nbsp;일</dt>
						<dd><input type = "file" name = "upload" size = "68" maxlength="100" class="boxTF"></dd>
					</dl>
				</div>
			</div>
			
			<div id = "bbsCreated_footer">	
				<input type = "hidden" name = "mode" value = "${mode }" >
				<input type ="button" value = " 등록하기 " class="btn2" onclick="sendIt();" />
				<input type ="reset" value = " 다시입력 " class="btn2" onclick="document.myForm.subject.foucs();"/>
				<input type ="button" value = " 작성취소 " class="btn2" onclick="javascript:location.href = '<%=cp %>/file/list.action'"/>
			</div>
		</div>
	</form>
</div>
</body>
</html>