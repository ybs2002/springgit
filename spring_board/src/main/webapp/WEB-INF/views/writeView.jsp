<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage= "errorPage.jsp" %>
<% request.setCharacterEncoding("utf-8"); %> 
 
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title></title> 
<style type="text/css"> 
*{ 
  font-family: gulim; 
  font-size: 24px; 
} 
</style> 
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="Stylesheet" type="text/css">
</head> 

<!-- *********************************************** -->
<body leftmargin="0" topmargin="0">
 
<DIV class="title">글쓰기</DIV>
 
<FORM name='frm' 
	  method='POST' 
	  enctype="multipart/form-data" 
	  action='./write.do'>
  <TABLE class='table'>
    <TR>
      <TH>글쓴이</TH>
      <TD><input type="text" name="name"></TD>
    </TR>
    <TR>
      <TH>제목</TH>
      <TD><input type="text" name="subject" size="30"></TD>
    </TR>
    <TR>
      <TH>내용</TH>
      <TD><textarea cols="30" rows="10" name="content"></textarea></TD>
    </TR>
    <TR>
      <TH>비밀번호</TH>
      <TD><input type="password" name="passwd"></TD>
    </TR>
    <TR>
      <TH>파일</TH>
      <TD><input type="file" name="filenameMF"></TD>
    </TR>
  </TABLE>
  
  <DIV class='bottom'>
    <input type='submit' value='등록'>
    <input type='button' value='목록' onclick="location.href='./list.do'">
  </DIV>
</FORM>
 
 
</body>
<!-- *********************************************** -->
</html> 
