<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	
	<!-- css , js -->
	<link href="${pageContext.request.contextPath}/resources/css/board&aside.css" rel="stylesheet" >
	<!-- 다이얼로그 js -->
	<script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
	
</head>
<body>

<div class="box">
<content>
  <div id="js-load" class="main">
    <ul class="lists">
    	
    <c:forEach items="${boardList}" var="vo">
		<li class="lists__item js-load">
		<a href="post.do?no=${vo.getBoardNo()}" style="display: block;">
			${ vo.getgName() } 
			<span style="color:#666666;">${vo.getWriter()} ${vo.getRegdate()}</span>
			<br>
			<span style="font-weight:bold; font-size: 15pt;">${vo.getTitle()}</span><br>
			<div class="content">${vo.getContent()}</div> 
			</a>
		</li>
	</c:forEach>
    </ul>
    <div id="js-btn-wrap" class="btn-wrap"> <a href="javascript:;" class="morebutton">더보기</a> </div>
  </div>
 </content>
  


	<aside>
		<jsp:include page="/WEB-INF/views/dialog/aside.jsp"/> 
	</aside>


 </div>
</body>
</html>