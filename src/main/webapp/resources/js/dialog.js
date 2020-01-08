/**
 * 
 */

/*다이얼로그 - */
function dialog() {

    var dialogBoxLogin = $('.dialog-login'),
    	dialogBoxJoin = $('.dialog-join'),
        dialogTriggerLogin = $('.dialog-trigger-login'),
        dialogTriggerJoin = $('.dialog__trigger_join'),
        dialogClose = $('.dialog-close'),
        dialogAction = $('.dialog__action');

    // 다이얼로그 열기
    dialogTriggerLogin.on('click', function(e) {
    	dialogBoxLogin.toggleClass('dialog--active');
    	
        e.stopPropagation() //상위 전파 막음
        
        wrapWindowByMask(); //마스크
        $(window).resize(function () { //창 크기 조절시
        	if(	$('.dialog--active').length){
            	wrapWindowByMask();
        	}
        })
        
    });
    
    dialogTriggerJoin.on('click', function(e) {
    	dialogBoxJoin.toggleClass('dialog--active');
    	
        e.stopPropagation() //상위 전파 막음
        
        wrapWindowByMask(); //마스크
        $(window).resize(function () { //창 크기 조절시
        	if(	$('.dialog--active').length){
            	wrapWindowByMask();
        	}
        })
        
    });

    // 다이얼로그 닫기 - 버튼
    dialogClose.on('click', function() {
    	dialogBoxLogin.removeClass('dialog--active');
    	dialogBoxJoin.removeClass('dialog--active');
    });

    // 다이얼로그 닫기 - esc누르면 꺼짐
    $(document).keyup(function(e) {
        if (e.keyCode === 27) {
        	dialogBoxLogin.removeClass('dialog--active');
        	dialogBoxJoin.removeClass('dialog--active');
        }
    });
    
    //다이얼로그 가운데 정렬
    dialogcenter(); //시작하자마자
    $(window).resize(function () { //창 크기 조절시
		dialogcenter();
	});

};

/*다이얼로그 위치 조절*/
function dialogcenter() {
	var dialogBoxLogin = $('.dialog-login');
	var dialogBoxJoin = $('.dialog-join');
	var widthLogin = dialogBoxLogin.width();
	var widthJoin = dialogBoxJoin.width();
	
	dialogBoxLogin.css({ 'left' : ($(window).width() - widthLogin) / 2});
	dialogBoxJoin.css({ 'left' : ($(window).width() - widthJoin) / 2});
	
}

/*다이얼로그 검은 막*/
function wrapWindowByMask(){
    //화면의 높이와 너비를 구한다.
    var maskHeight = $(document).height();  
    var maskWidth = $(window).width();  
	var mask  = $('#dialog-mask');
    
    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
    mask.css({'width':maskWidth,'height':maskHeight});  
	
    //애니메이션 효과
    mask.fadeIn(1000);      
    mask.fadeTo("slow",0.8);   
    
    //닫기 버튼을 눌렀을 때
    $('.dialog-close').click(function (e) {  
        //링크 기본동작은 작동하지 않도록 한다.
        e.preventDefault();  
        $('#dialog-mask').hide();  
    });       

    //검은 막을 눌렀을 때
    mask.click(function () {  
        $(this).hide();  
        $('.window').hide();  
        $('.dialog-login').removeClass('dialog--active');
        $('.dialog-join').removeClass('dialog--active');
    });
    
}

/*버튼 비활성화*/
function button_disable(){
	
	var login = $('#login');
	var join = $('#join');
	
	login.prop("disabled", true);
	join.prop("disabled", true);
	
	$("input").on("change keyup paste", function() {
		if($.trim($("#id").val()) != "" && $.trim($("#pwd").val()) != ""){
			login.prop("disabled", false);
			
			login.css('cursor', 'pointer');
			login.css('background', '#fffa77');
			login.css('color', 'black');
		}
		else{
			login.prop("disabled", true);
			login.css('cursor', 'no-drop');
			login.css('background', '#dddddd');
			login.css('color', '#666666');
		}
		
		if($.trim($("#join_id").val()) != "" && $.trim($("#join_nickname").val()) != ""
			&& $.trim($("#join_pwd").val()) != "" && $.trim($("#join_pwd_ok").val()) != ""){
			join.prop("disabled", false);
			
			join.css('cursor', 'pointer');
			join.css('background', '#fffa77');
			join.css('color', 'black');
		}
		else{
			join.prop("disabled", true);
			join.css('cursor', 'no-drop');
			join.css('background', '#dddddd');
			join.css('color', '#666666');
		}
		
		
	});
}

/*로그인 전송 ajax*/
function loginbutton(){
	$("#login").bind("click" ,function(e){
		e.preventDefault();
		var id = $("#id").val();
		var pwd = $("#pwd").val();
		
		var result = {
			"id" : id, "pwd" : pwd,
		};
		
		$.ajax({
			 type : "post", //post로 요청
             data : result, // json데이터를 전성
            	url:"login",
            //async:false,
            success: function(data){
            	switch (Number(data)) { //위치문
                case 0: // 0 일때
                    alert("아이디 또는 비밀번호가 일치하지 않습니다."); //경고 출력
                    break;
                case 1: // 1 일떄
                    window.location.href = "/lsy";

                default:
                    break;
                }       		
	        }
	       , error:function(request,status,error){
	           console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	           return false;
	       }
	    });//ajaxend
	});
};

/*회원가입 전송 ajax*/
function joinbutton(){
	$("#join").bind("click" ,function(e){
		e.preventDefault();
		console.log("아무말");
		
		var id = $("#join_id").val(),
			nickname = $("#join_nickname").val(),
			pwd = $("#join_pwd").val(),
			pwdok = $("#join_pwd_ok").val();
		
		if(pwd == pwdok){
			var result = {
					"id" : id, "pwd" : pwd, "nickname" : nickname
				};
			
			$.ajax({
				type : "post", //post로 요청
		        data : result, // json데이터를 전성
		        url:"join",
		            success: function(data){
		            	switch (Number(data)) { //위치문
		            	case 0: // 0 일때
		                    alert("이미 존재하는 아이디 입니다");
		                    break;
		            	case 1: // 0 일때
		                    alert("이미 존재하는 닉네임 입니다");
		                    break;
		                case 2: // 1 일떄
		                    window.location.href = "/lsy";

		                default:
		                    break;
		                }       		
			        }
			       , error:function(request,status,error){
			           console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			           return false;
			       }
			   });//ajax
		}//if pwd end
		
		else{
			alert("비밀번호가 서로 일치하지 않습니다");
		}
		
	});//join button click
};

/*회원가입 전송 ajax*/
function joinbutton(){
	$("#join").bind("click" ,function(e){
		e.preventDefault();
		console.log("아무말");
		
		var id = $("#join_id").val(),
			nickname = $("#join_nickname").val(),
			pwd = $("#join_pwd").val(),
			pwdok = $("#join_pwd_ok").val();
		
		if(pwd == pwdok){
			var result = {
					"id" : id, "pwd" : pwd, "nickname" : nickname
				};
			
			$.ajax({
				type : "post", //post로 요청
		        data : result, // json데이터를 전성
		        url:"join",
		            success: function(data){
		            	switch (Number(data)) { //위치문
		            	case 0: // 0 일때
		                    alert("이미 존재하는 아이디 입니다");
		                    break;
		            	case 1: // 0 일때
		                    alert("이미 존재하는 닉네임 입니다");
		                    break;
		                case 2: // 1 일떄
		                    window.location.href = "/lsy";

		                default:
		                    break;
		                }       		
			        }
			       , error:function(request,status,error){
			           console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			           return false;
			       }
			   });//ajax
		}//if pwd end
		
		else{
			alert("비밀번호가 서로 일치하지 않습니다");
		}
		
	});//join button click
};


/*user안에 정보 기능 클릭시 보이기*/
function user(){
	 var userbutton = $('.userbutton');
	 var display = $('.user_display');
	
	 userbutton.on("click" ,function(e){
		e.preventDefault();
		e.stopPropagation()
		display.css('visibility', 'visible');
	}); 
	 
	 display.on("click" ,function(e){
			e.stopPropagation()
	}); 
		 
	 $(window).click(function(e) {
		 e.stopPropagation()
		 if (!e.target.display) {
			 display.css('visibility', 'hidden');
		    }
	});
};


// 문서 로드 시 기능 수행
$(function() {
	joinbutton();
	loginbutton();
    dialog();
    button_disable();
    user();
});

