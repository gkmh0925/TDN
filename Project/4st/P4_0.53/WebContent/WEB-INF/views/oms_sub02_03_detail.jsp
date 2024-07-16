<!-- !@@0701 new -->


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>배달 서비스 상품 상세</title>
</head>

<!-- 테스트 -->





<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub02_03_detail.css" />

<!-- 이게들어가야 데이트피커 css 적용됨 -->
<link rel="stylesheet" type="text/css" href="./resources/css/sub02_03_insert.css">
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">
				상품관리<b class="greater">&gt;</b>배달서비스상품리스트관리<b class="greater">&gt;</b>배달서비스상품상세
			</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class="btn_div">
				<p id="warn_p">
					<strong id="strong"></strong>&nbsp; 표시는 수정이 불가합니다.
				</p>
				<c:if test="${UserInfo.USER_LV eq 'M' || UserInfo.USER_LV eq 'A'}">
					<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
					<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
					<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
				</c:if>

				<button type="button" class='btn_search' onclick="remove();">삭제</button>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</section>
		<div class='main_div container'>
			<form id="comp_master">
				<table id="th_table">
					<tr>
						<th colspan='11' class='mtitle'>배달 상품 기본 정보</th>
					</tr>
					<tr>
						<th class='stitle'>채널사코드</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.chCd}" name="chCd" id="chCd" readonly /></td>
						<td colspan='2' class="defTd">${product.chCd}</td>
					</tr>
					<tr>
						<th class='stitle'>채널사명</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.cpnNm}" name="cpnNm" id="cpnNm" readonly /></td>
						<td colspan='2' class="defTd">${product.cpnNm}</td>
					</tr>
					<tr>
						<th class='stitle'>상품코드</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.prdCd}" id="prdCd" name="prdCd" readonly /></td>
						<td colspan='2' class="defTd">${product.prdCd}</td>
					</tr>

					<!-- 					변경 -->
					<tr>
						<th class='stitle'>상품명</th>
						<td colspan='2'><input type="text" class="tdInput chList" value="${product.prdNm}" name="prdNm" readonly /></td>
					</tr>

					<!-- 					변경 - 셀렉트 -->
					<tr>
						<th class='stitle'>매장카테고리</th>
						<td colspan='2'><select class='select_box chList' id="stoCtgr" name='stoCtgr' disabled>
								<option value='${product.stoCtgrCd}' selected hidden>${product.stoCtgrNm}</option>
								<c:forEach var="ctgr" items="${categoryList}">
									<option value='${ctgr.CTGR_CD}'>${ctgr.CTGR_NM }</option>
								</c:forEach>
						</select></td>
					</tr>

					<!-- 					변경 -->
					<tr>
						<th class='stitle'>판매단가</th>
						<td colspan='2'><input type="text" class="tdInput chList" value="${product.stdAmt}" name="stdAmt" readonly /></td>
					</tr>

					<!-- 					변경 -->
					<tr>
						<th class='stitle'>세일단가</th>
						<td colspan='2'><input type="text" class="tdInput chList" value="${product.saleAmt}" name="saleAmt" readonly /></td>
					</tr>

					<!-- 					변경 -->
					<tr>
						<th class='stitle'>정렬순서</th>
						<td colspan='2'><input type="text" class="tdInput chList" value="${product.seq}" name="seq" readonly /></td>
					</tr>

					<!-- 					변경 -->
					<tr>
						<th class='stitle'>과세구분</th>
						<td colspan='2'><select class='select_box chList' id="taxType" name='taxType' disabled>
								<option value='${product.taxType}' selected hidden>${product.taxType}</option>
								<c:forEach var="taxType" items="${taxTypeList}">
									<option value='${taxType.DEFINITION_NM}'>${taxType.DEFINITION_NM}</option>
								</c:forEach>
						</select></td>
					</tr>

					<!-- 					변경 - 셀렉트 -->
					<tr>
						<th class='stitle'>상품상태</th>
						<td colspan='2'><select class='select_box chList' id="prdState" name='prdState' disabled>
								<option value='${product.prdState}' selected hidden>${product.prdState}</option>
								<c:forEach var="prdState" items="${prdStateList}">
									<option value='${prdState.DEFINITION_NM}'>${prdState.DEFINITION_NM }</option>
								</c:forEach>
						</select></td>
					</tr>

					<!-- 					변경 - 셀렉트 -->
					<tr>
						<th class='stitle'>업무방식</th>
						<td colspan='2'>
						<select class='select_box chList' id="workType" name='workType' disabled>
								<option value='${product.workType}' selected hidden>${product.workType}</option>
								<c:forEach var="workType" items="${workTypeList}">
									<option value='${workType.DEFINITION_NM}'>${workType.DEFINITION_NM }</option>
								</c:forEach>
						</select></td>
					</tr>


					<tr>
						<th class='stitle'>상품가이드</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.prdGuide}" name="prdGuide" id="prdGuide" readonly /></td>
						<td colspan='2' class="defTd">${product.prdGuide}</td>
					</tr>
					<tr>
						<th class='stitle'>등록사용자코드</th>
						<td colspan='2' style="display: none;">
						<input type="text" class="tdInput" value="${product.regUserCd}" name="regUserCd" id="reg_user_cd" readonly />
						</td>
						<td colspan='2' class="defTd">${product.regUserCd}</td>
					</tr>
					<tr>
						<th class='stitle'>등록날짜</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.regDateStr}" name="regDate" id="regDate" readonly /></td>
						<td colspan='2' class="defTd">${product.regDateStr}</td>
					</tr>
					<tr>
						<th class='stitle'>수정사용자코드</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.mdfUserCd}" name="mdfUserCd" id="mdfUserCd" readonly /></td>
						<td colspan='2' class="defTd">${product.mdfUserCd}</td>
					</tr>
					<tr>
						<th class='stitle'>수정날짜</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.mdfDateStr}" name="mdfDate" id="mdfDate" readonly /></td>
						<td colspan='2' class="defTd">${product.mdfDateStr}</td>
					</tr>

					<!-- 					배민상세 -->
					
					<tr>
						<th class='stitle'>판매시작날짜</th>
						
						<td colspan='2' style="display: none;"> <input type='text' class='input_date' id='sdate' name='sale_start_dt' autocomplete='off' readonly /> </td>
<%-- 						<td colspan='2' style="display: none;"><input type="text" class="tdInput"  value="${product.saleStartDt}" name="sale_start_dt" id="sale_start_dt" readonly /></td> --%>
						<td colspan='2' class="defTd">${product.saleStartDt}</td>
					</tr>
					
					
<!-- 					원본 -->
<!-- 					<tr> -->
<!-- 						<th class='stitle'>판매시작날짜</th> -->
<!-- 						<td colspan='2' style="display: none;"> -->
<%-- 							<input type='text' class='input_date tdInput' id='sdate' value="${product.saleStartDt}" name='sale_start_dt' autocomplete='off' readonly /> --%>
<!-- 						</td> -->
<%-- 						<td colspan='2'>${product.saleStartDt}</td> --%>
<!-- 					</tr> -->
					
					
<!-- 					defTd : 수정 불가 만들기 위해 존재하는 클래스 -->
<!-- 					기존에 판매시작 날짜 출력하던 td 비활성화 -->
<!-- 					데이트픽커 활성화 시키면서 기존 판매날짜 value로 넣기 -->
						
					
					
					
					
					
					
					<tr>
						<th class='stitle'>판매종료날짜</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.saleEndDt}" name="saleEndDt" id="saleEndDt" readonly /></td>
						<td colspan='2' class="defTd">${product.saleEndDt}</td>
					</tr>
					<tr>
						<th class='stitle'>상품대표이미지URL</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.prdBaseImgUrl}" name="prdBaseImgUrl" id="prdBaseImgUrl" readonly /></td>
						<td colspan='2' class="defTd">${product.prdBaseImgUrl}</td>
					</tr>
					<tr>
						<th class='stitle'>상품상세이미지URL</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${product.prdDtImgUrl}" name="prdDtImgUrl" id="prdDtImgUrl" readonly /></td>
						<td colspan='2' class="defTd">${product.prdDtImgUrl}</td>
					</tr>
					
					<tr>								
						<!-- 				레퍼런스 코드 필요 -->
						<th class='stitle'>미성년자구매유무</th>
						<td colspan='2'>
							<select class='select_box chList' id="useSalesAdult" name='useSalesAdult' disabled>
								<c:choose>
    								<c:when test="${product.useSalesAdult != null and product.useSalesAdult eq '0'}">
								   		<option value='${product.useSalesAdult}' selected hidden>성인</option>
								   	</c:when>
								    <c:when test="${product.useSalesAdult != null and product.useSalesAdult eq '1'} ">
								    	<option value='${product.useSalesAdult}' selected hidden>전연령</option>
							        </c:when>
							        <c:otherwise>
										<option value='' selected hidden></option>
									</c:otherwise>
								</c:choose>															
								<option value='0'>성인</option>
								<option value='1'>전연령</option>								
							</select>
						</td>	
					</tr>
					
					<tr>										
						<th class='stitle'>상품타입</th>
						<td colspan='2'><select class='select_box chList' id="prdType" name='prdType' disabled>
								<option value='${product.prdType}' selected hidden>${product.prdType}</option>
								<c:forEach var="prdType" items="${prdCategoryList}">
									<option value='${prdType.DEFINITION_NM}'>${prdType.DEFINITION_NM }</option>
								</c:forEach>
						</select></td>
					</tr>
					
					
					
					
					
		
				</table>

			</form>
		</div>
	</div>
</body>
<script>
//주석 추가

//수정모드 true/false
let editStat = false;
//수정된 값 Array
let modiArr = new Array();
//수정된 Index Array
let rowIdx = new Array(); // 해당 행 저장
//수정된 form 저장
let mdfForm = new Array();

//수정
function edit() {
	const td = $('.tdInput, .tdTextAr').parent(); //해당 클래스 요소들의 부모 요소들 가져옴
	$('#edit_button').css('display', 'none'); //수정 버튼 비활성화
	$('#cancel_button').css('display', 'inline'); //취소버튼 활성화
	$('#save_button').css('display', 'inline'); //저장버튼 활성화
	$('.select_box').attr('disabled', false); //셀렉트박스 활성화
	$(td).addClass('row'); //row 클래스 추가
	$('.select_box').addClass('edit_sbox'); //셀렉트 박스 수정될 경우 CSS 적용을 위한 클래스 추가
	$('.defTd').addClass('uneditable'); //수정 불가한 필드 클래스 추가
	$('#warn_p').css('display','inline-block');
	//수정모드 true
	editStat = true;

	swal("수정 모드가 켜졌습니다.");
}

//취소 버튼 클릭시
function cancel() {
	const td = $('.tdInput, .tdTextAr').parent();
	$('#cancel_button').css('display', 'none');
	$('#save_button').css('display', 'none');
	$('#edit_button').css('display', 'inline');
	$(".editCompleted").removeClass("editCompleted");
	$('.select_box').attr('disabled', true);
	$(td).removeClass('row');
	$('.select_box').removeClass('edit_sbox');
	$('.defTd').removeClass('uneditable');
	$('#warn_p').css('display','none');
	//Form 리셋

	location.reload();

	mdfForm.length = 0;
	
	//Array 리셋
	rowIdx.length = 0;
	modiArr.length = 0;

	editStat = false;

}

//마우스 over 시 input줄 회색
$(document).on("mouseover", "td", function() {
	const td = $(this);
	const input = td.children(".tdInput, .tdTextAr");

	//해당 input이 readonly일 경우만 addClass
	if (editStat) {
		$(input).addClass('input_hover');
	}
});
//마우스 leave시 원래대로
$(document).on("mouseleave", "td", function() {
	const td = $(this);
	const input = td.children(".tdInput, .tdTextAr");

	$(input).removeClass('input_hover');
});

//input click시 수정 감지 및 데이터 저장 ( 수정모드 )
$(document).on("click", "td", function() {
	if (editStat) { //수정모드라면
		const tdOne = $(this);
		const inputOne = tdOne.children();

		const tr = tdOne.parent();
		const td = tr.children();
		const input = td.children();

		if (inputOne.is('.tdInput, .tdTextAr')) {
			$(inputOne).attr("readonly", false);
			$(inputOne).addClass("writeonly");
			$(tr).css("pointer-events", "none");
		}

		$(inputOne).focus();

		$(inputOne).on("change", function() {

			$(tr).css("pointer-events", "auto");
			$(input).removeClass("writeonly");
			$(input).attr("readonly", true);

			$(inputOne).addClass("editCompleted");
			$(tdOne).addClass("editCompleted");
			$(tdOne).removeClass("row");

			mdfForm.push($(inputOne).closest('form').attr('id'));


			
			
			mdfForm = multiDimensionalUnique(mdfForm);
			

		});
	}
});

//변경된 로우 index 저장 (+1 하여 저장)
$(document).on("change", ".chList", function() {

	rowIdx.push($(this).closest('tr').index()); // th 때문에 tr index +1

	rowIdx = multiDimensionalUnique(rowIdx);
});



$(document).on("change", ".chList", function() {

	rowIdx.push($(this).closest('tr').index()); // th 때문에 tr index +1

	rowIdx = multiDimensionalUnique(rowIdx);
});


//input에서 focusout시 원래대로
$(document).on("focusout", ".tdInput, .tdTextAr", function() {
	const inputOne = $(this);
	const tdOne = inputOne.parent();

	const tr = tdOne.parent();
	const td = tr.children();
	const input = td.children();

	$(tr).css("pointer-events", "auto");
	$(input).removeClass("writeonly");
	$(input).attr("readonly", true);

});

//저장시 수정된 배열 전송
function save() {
	swal({
		text: "저장하시겠습니까?",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {			
			const temp = ["temp"]; 

			for(let i = 0; i < rowIdx.length; i++){ // 수정된 row 가져오기
				const tempArr = new Array(); //수정된 tr안에 input이 여러개일 수 있기때문에 수정 된 로우마다 배열 선언
			
				const input = $('#th_table tr').eq(rowIdx[i]).children().children(); // 수정된 row의 input 가져오기

				input.each(function(i) { // input 값 tempArr배열에 저장
					tempArr.push(input.eq(i).val()); 
				});
								
				modiArr.push(tempArr); //row마다의 변경된 input 배열을 modiArr에 넣음
			}					
			if(modiArr.length == 1){
				modiArr.push(temp);
			}
			
			let modifyArr = multiDimensionalUnique(modiArr); //다차원 배열중 고유한 값들만 추출(중복데이터 제거)						
			
// 			mdfForm : 수정된 폼, 보통 1개, 클릭해서 수정을 한번이라도 하면 담기게 됨
			if (isEmpty(mdfForm)) { //아예 수정이 이루어지지 않은 경우 바로 리턴
				swal({
					text: "수정 된 데이터가 없습니다.",
					icon: "warning"
				}).then(function() {
					cancel();
					return
				});

			} else { //수정 액션이 발생 한 경우
				let comp_master = "";
				let sto_oper = "";
				for (let i = 0; i < mdfForm.length; i++) {
					if (mdfForm[i] == 'comp_master') { // 폼 아이디가 comp_master인 경우
						comp_master = $('#comp_master').serializeObject(); //직렬화
					} else {						
					}
				}		
				
				console.log("폼데이터");
				console.log(comp_master);			
				
				$.ajax({
					url: 'modifyDeliveryProduct',
					type: 'POST',
					async: false,
					traditional: true,
					data: comp_master,
					success: function(data) {
						swal({
							text: "저장되었습니다.",
							icon: "success"
						}).then(function() {
							location.reload();
							opener.parent.search_go();
						});
					},
					error: function(request, status, error) {
						const errCd = request.responseText;
						let errMsg = errCodeCheck(errCd);
						if (errMsg == false) {
							swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error).then(function() {
							return;
						});
						}
						swal({
							text: errMsg,
							icon: "error"
						}).then(function() {
							return;
						});
					}
				});								
			}
		}
	});
}



function remove() {
	  swal({
	      text: "삭제하시겠습니까?",
	      buttons: true,
	  }).then((willDelete) => {
	      if (willDelete) {
	          const chCd = $('#chCd').val();
	          const prdCd = $('#prdCd').val();

	          const data = {
	        	  chCd,
	        	  prdCd
	          };
	          $.ajax({
	              url: 'removeDeliveryProduct',
//	              dataType: 'json',
	              type: 'POST',
	              data: data,
	              success: function(data) {	            	  
	                  swal({
	                      text: "삭제되었습니다.",
	                      icon: "success"
	                  }).then(function() {
	                      window.close();
	                  });
	              },
	              error: function(error) {
	                  swal({
	                      text: "삭제에 실패하였습니다.",
	                      icon: "error"
	                  }).then(function() {
	                      window.close();
	                  });
//	                  let errMsg = errCodeCheck(errCd);
//	                  if (errMsg == false) {
//	                      swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
//	                      return;
//	                  }
//	                  swal({
//	                      text: errMsg,
//	                      icon: "error"
//	                  }).then(function() {
//	                      return;
//	                  });
	              }
	          });
	      }
	  });
	}

</script>
</html>
