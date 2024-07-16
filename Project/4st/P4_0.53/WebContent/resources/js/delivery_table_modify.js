/*
 * 작성자 : 이준혁
 * 주석처리날짜 : 2022-08-26
 */
//수정모드 true/false
var deliveryCpnCd;
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

	 //수수료 등록 버튼 클릭시 나오는 추가 버튼클릭시 값을 넣어주기위해사용
	 var cmsApplyType = [
		  { DEFINITION_CD: "BASE", DEFINITION_NM: "기본" },
		  { DEFINITION_CD: "HARD_AREA", DEFINITION_NM: "지역" },
		  { DEFINITION_CD: "NIGHT", DEFINITION_NM: "야간" },
		  { DEFINITION_CD: "OVERLOAD", DEFINITION_NM: "과적" },
		  { DEFINITION_CD: "WEATHER", DEFINITION_NM: "기상" }
		];
	 var cmsCalcType = [
		 { DEFINITION_CD: "AMOUNT", DEFINITION_NM: "금액" },
		 { DEFINITION_CD: "RATE", DEFINITION_NM: "율" }
		 ];
	 //수수료등록 버튼 클릭시 이벤트
		$('.table_insert_pbtn').click(function() {
		  var deliveryCpnCd = $('#INPUT_CPN_CD').val();
		  var newRow = $(
		    `<tr class='insert_tr insert_table_list_tr'>
		      <td>
		        <input type='text' class='reg_input ist' id='INPUT_CPN_TYPE' name='CPN_TYPE' autocomplete='off' value='DELIVERY' readonly/>
		      </td>
		      <td>
		        <input type='text' class='reg_input ist' id='INPUT_CPN_CD' name='CPN_CD' autocomplete='off' value='${deliveryCpnCd}' readonly/>
		      </td>
		      <td>
		        <select class='insert_select_box' name='CMS_APPLY_TYPE' disabled>
		          <option value='' disabled selected hidden>수수료 선택</option>
		          ${cmsApplyType.map(item => `<option value='${item.DEFINITION_NM}'>${item.DEFINITION_NM}</option>`)}
		        </select>
		      </td>
		      <td>
		        <input type='text' class='reg_input ist' id='INPUT_CMS_APPLY_TYPE_NM' name='CMS_APPLY_TYPE_NM' autocomplete='off'/>
		      </td>
		      <td>
		        <select class='insert_select_box' name='CMS_CALC_TYPE' disabled>
		          <option value='' disabled selected hidden>수수료금액 선택</option>
		          ${cmsCalcType.map(item => `<option value='${item.DEFINITION_NM}'>${item.DEFINITION_NM}</option>`)}
		        </select>
		      </td>
		      <td>
		        <input type='text' class='reg_input ist' id='INPUT_CMS_CALC_TYPE_NM' name='CMS_CALC_TYPE_NM' autocomplete='off'/>
		      </td>
		       <td style="border:none;"></td>
		       <td style="border:none;"></td>
		       <td style="border:none;"></td>
			      <td class="remove_tr" style="text-align: center;">
			        취소
			      </td>
		    </tr>`
		  );
		  newRow.find(".insert_select_box").prop("disabled", false);
		  $(".table_insert_tbody").append(newRow);
		});
		//추가버튼 클릭 후 추가된 row의 취소버튼 클릭시 remove
		$(document).on('click', '.remove_tr', function() {
		    $(this).closest('tr').remove();
		});
	 
	


	

		//등록버튼클릭시
		$("#reg_submit").click(function() {
			  const regVal = $('#delivery_reg').serializeObject();
			  const tableData = [];

			  for(let i = 0; i < $(".insert_table_list_tr").length; i++) {
			    const currentRow = $(".insert_table_list_tr")[i];
			    const CPN_TYPE = $(currentRow).find("input[name='CPN_TYPE']").val();
			    const CPN_CD = $(currentRow).find("input[name='CPN_CD']").val();
			    const CMS_APPLY_TYPE = $(currentRow).find("select[name='CMS_APPLY_TYPE']").val();
			    const CMS_APPLY_TYPE_NM = $(currentRow).find("input[name='CMS_APPLY_TYPE_NM']").val();
			    const CMS_CALC_TYPE = $(currentRow).find("select[name='CMS_CALC_TYPE']").val();
			    const CMS_CALC_TYPE_NM = $(currentRow).find("input[name='CMS_CALC_TYPE_NM']").val();

			    // 값이 없는 경우 모달창을 띄우고 함수 종료
			    if (!CMS_APPLY_TYPE || !CMS_CALC_TYPE) {
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
			    url: 'delivery_fee_register',
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
			  //      location.reload();
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
			});
		//삭제버튼 클릭시
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
		
		
		
	
		
		
		
	
	//수정모드 마우스 over 시 input줄 회색
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
	//마우스 leave시 원래대로
	$(document).on("mouseleave", "td", function() {
		const td = $(this);
		const input = td.children(".feeInput");
		const select = td.children(".fee_select_box");

		$(input).removeClass('input_hover');
		$(select).removeClass('input_hover');
	});

	//input click시 수정 감지 및 데이터 저장 ( 수정모드 )
	$(document).on("click", "#delivery_reg td", function() {
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

	//input에서 focusout시 원래대로
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
	//input에서 focusout시 원래대로
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
		  deliveryCpnCd = $(this).val();
		});
	//form 취소 이벤트
	$(document).on("reset", "form", (form)=>{
		const scrollY = window.scrollY;
	    sessionStorage.setItem('scrollPos', scrollY);
		deliveryCpnCd = null;
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

//			location.reload();
			mdfForm.length = 0;
			modifyStat = false;
			// 페이지 리로드 후 스크롤 위치 이동
		    window.location.reload();
			
		});
	});
});
//새로고침이되도 스크롤을 유지할수있도록하기위함
$(window).on('load', function() {
    const scrollY = sessionStorage.getItem('scrollPos');
    if (scrollY !== null) {
        window.scrollTo(0, scrollY);
        sessionStorage.removeItem('scrollPos');
    }
});



// 수정시 저장버튼 클릭->컨트롤러 송신
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
			const regVal = $('#delivery_reg').serializeObject();
			const CPN_TYPE = "DELVIERY";
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





