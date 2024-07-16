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
<!-- <script src="./resources/js/sub02_80_detail.js"></script> -->
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub01_01_detail.css" />

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
				<table id="f_table">
					<tr>
						<th colspan='11' class='mtitle'>매장 기본 정보</th>
					</tr>
						<tr><th class='stitle'>카테고리코드</th>
						<td colspan='2' style="display: none;"><input type="text" class="tdInput" value="${getGetCtgrCd.CTGR_CD}" name="CTGR_CD" readonly/></td>
						<td colspan='2' class="defTd">${getGetCtgrCd.CTGR_CD }</td>
						<tr><th class='stitle'>카테고리타입</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.CTGR_TYPE }
							<input type="hidden" name="CTGR_TYPE" value="${getGetCtgrCd.CTGR_TYPE }">
						</td>
						<tr><th class='stitle'>카테고리명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${getGetCtgrCd.CTGR_NM}" name="CTGR_NM" readonly/></td> </tr>
						<tr><th class='stitle'>채널사코드</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.CH_CD }
							<input type="hidden" name="CH_CD" value="${getGetCtgrCd.CH_CD }">
						</td>
						<tr><th class='stitle'>채널사명</th>
						<td colspan='2' class="defTd">${getGetCtgrCd.CPN_NM }</td>
						<tr><th class='stitle'>정렬순서</th>
						<td colspan='2'><input type="text" class="tdInput" value="${getGetCtgrCd.SEQ}" name="SEQ" readonly/></td>
						<tr><th class='stitle'>카테고리 설명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${getGetCtgrCd.CTGR_INFO}" name="CTGR_INFO" readonly/></td>
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
<script>

//수정모드 true/false
let editStat = false;
//수정된 값 Array
let modiArr = new Array();
//수정된 Index Array
let rowIdx = new Array(); // 해당 행 저장
//수정된 form 저장
let mdfForm = new Array();

// 수정
function edit() {
	const td = $('.tdInput, .tdTextAr').parent();
	$('#edit_button').css('display', 'none');
	$('#cancel_button').css('display', 'inline');
	$('#save_button').css('display', 'inline');
	$('.select_box').attr('disabled', false);
	$(td).addClass('row');
	$('.select_box').addClass('edit_sbox');
	$('.defTd').addClass('uneditable');
	$('.defTd').addClass('uneditable');
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
	if (editStat) {
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
			const ctgr_cd = $('input[name="CTGR_CD"]').val();

			
			for(let i = 0; i < rowIdx.length; i++){ // 수정된 row 가져오기
				const tempArr = new Array();
			
				const input = $('#th_table tr').eq(rowIdx[i]).children().children(); // 수정된 row의 input 가져오기
				input.each(function(i) { // input 값 tempArr배열에 저장
					tempArr.push(input.eq(i).val());
				});
				tempArr.push(ctgr_cd);
				
				modiArr.push(tempArr);
			}
			
			if(modiArr.length == 1){
				modiArr.push(temp);
			}
			
			let modifyArr = multiDimensionalUnique(modiArr);
			
			if (isEmpty(mdfForm)) {
				swal({
					text: "수정 된 데이터가 없습니다.",
					icon: "warning"
				}).then(function() {
					cancel();
					return
				});

			} else {			
				const ctgr_cd = $('input[name="CTGR_CD"]').val();
				let comp_master = "";
				let sto_oper = "";
				for (let i = 0; i < mdfForm.length; i++) {
					if (mdfForm[i] == 'comp_master') {
						comp_master = $('#comp_master').serializeObject();
					} else if (mdfForm[i] == 'sto_oper') {
						sto_oper = $('#sto_oper').serializeObject();
					}
					//eval("var " + mdfForm[i] + " = $('#'+mdfForm[i]).serializeObject()");
				}
				
				if(isEmpty(modifyArr)){
					
					$.ajax({
						url: 'oms_sub02_80edit',
						contentType: 'application/json',
						dataType: 'json',
						type: 'POST',
						async: false,
						traditional: true,
						data: JSON.stringify({
							ctgr_cd,
							comp_master,
							sto_oper
						}),
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
				} else if(isEmpty(comp_master) && isEmpty(sto_oper)){

					$.ajax ({
						url: 'oms_sub02_80editArray',
						dataType: 'json',
					    type: 'POST',
					    traditional: true,
					    async : false,
					    data: {
					    	modifyArr
					    },
					    success: function (data) {
							swal({
								text: "저장되었습니다.",
								icon: "success"
							}).then(function() {
								//Array 리셋
						    	rowIdx.length = 0;
						    	modiArr.length = 0;
						    	modifyArr.length = 0;
								location.reload();
								opener.parent.search_go();
								return
							});
						},
						error: function(e) { 
				        	swal({
				   				text: "수정에 실패했습니다.",
				   				icon: "error"
				    		}).then(function() {
					        	modiArr.length = 0;
						    	modifyArr.length = 0;
						    	rowIdx.length = 0;
						    	
						    	return;
							});
				        }
					});
				}else{

					$.ajax({
						url: 'oms_sub02_80edit',
						contentType: 'application/json',
						dataType: 'json',
						type: 'POST',
						async: false,
						traditional: true,
						data: JSON.stringify({
							ctgr_cd,
							in_app,
							comp_master,
							sto_oper
						}),
						success: function(data) {
							$.ajax ({
								url: 'oms_sub02_80editArray',
								dataType: 'json',
							    type: 'POST',
							    traditional: true,
							    async : false,
							    data: {
							    	modifyArr
							    },
							     success: function (data) {
										//Array 리셋
								    	rowIdx.length = 0;
								    	modiArr.length = 0;
								    	modifyArr.length = 0;
								},error: function(e) { 
						        	modiArr.length = 0;
							    	modifyArr.length = 0;
							    	rowIdx.length = 0;
						        }
							});
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
								swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
								return;
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
		}
	});
}

function remove() {
    swal({
        text: "삭제하시겠습니까?",
        buttons: true,
    }).then((willDelete) => {
        if (willDelete) {
            const ctgr_cd = $('input[name="CTGR_CD"]').val();
            const ctgr_type = $('input[name="CTGR_TYPE"]').val();
            const ch_cd = $('input[name="CH_CD"]').val();
 
            let ctgr = "";

            $.ajax({
                url: 'oms_sub02_80remove',
                contentType: 'application/json',
                dataType: 'json',
                type: 'POST',
                data: JSON.stringify({
                    ctgr_cd,
                    ctgr_type,
                    ch_cd 
                }),
                success: function(data) {
                    swal({
                        text: "삭제되었습니다.",
                        icon: "success"
                    }).then(function() {
                        window.close();
                    });
                },
                error: function(request, status, error) {
                    const errCd = request.responseText;
                    let errMsg = errCodeCheck(errCd);
                    if (errMsg == false) {
                        swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                        return;
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
    });
}

</script>
</html>
