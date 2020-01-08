<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/css/search.css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/resources/js/board.js"></script>
<style type="text/css">

	.board{
	display: block; background-color: #fffee7; border-radius: 5px; padding:5px;}

</style>
</head>
<body>
	<header id="search-header">
		<form autocomplete="off" action="search" method="get">
			<div class="search-searchbox" >
				<div class="box">
      			<input type="search" name="search" id="search" placeholder="검색" value="<c:out value="${search}"/>"/>
      			<button type="submit" class="icon"><i class="fa fa-search"></i></button>
  				</div>
  			</div>
		</form>
		<br>
		<div class="nav">

		<a href="search?search=${encoding}&type=all">전체</a>
		<a href="search?search=${encoding}&type=board">게시물</a>
		<a href="search?search=${encoding}&type=groups">그룹</a>
		</div>
		
	</header> 
	
	<div id="search-content">
	
	<c:if test ="${param.type ne 'groups'}">
	
	<span style="font-size:15pt;">게시판</span> 
	
	<!-- 게시판에 대한 검색 결과 -->
	<c:choose>
		<%-- 검색결과가 존재 --%>
		<c:when test= "${fn:length(BboardSearch) > 0}"> 
			

			<c:choose>
			
				<c:when test ="${param.type eq 'board'}"> 
					<br>
					<ul class="lists" >
					<c:forEach items="${BboardSearch}" var="vo">
		        	<li class="lists__item js-load" style="">
						<a href="post.do?no=${vo.getBoardNo()}" class="board">
						${ vo.getgName() } 
						<span style="color:#666666;">${vo.getWriter()} ${vo.getRegdate()}</span>
						<br>
						<span style="font-weight:bold; font-size: 15pt;">${vo.getTitle()}</span><br>
						</a>
					</li>
					</c:forEach>
					</ul>
				</c:when>

				<c:otherwise>
					<a href="search?search=${encoding}&type=board" id="float-right" >더보기▶</a><br>
					<ul class="lists" >
					<c:forEach items="${BboardSearch}" var="vo" begin="0" end="4">
		        	<li class="lists__item js-load" style="">
						<a href="post.do?no=${vo.getBoardNo()}" class="board">
						${ vo.getgName() } 
						<span style="color:#666666;">${vo.getWriter()} ${vo.getRegdate()}</span>
						<br>
						<span style="font-weight:bold; font-size: 15pt;">${vo.getTitle()}</span><br>
						</a>
					</li>
					</c:forEach>
					</ul>
				</c:otherwise>
				
			</c:choose>
			
		</c:when>
		
		<%-- 검색결과가 없음 --%>
		<c:otherwise>
			<div class="noresult">
				<img src="/lsy/resources/image/noresult.jpg" style="width:100px; height: auto;"><br>
				너무...조용합니다
			</div>
		</c:otherwise>
	</c:choose> 
	
	<br><br>
	
	</c:if>
	
	
	
	<c:if test ="${param.type ne 'board'}">
	
	<span style="font-size:15pt;">그룹</span>
	<!-- 그룹에 대한 검색 결과 -->
	<c:choose>
		<%-- 검색결과가 존재 --%>
		<c:when test= "${fn:length(GboardSearch) > 0}"> 
			

			<c:choose>
				<c:when test ="${param.type eq 'groups'}"> 
					<br>
					<c:forEach items="${GboardSearch}" var="vo">
	        			${vo.getgName() }
					</c:forEach>
				
				</c:when>

				<c:otherwise>
					<a href="search?search=${encoding}&type=groups" id="float-right" >더보기▶</a><br>
					<ul class="lists" >
					<c:forEach items="${GboardSearch}" var="vo" begin="0" end="4">
						<li class="lists__item js-load">
						<a href="groups.do?no=${vo.getgNo()}" class="board">
						${vo.getgName() }
						</a>
					</li>
					</c:forEach>
					</ul>
				
					
					
				</c:otherwise>
			
			</c:choose>
		
		
			
		</c:when>
		
		<%-- 검색결과가 없음 --%>
		<c:otherwise>
			<div class="noresult">
				<img src="/lsy/resources/image/noresult.jpg" style="width:100px; height: auto;"><br>
				너무...조용합니다
			</div>
		</c:otherwise>
	</c:choose> 
	
	</c:if>
	
	</div>
	
</body>
</html>