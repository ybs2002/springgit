<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String root = request.getContextPath(); 
%>
<html>
<head>
<style type="text/css">
	ul#menu li{
		display : inline;
	}
	ul#menu li a{
		background-color: black;
		color:white;
		pading : 10px 20px;
		text-decoration:none;
		border-radius:4px 4px 0 0;
	}
	ul#menu li a:hover{
		background-color: orange;
	}
	
	li#admin{
		float:right;
		margin-right: 30px
	}
</style>
</head>
<div style="background-color: #EEEEEE;">
<table style="width: 100%; ">
  <tr>
    <td>
        <img src="${pageContext.request.contextPath}/resources/images/images.jpg" width='100%' height='100%'>
    </td>
  </tr>
  
  <tr>
    <td>
    <ul id="menu">
    	<li><a href="<%=root %>/">홈</a></li>   	
    	<li><a href="<%=root %>/board/create.do">글쓰기</a></li>
    	<li><a href="<%=root %>/board/list.do">목록</a></li>
    	<li><a href="">메뉴</a></li>
    	<li><a href="">메뉴</a></li>
    	<li><a href="">메뉴</a></li>
     </ul>
    </td> 
  </tr>
 
</table>
</div>