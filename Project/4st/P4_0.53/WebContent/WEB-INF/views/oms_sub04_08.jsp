<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>배달 정산 집계조회</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery.ui.monthpicker.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub04_08.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub04_08.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title">정산내역관리<b class="greater">&gt;</b>배달정산집계조회</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p>집계연월</p>
				<input type='text' class='input_date' id='month' name='month' autocomplete='off' readonly />
				
				<!-- 230119 추가  잠시대기-->
				<p id="bold_p">MFC 구분</p>
				<select class='title_box' id="stype" name='stype'>
					<option class="upload_option" value='all'>전체</option>
					<option class="upload_option" value='nomal'>일반매장</option>
					<option class="upload_option" value='bsdc'>BSDC</option>
				</select> 
				<!-- 여기까지 추가 -->
				
			</div>
			<div class="btn_div">
				<button type="button" class='btn_excel' id="excel_upload" onclick='download_click();'>정산 파일 다운로드</button>
				<button type="button" class='btn_excel' id="excel_download" onclick='ExcelDown();' disabled>엑셀 다운로드</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<div class='main_div'>
			<div id="grid"></div>
		</div>
		<div class="hide_div">
			<div class="close_button" onclick="uploadClose()"></div>
			<form name="excelUploadForm" id="excelUploadForm">
				<div class="download_pop">
					<select class='upload_select_box' id='channel' name="channel">
						<option class="upload_option" value='Order'>주문채널사</option>
						<option class="upload_option" value='Delivery'>배달대행사</option>
					</select>
					
					<button type="button" class='btn_excel' id="btn_excel1" onclick='ExcelDown_settle();'>정산 파일 다운로드</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
