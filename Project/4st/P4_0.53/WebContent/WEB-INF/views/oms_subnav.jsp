<%@page import="org.apache.commons.lang3.StringUtils" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<link rel="stylesheet" type="text/css" href="./resources/css/subnav.css"/>
<script src="./resources/js/sweetalert.min.js"></script>
<script>
$(function () {
    $(".mainmenu li").click(function () {
        $(this).children(".submenu").stop().slideToggle();
    });

    $('.submenu').click(function () {
        $(this).css('display', 'block');
    });

    $('.submenu').hover(function () {
        $(this).css('color', '#F39800');
    });

    $('.submenu').mouseleave(function () {
        $(this).css('color', 'white');
    });
});

</script>
<body>
<c:if test="${empty UserInfo}">
	<script>
		swal({
			text : "로그인 후 이용할 수 있는 페이지입니다.",
			icon : "warning"
		}).then(function() {
			location.replace("/");
		});
	</script>
</c:if>
<div class="nav_bar">
	<section id="user_Info">
	<img src="./resources/img/logo.png" style="width: 70px;" onclick="window.open(main);"/>
	<p>
		${UserInfo.USER_NM }님반갑습니다.
	</p>
	</section>
	<section id="main-list">
	<ul class="mainmenu">

		
		<li id="main-item"><img src="./resources/img/icon_shop.png" style="width: 26px"/> &nbsp; 매장관리<img src="./resources/img/btn_list.png" class="list"/>
		<ul class="submenu" onclick="window.open('storeList');">
			매장리스트조회
		</ul>
		</li>
		<!-- <li id="main-item"><img src="./resources/img/icon_product.png"/> &nbsp; 메뉴관리<img src="./resources/img/btn_list.png" class="list"/>
		<ul class="submenu" onclick="window.open('productList');">
			메뉴리스트조회
		</ul>
		</li> -->
		
		<li id="main-item"><img src="./resources/img/icon_shop.png" style="width: 26px"/> &nbsp; 상품관리<img src="./resources/img/btn_list.png" class="list"/>
		<ul class="submenu submenu_list" onclick="window.open('categoryList');">
			플랫폼사별 카테고리 조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('categoryLv2List');">
			플랫폼사별 카테고리 상세
		</ul>
		
		
<!-- 		!@@0701 yh -->
		<ul class="submenu submenu_list" onclick="window.open('deliveryProductMain');">
			배달 서비스 상품 리스트 관리
		</ul>
		
				<ul class="submenu submenu_list" onclick="window.open('deliveryEventList');">
			배달 행사상품 리스트 관리
		</ul>
		</li>
		
		
		<li id="main-item"><img src="./resources/img/icon_order.png" style="width: 26px"/> &nbsp; 주문내역관리<img src="./resources/img/btn_list.png" class="list"/>
		<ul class="submenu" onclick="window.open('orderList');">
			주문내역조회
		</ul>
		</li>
		<li id="main-item"><img src="./resources/img/icon_calculate.png" style="width: 28px"/> &nbsp; 정산내역관리<img src="./resources/img/btn_list.png" class="list"/>
		<ul class="submenu submenu_list" onclick="window.open('posList');">
			POS데이터 조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('compare');">
			POS데이터 대사조회
		</ul>
		<!--  <ul class="submenu submenu_list" onclick="window.open(0402);">중계수수료 정산 조회</ul>
                                        <ul class="submenu submenu_list" onclick="window.open(0403);">대사 조회</ul> -->
		<ul class="submenu submenu_list" onclick="window.open('ordFeeList');">
			주문채널사 수수료 정산 조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('dlvFeeList');">
			배달대행사 수수료 정산 조회
		</ul>
<!--  <ul class="submenu submenu_list" onclick="window.open(0406);">KIS PG 수수료 정산 조회</ul>
      <ul class="submenu submenu_list" onclick="window.open(0407);">가맹점 수수료 정산 조회</ul> -->
		<ul class="submenu submenu_list" onclick="window.open('totalCalcList');">
			배달 정산 집계조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('RelayFee');">
			중계 수수료 정산 조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('RelayFee_Campare');">
			중계 수수료 정산 대사조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('StoreOrdNoDetails');">
			점별/주문번호별 상세 조회 화면
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('StoreMonthDetails');">
			점별 월 정산 조회
		</ul>
		<ul class="submenu submenu_list" onclick="window.open('deliveryFee');">
			배달료 부담금 마스터
		</ul>
		
		</li>
		<c:if test="${UserInfo.USER_LV eq 'M' || UserInfo.USER_LV eq 'A'}">
		<li id="main-item"><img src="./resources/img/icon_system.png" style="width: 26px"/> &nbsp; 시스템관리<img src="./resources/img/btn_list.png" class="list"/><c:if test="${UserInfo.USER_LV eq 'M'}">
		<ul class="submenu" onclick="window.open('userList');">
			계정 관리
		</ul>
		</c:if>
		<c:if test="${UserInfo.USER_LV eq 'M' || UserInfo.USER_LV eq 'A'}">
		<ul class="submenu" onclick="window.open('orderchannelList');">
			채널사 관리
		</ul>
		</c:if>
		<c:if test="${UserInfo.USER_LV eq 'M' || UserInfo.USER_LV eq 'A'}">
		<ul class="submenu" onclick="window.open('deliverychannelList');">
			배달대행사 관리
		</ul>
		</c:if></li>
		</c:if>
	</ul>
	</section>
	<section id="nav_footer">
	<p class="call_title">
		[주문채널 콜센터]
	</p>
	<p class="call_content">
		<c:forEach var="franchList" items="${franchList}" varStatus="status">
			<c:out value="${franchList.CPN_NM }"/> : <c:out value="${franchList.CPN_TEL }"/>
			<br>
		</c:forEach>
	</p>
	<p class="call_title">
		[배달 대행 콜센터]
	</p>
	<p class="call_content">
		<c:forEach var="deliveryList" items="${deliveryList}" varStatus="status">
			<c:out value="${deliveryList.CPN_NM }"/> : <c:out value="${deliveryList.CPN_TEL }"/>
			<br>
		</c:forEach>
	</p>
	<p id="logout">
		<a href="logoutCustomer">로그아웃</a>
	</p>
	</section>
</div>
</body>
</html>
