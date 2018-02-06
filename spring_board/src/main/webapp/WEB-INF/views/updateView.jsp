<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage= "errorPage.jsp" %>
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
 
<DIV class="title">수정</DIV>
 
<FORM name='frm' 
      method='POST'
      enctype="multipart/form-data"
      action='./update.do'>
  <input type="hidden" name="num" value="${dto.num}">
  <input type="hidden" name="nowPage" value="${param.nowPage}">
  <input type="hidden" name="col" value="${param.col}">
  <input type="hidden" name="word" value="${param.word}">
  <input type="hidden" name="oldfile" value="${dto.filename}">
  
  <TABLE class="table">
   <TR>
      <TH>글쓴이</TH>
      <TD><input type="text" name="name" value="${dto.name}"></TD>
    </TR>
    <TR>
      <TH>제목</TH>
      <TD><input type="text" name="subject" size="30" value="${dto.subject}"></TD>
    </TR>
    <TR>
      <TH>내용</TH>
      <TD><textarea cols="30" rows="10" name="content">${dto.content}</textarea></TD>
    </TR>
    <TR>
      <TH>비밀번호</TH>
      <TD><input type="password" name="passwd"></TD>
    </TR>
    <TR>
      <TH>파일</TH>
      <TD><input type="file" name="filenameMF">(${dto.filename})</TD>
    </TR>
  </TABLE>
  <DIV class='bottom'>
    <input type='submit' value='수정'>
    <input type='button' value='목록' onclick="location.href='./list.do'">
  </DIV>
</FORM>
 
 
</body>
<!-- *********************************************** -->
</html> 
