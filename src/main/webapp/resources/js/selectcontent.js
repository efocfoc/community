/**
 * 
 */

function opselect(){
	$("#inputpost").on("click" ,function(){
		var selected = $("#selectgroups option:selected").val(); //그룹아이디(gNo) 가져옴
		
		var title = $(".titlebox").val();
		var content = $(".contentbox").val();
		
		if(selected == "그룹을 선택하세요" ){
			alert(selected);
			return false;
		}
		
		if(title == "" ){
			alert("제목을 입력해주세요");
			return false;
		}
		
		if(content == "" ){
			alert("내용을 입력해주세요");
			return false;
		}
		
		
		
		var result = {
			"title" : title, "content" : content, "gNo" : selected,
		};
		
		$.ajax({
			 type : "post", //post로 요청
             data : result, // json데이터를 전성
            	url:"submitPost",
            //async:false,
            success: function(data){
            	window.location.href = "/lsy";
	        }
	       , error:function(request,status,error){
	           console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	           return false;
	       }
	    });//ajaxend

	  });
}



// 문서 로드 시 기능 수행
$(function() {
	opselect()
	update()
});
