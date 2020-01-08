<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>

<footer>
	
	
	<div class="aside">
		<c:choose>
		<c:when test= "${vo.getgNo() ne null}"> 
			<h5>${vo.getgName()}</h5>
			<span style="color:#484848"><i class="fas fa-user"></i> &nbsp; <b>${vo.getJoinMember()}</b><br>
			</span>
			
		</c:when>
		<c:otherwise>
			<h5>Home</h5>
			지금 게시물을 등록해보세요!<br>
		</c:otherwise>
		
		</c:choose> 
		
		<c:choose>
		<c:when test= "${id ne null}"> 
			<form action="submit.do" method="post">
				<input type="submit" class="postButton" value="글쓰기" />
			</form>
			<c:if test= "${vo.getgNo() ne null}">
			<c:if test= "${checkfollow.getId() ne null }"> 
				<form action="unfollow.do?no=${vo.getgNo()}" method="post">
				<input type="submit" class="postButton" value="팔로우끊기" />
				</form>
			</c:if>
			<c:if test= "${checkfollow.getId() eq null}"> 
				<form action="follow.do?no=${vo.getgNo()}" method="post">
				<input type="submit" class="postButton" value="팔로우하기" />
				</form>
			</c:if>
			 </c:if>
		</c:when>
		<c:otherwise>
			<button class="postButton dialog-trigger-login">글쓰기</button>
		</c:otherwise>
		
		</c:choose> 
		
	</div>
	
	
    <div class="aside">울산과학대 소프트웨어전공<br><br> © 1712121 이승연</div>
 </footer>

</body>
</html>