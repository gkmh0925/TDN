<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>배달대행사 수수료 정산 조회</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery.ui.monthpicker.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub04_05.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub04_05.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title">정산내역관리<b class="greater">&gt;</b>배달대행사 수수료 정산 조회</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_fheader'>
			<div class='search_div'>
				<p id="bold_p">정산 월</p>
				<!-- <input type="button" name="day" id="dateType1" class="dateType1 active_button" onclick="setSearchDate('1d')" value="오늘" /><input
					type="button" name="week" id="dateType2" class="dateType2" onclick="setSearchDate('7d')" value="일주일" /><input
					type="button" name="month" id="dateType3" class="dateType3" onclick="setSearchDate('1m')" value="1개월" />  -->
					<!-- <input type='text' class='input_date' id='sdate' name='sdate' autocomplete='off' readonly /> ~ <input type='text'
					class='input_date' id='edate' name='edate' autocomplete='off' readonly /> -->
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
			<div class='search_div' onkeyup='onEnterSearch()'>
				<p id="bold_p">배달대행사</p>
				<select class='ch_title_box' id="dv_view" name='dv_view'>
					<option class="upload_option" value='01'>부릉</option>
					<option class="upload_option" value='02'>바로고</option>
					<option class="upload_option" value='03'>생각대로</option>
					<option class="upload_option" value='04'>딜버</option>
					<option class="upload_option" value='05'>스파이더</option>
					<!-- <option class="upload_option" value='08'>체인로지스</option> -->
				</select>
				<%-- <c:if test="${UserInfo.USER_LV ne 'S'}">
					<p id='sto_nm
					<div class="search_form" id="sto_nm
						<input class='search_box' type="search" id='sto_nm
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
				<p class='ono_p'>상세주문번호</p>
				<div class="search_form2">
					<input type="search" class='search_box' type="search" id='ono' autocomplete="off" />
				</div>
				<p class='ono_p'>주문번호</p>
				<div class="search_form2">
					<input class='search_box' type="search" id='chono' autocomplete="off" />
				</div>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button> --%>
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
			<form name="excelUploadForm" id="excelUploadForm">
				<div class="close_button" onclick="uploadClose()"></div>
				<select class='upload_select_box' id='franchise_cd' name="franchise_cd">
					<option class="upload_option" value='' hidden>배달사 선택</option>
					<c:forEach var='deliveryList' items="${deliveryList }">
						<option class="upload_option" value='${deliveryList.CPN_CD }'>${deliveryList.CPN_NM }</option>
					</c:forEach>
				</select>
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
			<form name="excelSampleForm" id="excelSampleForm">
				<div class="download_pop">
					<select class='sample_select_box' id='delivery' name="delivery">
						<option class="sample_option" value='' hidden>배달사 선택</option>
					     <c:forEach var='deliveryList' items="${deliveryList }">
						   <option class="sample_option" value='${deliveryList.CPN_CD }'>${deliveryList.CPN_NM }</option>
					     </c:forEach>
					</select>
					<!-- <select class='upload_select_box2' id='addsample_selectbox' name="addsample_selectbox">
						<option class="upload_option" value='' hidden>추가옵션</option>
					</select> -->
					<button type="button" class='btn_excel' id="btn_excel1" onclick='ExcelDown_settle();'>샘플 다운로드</button>
				</div>
			</form>
			</div>
	</div>
</body>
</html>
