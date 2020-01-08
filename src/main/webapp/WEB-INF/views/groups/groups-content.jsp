<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
    	
    	
    <c:forEach items="${GboardList}" var="vo">
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
	<footer>
	
	<div class="aside">
		<c:if test= "${follow.getgNo() ne null}"> 
				<h5>${follow.getgName()}</h5>
				<span style="color:#484848"><i class="fas fa-user"></i> &nbsp; <b>${follow.getJoinMember()}</b><br>
				<i class="fas fa-birthday-cake"></i>  &nbsp; <b>${follow.getRegdate()}</b><br></span>
			
		</c:if>
		
		<c:choose>
		<c:when test= "${id ne null}"> 
			<form action="submit.do" method="post">
				<input type="submit" class="postButton" value="글쓰기" />
			</form>
			<c:if test= "${checkfollow.getId() ne null}"> 
				<form action="unfollow.do?no=${follow.getgNo()}" method="post">
				<input type="submit" class="postButton" value="팔로우끊기" />
				</form>
			</c:if>
			<c:if test= "${checkfollow.getId() eq null}"> 
				<form action="follow.do?no=${follow.getgNo()}" method="post">
				<input type="submit" class="postButton" value="팔로우하기" />
				</form>
			</c:if>
		</c:when>
		<c:otherwise>
			<button class="postButton dialog-trigger-login">글쓰기</button>
			
		</c:otherwise>
		
		</c:choose> 
		 
			

	</div>
	
	
    <div class="aside">울산과학대 소프트웨어전공<br><br> © 1712121 이승연</div>
 </footer>
	</aside>


 </div>
</body>
</html>