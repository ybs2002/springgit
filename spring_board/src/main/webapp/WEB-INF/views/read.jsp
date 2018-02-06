<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="spring.model.board.*" %>
<%@ page errorPage= "errorPage.jsp" %>
<% 
    String root = request.getContextPath();
	String nowPage = request.getParameter("nowPage");
	String col = request.getParameter("col");
	String word = request.getParameter("word");

	BoardDTO dto = (BoardDTO)request.getAttribute("dto");
	String content = dto.getContent();
	content = content.replaceAll("\r\n", "<br>");
%> 
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
function downFile(fname){
	var url = "<%=root%>/download";
	url = url + "?dir=/resources/storage";
	url = url + "&filename="+fname;
	
	location.href=url;
}

function listB(){
	var url ="./list.do";
	url = url + "?nowPage=<%=nowPage%>";
	url = url + "&col=<%=col%>";
	url = url + "&word=<%=word%>";
	
	location.href=url;	
}

function updateB(num){
	var url ="./update.do";
	url = url + "?nowPage=<%=nowPage%>";
	url = url + "&col=<%=col%>";
	url = url + "&word=<%=word%>";
	url = url + "&num="+num;
	
	location.href=url;	
}
function replyB(num){
	var url="./reply.do";
	url = url + "?nowPage=<%=nowPage%>";
	url = url + "&col=<%=col%>";
	url = url + "&word=<%=word%>";
	url = url + "&num="+num;
	
	location.href= url;
}
function deleteB(num){
	var url="./delete.do";
	url = url + "?nowPage=<%=nowPage%>";
	url = url + "&col=<%=col%>";
	url = url + "&word=<%=word%>";
	url = url + "&num="+num;
	url = url + "&oldfile=<%=dto.getFilename()%>";
	
	location.href=url;
}

</script>

</head> 

<!-- *********************************************** -->
<body leftmargin="0" topmargin="0">
 
<DIV class="title">내용보기</DIV>
 
<TABLE class='table' width="50%">
   <TR>
     <TH>글쓴이</TH>
     <TD><%=dto.getName() %></TD>
   </TR>
   <TR>
     <TH>제목</TH>
     <TD><%=dto.getSubject() %></TD>
   </TR>
   <TR>
     <TH>내용</TH>
     <TD><%=content %></TD>
   </TR>
   <TR>
     <TH>조회수</TH>
     <TD><%=dto.getCount() %></TD>
   </TR>
   <TR>
     <TH>파일명</TH>
     <TD>
     <%
     	if(dto.getFilename()==null){
     		out.print("파일없음");
     	}else{
     %>
	     <a href="javascript:downFile('<%=dto.getFilename() %>')">
	     <%=dto.getFilename() %>(<%=dto.getFilesize() %>) 
	     </a>
	     
	 <%} %>    
     </TD>
   </TR>
</TABLE>
  
<DIV class='bottom'>
  <input type='button' value='목록' onclick="listB()">
  <input type='button' value='수정' onclick="updateB('<%=dto.getNum()%>')">
  <input type='button' value='답변' onclick="replyB('<%=dto.getNum()%>')">
  <input type='button' value='삭제' onclick="deleteB('<%=dto.getNum()%>')">
</DIV>

 
 
</body>
<!-- *********************************************** -->
</html> 
