<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="util" uri="/WEB-INF/tlds/el-functions.tld" %>
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
<script type="text/javascript">
function read(num){
	var url = "./read.do";
	url = url + "?num="+num;
	url = url + "&nowPage=${nowPage}";
	url = url + "&word=${word}";
	url = url + "&col=${col}";
	
	//Apache Tomcat 8.0.30에서는 작동하지만 Tomcat 8.5에서는 작동하지 않습니다
	//아파치버젼에서 오류발생 한글이 매개값으로 가면 에러남
	//url = encodeURI(url);
		
	location.href=url;
}

function downFile(fname){
	var url = "${pageContext.request.contextPath}/download";
	url = url + "?dir=/resources/storage";
	url = url + "&filename="+fname;
alert("파일url" + url);
	
	location.href=url;
}
</script>

</head> 

<!-- *********************************************** -->
<body leftmargin="0" topmargin="0">
 
<DIV class="title">게시판 목록</DIV>
 <DIV class="content">
 	<form name="frm" method="post" action="./list.do">
 		<select name="col">
 			<option value="name">성명</option>
 			<option value="subject" selected='selected'>제목</option>
 			<option value="content">내용</option>
 			<option value="subject_content">제목+내용</option>
 			<option value="total">전체출력</option>
 		</select>
 		<script type="text/javascript">
 			document.frm.col.value='${col}'
 			if(document.frm.col.value == '') document.frm.col.value= 'subject';
 		</script>
 		<input type="text" name="word" value="${word}">
 		<input type="submit" value="검색"/>
 	</form>
 </DIV>
  <TABLE class='table'>
    <TR>
      <TH>번호</TH>
      <TH>성명</TH>
      <TH>제목</TH>
      <TH>조회수</TH>
      <TH>등록날짜</TH>
      <TH>파일</TH>     
    </TR>
 <c:forEach var="dto" items="${list}">
    <c:set var="recNo" value="${recNo-1}"/>
	<TR>
		<TD>${recNo}</TD>
		<TD>${dto.name}</TD>
		<TD>
		<c:forEach  begin="1" end="${dto.indent}">
		    <c:out value="&nbsp;&nbsp;" escapeXml="false"/>
		</c:forEach>
		<c:if test="${dto.indent>0}">
			[답변]
		</c:if>
		<a href="javascript:read('${dto.num}')">${dto.subject}</a>
		
	<%-- 	<c:if test="${util:newIMG(dto.regdate)}">
 		<img src="${pageContext.request.contextPath}/resources/images/new.gif" > 
 		</c:if>  --%>
		</TD>
		<TD>${dto.count}</TD>
		<TD>${dto.regdate}</TD>
		<TD>
		<c:choose>
			<c:when test="${empty dto.filename}">
				파일없음
			</c:when>
			<c:otherwise>
				<a href="javascript:downFile('${dto.filename}')">${dto.filename}</a>
		    </c:otherwise>
		</c:choose>
		</TD>
	</TR>
</c:forEach> 
  </TABLE>
  
  <DIV class='bottom'>
    ${paging}
    <input type='button' value='등록' onclick="location.href='./write.do'">
  </DIV>
</body>
<!-- *********************************************** -->
</html> 
