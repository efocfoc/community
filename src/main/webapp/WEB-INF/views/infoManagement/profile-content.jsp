<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/css/board&aside.css" rel="stylesheet" >
</head>
<body>
	<nav id="main-header" style="border-top: 1px solid #eeeeee;">
		글
		댓글
	</nav>
	<content>
	
	</content>
	<aside>
		<div class="aside">
			<c:out value="${sessionScope.name}"/><br>
			
		</div>
	</aside>
</body>
</html>