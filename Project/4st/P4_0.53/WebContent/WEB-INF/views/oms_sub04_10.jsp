<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>중계 수수료 정산 조회</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery.ui.monthpicker.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub04_10.css" /> 
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub04_10.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title">정산내역관리<b class="greater">&gt;</b>중계 수수료 정산 조회</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p id="bold_p">정산 월</p>
				 <input type='text' class='input_date' id='tmonth' name='tmonth' autocomplete='off' readonly />
			</div>
			
			<div class="btn_div">
				<c:if test="${UserInfo.USER_LV eq 'A' || UserInfo.USER_LV eq 'M'}">
					<button type="button" class='btn_excel' id="excel_upload" onclick='upload_click();'>정산 파일 업로드</button>
				</c:if>
				<button type="button" class='btn_excel' id="excel_download" onclick='ExcelDown();' disabled>엑셀 다운로드</button>
				<button type="button" class='btn_excel' id="excel_sample_download" onclick='download_click();'>업로드용 샘플</button>
			</div>
		</section>
		<section class='div_header'>
			<div class='search_div'  >	 
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
		
		<div class="hide_div">
			<div class="close_button" onclick="uploadClose()"></div>
			<br><br>
			<form name="excelUploadForm" id="excelUploadForm">
				<div class="filebox">
					<label for="excelFile">파일선택</label> <input type="file" id="excelFile" name="excelFile" class="upload-hidden">
					<input class="upload-name" id="upload_name" disabled="disabled">
				</div>
				<section class='div_fheader2'>
					<div class='search_div'>
						<p style="font-size:15px">정산 월</p>
						<input type='text' class='input_date2' id='month' name='month' autocomplete='off' readonly />
					</div>
				</section>
				<div class="upload_div2">
					<button type="button" class='btn_excel' id="btn_excel1" onclick='excelupload();'>정산 파일 업로드</button>
				</div>
			</form>
		</div>
		
		<!-- 업로드샘플 폼  -->
		<div class="shide_div">
		<div class="close_button" onclick="uploadClose()"></div>
			<form name="excelSampleForm_relay" id="excelSampleForm_relay">
				<div class="download_pop">
					
					<button type="button" class='btn_excel' id="btn_excel1" onclick='ExcelDown_settle_Relay();'>샘플 다운로드</button>
				</div>
			</form>
		</div>
		
		
	</div>
</body>
</html>
