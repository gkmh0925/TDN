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
<link rel="stylesheet" type="text/css" href="./resources/css/sub05_01_insert.css">
<script src="./resources/js/sweetalert.min.js"></script>
<script>


function insertLv2() {
    const ctgr_nm = $('#ctgr_nm').val();
    const seq = $('#seq').val();
    const ch_cd = $('#ch_cd').val();
    const ctgr_type = $('#ctgr_type').val();
    
        if (isEmpty(ctgr_nm)) {
            $('#ctgr_nm').text("카테고리명을 입력해주세요.");
            $('#cd_chectgr_nmck').css('color', 'red');
            return;
        } else { 
            $('#ctgr_nm').text("");
        }
    
    
    if (isEmpty(seq)) {
        $('#seq').text("정렬순서를 입력해주세요.");
        $('#seq').css('color', 'red');
        return;
        
    } else if (isEmpty(ch_cd)) {
        $('#ch_cd').text("채널사 코드를 선택주세요.");
        $('#ch_cd').css('color', 'red');
        return;
        
    } else if (isEmpty(ctgr_type)) {
        $('#ctgr_type').text("카테고리 타입를 선택주세요.");
        $('#ctgr_type').css('color', 'red');
        return;
    }
    
    if (ctgr_nm != null && seq != null && ch_cd != null && ctgr_type != null) {
        $.ajax({
            url: "categoryLv2Insert",
            type: "POST",
            async: false,
            data: {
            	ctgr_type,
            	ctgr_nm,
                seq,
                ch_cd
                
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

	


</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">상품관리<b class="greater">&gt;</b>플랫폼사별 카테고리 조회<b class="greater">&gt;</b>플랫폼사별 카테고리 조회</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<div class='div_header'>
			<div class="btn_div">
				<button type="button" class='btn_insert' id="btn_insert" onclick='insertLv2();'>등록</button>
				<button class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</div>
		
		<div class='main_div'>
		    <table id="f_table">
		        <tr>
		            <th colspan='2' class='mtitle'>카테고리 등록</th>
		        </tr>
		        <tr>
		            <th class='stitle'>카테고리명</th>
		            <td><input type="text" name="ctgr_nm" id="ctgr_nm" autocomplete="off" placeholder="한글 사용 가능"/></td>
		        </tr>
		        <tr>
		            <th class='stitle'>정렬순서</th>
		            <td><input type="text" name="seq" id="seq" autocomplete="off" placeholder="숫자"/></td>
		        </tr>
		        <tr>
					<th class='stitle'>채널사코드</th>
					<td>
						<select class="select_box" title="채널사코드" name="ch_cd" id="ch_cd">
						    <option value="">-선택-</option>
						    <c:forEach items="${GetAllCpnCd}" var="company">
							    <option value="${company.CPN_CD}">${company.CPN_CD} / ${company.CPN_NM}</option>
							    

							</c:forEach>   
						</select>
					</td>
				<tr>
				<tr>
					<th class='stitle'>카테고리타입</th>
					<td>
						<select class="select_box" title="카테고리타입" name="ctgr_type" id="ctgr_type">
						    <option value="">-선택-</option>
						    <option value="item">item</option>
						    <option value="home">home</option>
						</select>
					</td>
				<tr>
		    </table>
		</div>

		

	</div>
</body>

</html>
