<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>채널사 관리</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub05_02.css" />
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script>
let mdfForm = new Array();

$(function(){
	//오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
	});
	
});

$(document).on("change", ".reg_input", function() {
	mdfForm.push($(this).closest('form').attr('id'));

	mdfForm = multiDimensionalUnique(mdfForm);
});

//엑셀 다운
function ExcelDown() {
	const occd = $('#occd').val();
	const sst = $('#sst').val();
	const cpntype = $('#cpntype').val();
	const path = 'channelList_excel';
	const params = {
		occd,
		sst,
		cpntype
	}

	post(path, params);
}



//검색 쿼리
function search_go(num) {
	$.blockUI({
		message: '<img src="./resources/img/loading_spiner.png"/>',
		css: {
			backgroundColor: 'rgba(0,0,0,0.0)',
			color: '#000000',
			border: '0px solid #a00'
		}
	});

	const cpncd = $('#occd').val(); //채널사코드
	const sst = $('#sst').val(); //input box(오픈/중지)
	const cpntype = $('#cpntype').val();
	const pageNum = num;

	$.ajax({
		url: 'oms_sub05_02_process',
		dataType: 'json',
		type: 'POST',
		data: {
			cpncd,
			sst,
			cpntype,
			pageNum
		},
		success: function(data) {

			$('.ajaxTr').remove();
			$('.paging').children().remove();
			$.unblockUI();

			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.ordChList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {

				$(data.ordChList).each(function(i, e) {

					$('#ajax_tbody').append('<tr class="row ajaxTr">' +
						'<td class="a_tbody" style="padding-right:2px">' + e.rn + '</td>' +
						'<td class="a_tbody">' + e.cpn_cd + '</td>' + 							//업체코드
						'<td class="a_tbody">' + e.cpn_nm + '</td>' + 							//상호명
						'<td class="a_tbody">' + e.cpn_state + '</td>' +						//업체상태
						'<td class="a_tbody">' + e.cpn_biz_no + '</td>' +						//사업자번호
						'<td class="a_tbody">' + e.ceo_nm + '</td>' +							//대표자명
						'<td class="a_tbody">' + e.cpn_addr +' ' + e.cpn_addr_dt + '</td>' +	//업체상세주소
						'<td class="a_tbody">' + phoneNumberFormat(e.cpn_tel) + '</td>' +		//전화번호
						'<td class="a_tbody">' + e.mng_nm + '</td>' +							//점장명
						'<td class="a_tbody">' + phoneNumberFormat(e.mng_hpno) + '</td>' +		//휴대폰번호(점장)
						'<td class="a_tbody">' + e.email + '</td>' +							//이메일
						/* '<td>' + e.K7_LINK_CD + '</td>' + */                 //채널사 연동코드라는데 EM_COMPANY_MASTER_OMS 컬럼이없음;;;;
						'<td class="a_tbody">' + e.in_app + '</td>' + 							//인앱여부
						'<td class="a_tbody">' + unixTimeFormat(e.reg_date) + '</td>' +			//등록일시
						'</tr>'
					);
				});
				if (prev) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + 1 + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left_fin.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + (startPage - 1) + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left.png"/></a></li>');
				}
				for (let num = startPage; num <= endPage; num++) {
					if (num == page) {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn active">' + num + '</a></li>');
					} else {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn">' + num + '</a></li>');
					}
				}
				if (next) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + (endPage + 1) + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + totalPage + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right_fin.png"/></a></li>');
				}
				$("#excel_download").attr("disabled", false);
			}
		},
		error: function(e) {
			alert("데이터를 가져오지 못했습니다.");
			$("#excel_download").attr("disabled", true);
			$.unblockUI();
		}
	});
}

function refresh() {
	const userLv = "${UserInfo.USER_LV}";
	if(userLv != 'H'){
		$('#ocnm').val('');
		$('#occd').val('');
	}
	$('#sst').val('');
	$('#cpntype').val('');
	
}

function reg_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
}

function reg_submit(){
	const CPN_CD = $('#CPN_CD').val();
	const CPN_NM = $('#CPN_NM').val();
	const CPN_TEL = $('#CPN_TEL').val();
	
	
	if(isEmpty(CPN_CD) || isEmpty(CPN_NM) || isEmpty(CPN_TEL)){
		swal({
			text: "필수값을 입력해주세요.",
			icon: "warning"
		});
		
		return
	}
	swal({
		text: "저장하시겠습니까?",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {
			const regVal = $('#channel_reg').serializeObject();
			const CPN_TYPE = "ORDER";
			if(isEmpty(mdfForm)){
				swal({
					text: "입력 된 데이터가 없습니다.",
					icon: "warning"
				}).then(function() {
					reg_cancel();
					return
				});
		
			} else{
				$.ajax({
					url: 'channel_register',
					contentType: 'application/json',
					dataType: 'json',
					type: 'POST',
					async: false,
					traditional: true,
					data: JSON.stringify({
						regVal,
						CPN_TYPE
					}),
					success: function(data) {
						swal({
							text: "저장되었습니다.",
							icon: "success"
						}).then(function() {
							mdfForm.length = 0;
							search_go();
							reg_cancel();
						});
					},
					error: function(request, status, error) {
						const errCd = request.responseText;
						let errMsg = errCodeCheck(errCd);
						if (errMsg == false) {
							swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
							location.reload();
							return;
						}
						swal({
							text: errMsg,
							icon: "error"
						}).then(function() {
							cancel();
						});
					}
				});
				
			}
		}
	});
}

function reg_cancel() {
	$('.overlay').fadeOut(100);
	$('.hide_div').removeClass('show_div');
	$('.reg_input').val('');
}

//상세정보 페이지 이동
$(document).on("dblclick", ".a_tbody", function() {
	const checkBtn = $(this);

	const tr = checkBtn.parent();
	const td = tr.children();
	const CPN_CD = td.eq(1).text();
	const cpntype = td.eq(2).text();

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "oms_sub05_02_detail";
	form.style.display = "none";

	let Inputcpncd = document.createElement("input");
	Inputcpncd.type = "text";
	Inputcpncd.name = "CPN_CD";
	Inputcpncd.value = CPN_CD;
	form.appendChild(Inputcpncd);

	let Inputcpntype = document.createElement("input");
	Inputcpntype.type = "text";
	Inputcpntype.name = "cpntype";
	Inputcpntype.value = cpntype;
	form.appendChild(Inputcpntype);
	
	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);

});
</script>
<body>


	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title"><p>시스템관리<b class="greater">&gt;</b>채널사관리</p></div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch();'>
				<p id='ord_nm_p'>채널사명</p>
				<div class="search_form">
					<input class='search_box' type="search" id='ocnm' name='ocnm' readonly />
					<img src="./resources/img/icon_search.png" id='ocnm_img' />
					<input type='text' id='occd' name='occd' hidden />
				</div>
				<input type ='text' id='cpntype' name ='cpntype' value='ORDER' hidden/>
				<input type='text' id='occd' name='occd' hidden />
				<select class='select_box' id="sst" name='sst'>
					<option value='' selected >--업체상태--</option>
					<c:forEach var="stcode" items="${stcode }">
						<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
					</c:forEach>
				</select>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>
			<div class="btn_div">
				<button type="button" class='btn_excel' id="excel_download" onclick="ExcelDown();" disabled>엑셀 다운로드</button>
				<button type="button" class='btn_search' onclick='reg_click();'>채널사 등록</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<section class='main_div'>
			<form name="editForm">
				<table id="ajaxTable">
					<thead>
						<tr>
							<th class='mtitle'>순번</th>
							<th class='mtitle'>채널사코드</th>
							<th class='mtitle'>채널사명</th>
							<th class='mtitle'>업체상태</th>
							<th class='mtitle'>사업자번호</th>
							<th class='mtitle'>대표자명</th>
							<th class='mtitle'>주소</th>
							<th class='mtitle'>콜센터전화</th>
							<th class='mtitle'>담당자명</th>
							<th class='mtitle'>담당자휴대번호</th>
							<th class='mtitle'>이메일</th>
							<!-- <th class='mtitle'>채널사연동코드</th>  -->
							<th class='mtitle'>인앱여부</th>
							<th class='mtitle'>등록일자</th>
						</tr>
					</thead>
					<tbody id="ajax_tbody">
					</tbody>
				</table>
			</form>
		</section>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
	</div>
	<div class="hide_div">
		<form id="channel_reg">
			<p id="reg_title">채널사 등록</p>
			<div class="reg_box">
				<table>
				
					<tr>
						<th class='mtitle'>채널사코드<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" id="CPN_CD" name="CPN_CD" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>채널사명<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" id="CPN_NM" name="CPN_NM" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>업체상태<p id="emp">*</p></th>
						<td>
							<select class="reg_select" name='CPN_STATE'>
								<c:forEach var="stcode" items="${stcode }">
									<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th class='mtitle'>대표자명</th>
						<td><input type="text" class="reg_input" name="CEO_NM" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>사업자번호</th>
						<td><input type="text" class="reg_input" name="CPN_BIZ_NO" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>주소</th>
						<td><input type="text" class="reg_input" name="CPN_ADDR" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>콜센터전화<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" id="CPN_TEL" name="CPN_TEL" onKeyup="inputTelNumber(this);" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>담당자명</th>
						<td><input type="text" class="reg_input" name="MNG_NM" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>담당자휴대번호</th>
						<td><input type="text" class="reg_input" name="MNG_HPNO" onKeyup="inputPhoneNumber(this);" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>이메일</th>
						<td><input type="text" class="reg_input" name="EMAIL" autocomplete='off'/></td>  
					</tr>
					 <tr>
						<th class='mtitle'>인앱여부<p id="emp">*</p></th>
						<td>
							<select class="reg_select" name='IN_APP'>
								<option value='Y' selected>Y</option>
								<option value='N'>N</option>
							</select>
						</td>  
					</tr>
					<tr>
					<!--<th class='mtitle'>채널사연동코드<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" name="K7_LINK_CD" autocomplete='off'/></td>   -->
					</tr> 
				</table> 
			</div>
			<div class="upload_div2">    
			    <button type="button" class='btn_search' onclick='reg_submit();'>등록</button>
				<button type="button" class='btn_search' onclick='reg_cancel();'>취소</button>
			</div>
		</form>
	</div>
</body>
</html>