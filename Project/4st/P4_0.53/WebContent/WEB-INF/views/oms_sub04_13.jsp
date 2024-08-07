<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>점별 월 정산 조회 화면</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery.ui.monthpicker.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub01_01.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub04_09.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/sub04_13.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title"><p>정산내역관리<b class="greater">&gt;</b>점별 월 정산 조회 화면</p></div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p>대사연월</p>
				<input type='text' class='input_date' id='month' name='month' autocomplete='off' readonly />
					
				<p id="bold_p">MFC 구분</p>
			    <select class='title_box' id="stype" name='stype'>
					<option class="upload_option" value='all'>전체</option>
					<option class="upload_option" value='nomal'>일반매장</option>
					<option class="upload_option" value='bsdc'>BSDC</option>
				</select>  
			</div>
			<div class="btn_div">
			</div>
		</section>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch();'>
				<c:if test="${UserInfo.USER_LV ne 'S'}">
					<p id='snm_p'>매장명</p>
					<div class="search_form" id="snm_form">
						<input class='search_box' type="search" id='snm' name='snm' readonly /><img src="./resources/img/icon_search.png"
							id='snm_img' />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UserInfo.USER_LV eq 'S'}">
						<input type='hidden' id='scd' name='scd' value='${UserInfo.CPN_CD }' />
					</c:when>
					<c:otherwise>
						<input type='hidden' id='scd' name='scd'/>
					</c:otherwise>
				</c:choose>
				<c:if test="${UserInfo.USER_LV ne 'H'}">
					<p id='ord_nm_p'>채널사명</p>
					<div class="search_form">
						<input class='search_box' type="search" id='ocnm' name='ocnm' readonly /><img src="./resources/img/icon_search.png"
							id='ocnm_img' /> <input type='hidden' id='occd' name='occd'/>
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UserInfo.USER_LV eq 'H'}">
						<input type='hidden' id='occd' name='occd' value='${UserInfo.CPN_CD }'/>
					</c:when>
					<c:otherwise>
						<input type='hidden' id='occd' name='occd'/>
					</c:otherwise>
				</c:choose>
				
				
				<input type='hidden' id='dccd' name='dccd'/>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
				
			</div><!-- search_div -->
			<div class="btn_div">
				<button type="button" class='btn_excel' id="excel_download" onclick="ExcelDown();">엑셀 다운로드</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section><!-- div_header -->
		<section class='main_div'>
			<div id="grid"></div>
		</section>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
	</div>
</body>
</html>