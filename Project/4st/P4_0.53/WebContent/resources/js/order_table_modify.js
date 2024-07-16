/*
 * 작성자 : 이준혁
 * 주석처리날짜 : 2022-08-26
 */
//수정모드 true/false
var orderCpnCd;
let beforeTrCnt;
let afterTrCnt;
$(()=>{
	
	
	let modifyStat = false;
	
	//수정된 form 저장
	let mdfForm = new Array();
	//수정버튼 클릭
	$(".table_modify_btn").click(function() {
		const conetent_tr = $(this).closest("form").find(".info_table_list_content_tr");
		beforeTrCnt = conetent_tr.length; 
		// 버튼 toggle
		$(this).parent().children().toggleClass("hidden_btn");
		$(".fee_insert_btn").toggleClass("hidden_btn");
		$(".del").toggleClass("del_hidden");
		const td = $('.feeInput').parent();
		$('.fee_select_box').attr('disabled', false);
		$(td).addClass('row');
		$('.fee_select_box').addClass('modify_sbox');
		$('.feeTd').addClass('uneditable');
		$('.feeTd').addClass('uneditable');
		$(conetent_tr).append(`
				<td class="info_table_list_content_td delete_td delete_ctgr">
					삭제
				</td>
			`);

		modifyStat = true;

		swal("수정 모드가 켜졌습니다.");
	});


	//수수료 등록 버튼 클릭시
	 $(document).on("click", ".table_insert_btn", function() {
		    // 버튼 toggle
		    $(this).parent().children().toggleClass("hidden_btn");
		    $(".regist_ctgr_btn").toggleClass("hidden_btn");
		    $(".insert_tr").toggleClass("hidden_btn");
		    $('.insert_select_box').attr('disabled', false);
		    swal({
		      icon: "info",
		      text:"새로운 등록을 진행합니다."
		    });
		  });


		//등록버튼클릭시
		$("#reg_submit").click(function() {
			
			  const regVal = $('#channel_reg').serializeObject();
			  const tableData = [];

			  for(let i = 0; i < $(".insert_table_list_tr").length; i++) {
			    const currentRow = $(".insert_table_list_tr")[i];
			    const CPN_TYPE = $(currentRow).find("input[name='CPN_TYPE']").val();
			    const CPN_CD = $(currentRow).find("input[name='CPN_CD']").val();
			    const CMS_APPLY_TYPE = $(currentRow).find("input[name='CMS_APPLY_TYPE']").val();
			    const CMS_APPLY_TYPE_NM = $(currentRow).find("input[name='CMS_APPLY_TYPE_NM']").val();
			    const CMS_CALC_TYPE = $(currentRow).find("select[name='CMS_CALC_TYPE']").val();
			    const CMS_CALC_TYPE_NM = $(currentRow).find("input[name='CMS_CALC_TYPE_NM']").val();
			    
			    
			    // 값이 없는 경우 모달창을 띄우고 함수 종료
			    if (!CMS_APPLY_TYPE_NM || !CMS_CALC_TYPE || !CMS_CALC_TYPE_NM) {
			      swal({
			        text: "입력된 데이터가 없습니다.",
			        icon: "warning"
			      });
			      return;
			    }
			    tableData.push({
			      "CPN_TYPE": CPN_TYPE,
			      "CPN_CD": CPN_CD,
			      "CMS_APPLY_TYPE": CMS_APPLY_TYPE,
			      "CMS_APPLY_TYPE_NM": CMS_APPLY_TYPE_NM,
			      "CMS_CALC_TYPE": CMS_CALC_TYPE,
			      "CMS_CALC_TYPE_NM": CMS_CALC_TYPE_NM
			    });
			  }

			  regVal["list"] = tableData;

			  $.ajax({
			    url: 'channel_fee_register',
			    contentType: 'application/json',
			    dataType: 'json',
			    type: 'POST',
			    data: JSON.stringify(regVal),
			    success: function(data) {
			    	const scrollY = window.scrollY;
			        sessionStorage.setItem('scrollPos', scrollY);
			      swal({
			        text: "등록되었습니다.",
			        icon: "success"
			      }).then(function() {
			        location.reload();
			      });
			    },
			    error: function(request, status, error) {
			      const errCd = request.responseText;
			      let errMsg = errCodeCheck(errCd);
			      if (errMsg == false) {
			        swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
//			        location.reload();
			        return;
			      }
			      swal({
			        text: errMsg,
			        icon: "error"
			      }).then(function() {
	//		        cancel();
			      });
			    }
			  });
			});
		//수정 -> 삭제 버튼클릭시 delete 
		$(document).on("click", ".delete_ctgr", (event)=>{
			  const cpn_type = $(event.target).parent().find("#CPN_TYPE").text().trim();
			  const cpn_cd = $(event.target).parent().find("#CPN_CD").text().trim();
			  const cms_apply_type = $(event.target).parent().find("#CMS_APPLY_TYPE").text().trim();
			  swal({
			    text: "삭제 하시겠습니까?",
			    buttons: true,
			  }).then((willDelete) => {
			    if (willDelete) {
			      $.ajax({
			        url: 'oms_sub05del_fee',
			        type: 'POST',
			        data: {
			          cpn_type,
			          cpn_cd,
			          cms_apply_type
			        },
			        success: (data)=>{
			          swal({
			            icon: "success",
			            text: "삭제 되었습니다."
			          }).then(() => {
//			            location.reload();
			          });
			        },
			        error: (request, status, error)=>{
			          const errCode = request.responseText;
			          swal({
			            icon: "error",
			            title: `에러코드 : ${errCode}`,
			            text: "삭제에 실패했습니다"
			          });
			        },
			        complete: ()=>{
			          $(event.target).parent().remove();
			        }
			      });
			    }
			  });
			});
		

	//수수료마스터 테이블 수정버튼 클릭 후 마우스 over 시 input줄 회색
	$(document).on("mouseover", "td", function() {
		const td = $(this);
		const input = td.children(".feeInput");
		const select = td.children(".fee_select_box");
		//해당 input이 readonly일 경우만 addClass
		if (modifyStat) {
			$(input).addClass('input_hover');
			$(select).addClass('input_hover');
		}
	});
	//수수료마스터 테이블 수정버튼 클릭 후  마우스 leave시 원래대로
	$(document).on("mouseleave", "td", function() {
		const td = $(this);
		const input = td.children(".feeInput");
		const select = td.children(".fee_select_box");

		$(input).removeClass('input_hover');
		$(select).removeClass('input_hover');
	});

	//input click시 수정 감지 및 데이터 저장 ( 수정모드 )
	$(document).on("click", "#channel_reg td", function() {
		if (modifyStat) {
			const tdOne = $(this);
			const inputOne = tdOne.children();
			const selectOne = tdOne.children();

			const tr = tdOne.parent();
			const td = tr.children();
			const input = td.children();
			const select = td.children();

			if (inputOne.is('.feeInput')) {
				$(inputOne).attr("readonly", false);
				$(inputOne).addClass("writeonly");
				$(tr).css("pointer-events", "none");
			}
			if(selectOne.is('.fee_select_box')){
				$(selectOne).attr("readonly", false);
				$(selectOne).addClass("writeonly");
				$(tr).css("pointer-events", "none");
			}

			$(inputOne).focus();
			$(selectOne).focus();

			$(inputOne,selectOne).on("change", function() {

				$(tr).css("pointer-events", "auto");
				$(input).removeClass("writeonly");
				$(input).attr("readonly", true);
				$(select).removeClass("writeonly");
				$(select).attr("readonly", true);

				$(inputOne).addClass("modifyCompleted");
				$(inputOne).addClass("modifyCompleted");
				$(selectOne).addClass("modifyCompleted");
				$(selectOne).addClass("modifyCompleted");
				$(tdOne).addClass("modifyCompleted");
				$(tdOne).removeClass("row");

				mdfForm.push($(inputOne).closest('form').attr('id'));
				mdfForm.push($(selectOne).closest('form').attr('id'));

				mdfForm = multiDimensionalUnique(mdfForm);
			});
			
		}
	});

	//수수료마스터 테이블 수정버튼 클릭 후  input에서 focusout시 원래대로
	$(document).on("focusout", ".feeInput", function() {
		const inputOne = $(this);
//		const selectOne = $(this);
		const tdOne = inputOne.parent();
//		const tdOne = selectOne.parent();

		const tr = tdOne.parent();
		const td = tr.children();
		const input = td.children();
//		const select = td.children();

		$(tr).css("pointer-events", "auto");
		$(input).removeClass("writeonly");
		$(input).attr("readonly", true);
//		$(select).removeClass("writeonly");
//		$(select).attr("readonly", true);

	});
	//수수료마스터 테이블 수정버튼 클릭 후 input에서 focusout시 원래대로
	$(document).on("focusout", ".fee_select_box", function() {

		const selectOne = $(this);
		const tdOne = selectOne.parent();
		
		const tr = tdOne.parent();
		const td = tr.children();
		const select = td.children();
		
		$(tr).css("pointer-events", "auto");
		$(select).removeClass("writeonly");
		$(select).attr("readonly", true);
		
	});
/***************************************************************************/	

	$('#INPUT_CPN_CD').on('input', function() {
		  orderCpnCd = $(this).val();
		});
	//form 취소 이벤트
	$(document).on("reset", "form", (form)=>{
		orderCpnCd = null;
		const modify_input = $(form.target).find(".modify_input");
		const modify_select = $(form.target).find(".modify_select");
		const def = $(form.target).find(".def");
		//reset 후 실행
		setTimeout(()=>{
			$(modify_input).each((i, e)=>{
				$(e).parent().html(e.value);
			});
			$(modify_select).each((i, e)=>{
				const id = $(e).attr("id");
				const value = $(`#${id} option:selected`).text();
				$(e).parent().html(value);
			});
			$(def).removeClass("default_td");
			$(".changedTd").removeClass("changedTd");
			$(".changedVal").removeClass("changedVal");
			// 버튼 원래대로
			$(form.target).find(".table_main_btn").removeClass("hidden_btn");
			$(form.target).find(".table_sub_btn").addClass("hidden_btn");
			$(".insert_tr").addClass("hidden_btn");


			const td = $('.feeInput').parent();
			$(".modifyCompleted").removeClass("modifyCompleted");
			$('.fee_select_box').attr('disabled', true);
			$(td).removeClass('row');
			$('.fee_select_box').removeClass('modify_sbox');
			$('.feeTd').removeClass('uneditable');
//			$('#warn_fee').css('display','none');
			//Form 리셋
			// 삭제 버튼이 추가된 경우 삭제
			$(form.target).find(".delete_ctgr").remove();
//			location.reload();
			mdfForm.length = 0;
			modifyStat = false;
			
		});
	});
//	$(document).on("reset", "form", (form)=>{
//		event.preventDefault();
//		orderCpnCd = null;
//		const modify_input = $(form.target).find(".modify_input");
//		const modify_select = $(form.target).find(".modify_select");
//		//reset 후 실행
//		setTimeout(()=>{
//			$(modify_input).each((i, e)=>{
//				$(e).parent().html(e.value);
//			});
//			$(modify_select).each((i, e)=>{
//				const id = $(e).attr("id");
//				const value = $(`#${id} option:selected`).text();
//				$(e).parent().html(value);
//			});
//			$(".hangedTd").removeClass("changedTd");
//			$(".hangedVal").removeClass("changedVal");
//			// 버튼 원래대로
//			$(form.target).find(".table_main_btn").removeClass("hidden_btn");
//			$(form.target).find(".table_sub_btn").addClass("hidden_btn");
//			$(".insert_tr").addClass("hidden_btn");
//
//			const td = $('.feeInput').parent();
//			$(".modifyCompleted").removeClass("modifyCompleted");
//			$('.fee_select_box').attr('disabled', true);
//			$(td).removeClass('row');
//			$('.fee_select_box').removeClass('modify_sbox');
//			$('.feeTd').removeClass('uneditable');
//
//			// 삭제 버튼이 추가된 경우 삭제
//			$(form.target).find(".delete_ctgr").remove();
//			$(form.target).find(".insert_tr").remove();
//
//			// 페이지 새로고침
//			history.go(0);
//			// 스크롤 위치 유지
//			const x = window.scrollX;
//			const y = window.scrollY;
//			window.scrollTo(x, y);
//
//			mdfForm.length = 0;
//			modifyStat2 = false;
//		});
//	});
	
	
	
	
	
	/**************************************카드 수수료 마스터********************************************************/
	//수정버튼 클릭(카드수수료마스터)
	$(".card_table_modify_btn").click(function() {
		const conetent_tr = $(this).closest("form").find(".card_info_table_list_content_tr");
		beforeTrCnt = conetent_tr.length; 
		// 버튼 toggle
		$(this).parent().children().toggleClass("card_hidden_btn");
		$(".card_fee_insert_btn").toggleClass("card_hidden_btn");
		$(".del").toggleClass("del_hidden");
		const td = $('.card_feeInput').parent();
		$('.card_fee_select_box').attr('disabled', false);
		$(td).addClass('row');
		$('.card_fee_select_box').addClass('.card_modify_sbox');
		$('.card_feeTd').addClass('uneditable');
		$('.card_feeTd').addClass('uneditable');
		$(conetent_tr).append(`
				<td class="card_info_table_list_content_td card_delete_td card_delete_ctgr">
					삭제
				</td>
			`);

		modifyStat2 = true;

		swal("수정 모드가 켜졌습니다.");
	});
	
	
});
let modifyStat2 = false;
////수수료마스터 테이블 수정버튼 클릭 후 마우스 over 시 input줄 회색
$(document).on("mouseover", "td", function() {
	const td = $(this);
	const input = td.children(".card_feeInput");
	const select = td.children(".card_fee_select_box");
	//해당 input이 readonly일 경우만 addClass
	if (modifyStat2) {
		$(input).addClass('input_hover');
		$(select).addClass('input_hover');
	}
});
//수수료마스터 테이블 수정버튼 클릭 후  마우스 leave시 원래대로
$(document).on("mouseleave", "td", function() {
	const td = $(this);
	const input = td.children(".card_feeInput");
	const select = td.children(".card_fee_select_box");

	$(input).removeClass('input_hover');
	$(select).removeClass('input_hover');
});

//input click시 수정 감지 및 데이터 저장 ( 수정모드 )
$(document).on("click", "#card_channel_reg td", function() {
	if (modifyStat2) {
		const tdOne = $(this);
		const inputOne = tdOne.children();
		const selectOne = tdOne.children();

		const tr = tdOne.parent();
		const td = tr.children();
		const input = td.children();
		const select = td.children();

		if (inputOne.is('.card_feeInput')) {
			$(inputOne).attr("readonly", false);
			$(inputOne).addClass("writeonly");
			$(tr).css("pointer-events", "none");
		}
		if(selectOne.is('.card_fee_select_box')){
			$(selectOne).attr("readonly", false);
			$(selectOne).addClass("writeonly");
			$(tr).css("pointer-events", "none");
		}

		$(inputOne).focus();
		$(selectOne).focus();

		$(inputOne,selectOne).on("change", function() {

			$(tr).css("pointer-events", "auto");
			$(input).removeClass("writeonly");
			$(input).attr("readonly", true);
			$(select).removeClass("writeonly");
			$(select).attr("readonly", true);

			$(inputOne).addClass("card_modifyCompleted");
			$(inputOne).addClass("card_modifyCompleted");
			$(selectOne).addClass("card_modifyCompleted");
			$(selectOne).addClass("card_modifyCompleted");
			$(tdOne).addClass("card_modifyCompleted");
			$(tdOne).removeClass("row");

			mdfForm.push($(inputOne).closest('form').attr('id'));
			mdfForm.push($(selectOne).closest('form').attr('id'));

			mdfForm = multiDimensionalUnique(mdfForm);
		});
		
	}
});

//수수료마스터 테이블 수정버튼 클릭 후  input에서 focusout시 원래대로
$(document).on("focusout", ".card_feeInput", function() {
	const inputOne = $(this);
//	const selectOne = $(this);
	const tdOne = inputOne.parent();
//	const tdOne = selectOne.parent();

	const tr = tdOne.parent();
	const td = tr.children();
	const input = td.children();
//	const select = td.children();

	$(tr).css("pointer-events", "auto");
	$(input).removeClass("writeonly");
	$(input).attr("readonly", true);
//	$(select).removeClass("writeonly");
//	$(select).attr("readonly", true);

});
//수수료마스터 테이블 수정버튼 클릭 후 input에서 focusout시 원래대로
$(document).on("focusout", ".card_fee_select_box", function() {

	const selectOne = $(this);
	const tdOne = selectOne.parent();
	
	const tr = tdOne.parent();
	const td = tr.children();
	const select = td.children();
	
	$(tr).css("pointer-events", "auto");
	$(select).removeClass("writeonly");
	$(select).attr("readonly", true);
	
});


$('#CARD_INPUT_CPN_CD').on('input', function() {
	  orderCpnCd = $(this).val();
	});
//form 취소 이벤트
//$(document).on("reset", "form", (form)=>{
//	orderCpnCd = null;
//	const modify_input = $(form.target).find(".card_modify_input");
//	const modify_select = $(form.target).find(".card_modify_select");
//	const def = $(form.target).find(".card_info_table_list_content_td");
//	//reset 후 실행
//	setTimeout(()=>{
//		$(modify_input).each((i, e)=>{
//			$(e).parent().html(e.value);
//		});
//		$(modify_select).each((i, e)=>{
//			const id = $(e).attr("id");
//			const value = $(`#${id} option:selected`).text();
//			$(e).parent().html(value);
//		});
//		$(form.target).find(".card_delete_ctgr").removeClass("card_delete_ctgr")
//		$(".card_hangedTd").removeClass("card_changedTd");
//		$(".card_hangedVal").removeClass("card_changedVal");
//		// 버튼 원래대로
//		$(form.target).find(".card_table_main_btn").removeClass("card_hidden_btn");
//		$(form.target).find(".card_table_sub_btn").addClass("card_hidden_btn");
//		$(".card_insert_tr").addClass("card_hidden_btn");
//
//
//		const td = $('.card_feeInput').parent();
//		$(".card_modifyCompleted").removeClass("card_modifyCompleted");
//		$('.card_fee_select_box').attr('disabled', true);
//		$(td).removeClass('row');
//		$('.card_fee_select_box').removeClass('card_modify_sbox');
//		$('.card_feeTd').removeClass('uneditable');
////		$('#warn_fee').css('display','none');
//		//Form 리셋
//
//	//	location.reload();
//		mdfForm.length = 0;
//		modifyStat2 = false;
//		
//	});
//});
$(document).on("click", ".card_table_cancel_btn", function() {
    // 이전 스크롤 위치 저장
    const scrollY = window.scrollY;
    sessionStorage.setItem('scrollPos', scrollY);

    // 폼 초기화 및 처리
    $(this).closest('form').get(0).reset();
    orderCpnCd = null;
    const modify_input = $(this).closest('form').find(".card_modify_input");
    const modify_select = $(this).closest('form').find(".card_modify_select");

    //reset 후 실행
    $(modify_input).each((i, e)=>{
        $(e).parent().html(e.value);
    });
    $(modify_select).each((i, e)=>{
        const id = $(e).attr("id");
        const value = $(`#${id} option:selected`).text();
        $(e).parent().html(value);
    });
    $(".card_hangedTd").removeClass("card_changedTd");
    $(".card_hangedVal").removeClass("card_changedVal");
    // 버튼 원래대로
    $(this).closest('form').find(".card_table_main_btn").removeClass("card_hidden_btn");
    $(this).closest('form').find(".card_table_sub_btn").addClass("card_hidden_btn");
    $(".card_insert_tr").addClass("card_hidden_btn");

    const td = $('.card_feeInput').parent();
    $(".card_modifyCompleted").removeClass("card_modifyCompleted");
    $('.card_fee_select_box').attr('disabled', true);
    $(td).removeClass('row');
    $('.card_fee_select_box').removeClass('card_modify_sbox');
    $('.card_feeTd').removeClass('uneditable');

    // 삭제 버튼이 추가된 경우 삭제
    $(this).closest('form').find(".card_delete_ctgr").remove();
    $(this).closest('form').find(".card_insert_tr").remove();

    // 페이지 리로드 후 스크롤 위치 이동
    window.location.reload();
});
//새로고침이되도 스크롤을 유지할수있도록하기위함
$(window).on('load', function() {
    const scrollY = sessionStorage.getItem('scrollPos');
    if (scrollY !== null) {
        window.scrollTo(0, scrollY);
        sessionStorage.removeItem('scrollPos');
    }
});



//수정 -> 삭제 버튼클릭시 delete 
$(document).on("click", ".card_delete_ctgr", (event)=>{
	  const cpn_type = $(event.target).parent().find("#CARD_CPN_TYPE").text().trim();
	  const cpn_cd = $(event.target).parent().find("#CARD_CPN_CD").text().trim();
	  const cms_apply_type = $(event.target).parent().find("#CARD_CMS_APPLY_TYPE").text().trim();
	  swal({
	    text: "삭제 하시겠습니까?",
	    buttons: true,
	  }).then((willDelete) => {
	    if (willDelete) {
	      $.ajax({
	        url: 'oms_sub05del_fee',
	        type: 'POST',
	        data: {
	          cpn_type,
	          cpn_cd,
	          cms_apply_type
	        },
	        success: (data)=>{
	          swal({
	            icon: "success",
	            text: "삭제 되었습니다."
	          }).then(() => {
//	            location.reload();
	          });
	        },
	        error: (request, status, error)=>{
	          const errCode = request.responseText;
	          swal({
	            icon: "error",
	            title: `에러코드 : ${errCode}`,
	            text: "삭제에 실패했습니다"
	          });
	        },
	        complete: ()=>{
	          $(event.target).parent().remove();
	        }
	      });
	    }
	  });
	});




//수수료 등록 버튼 클릭시
$(document).on("click", ".card_table_insert_btn", function() {
	    // 버튼 toggle
		$(this).parent().children().toggleClass("card_hidden_btn");
	    $(".card_regist_ctgr_btn").toggleClass("card_hidden_btn");
	    $(".card_insert_tr").toggleClass("card_hidden_btn");
	    $('.card_insert_select_box').attr('disabled', false);
	    swal({
	      icon: "info",
	      text:"새로운 등록을 진행합니다."
	    });
	  });


var cmsCalcType = [
	 { DEFINITION_CD: "AMOUNT", DEFINITION_NM: "금액" },
	 { DEFINITION_CD: "RATE", DEFINITION_NM: "율" }
	 ];
//수수료등록 ->추가 버튼 클릭시 row생성
$(document).on('click', '.card_table_insert_pbtn', function() {

	  var orderCpnCd = $('#CARD_INPUT_CPN_CD').val();
	  if(orderCpnCd != '01'){
		  var cmsApplyType = [
		  	 
		  	  { DEFINITION_CD: "CARD_NORMAL", DEFINITION_NM: "일반" },
		  	  { DEFINITION_CD: "CARD_LESS_300M", DEFINITION_NM: "3억이하" },
		  	  { DEFINITION_CD: "CARD_LESS_500M", DEFINITION_NM: "5억이하" },
		  	  { DEFINITION_CD: "CARD_LESS_1B", DEFINITION_NM: "10억이하" },
		  	  { DEFINITION_CD: "CARD_LESS_3B", DEFINITION_NM: "30억이하" },
		  	  { DEFINITION_CD: "CARD_OVER_3B", DEFINITION_NM: "30억초과" }
		  	];
		  }else{
		  	var cmsApplyType = [
		  		  { DEFINITION_CD: "CARD_LESS_300M", DEFINITION_NM: "3억이하" },
		  		  { DEFINITION_CD: "CARD_LESS_500M", DEFINITION_NM: "5억이하" },
		  		  { DEFINITION_CD: "CARD_LESS_1B", DEFINITION_NM: "10억이하" },
		  		  { DEFINITION_CD: "CARD_LESS_3B", DEFINITION_NM: "30억이하" },
		  		  { DEFINITION_CD: "CARD_OVER_3B", DEFINITION_NM: "30억초과" }
		  		];
		  }
	  var newRow = $(
			    `<tr class='card_insert_tr card_insert_table_list_tr'>
			      <td>
			        <input type='text' class='card_reg_input ist' id='CARD_INPUT_CPN_TYPE' name='CPN_TYPE' autocomplete='off' value='ORDER' readonly/>
			      </td>
			      <td>
			        <input type='text' class='card_reg_input ist' id='CARD_INPUT_CPN_CD' name='CPN_CD' autocomplete='off' value='${orderCpnCd}' readonly/>
			      </td>
			      <td>
			        <select class='card_insert_select_box' name='CMS_APPLY_TYPE' disabled>
			          <option value='' disabled selected hidden>수수료타입 선택</option>
			          ${cmsApplyType.map(item => `<option value='${item.DEFINITION_NM}'>${item.DEFINITION_NM}</option>`)}
			        </select>
			      </td>
			      <td>
			        <input type='text' class='card_reg_input ist' id='CARD_INPUT_CMS_APPLY_TYPE_NM' name='CMS_APPLY_TYPE_NM' autocomplete='off'/>
			      </td>
			      <td>
			        <select class='card_insert_select_box' name='CMS_CALC_TYPE' disabled>
			          <option value='' disabled selected hidden>수수료금액 선택</option>
			          ${cmsCalcType.map(item => `<option value='${item.DEFINITION_NM}'>${item.DEFINITION_NM}</option>`)}
			        </select>
			      </td>
			      <td>
			        <input type='text' class='card_reg_input ist' id='CARD_INPUT_CMS_CALC_TYPE_NM' name='CMS_CALC_TYPE_NM' autocomplete='off'/>
			      </td>
			      <td></td><td></td><td></td>
			      <td class="remove_tr" style="text-align: center;">
			        취소
			      </td>
			    </tr>`
			  );

			  newRow.find(".card_insert_select_box").prop("disabled", false);
			  $(".card_table_insert_tbody").append(newRow);
	});
//추가버튼 클릭 후 추가된 row의 취소버튼 클릭시 remove
$(document).on('click', '.remove_tr', function() {
    $(this).closest('tr').remove();
});


//등록버튼클릭시
$(document).on('click', '#card_reg_submit', function() {
	
	  const regVal = $('#card_channel_reg').serializeObject();
	  const tableData = [];

	  for(let i = 0; i < $(".card_insert_table_list_tr").length; i++) {
	    const currentRow = $(".card_insert_table_list_tr")[i];
	    const CPN_TYPE = $(currentRow).find("input[name='CPN_TYPE']").val();
	    const CPN_CD = $(currentRow).find("input[name='CPN_CD']").val();
	    const CMS_APPLY_TYPE = $(currentRow).find("select[name='CMS_APPLY_TYPE']").val();
	    const CMS_APPLY_TYPE_NM = $(currentRow).find("input[name='CMS_APPLY_TYPE_NM']").val();
	    const CMS_CALC_TYPE = $(currentRow).find("select[name='CMS_CALC_TYPE']").val();
	    const CMS_CALC_TYPE_NM = $(currentRow).find("input[name='CMS_CALC_TYPE_NM']").val();
	    
	    
	    // 값이 없는 경우 모달창을 띄우고 함수 종료
	    if (!CMS_APPLY_TYPE_NM || !CMS_CALC_TYPE || !CMS_CALC_TYPE_NM) {
	      swal({
	        text: "입력된 데이터가 없습니다.",
	        icon: "warning"
	      });
	      return;
	    }
	    tableData.push({
	      "CPN_TYPE": CPN_TYPE,
	      "CPN_CD": CPN_CD,
	      "CMS_APPLY_TYPE": CMS_APPLY_TYPE,
	      "CMS_APPLY_TYPE_NM": CMS_APPLY_TYPE_NM,
	      "CMS_CALC_TYPE": CMS_CALC_TYPE,
	      "CMS_CALC_TYPE_NM": CMS_CALC_TYPE_NM
	    });
	  }

	  regVal["list"] = tableData;

	  $.ajax({
	    url: 'channel_fee_register',
	    contentType: 'application/json',
	    dataType: 'json',
	    type: 'POST',
	    data: JSON.stringify(regVal),
	    success: function(data) {
	    	const scrollY = window.scrollY;
	        sessionStorage.setItem('scrollPos', scrollY);
	      swal({
	        text: "등록되었습니다.",
	        icon: "success"
	      }).then(function() {
	        location.reload();
	      });
	    },
	    error: function(request, status, error) {
	      const errCd = request.responseText;
	      let errMsg = errCodeCheck(errCd);
	      if (errMsg == false) {
	        swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
//	        location.reload();
	        
	        return;
	      }
	      swal({
	        text: errMsg,
	        icon: "error"
	      }).then(function() {
//	        cancel();
	      });
	    }
	  });
	});

/*************************************************************************************/
// 수정시 저장버튼 클릭->컨트롤러 송신 (수수료마스터 카수정폼)
function modifyComplete(event){
	const modiList = [];
	const fList = {};
	const trList = document.querySelectorAll(".info_table_list_content_tr");
	afterTrCnt = trList.length;
	for(let a=0; a<trList.length; a++) {
		const tdItems = trList[a].querySelectorAll("td");
		for(let j=0; j<tdItems.length; j++){
			const tdItem = tdItems.item(j);
			if(tdItem.classList.contains("modifyCompleted")){
//				modiList.push(a);
				modiList.push(tdItems);
				break;
			}
		}
	}
	const sendData = [];
	let modiRow = {};
	for(let i=0; i<modiList.length; i++) {
		modiRow = {
			cpnType: modiList[i].item(0).innerText,
			cpnCd: modiList[i].item(1).innerText,
			cmsApplyType: modiList[i].item(2).innerText,
			cmsApplyTypeNm: modiList[i].item(3).querySelector("input").value,
			cmsCalcType: modiList[i].item(4).querySelector("select").value,
			cmsCalcTypeNm: modiList[i].item(5).querySelector("input").value


		};
		sendData.push(modiRow);
	}

	console.log(sendData);
	const form = $(event).closest("form");
	const form_name = $(form).attr("name");
	const changeVal = $(form).find(".modifyCompleted");
	const changeTr = $(changeVal).closest(".info_table_list_content_tr");
	const table_title = $(form).find(".info_table_title_span").text();
	let serialize = new Object;
	const modifyArr = [];
	swal({
		text: "저장하시겠습니까?",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {
			const regVal = $('#channel_reg').serializeObject();
			const CPN_TYPE = "ORDER";
			if (changeVal.length === 0)  {
				if(beforeTrCnt === afterTrCnt){
					swal({
				    text: "수정된 데이터가 없습니다.",
				    icon: "warning"
				  }).then(function() {
				    return;
				  });
				}else{
					const scrollY = window.scrollY;
			        sessionStorage.setItem('scrollPos', scrollY);
					swal({
						text: "저장되었습니다.",
						icon: "success"
					}).then(function() {
						location.reload();
						opener.parent.search_go();
					});
				}
			} else {
				const cpnCd = $('#CPN_CD').text().trim();
				const cpnType = $('#CPN_TYPE').text().trim();
				const cmsApplyType = $('#CMS_APPLY_TYPE').text().trim();
				const cmsApplyTypeNm = $('#CMS_APPLY_TYPE_NM').children().get(0).value;
				const cmsCalcType = $('#CMS_CALC_TYPE').children().get(0).value;
				const cmsCalcTypeNm = $('#CMS_CALC_TYPE_NM').children().get(0).value;

//				let channel_reg = "";
//				for (let i = 0; i < mdfForm.length; i++) {
//					if (mdfForm[i] == 'channel_reg') {
//						channel_reg = $('#channel_reg').serializeObject();
//					}
//				}
				console.log(JSON.stringify({
						cpnCd,
						cpnType,
						cmsApplyType,
						cmsApplyTypeNm,
						cmsCalcType,
						cmsCalcTypeNm,}
						));
				$.ajax({
					url: 'oms_sub05_02edit_fee',
					contentType: 'application/json',
					dataType: 'json',
					type: 'POST',
					traditional: true,
					data: JSON.stringify(sendData),
					success: function(data) {
						const scrollY = window.scrollY;
				        sessionStorage.setItem('scrollPos', scrollY);
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
function card_modifyComplete(event){
	const modiList = [];
	const fList = {};
	const trList = document.querySelectorAll(".card_info_table_list_content_tr");
	afterTrCnt = trList.length;
	for(let a=0; a<trList.length; a++) {
		const tdItems = trList[a].querySelectorAll("td");
		for(let j=0; j<tdItems.length; j++){
			const tdItem = tdItems.item(j);
			if(tdItem.classList.contains("card_modifyCompleted")){
//				modiList.push(a);
				modiList.push(tdItems);
				break;
			}
		}
	}
	const sendData = [];
	let modiRow = {};
	for(let i=0; i<modiList.length; i++) {
		modiRow = {
				cpnType: modiList[i].item(0).innerText,
				cpnCd: modiList[i].item(1).innerText,
				cmsApplyType: modiList[i].item(2).innerText,
				cmsApplyTypeNm: modiList[i].item(3).querySelector("input").value,
				cmsCalcType: modiList[i].item(4).querySelector("select").value,
				cmsCalcTypeNm: modiList[i].item(5).querySelector("input").value
				
				
		};
		sendData.push(modiRow);
	}
	
	console.log(sendData);
	const form = $(event).closest("form");
	const form_name = $(form).attr("name");
	const changeVal = $(form).find(".card_modifyCompleted");
	const changeTr = $(changeVal).closest(".card_info_table_list_content_tr");
	const table_title = $(form).find(".card_info_table_title_span").text();
	let serialize = new Object;
	const modifyArr = [];
	swal({
		text: "저장하시겠습니까?",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {
			const regVal = $('#card_channel_reg').serializeObject();
			const CPN_TYPE = "ORDER";
			if (changeVal.length === 0)  {
				if(beforeTrCnt === afterTrCnt){
					swal({
				    text: "수정 된 데이터가 없습니다.",
				    icon: "warning"
				  }).then(function() {
				    return;
				  });
				}else{
					const scrollY = window.scrollY;
			        sessionStorage.setItem('scrollPos', scrollY);
					swal({
						text: "저장되었습니다.",
						icon: "success"
					}).then(function() {
						location.reload();
						opener.parent.search_go();
					});
				}
				} else {
				const cpnCd = $('#CARD_CPN_CD').text().trim();
				const cpnType = $('#CARD_CPN_TYPE').text().trim();
				const cmsApplyType = $('#CARD_CMS_APPLY_TYPE').text().trim();
				const cmsApplyTypeNm = $('#CARD_CMS_APPLY_TYPE_NM').children().get(0).value;
				const cmsCalcType = $('#CARD_CMS_CALC_TYPE').children().get(0).value;
				const cmsCalcTypeNm = $('#CARD_CMS_CALC_TYPE_NM').children().get(0).value;
				
//				let channel_reg = "";
//				for (let i = 0; i < mdfForm.length; i++) {
//					if (mdfForm[i] == 'channel_reg') {
//						channel_reg = $('#channel_reg').serializeObject();
//					}
//				}
				console.log(JSON.stringify({
					cpnCd,
					cpnType,
					cmsApplyType,
					cmsApplyTypeNm,
					cmsCalcType,
					cmsCalcTypeNm,}
				));
				$.ajax({
					url: 'oms_sub05_02edit_fee',
					contentType: 'application/json',
					dataType: 'json',
					type: 'POST',
					traditional: true,
					data: JSON.stringify(sendData),
					success: function(data) {
						const scrollY = window.scrollY;
				        sessionStorage.setItem('scrollPos', scrollY);
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
	
	

	





	






