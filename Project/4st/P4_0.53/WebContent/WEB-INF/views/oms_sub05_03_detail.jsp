<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>배달대행사 상세</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub05_03_detail.js"></script>
<script src="./resources/js/delivery_table_modify.js"></script> 
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub05_03_detail.css" />
<script>
$(document).ready(function() {
	const cpn_tel = "${DELIVERY.CPN_TEL }";
	const mng_hpno = "${DELIVERY.MNG_HPNO }";
	const opn_dt = "${DELIVERY.OPN_DT }";

	$('#cpn_tel').val(phoneNumberFormat(cpn_tel));
	$('#mng_hpno').val(phoneNumberFormat(mng_hpno));
	
	$('#opn_dt').val(dateFormat(opn_dt));

});
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">시스템관리<b class="greater">&gt;</b>배달대행사관리<b class="greater">&gt;</b>배달대행사상세</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class="btn_div">
				<p id="warn_p"><strong id="strong"></strong>&nbsp; 표시는 수정이 불가합니다. </p>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
				<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
				<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
				<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
			</div>
		</section>
		<div class='main_div'>
			<form id="comp_master">
				<table>
					<tr>
						<th colspan='11' class='mtitle'>배달대행사 기본 정보</th>
					</tr>
					<tr>
						<th class='stitle'>배달대행사코드</th>
						<td colspan='2' id="CPN_CD" class="defTd">${DELIVERY.CPN_CD }</td>
						<th class='stitle'>배달대행사명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${DELIVERY.CPN_NM }" name="CPN_NM" readonly/></td>
						<th class='stitle'>콜센터번호</th>
						<td colspan='2'><input type="text" class="tdInput" name="CPN_TEL" id="cpn_tel" onKeyup="inputTelNumber(this);" maxlength="13" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>사업자유형</th>
						<td colspan='2'><input type="text" class="tdInput" value="${DELIVERY.CPN_BIZ_TYPE}" name="CPN_BIZ_TYPE" readonly/></td>
						<th class='stitle'>사업자번호</th> 
						<td colspan='2'><input type="text" class="tdInput" value="${DELIVERY.CPN_BIZ_NO }" name="CPN_BIZ_NO" readonly/></td>
						<th class='stitle'>대표자명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${DELIVERY.CEO_NM }" name="CEO_NM" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>담당자명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${DELIVERY.MNG_NM }" name="MNG_NM" maxlength="13" readonly/></td>
						<th class='stitle'>휴대폰번호 (담당자)</th>
						<td colspan='2'><input type="text" class="tdInput" name="MNG_HPNO" id="mng_hpno" onKeyup="inputPhoneNumber(this);" maxlength="13" readonly/></td>
						<th class='stitle'>이메일</th>
						<td colspan='2'><input type="text" class="tdInput" value="${DELIVERY.EMAIL}" name="EMAIL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>업체상태</th>
						<td colspan='2'>
							<select class='select_box' id="CPN_STATE" name='CPN_STATE' disabled>
								<option value='' disabled selected hidden>${DELIVERY.CPN_STATE}</option>
								<c:forEach var="stcode" items="${stcode }">
									<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
								</c:forEach>
							</select>
						</td>
						<th class='stitle'>상태변경일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${DELIVERY.STATE_MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th class='stitle'>오픈일자</th>
						<td colspan='2'><input type="text" class="tdInput" name="OPN_DT" id="opn_dt" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>업체 이미지 URL</th>
						<td colspan='7' class="defTd"><input type="text" class="tdInput" value="${DELIVERY.CPN_IMAGE_URL}" name="CPN_IMAGE_URL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>주소</th>
						<td colspan='7'><input type="text" class="tdInput" value="${DELIVERY.CPN_ADDR}" name="CPN_ADDR" readonly/></td>
					</tr>
					<tr>
						<th class='st_des'>업체설명</th>
						<td colspan='7'><textarea class="tdTextAr" name="CPN_DESC" maxlength="200" readonly>${DELIVERY.CPN_DESC}</textarea></td>
					</tr>
					<tr>
						<th class='st_des'>비고</th>
						<td colspan='7'><textarea class="tdTextAr" name="MEMO" maxlength="200" readonly>${DELIVERY.MEMO}</textarea></td>
					</tr>
					<tr>
						<th class='stitle'>등록사용자</th>
						<td class="defTd" colspan='2'>${DELIVERY.REG_USER_NM}</td>
						<th class='stitle'>등록일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${DELIVERY.REG_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th class='stitle'>인앱여부</th>
						<td colspan='2'>
							<select class='select_box' id="IN_APP" name='IN_APP' disabled>
								<option value='${DELIVERY.IN_APP}' selected hidden>${DELIVERY.IN_APP}</option>
								<option value='Y'>Y</option>
								<option value='N'>N</option> 
							</select>
					</tr>
					<tr>
						<th class='stitle'>수정사용자</th>
						<td class="defTd" colspan='2'>${DELIVERY.MDF_USER_NM}</td>
						<th class='stitle'>수정일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${DELIVERY.MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
			</form>
		</div><!-- ㅡmain_div -->
		<div class="fee_div">
				<div class="list_table_div">
					<form class="table_form" name="EM_CMS_OPERATION_OMS" id="delivery_reg">
						<table class="info_table first_table">
							<thead>
								<tr class="info_table_title_tr">
									<th class="info_table_title_th" colspan="11">
										<span class="info_table_title_span mtitle'">수수료 마스터</span>
											<span class="table_modify_span">
												
												<button class="info_table_btn table_modify_btn table_main_btn regist_ctgr_btn" type="button">수정</button>
												<button class="info_table_btn table_cancel_btn table_sub_btn regist_cancel_btn hidden_btn" type="reset">취소</button>
												<button class="info_table_btn table_regist_btn table_sub_btn hidden_btn" type="button" onclick="modifyComplete(this);">저장</button>
											</span>
											<span class="table_insert_span">
												<button class="info_table_btn2 table_insert_btn table_main_btn fee_insert_btn" id="submit" type="button" >수수료 등록</button>
												<button class="info_table_btn2 table_insert_pbtn table_sub_btn hidden_btn" id="p_submit" type="button" >추가</button>
												<button class="info_table_btn2 table_insert_btn2 table_sub_btn hidden_btn" id="reg_submit" type="button">등록하기</button>
												<button class="info_table_btn2 table_cancel_btn table_sub_btn hidden_btn" type="reset">취소</button>
											</span>
									</th>
								</tr>
							</thead>
							<tbody class="table_insert_tbody">
								<tr class="info_table_list_title_tr">
									<th class="info_table_list_content_th">업체타입</th>
									<th class="info_table_list_content_th">업체코드</th>
									<th class="info_table_list_content_th">수수료타입</th>
									<th class="info_table_list_content_th">수수료타입명</th>
									<th class="info_table_list_content_th">수수료금액타입</th>
									<th class="info_table_list_content_th">수수료금액타입명</th>
									<th class="info_table_list_content_th">등록사용자</th>
									<th class="info_table_list_content_th">등록일시</th>
									<th class="info_table_list_content_th">수정사용자</th>
									<th class="info_table_list_content_th">수정일시</th>
								</tr>
								<c:forEach items="${FEE_DELIVERY }" var="FEE_DELIVERY" varStatus="i">
									<tr class="info_table_list_content_tr" id="${i.index }">
										<td class="info_table_list_content_td defTd feeTd" id="CPN_TYPE" ><!-- 업체타입 -->
											${FEE_DELIVERY.CPN_TYPE }
										</td>
										<td class="info_table_list_content_td defTd feeTd" id="CPN_CD"><!-- 업체코드 -->
											${FEE_DELIVERY.CPN_CD }
										</td>
										<td class="info_table_list_content_td defTd feeTd" id="CMS_APPLY_TYPE"><!-- 수수료타입 -->
										${FEE_DELIVERY.CMS_APPLY_TYPE }
										</td>
										<td class="info_table_list_content_td mdf" id="CMS_APPLY_TYPE_NM"><!-- 수수료타입명 -->
											<input class="feeInput" value="${FEE_DELIVERY.CMS_APPLY_TYPE_NM }"readonly />
										</td>
										<td><!-- 수수료금액타입 -->
										<select class="fee_select_box" id="CMS_CALC_TYPE" name='CMS_CALC_TYPE' disabled>
											<option value='${FEE_DELIVERY.CMS_CALC_TYPE }' selected hidden >${FEE_DELIVERY.CMS_CALC_TYPE }</option>
											<c:forEach var="cmsCalcType" items="${cmsCalcType }">
												<option value='${cmsCalcType.DEFINITION_NM }'>${cmsCalcType.DEFINITION_NM }</option>
											</c:forEach>
							  			</select>
										</td>
										<td class="info_table_list_content_td mdf" id="CMS_CALC_TYPE_NM"><!-- 수수료금액타입명 -->
											<input class="feeInput" value="${FEE_DELIVERY.CMS_CALC_TYPE_NM }"readonly />
										</td>
										<td class="info_table_list_content_td defTd feeTd"><!-- 등록사용자 -->
											${FEE_DELIVERY.REG_USER_NM }
										</td>
										<td class="info_table_list_content_td defTd feeTd"><!-- 등록일시 -->
											<fmt:formatDate value="${FEE_DELIVERY.REG_DATE }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td class="info_table_list_content_td defTd feeTd"><!-- 수정사용자 -->
											${FEE_DELIVERY.MDF_USER_NM }
										</td>
										<td class="info_table_list_content_td defTd feeTd"><!-- 수정일시 -->
											<fmt:formatDate value="${FEE_DELIVERY.MDF_DATE }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<!-- <td class="del del_hidden" ><button class="btn btn-danger" id="del_btn">삭제</button></td> -->
									</tr>
									
									</c:forEach>
						 <tr class="insert_tr hidden_btn insert_table_list_tr">
							  <td><input type="text" class="reg_input ist" id="INPUT_CPN_TYPE" name="CPN_TYPE" autocomplete='off' value='DELIVERY' readonly/></td>
							  <td>
							  <input type="text" class="reg_input ist" id="INPUT_CPN_CD" name="CPN_CD" autocomplete='off' value='${DELIVERY.CPN_CD }' readonly/>
							  
							  </td>
							  <td>
							 <select class='insert_select_box' name='CMS_APPLY_TYPE' disabled>
								<option value='' disabled selected hidden>수수료 선택</option>
								<c:forEach var="cmsApplyType" items="${cmsApplyType }">
									<option value='${cmsApplyType.DEFINITION_NM }'>${cmsApplyType.DEFINITION_NM }</option>
								</c:forEach>
							</select> 
							  </td> 
							  <td><input type="text" class="reg_input ist" id="INPUT_CMS_APPLY_TYPE_NM" name="CMS_APPLY_TYPE_NM" autocomplete='off'/></td>
							  <td>
							  <select class='insert_select_box' name='CMS_CALC_TYPE' disabled>
								<option value='' disabled selected hidden>수수료금액 선택</option>
								<c:forEach var="cmsCalcType" items="${cmsCalcType }">
									<option value='${cmsCalcType.DEFINITION_NM }'>${cmsCalcType.DEFINITION_NM }</option>
								</c:forEach>
							</select> 
							  </td>
							  <td><input type="text" class="reg_input ist" id="INPUT_CMS_CALC_TYPE_NM" name="CMS_CALC_TYPE_NM" autocomplete='off'/></td>
							</tr> 

							</tbody>
						</table>
					</form>
				</div>
			</div>
	</div><!-- page_div -->
</body>

</html>
