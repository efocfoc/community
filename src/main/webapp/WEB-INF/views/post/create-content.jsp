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
<script src="${pageContext.request.contextPath}/resources/js/selectcontent.js"></script>
<script type="text/javascript">

function update(){
	$("#updatepost").on("click" ,function(){
		var title = $(".titlebox").val();
		var content = $(".contentbox").val();
		var bNo = "${param.no}";
		
		if(title == "" || content == "" ){
			alert("제목을 입력해주세요");
			return false;
		}
		
		if(title == "" || content == "" ){
			alert("내용을 입력해주세요");
			return false;
		}
		
		var result = {
			"title" : title, "content" : content, "boardNo" : bNo,
		};
		
		$.ajax({
			type : "post", //post로 요청
		    data : result, // json데이터를 전성
		    url:"updatePost",
		   //async:false,
		   success: function(data){
		        window.location.href = "/lsy/post.do?no=" + bNo;
			}
			, error:function(request,status,error){
			     console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			 return false;
			}
		});//ajaxend

		
	  });
}



</script>

</head>
<body>
	<div class="box" >

	<content>
	
	
	<c:if test ="${vo.getgNo() eq null}">
	글쓰기
	<hr>
	<select id="selectgroups" style="border-radius: 5px; padding:5px; height:30px; border: 1px solid #dfdfdf; margin-right: 5px; margin-bottom: 10px;">
		<option>그룹을 선택하세요</option>
		<c:forEach items="${getGroups}" var="vo">
			<option id="opgroups" value="${vo.getgNo()}">
				${vo.getgName()}
			</option>
		</c:forEach>
	
	</select>
	</c:if>
	
	<c:if test ="${vo.getgNo() ne null}">
	글수정
	<hr>
	</c:if>
	
	<br>
	
	<div class="writebox">

	<form method="post">

	<input type="text" name="title" size="10" placeholder="제목" class="titlebox" maxlength="50" value="${ vo.getTitle() }"><br><br>
	
	<textarea name="content" placeholder="내용" class="contentbox" maxlength="1000"><c:if test ="${vo.getgNo() ne null}">${ vo.getContent() }</c:if></textarea>
	
	<br><br>
	<c:if test ="${vo.getgNo() ne null}"><input type = "submit" value="글수정" id="updatepost" class="inputpost"></c:if>
	<c:if test ="${vo.getgNo() eq null}"><input type = "submit" value="글등록" id="inputpost" class="inputpost"></c:if>
	</form>
	
	</div>
	
	</content>
	
	<aside>
		<jsp:include page="/WEB-INF/views/dialog/aside.jsp"/> 
	</aside>
</div>
</body>
</html>