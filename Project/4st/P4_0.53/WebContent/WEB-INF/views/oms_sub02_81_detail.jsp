<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>카테고리 리스트 상세</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub02_80_detail.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub02_01_detail.css" />
<script>





</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">상품관리<b class="greater">&gt;</b>플랫폼사별 카테고리 조회<b class="greater">&gt;</b>플랫폼사별 카테고리 상세</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class="btn_div">
			<p id="warn_p"><strong id="strong"></strong>&nbsp; 표시는 수정이 불가합니다. </p>
				<c:if test="${UserInfo.USER_LV eq 'M' || UserInfo.USER_LV eq 'A'}">
					<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
					<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
					<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
				</c:if>
				
				<button type="button" class='btn_search' onclick="remove();">삭제</button>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</section>
		<div class='main_div'>
			<form id="comp_master">
				<table>
					<tr>
						<th colspan='11' class='mtitle'>매장 기본 정보</th>
					</tr>
					


						
						<tr><th class='stitle'>카테고리코드</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${getGetCtgrCd.CTGR_CD}" name="CTGR_CD" readonly/></td>
						<td colspan='2' class="defTd">${getGetCtgrCd.CTGR_CD }</td>
						<tr><th class='stitle'>카테고리타입</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.CTGR_TYPE }</td>
						<tr><th class='stitle'>카테고리명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${getGetCtgrCd.CTGR_NM}" name="CTGR_NM" readonly/></td> </tr>
						<tr><th class='stitle'>채널사코드</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.CH_CD }</td>
						<tr><th class='stitle'>채널사명</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.CPN_NM }</td>
						<tr><th class='stitle'>정렬순서</th>
						<td colspan='2'><input type="text" class="tdInput" value="${getGetCtgrCd.SEQ}" name="SEQ" readonly/></td>
						<tr><th class='stitle'>등록사용자</th> 
						<td colspan='2' class="defTd">${getGetCtgrCd.REG_USER_CD }</td>
						<tr><th class='stitle'>등록일시</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.REG_DATE }</td>
						<tr><th class='stitle'>수정사용자</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.MDF_USER_CD }</td>
						<tr><th class='stitle'>수정일시</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${getGetCtgrCd.MDF_DATE}" name="MDF_DATE" readonly/></td>
						<td colspan='2' class="defTd">${getGetCtgrCd.MDF_DATE }</td>
					
				
				

				</table>
				</form>
				</div>

	</div>
</body>

</html>
