$(document).ready(function() {
	 
     $('#userId').focus();
	 
     $('.login_insert').on('keypress', function(e) {
         if (e.keyCode == '13') {
             $('#loginbt').click();
         }
     });
     
     // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
	    let key = getCookie("key");
	    $("#userId").val(key); 
	     
	    if($("#userId").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
	        $("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
	        $('#userPw').focus();
	        
	    }
	     
	    $("#idSaveCheck").change(function(){ // 체크박스에 변화가 있다면,
	        if($("#idSaveCheck").is(":checked")){ // ID 저장하기 체크했을 때,
	            setCookie("key", $("#userId").val(), 30); // 30일 동안 쿠키 보관
	        }else{ // ID 저장하기 체크 해제 시,
	            deleteCookie("key");
	        }
	    });
	     
	    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
	    $("#userId").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
	        if($("#idSaveCheck").is(":checked")){ // ID 저장하기를 체크한 상태라면,
	            setCookie("key", $("#userId").val(), 30); // 30일 동안 쿠키 보관
		}
	});     
 });
 
	function setCookie(cookieName, value, exdays){
	    let exdate = new Date();
	    exdate.setDate(exdate.getDate() + exdays);
	    let cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
	    document.cookie = cookieName + "=" + cookieValue;
	}
	 
	function deleteCookie(cookieName){
	    let expireDate = new Date();
	    expireDate.setDate(expireDate.getDate() - 1);
	    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
	}
	 
	function getCookie(cookieName) {
	    cookieName = cookieName + '='; 
	    let cookieData = document.cookie;
	    let start = cookieData.indexOf(cookieName);
	    let cookieValue = '';
	    if(start != -1){
	        start += cookieName.length;
	        let end = cookieData.indexOf(';', start);
	        if(end == -1)end = cookieData.length;
	        cookieValue = cookieData.substring(start, end);
	    }
	    return unescape(cookieValue);
	}

// 로그인 정보 체크
 function loginCheck() {
	const ACCOUNT = $('#userId').val();
	const PASSWORD = $('#userPw').val();
     
	if(isEmpty(ACCOUNT)){
     	swal('아이디를 입력해주세요.').then(function() {
     		$('#userId').focus();
		});
    	
    	return;
    	
	}else if(isEmpty(PASSWORD)){
     	swal('비밀번호를 입력해주세요.').then(function() {
     		$('#userPw').focus();
		});
     	return;
	}
	$('#loginbt').attr('disabled', true);
	$.ajax({
		type: "POST",
		url: "loginCheck",
		async: true,
		data: {
		    ACCOUNT,
		    PASSWORD
		},
		success: function(data) {
			if(data.duplication == true){
				swal({
					title: "중복 로그인",
					text: "새로운 로그인이 감지되어 기존 세션을 종료합니다.",
					icon: "error"
				}).then(function() {
					location.replace("main");
				});
			}else if (data.logStat) {
				$('#loginbt').attr('disabled', false);
				location.replace("main");
				
			}else {
				swal('잘못된 아이디이거나, 비밀번호가 틀렸습니다.').then(function(){
					$('#loginbt').attr('disabled', false);
				}).then(function(){
					$('#userPw').focus();
				});	
				return;
			}
         },error: function(data)	{
        	 swal({
					title: "ERROR",
					text: "알 수 없는 오류가 발생했습니다.",
					icon: "error"
				}).then(function(){
					$('#loginbt').attr('disabled', false);
				});	
         }
     });
 }