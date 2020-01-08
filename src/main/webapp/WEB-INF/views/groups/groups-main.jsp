<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$('.sort').hide();
});
</script>
<body>
<header id="main-header">
		<jsp:include page="/WEB-INF/views/main/main-header.jsp"/> 
	</header>
	<content id="content">
		<jsp:include page="/WEB-INF/views/groups/groups-content.jsp"/> 
	</content>
</body>
</html>