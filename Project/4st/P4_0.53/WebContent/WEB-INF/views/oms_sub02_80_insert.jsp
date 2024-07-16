<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>플랫폼사별 카테고리 등록</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub02_80_detail.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/sub05_01_insert.css">
<script src="./resources/js/sweetalert.min.js"></script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">
				상품관리<b class="greater">&gt;</b>플랫폼사별 카테고리 검색<b class="greater">&gt;</b>플랫폼사별 카테고리 등록
			</div>
			<img src="./resources/img/Calculate Management Program.png"
				id="top_logo" />
		</header>
		<div class='div_header_right'>
			<div class="btn_div">
				<button type="button" class='btn_insert' id="btn_insert" onclick='insertLv1();'>등록</button>
				<button class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</div>

		<div class='main_div'>
			<table id="f_table">
				<tr>
					<th colspan='2' class='mtitle'>카테고리 등록</th>
				</tr>

				<tr>
					<th class='stitle'>카테고리코드</th>
					<td><input type="text" name="ctgr_cd" id="ctgr_cd"
						autocomplete="off" placeholder="플랫폼사 카테고리 코드 입력" /></td>
				</tr>
				<tr>
					<th class='stitle'>카테고리명</th>
					<td><input type="text" name="ctgr_nm" id="ctgr_nm"
						autocomplete="off" placeholder="한글 사용 가능" /></td>
				</tr>
				<tr>
					<th class='stitle'>정렬순서</th>
					<td><input type="text" name="seq" id="seq" class="num_input"
						autocomplete="off" placeholder="숫자" /></td>
				</tr>
<!-- 				<tr>
					<th class='stitle'>연결 카테고리 코드</th>
					<td><input type="text" name="link_cd" id="link_cd"
						autocomplete="off" placeholder="코드" /></td>
				</tr> -->
				<tr>
					<th class='stitle'>카테고리 설명</th>
					<td><input type="text" name="ctgr_info" id="ctgr_info"
						autocomplete="off" placeholder="카테고리 설명" /></td>
				</tr>
				<tr>
					<th class='stitle'>채널사명</th>
					<td><select class="select_box" title="채널사명" name="ch_cd" id="ch_cd">
							<option value="">-선택-</option>
							<c:forEach items="${GetAllCpnCd}" var="company">
								<option value="${company.CPN_CD}">${company.CPN_CD}/
									${company.CPN_NM}</option>
							</c:forEach>
					</select></td>
				<tr>
				<tr>
					<th class='stitle'>카테고리타입</th>
					<td><select class="select_box" title="카테고리타입" name="ctgr_type" id="ctgr_type">
							<option value="">-선택-</option>
							<option value="item">상품</option>
							<option value="home">홈</option>
					</select></td>
				<tr>
			</table>
		</div>
	</div>
</body>
<script>

$('#ch_cd').change(function() {
    updateCtgrTypeOptions($(this).val());
});

function updateCtgrTypeOptions(selectedChCd) {
	
    let options = [];

    switch(selectedChCd) {
        case '01':
            options = [
                { value: 'YGY_CTGR', text: '요기요 카테고리' }
            ];
            break;
        case '07':
            options = [
                { value: 'BMIN_PRD', text: '배민 상품 카테고리' },
                { value: 'BMIN_HOME', text: '배민 가게홈 카테고리' }
            ];
            break;
    }

    $('#ctgr_type').empty();

    options.forEach(function(option) {
        $('#ctgr_type').append(new Option(option.text, option.value));
    });
}


function insertLv1() {
	
	var ctgr_cd = $("#ctgr_cd").val();
    var ctgr_nm = $('#ctgr_nm').val();
    var seq = $('#seq').val();
    var ctgr_info = $("#ctgr_info").val();
    var ch_cd = $('#ch_cd').val();
    var ctgr_type = $('#ctgr_type').val();
    
    if (isEmpty(ctgr_cd)) {

        swal({
            text: '카테고리코드를 입력해주세요.',
            icon: "info"
        });
        return
    	
    }else if(isEmpty(ctgr_nm)){
        swal({
            text: '카테고리명을 입력해주세요.',
            icon: "info"
        });
        return
    }else if (isEmpty(seq)) {
        swal({
            text: '정렬순서를 입력해주세요.',
            icon: "info"
        });
        return
    } else if(isEmpty(ctgr_info)){
        swal({
            text: '카테고리 설명을 입력해주세요.',
            icon: "info"
        });
        return
    }else if (isEmpty(ch_cd)) {

        swal({
            text: '채널사 명를 선택주세요.',
            icon: "info"
        });
        return
    } else if (isEmpty(ctgr_type)) {
        swal({
            text: '카테고리 타입를 선택주세요.',
            icon: "info"
        });
        return
    }
    
    if (ctgr_cd != null && ctgr_nm != null && seq != null && ctgr_info != null && ch_cd != null && ctgr_type != null) {
    	
    	if(ch_cd == '07'){		//배달의민족
    		
        	if(ctgr_type == 'home'){
        		ctgr_type = 'BMIN_HOME';
        	}else if(ctgr_type == 'item'){
        		ctgr_type = 'BMIN_PRD';
        	}
    	
    	}else if(ch_cd == '01'){	//요기요
        		ctgr_type = 'YGY_CTGR';
    	}
    	
         $.ajax({
            url: "categoryInsert",
            type: "POST",
            async: false,
            data: {
            	ctgr_cd,
            	ctgr_nm,
                seq,
                ctgr_info,
                ch_cd,
            	ctgr_type
                
            },
            success: function(data) {
                if (data == "true") {
                    swal({
                        text: "등록이 완료되었습니다.",
                        icon: "success"
                    }).then(function() {
                        opener.parent.search_go();
                        window.close();
                    });
                } else if(data == "dupl"){
                    swal({
                        text: '이미등록되어 있습니다.',
                        icon: "error"
                    }).then(function() {
                        location.href = "userRegister";
                    });  
                } else {
                    swal({
                        text: '등록을 실패했습니다.',
                        icon: "error"
                    }).then(function() {
                        location.href = "userRegister";
                    });     
                }
            }
        });	 
    }
}

document.addEventListener('DOMContentLoaded', function() {
    var numericInputs = document.querySelectorAll('.num_input');

    numericInputs.forEach(function(input) {
        input.addEventListener('keydown', function(event) {
            if (event.key !== 'Backspace' && isNaN(Number(event.key))) {
                event.preventDefault();
            }
        });
    });
});


</script>
</html>
