<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/css/board&aside.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/resources/css/post.css" rel="stylesheet" >
</head>
<body>
<div class="box">
	<content class="postcontent">
		<div style="font-size: 20px; margin-bottom:3px;">
			<b>${vo.getTitle()}</b>
			
			<c:if test ="${vo.getWriter() eq sessionScope.id}">
				<div id="float-right" style="padding:0px; margin: 0px;">
				<a href="update.do?no=${param.no}"><i class="far fa-edit"></i> </a> <!-- 수정 -->
				&nbsp;
				<a href="delete.do?no=${param.no}"><i class="fas fa-times"></i></a><!-- 삭제 -->
				</div>
			</c:if>
		</div>
		${vo.getWriter()}  |  ${vo.getRegdate()}
			<span style="float: right;">조회 ${ vo.getViews() } </span>
		<br>
		<hr>
		
		<div style="margin-bottom: 10px;">
		${vo.getContent()}<br>
		</div>
			
		
			
		<form action="reply.do?no=${vo.getBoardNo()}"method="post" >
			<textarea id="reply" name="reply" ></textarea>
			<input type="submit" value="등록" class="buttonstyle" style="margin-top:10px;">
		</form>
		
		<br><br><br><br>
		<c:forEach items="${Rvo}" var="vo" begin="0" end="0">
			<c:if test ="${vo.getName() ne null}"> 
				<hr style="color: #fffa77; border-color: #fffa77; background-color: #fffa77; height: 5px; border: none;">
			</c:if>
		</c:forEach>
		
		
		<c:forEach items="${Rvo}" var="vo">
			<div style="font-size: 11pt; color:#222222">${ vo.getName() }
			${ vo.getRegdate() }</div>
			
			<c:if test ="${vo.getName() eq sessionScope.name}">
				<div id="float-right" style="padding:0px; margin: 0px;">
				<a href="deletereply.do?no=${ vo.getbNo() }"><i class="fas fa-times"></i></a><!-- 삭제 -->
				</div>
			</c:if>
			
			<div style="font-size: 14pt;">${ vo.getContent() }</div>
			<hr style="color: #999999; border-color: #999999; background-color: #999999; border: none; height: 1px;">
		</c:forEach>
		
	</content>
		
	<aside>
		<jsp:include page="/WEB-INF/views/dialog/aside.jsp"/> 
	</aside>
</div>
</body>
</html>