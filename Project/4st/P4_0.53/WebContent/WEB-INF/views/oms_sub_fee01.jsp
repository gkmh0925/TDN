<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<!-- 정산내역관리 > 배달료 부담금 마스터 -->
<head>
<title>배달료 부담금 마스터</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub05_05.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub05_05.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">정산내역관리<b class="greater">&gt;</b>배달료 부담금 마스터</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p id="bold_p">검색일자</p>
				<!-- 보완 사항 : id / class 재구성 필요 -->
				<input type="button" name="month" id="dateType1" class="dateType1 active_button" onclick="setSearchDate('1m')" value="1개월" />
				<input type="button" name="6month" id="dateType2" class="dateType2" onclick="setSearchDate('6m')" value="6개월" />
				<input type="button" name="12month" id="dateType3" class="dateType3" onclick="setSearchDate('12m')" value="12개월" />
				<input type='text' class='input_date' id='sdate' name='sdate' autocomplete='off' readonly /> 
					~ 
				<input type='text'class='input_date' id='edate' name='edate' autocomplete='off' readonly />
				
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>				
			<div class="btn_div">
				<button type="button" class='btn_search btn_reg_deliveryFee_lv1'>등록</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>		
<!-- 		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch()'>			
				<p id='snm_p'></p>
				<div class="search_form" id="snm_form">
					<input class='search_box' type="search" id='snm' name='snm' readonly /><img src="./resources/img/icon_search.png" id='snm_img' />
				</div>		
			</div>
		</section> -->

		<div class='main_div'>
			<div id="grid">${deliveryFeeList}</div>			
		</div>

		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
		
	</div>
</body>
</html>


