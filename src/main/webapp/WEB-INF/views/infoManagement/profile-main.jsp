<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<header id="main-header">
		<jsp:include page="/WEB-INF/views/main/main-header.jsp"/> 
	</header>
	<content id="content">
		<jsp:include page="/WEB-INF/views/infoManagement/profile-content.jsp"/> 
	</content>
</body>
</html>