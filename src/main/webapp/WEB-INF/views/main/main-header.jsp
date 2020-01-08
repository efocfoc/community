<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base target="_self">
<title>Insert title here</title>
	<!-- css , js -->
	<link href="${pageContext.request.contextPath}/resources/css/main-header.css" rel="stylesheet" >
	<link href="${pageContext.request.contextPath}/resources/css/main-layout.css" rel="stylesheet" >
	<!-- 아이콘 -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
	<!-- 다이얼로그 js -->
	<script src="${pageContext.request.contextPath}/resources/js/dialog.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
</head>
<body>
	<!-- 로고 -->
	<a href="main"><img src="/lsy/resources/image/logo4.png"></a>
	
	<%
		String id = (String)session.getAttribute("id");
		if(id != null){
	%>
		<div id="float-right" class="membermenu">
			<button class="userbutton" type="submit"><i class="fas fa-user"></i> <c:out value="${sessionScope.name}"/></button>
			<div class="user_display">
				<ul>
					<li> <div class="user_display_title">나의 페이지</div>
						<ul class="user_display_detail">
							<li>
								<a href="myProfile.do" class="postlink">
								<i class="fas fa-user-circle"></i>내 프로필</a>
							</li>
							<li><i class="fas fa-user-cog"></i>사용자 설정</li>
							<li>
								<a href="logout.do" class="postlink">
								<i class="fas fa-sign-out-alt"></i>로그아웃 </a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			
		</div>
	
		
	<%	
		}else{//로그인하기전
	%>
		
	<div id="float-right">
	<!-- 로그인 -->
		
	<div id="dialog-mask"></div> <!-- 다이얼로그 실행시 검은 화면 -->
		
	<button class="dialog-trigger-login">로그인</button>

	<div class="dialog-login">
  		<jsp:include page="/WEB-INF/views/dialog/login.jsp"/> 
	</div>  
		
	<!-- 회원가입 -->
		
	<button class="dialog__trigger_join">회원가입</button>
	<div class="dialog-join">
  		<jsp:include page="/WEB-INF/views/dialog/join.jsp"/> 
	</div> <!-- join-end -->
		
	
	</div> <!-- float-right:end -->
	
	
	<%
		}  //else end
	%>
	<!-- select box 메뉴에 따라 화면 바뀌게 -->
	<div id="float-right" class="sort" >
	<select onchange="location.href=this.value" style="border-radius: 5px; padding:5px; height:30px; border: 1px solid #dfdfdf; margin-right: 5px;">
		<option>정렬</option>
		<option value="SortAll.do">전체</option>
		<option value="SortHot.do">인기</option>
		<option value="SortNew.do">최신</option>
	</select>
	</div>
	<!-- 검색 -->
	<div id="float-right">

		<form autocomplete="off" action="search?search=${param.search}" method="get">
			<div class="search-box" >
      			<input type="search" name="search" id="search" placeholder="검색" />
      			<button type="submit" class="icon"><i class="fa fa-search"></i></button>
  			</div>
		</form>
	</div>
	
	<script>
			(function() {
				// trim polyfill : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/Trim
				if (!String.prototype.trim) {
					(function() {
						// Make sure we trim BOM and NBSP
						var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
						String.prototype.trim = function() {
							return this.replace(rtrim, '');
						};
					})();
				}

				[].slice.call( document.querySelectorAll( 'input.input__field-login' ) ).forEach( function( inputEl ) {
					// in case the input is already filled..
					if( inputEl.value.trim() !== '' ) {
						classie.add( inputEl.parentNode, 'input--filled' );
					}

					// events:
					inputEl.addEventListener( 'focus', onInputFocus );
					inputEl.addEventListener( 'blur', onInputBlur );
				} );

				function onInputFocus( ev ) {
					classie.add( ev.target.parentNode, 'input--filled' );
				}

				function onInputBlur( ev ) {
					if( ev.target.value.trim() === '' ) {
						classie.remove( ev.target.parentNode, 'input--filled' );
					}
				}
			})();
		</script>
</body>
</html>