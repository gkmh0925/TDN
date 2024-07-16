<!-- !@@0701 new -->


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>배달서비스상품리스트관리</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub02_03.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<script src="./resources/codebase/grid.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub02_03.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script>
function refresh() {
	$('#cpn_nm').val('');
	$('#prd_nm').val('');
}

</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title"><p>상품관리<b class="greater">&gt;</b>배달서비스상품리스트관리</p></div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch();'>

				<p id='ono_p'>채널사명</p>
				<div class="search_form2">
					<input class='search_box' type="search" id='cpn_nm'
						autocomplete="off" />
				</div>
				
				<p id='ono_p'>상품명</p>
				<div class="search_form2">
					<input class='search_box' type="search" id='prd_nm'
						autocomplete="off" />
				</div>

				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
				
			</div><!-- search_div -->
			<div class="btn_div">
				<button type="button" class='btn_search btn_reg_deliveryProduct_lv1'>등록</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section><!-- div_header -->

		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
	</div>
</body>


</html>






















































