<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>계정 관리</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub05_01.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub05_01.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			
			<div id="header_title">시스템관리<b class="greater">&gt;</b>계정관리</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch()'>
				<p id='user_nm_p'>사용자명</p>
				<input class='search_box' type="search" id='userNm' name='userNm' autocomplete="off" />
			</div>
			<div class="btn_div">
				<button type="button" class='btn_reg'>등록</button>
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