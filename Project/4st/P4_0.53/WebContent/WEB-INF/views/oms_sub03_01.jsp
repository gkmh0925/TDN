<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>주문 내역 관리</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub03_01.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub03_01.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script>
function refresh() {
	const userLv = "${UserInfo.USER_LV}";
	if(userLv != 'S'){
		$('#snm').val('');
		$('#scd').val('');
	}
	if(userLv != 'H'){
		$('#ocnm').val('');
		$('#occd').val('');
	}
	$('#ono').val('');
	$('#chono').val('');
	$('#ordst').val('');
}
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
		<div class="page_div">
		<header>
			<div id="header_title">주문내역관리<b class="greater">&gt;</b>주문내역조회</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p id="bold_p">주문일자</p>
				<input type="button" name="day" id="dateType1" class="dateType1 active_button" onclick="setSearchDate('1d')" value="오늘" /><input
					type="button" name="week" id="dateType2" class="dateType2" onclick="setSearchDate('7d')" value="일주일" /><input
					type="button" name="month" id="dateType3" class="dateType3" onclick="setSearchDate('1m')" value="1개월" /> <input
					type='text' class='input_date' id='sdate' name='sdate' autocomplete='off' readonly /> ~ <input type='text'
					class='input_date' id='edate' name='edate' autocomplete='off' readonly />
					
				<!-- 여기부터 추가 -->
				<p id="bold_p">MFC 구분</p>
				<select class='title_box' id="stype" name='stype'>
					<option class="upload_option" value='all'>전체</option>
					<option class="upload_option" value='nomal'>일반매장</option>
					<option class="upload_option" value='bsdc'>BSDC</option>
				</select>  
				<!-- 여기까지 추가 -->
				
			</div>
			<div class="btn_div">
				<button type="button" class='btn_excel' onclick='ExcelDown();' disabled>엑셀 다운로드</button>
			</div>
		</section>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch()'>
				<c:if test="${UserInfo.USER_LV ne 'S'}">
					<p id='snm_p'>매장명</p>
					<div class="search_form" id="snm_form">
						<input class='search_box' type="search" id='snm' name='snm' readonly /><img src="./resources/img/icon_search.png"
							id='snm_img' />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UserInfo.USER_LV eq 'S'}">
						<input type='text' id='scd' value='${UserInfo.CPN_CD }' hidden />
					</c:when>
					<c:otherwise>
						<input type='hidden' id='scd' />
					</c:otherwise>
				</c:choose>
				<c:if test="${UserInfo.USER_LV ne 'H'}">
					<p id='ord_nm_p'>채널사명</p>
					<div class="search_form">
						<input class='search_box' type="search" id='ocnm' readonly /><img src="./resources/img/icon_search.png"
							id='ocnm_img' /> <input type='text' id='occd' hidden />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UserInfo.USER_LV eq 'H'}">
						<input type='hidden' id='occd' value='${UserInfo.CPN_CD }' />
					</c:when>
					<c:otherwise>
						<input type='hidden' id='occd'/>
					</c:otherwise>
				</c:choose>
				<p id='ono_p'>주문번호</p>
				<div class="search_form2">
					<input type="search" class='search_box' type="search" id='ono' autocomplete="off" />
				</div>
				<p id='ono_p'>상세주문번호</p>
				<div class="search_form2">
					<input class='search_box' type="search" id='chono' autocomplete="off" />
				</div>
				<select class='select_box' id="ordst" name='ordst'>
					<option value='' selected>- 주문상태 -</option>
					<c:forEach var="ordst" items="${ordstate }">
						<option value='${ordst.DEFINITION_CD }'>${ordst.DEFINITION_NM }</option>
					</c:forEach>
					<option value='Success'>주문성공건</option>
					<option value='Canceled'>주문실패건</option>
				</select>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>
			<div class="btn_div">
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<div class='main_div'>
			<div id="grid"></div>
		</div>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
	</div>
</body>
</html>