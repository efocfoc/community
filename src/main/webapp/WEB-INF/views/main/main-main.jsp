<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메인페이지</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<header id="main-header">
		<jsp:include page="/WEB-INF/views/main/main-header.jsp"/> 
	</header>
	<content id="content">
		<jsp:include page="/WEB-INF/views/main/main-content.jsp"/> 
	</content>

</body>
</html>