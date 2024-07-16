<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>플랫폼사별 카테고리 상세 생성</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub05_01.css">
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub02_81.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title">매장기본정보<b class="greater">&gt;</b>카테고리 정보</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch()'>
				<c:if test="${UserInfo.USER_LV ne 'S'}">
					<p id='snm_p'>채널사코드</p>
					<div class="search_form" id="snm_form">
						<input class='search_box' type="search" id='snm' name='snm' readonly /><img src="./resources/img/icon_search_hover.png"
							id='pnm_img' />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UserInfo.USER_LV eq 'S'}">
						<input type='text' id='scd' name='scd' value='${UserInfo.CPN_CD }' hidden />
					</c:when>
					<c:otherwise>
						<input type='text' id='scd' name='scd' hidden />
					</c:otherwise>
				</c:choose>

			</div>
			<div class="refresh">
				<button type="button" class='btn_refresh' onclick="refresh();" title="검색 초기화"></button>
			</div>
			
			<div class="btn_div">
				<button type="button" class='btn_reg_category_lv1'>등록</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
			
		</section>
		<div class='main_div'>
			<div id="grid"></div>
		</div>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
		<!-- 
		<div class="hide_div">
		
			<form name="excelUploadForm" id="excelUploadForm">
				<div class="filebox">
					<label for="excelFile">파일선택</label> <input type="file" id="excelFile" name="excelFile" class="upload-hidden">
					<input class="upload-name" id="upload_name" disabled="disabled">
				</div>
					 <input type="file" id="excelFile" name="excelFile" value="엑셀 업로드" /> 
				
				<div class="upload_div2">
					<button type="button" class='btn_excel' id="btn_excel1" onclick='excelupload();'>전체 업로드</button>
					<button type="button" class='btn_excel' id="btn_excel2" onclick='excelpartupload();'>일부 업로드</button>
					<button type="button" class='btn_excel' id="btn_excel2" onclick='sampleDownload();'>양식 다운로드</button>
				</div>
			</form>
		</div>  -->
	</div>
</body>
</html>
