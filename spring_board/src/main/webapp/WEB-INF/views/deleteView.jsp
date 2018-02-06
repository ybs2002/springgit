<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage= "errorPage.jsp" %>
<c:choose>
<c:when test="${flag}">
	<script type="text/javascript">
		alert("답변있는 글이므로 삭제할 수 없습니다.");
		history.back();
	</script>
</c:when>
<c:otherwise>
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
	 
	<DIV class="title">삭제</DIV>
	 
	<FORM name='frm' method='POST' action='./delete.do'>
	  <input type="hidden" name="nowPage" value="${param.nowPage}">
	  <input type="hidden" name="col" value="${param.col}">
	  <input type="hidden" name="word" value="${param.word}">
	  <input type="hidden" name="num" value="${param.num}">
	  <input type="hidden" name="oldfile" value="${param.oldfile}">
	  <TABLE class='table'>
	    <TR>
	      <TH>비밀번호</TH>
	      <TD><input type="password" name="passwd"></TD>
	    </TR>
	  </TABLE>
	  
	  <DIV class='bottom'>
	    <input type='submit' value='삭제'>
	    <input type='button' value='취소' onclick="history.back()">
	  </DIV>
	</FORM>
	 
	 
	</body>
	<!-- *********************************************** -->
	</html>
</c:otherwise> 
</c:choose>