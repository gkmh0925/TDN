//수정모드 true/false
let editStat = false;
//수정된 form 저장
let mdfForm = new Array();

// 수정 버튼 클릭
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
	editStat = false;

}
//등록 부분에 취소 버튼 클릭시
function cancel() {
	const td = $('.tdInput, .tdTextAr').parent();
	$('.insert_cancel_btn').css('display', 'none');
	$('.insert_insert_btn').css('display', 'none');
	$('.regist_insert_btn').css('display', 'inline');
//	$(".editCompleted").removeClass("editCompleted");
//	$('.select_box').attr('disabled', true);
	$(td).removeClass('row');
	$('.defTd').removeClass('uneditable');
	//Form 리셋
	
	location.reload();
	mdfForm.length = 0;
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
$(document).on("click", "#comp_master td", function() {
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
			if (isEmpty(mdfForm)) {
				swal({
					text: "수정 된 데이터가 없습니다.",
					icon: "warning"
				}).then(function() {
					cancel();
					return
				});

			} else {			
				const cpn_cd = $('#CPN_CD').text();
				const cpntype = "ORDER";		
				let comp_master = "";
				for (let i = 0; i < mdfForm.length; i++) {
					if (mdfForm[i] == 'comp_master') {
						comp_master = $('#comp_master').serializeObject();
					}
				}
					
				$.ajax({
					url: 'oms_sub05_02edit',
					contentType: 'application/json',
					dataType: 'json',
					type: 'POST',
					traditional: true,
					data: JSON.stringify({
						cpn_cd,
						cpntype,
						comp_master
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
//$(()=>{
//	// 카테고리 테이블 수정
//	$(".regist_ctgr_btn").click(function(){
//		const title_tr = $(this).closest("form").find(".info_table_list_title_tr");
//		const conetent_tr = $(this).closest("form").find(".info_table_list_content_tr");
////		$(title_tr).after(`
////			<tr class="regist_tr add_ctgr">
////				<td class="regist_td" colspan="100%"><img class="plus_icon_img" src="./resources/img/plus_icon.png"></td>
////			</tr>
////		`);
////		$(title_tr).append(`
////			<th class="info_table_list_content_th delete_th"></th>
////		`);
////		$(conetent_tr).append(`
////			<td class="info_table_list_content_td delete_td delete_ctgr">
////				삭제하기
////			</td>
////		`);
//	});	
//
//});


