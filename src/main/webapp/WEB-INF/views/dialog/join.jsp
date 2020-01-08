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
  	<h2 class="dialog-title">회원가입</h2>
  		
  	<p class="dialog-content">
  	<form method="post" autocomplete="off" onsubmit="return joinbutton()">
  		
  		<div class="input">
			<input class="id input__field-login" type="text" name ="id" size="10" id="join_id" maxlength="10"/>
  			<label class="input__label input__label--haruki" for="username" for="id">
  			<span class="input__label-content input__label-content--haruki">아이디</span>
  			</label>            
  		</div>
  		
  		<div class="input">
			<input class="id input__field-login" type="text" name ="id" size="10" id="join_nickname" maxlength="10"/>
  			<label class="input__label input__label--haruki" for="username" for="id">
  			<span class="input__label-content input__label-content--haruki">닉네임</span>
  			</label>            
  		</div>
  		
  		
  		<div class="input">
  				
			<input class="pwd input__field-login" type="password" name ="pwd" size="10" id="join_pwd" maxlength="15"/>
			<label class="input__label input__label--haruki" for="password">
				<span class="input__label-content input__label-content--haruki">비밀번호</span>
			</label>
  		</div>
  		
  		<div class="input">
  				
			<input class="pwd input__field-login" type="password" name ="pwd" size="10" id="join_pwd_ok" maxlength="15"/>
			<label class="input__label input__label--haruki" for="password">
				<span class="input__label-content input__label-content--haruki">비밀번호 확인</span>
			</label>
  		</div>
  		
  		이용 약관은 <a href="">여기</a>에서 확인해주세요<br>
  		
		<input type="submit" id="join" value="회원가입">
		
		
	</form>
  	</p><!-- content-end -->
</body>
</html>