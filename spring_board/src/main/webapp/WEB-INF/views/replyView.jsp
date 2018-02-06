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
 
<DIV class="title">답변</DIV>
 
<FORM name='frm' 
 	  method='POST'
 	  enctype="multipart/form-data"
 	  action='./reply.do'>
  <!-- 부모글 삭제 제한 -->
  <input type="hidden" name="num" value="${dto.num}"/>
  <!-- 답변처리용 -->
  <input type="hidden" name="ref" value="${dto.ref}"/>
  <input type="hidden" name="indent" value="${dto.indent}"/>
  <input type="hidden" name="ansnum" value="${dto.ansnum}"/>
  <!-- 페이지와 검색유지용 -->
  <input type="hidden" name="nowPage" value="${param.nowPage}"/>
  <input type="hidden" name="col" value="${param.col}"/>
  <input type="hidden" name="word" value="${param.word}"/>
  <TABLE class='table'>
    <TR>
      <TH>글쓴이</TH>
      <TD><input type="text" name="name" ></TD>
    </TR>
    <TR>
      <TH>제목</TH>
      <TD><input type="text" name="subject" value="${dto.subject }" size="30" ></TD>
    </TR>
    <TR>
      <TH>내용</TH>
      <TD><textarea rows="10" cols="30" name="content"></textarea></TD>
    </TR>
    <TR>
      <TH>파일</TH>
      <TD><input type="file" name="filenameMF" ></TD>
    </TR>
    <TR>
      <TH>비밀번호</TH>
      <TD><input type="password" name="passwd" ></TD>
    </TR>
  </TABLE>
  
  <DIV class='bottom'>
    <input type='submit' value='답변'>
    <input type='button' value='목록' onclick="location.href='./list.do'">
  </DIV>
</FORM>
 
 
</body>
<!-- *********************************************** -->
</html> 
