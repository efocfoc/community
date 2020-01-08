<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<span class="dialog-close">&#x2715;</span>
  	<h2 class="dialog-title">로그인</h2>
  		
  	<p class="dialog-content">
  		
  		
  	<form method="post" autocomplete="off" onsubmit="return loginbutton()">
  		
  		<div class="input">
			<input class="id input__field-login" type="text" name ="id" size="10" id="id" maxlength="10"/>
  			<label class="input__label input__label--haruki" for="username" for="id">
  			<span class="input__label-content input__label-content--haruki">아이디</span>
  			</label>            
  		</div>
  		
  		<div class="input">
  				
			<input class="pwd input__field-login" type="password" name ="pwd" size="10" id="pwd" maxlength="15"/>
			<label class="input__label input__label--haruki" for="password">
				<span class="input__label-content input__label-content--haruki">비밀번호</span>
			</label>
  		</div>
  		
		<!-- <p>로그인 유지</p> -->
		<br><br>
		
		<input type="submit" id="login" value="로그인">
			
		<!-- <p>로그인에 문제가 있나요? 아이디/비밀번호 찾기</p> -->
		<p>아직 회원이 아니신가요? <span class="dialog__trigger_join" style="background-color: white; color:black; padding:0px;">회원가입</span></p>
		
		
		</form>
  		
  	</p><!-- content-end -->
</body>
</html>