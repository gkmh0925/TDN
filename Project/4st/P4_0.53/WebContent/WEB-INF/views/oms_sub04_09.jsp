<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>POS데이터 대사 조회</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery.ui.monthpicker.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub04_09.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub04_09.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<span id="header_title">정산내역관리<b class="greater">&gt;</b>POS데이터 대사조회</span>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p>대사연월</p>
				<input type='text' class='input_date' id='month' name='month' autocomplete='off' readonly />
					
				<p id='bold_p'>대사데이터</p>
				<select class='select_box' id="comp_data" name='comp_data'>
					<option class="upload_option" value='relay'>중계</option>
					<option class="upload_option" value='order'>주문채널사</option>
					<option class="upload_option" value='delivery'>배달대행사</option>
				</select>
				
				<!-- 230119 추가 -잠시대기- -->
				  <p id="bold_p">MFC 구분</p>
			    <select class='title_box' id="stype" name='stype'>
					<option class="upload_option" value='all'>전체</option>
					<option class="upload_option" value='nomal'>일반매장</option>
					<option class="upload_option" value='bsdc'>BSDC</option>
				</select>  
				<!-- 여기까지 추가 -->
			</div>
			<div class="btn_div">
				 <!-- <button type="button" class='btn_excel' id="excel_download" onclick='ExcelDown();' disabled>엑셀 다운로드</button>  -->
<!-- 				<section class='s_header'>
			      <div class="btn_div">
					<p id="warn_p"><strong id="strong"></strong>&nbsp; 표시는 수정이 불가합니다. </p>
					<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
					<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
					<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
				  </div>
				</section> -->
				<button type="button" class='btn_excel' id="excel_download" onclick="ExcelDown();">엑셀 다운로드</button>				
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<div class='main_div'>
			<div id="grid">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//엑셀 다운
	function ExcelDown() {

 		const month = $("#month").val().replaceAll(/[^0-9]/g, '');
		const comp_data = $('#comp_data option:selected').val();
		const stype = $('#stype option:selected').val();

		let path = 'Excel_PosCompare_Download';

		const params = {
			month,
			comp_data,
			stype
		}
		post(path, params); 
	}
	</script>
</body>
</html>
