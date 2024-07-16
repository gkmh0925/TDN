let grid;
//dhtmlx 기본 양식 (부릉)
const vroongGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{
			minWidth: 100, id: "receipt_date", header: [{ text: "일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "store_id", header: [{ text: "상점코드", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "상점명", align: "center" }] },
		{ minWidth: 140, id: "franchisee_nm", header: [{ text: "프렌차이즈명", align: "center" }] },
		{ minWidth: 150, id: "franchisee_type", header: [{ text: "프렌차이즈유형", align: "center" }] },
		{ minWidth: 140, id: "vroong_no", header: [{ text: "부릉오더넘버", align: "center" }] },
		{ minWidth: 180, id: "re_vroong_no", header: [{ text: "재배송부릉오더넘버", align: "center" }] },
		{ minWidth: 180, id: "prime_delivery_no", header: [{ text: "프라임딜리버리넘버", align: "center" }] },
		{ minWidth: 160, id: "ord_id", header: [{ text: "자체주문번호", align: "center" }] },
		{ minWidth: 150, id: "self_delivery_no", header: [{ text: "자체배송번호", align: "center" }] },
		{ minWidth: 80, id: "state", header: [{ text: "상태", align: "center" }] },
		{ minWidth: 120, id: "start_address", header: [{ text: "출발지주소", align: "center" }] },
		{ minWidth: 120, id: "finish_address", header: [{ text: "도착지주소", align: "center" }] },
		{ minWidth: 100, id: "pay_type", header: [{ text: "결제수단", align: "center" }] },
		{ minWidth: 100, id: "item_amt", header: [{ text: "상품가액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "cancel_cms", header: [{ text: "취소수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "settle_item_amt", header: [{ text: "정산상품가액(송급포함)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "basic_deliver_fee", header: [{ text: "기본배송료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "add_distance_amt", header: [{ text: "거리추가", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "weather_premium_amt", header: [{ text: "기상할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "area_premium_amt", header: [{ text: "지역할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "pickup_premium_amt", header: [{ text: "픽업할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "holiday_premium_amt", header: [{ text: "명절할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "overload_premium_amt", header: [{ text: "과적할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 140, id: "re_move_deliver_fee", header: [{ text: "재이동배송비", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "return_deliver_fee", header: [{ text: "반납배송비", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 160, id: "settle_cancel_cms", header: [{ text: "정산취소수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "card_cms", header: [{ text: "카드수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "store_deposit_amt", header: [{ text: "상점입금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 160, id: "delivery_agency_cms", header: [{ text: "배달대행수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "card_cms_rate", header: [{ text: "카드수수료율(%)", align: "center" }] },
		{ minWidth: 80, id: "bigo", header: [{ text: "비고", align: "center" }] },
		{ minWidth: 80, id: "distance", header: [{ text: "거리", align: "center" }], type: "number" },
		{
			minWidth: 100, id: "receipt_time", header: [{ text: "접수시각", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "assigned_time", header: [{ text: "배차시각", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "pickup_time", header: [{ text: "픽업시각", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "complet_time", header: [{ text: "완료시각", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "cancel_time", header: [{ text: "취소시각", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 160, id: "final_pay_type", header: [{ text: "최종결제수단", align: "center" }] },
		{ minWidth: 100, id: "complet_count", header: [{ text: "완료건수", align: "center" }] },
		{ minWidth: 100, id: "cancel_count", header: [{ text: "취소건수", align: "center" }] },
		{ minWidth: 100, id: "premium_amt", header: [{ text: "할증금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_memo", header: [{ text: "할증내용", align: "center" }] },
		{ minWidth: 120, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
			template: function(text) {
				return unixTimeFormat(text);
			}
		}
	],
	autoWidth: true,
	adjust: true,
	selection: false,
	resizable: true
};
//dhtmlx 기본 양식 (바로고)
const barogoGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "receipt_no", header: [{ text: "접수번호", align: "center" }] },
		{ minWidth: 100, id: "ord_id", header: [{ text: "주문번호", align: "center" }] },
		{
			minWidth: 100, id: "receipt_date", header: [{ text: "접수일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "receipt_time", header: [{ text: "접수시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "complet_time", header: [{ text: "완료시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "cancel_time", header: [{ text: "최소시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 140, id: "ord_issuer_nm", header: [{ text: "발주가맹점명", align: "center" }] },
		{ minWidth: 80, id: "distance", header: [{ text: "거리", align: "center" }] },
		{ minWidth: 100, id: "basic_fare_amt", header: [{ text: "기본운임", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "distance_fare_amt", header: [{ text: "거리운임", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "late_nigth_premium_amt", header: [{ text: "심야할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "rain_premium_amt", header: [{ text: "우천할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "add_premium_amt", header: [{ text: "추가할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "area_premium_amt", header: [{ text: "구역할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "item_premium_amt", header: [{ text: "상품할증", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "delivery_fare_amt", header: [{ text: "배송운임", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 80, id: "vat_amt", header: [{ text: "VAT", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "vat_include_amt", header: [{ text: "VAT포함", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "receipt_ch", header: [{ text: "접수채널", align: "center" }] },
		{ minWidth: 80, id: "bigo", header: [{ text: "비고", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
			template: function(text) {
				return unixTimeFormat(text);
			}
		}
	],
	autoWidth: true,
	adjust: true,
	selection: false,
	resizable: true
};
//dhtmlx 기본 양식 (생각대로)
const logiallGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "점포코드", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "점포명", align: "center" }] },
		{
			minWidth: 100, id: "deliver_date", header: [{ text: "배달일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "receipt_time", header: [{ text: "접수시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "complet_time", header: [{ text: "완료시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "cancel_time", header: [{ text: "취소시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 120, id: "delivery_agency_fee", header: [{ text: "배달대행료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_fee", header: [{ text: "할증료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_reason", header: [{ text: "할증사유", align: "center" }] },
		{ minWidth: 120, id: "delivery_tot_amt", header: [{ text: "배달총금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 80, id: "distance", header: [{ text: "거리", align: "center" }] },
		{ minWidth: 100, id: "delivery_state", header: [{ text: "배달상태", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
			template: function(text) {
				return unixTimeFormat(text);
			}
		}

	],
	autoWidth: true,
	adjust: true,
	selection: false,
	resizable: true
};
//dhtmlx 기본 양식 (딜버)
const dealverGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "점포코드", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "점포명", align: "center" }] },
		{
			minWidth: 100, id: "deliver_date", header: [{ text: "배달일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "receipt_time", header: [{ text: "접수시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "complet_time", header: [{ text: "완료시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "cancel_time", header: [{ text: "취소시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 120, id: "delivery_agency_fee", header: [{ text: "배달대행료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_fee", header: [{ text: "할증료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_reason", header: [{ text: "할증사유", align: "center" }] },
		{ minWidth: 120, id: "delivery_tot_amt", header: [{ text: "배달총금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 80, id: "distance", header: [{ text: "거리", align: "center" }] },
		{ minWidth: 100, id: "delivery_state", header: [{ text: "배달상태", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
			template: function(text) {
				return unixTimeFormat(text);
			}
		}

	],
	autoWidth: true,
	adjust: true,
	selection: false,
	resizable: true
};
//dhtmlx 기본 양식 (스파이더)
const spidorGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "점포코드", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "점포명", align: "center" }] },
		{
			minWidth: 100, id: "deliver_date", header: [{ text: "배달일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "receipt_time", header: [{ text: "접수시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "complet_time", header: [{ text: "완료시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "cancel_time", header: [{ text: "취소시간", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 120, id: "delivery_agency_fee", header: [{ text: "배달대행료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_fee", header: [{ text: "할증료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "premium_reason", header: [{ text: "할증사유", align: "center" }] },
		{ minWidth: 120, id: "delivery_tot_amt", header: [{ text: "배달총금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 80, id: "distance", header: [{ text: "거리", align: "center" }] },
		{ minWidth: 100, id: "delivery_state", header: [{ text: "배달상태", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
			template: function(text) {
				return unixTimeFormat(text);
			}
		}

	],
	autoWidth: true,
	adjust: true,
	selection: false,
	resizable: true
};

//dhtmlx 기본 양식 (체인로지스)
const chainGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "ord_date", header: [{ text: "주문일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "ord_id", header: [{ text: "주문번호", align: "center" }] },
		{ minWidth: 100, id: "deliver_date", header: [{ text: "배달일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "set_chainlogis_dv_seq", header: [{ text: "체인로지스 배달 시퀀스", align: "center" }] },
		{ minWidth: 100, id: "reservation_no", header: [{ text: "예약번호", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "점포명", align: "center" }] },
		{ minWidth: 100, id: "delivery_tot_amt", header: [{ text: "배달총금액(부가세포함)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "user_nm", header: [{ text: "사용자명", align: "center" }] },
		{ minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
			template: function(text) {
				return unixTimeFormat(text);
			}
		}
	],
	autoWidth: true,
	adjust: true,
	selection: false,
	resizable: true
};
$(document).ready(function() {
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
		$('.shide_div').removeClass('show_div');
		$('#cancel_reason').val('');
	});
	
	var fileTarget = $('.filebox .upload-hidden');
	fileTarget.on('change', function() {
		// 값이 변경되면 
		if (window.FileReader) {
			// modern browser 
			if (isNotEmpty($("#excelFile").val())) {
				var filename = $(this)[0].files[0].name;

			} else {
				var filename = '';

			}
		} else {
			// old IE
			var filename = $(this).val().split('/').pop().split('\\').pop();
			// 파일명만 추출 
		} // 추출한 파일명 삽입
		$(this).siblings('.upload-name').val(filename);
	});

	grid = new dhx.Grid("grid", vroongGrid);

	$('#dv_view').on("change", function() {
		$('.paging').empty();
		if ($('#dv_view option:selected').val() == "01") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", vroongGrid);

		} else if ($('#dv_view option:selected').val() == "02") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", barogoGrid);

		} else if ($('#dv_view option:selected').val() == "03") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", logiallGrid);

		}else if ($('#dv_view option:selected').val() == "04") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", dealverGrid);

		}else if ($('#dv_view option:selected').val() == "05") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", spidorGrid);

		}else if ($('#dv_view option:selected').val() == "08") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", chainGrid);

		}

	});
	/* MonthPicker Set */
	let thisDt = new Date();
	let year = thisDt.getFullYear();
	let month = ("0" + (thisDt.getMonth() + 1)).slice(-2);
	$('#tmonth').val(`${year}년 ${month}월`);
	$('#tmonth').monthpicker();

});

// 엑셀 업로드 버튼 클릭
function upload_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
	$('#cancel_reason').val('');
	$('#cancel_reason').focus();
	
	/* MonthPicker Set */
	let thisDt = new Date();
	let year = thisDt.getFullYear();
	//thisDt.getMonth() + 0 이부분을 +1을 주면 당월로됨
	let month = ("0" + (thisDt.getMonth() + 0)).slice(-2);
	$('#month').val(`${year}년 ${month}월`);
	$('#month').monthpicker();
}

//엑셀 다운
function ExcelDown() {
	/*const sdate = $("#sdate").val().replaceAll('-', '');
	const edate = $("#edate").val().replaceAll('-', '');
	const scd = $('#scd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();*/
	const tmonth = $("#tmonth").val().replaceAll(/[^0-9]/g, '');
	const frccd = $('#dv_view option:selected').val();

	let path = 'ExcelDownload_DV_' + frccd;

	const params = {
		/*scd,
		ono,
		chono,
		sdate,
		edate*/
		tmonth
	}
	post(path, params);
}
//샘플엑셀 다운로드
function download_click() {
	$('.overlay').fadeIn(100);
	$('.shide_div').addClass('show_div');
	$('#cancel_reason').val('');
	$('#cancel_reason').focus();
}
//샘플 엑셀 다운
function ExcelDown_settle() {
	const delivery = $('#delivery option:selected').val();
//	const frccd = $('#ch_view option:selected').val();
//	const sfrccd = $('#delivery').val();
//	const addnm = $('#addsample_selectbox option:checked').val()
	const frcnm = $('#delivery option:checked').text();

	let path = 'Excel_Sample_Download_DV_'+ delivery ;
	

	const params = {
		delivery
	}
	post(path, params);
}
/*// 검색 초기화
function refresh() {
	$('#snm').val('');
	$('#scd').val('');
	$('#ono').val('');
	$('#chono').val('');
	$('#ordst').val('');
}*/


//검색 쿼리
function search_go(num) {
//	const sdate = $("#sdate").val().replaceAll('-', '');
//	const edate = $("#edate").val().replaceAll('-', '');
	const tmonth = $("#tmonth").val().replaceAll(/[^0-9]/g, '');
	const dv_view = $('#dv_view option:selected').val();

//	if (sdate > edate) {
//		swal(
//			"시작일이 종료일보다 클 수 없습니다."
//		).then(function() {
//			$('#sdate').focus();
//		});
//
//		return
//	}

	$.blockUI({
		message: '<img src="./resources/img/loading_spiner.png"/>',
		css: {
			backgroundColor: 'rgba(0,0,0,0.0)',
			color: '#000000',
			border: '0px solid #a00'
		}
	});

	/*const scd = $('#scd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();
	const ordst = $('#ordst').val();*/
	const pageNum = num;

	$.ajax({
		url: 'getDlvFeeList',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			dv_view,
			/*scd,
			ono,
			chono,
			ordst,
			sdate,
			edate,*/
			tmonth,
			pageNum
		},
		success: function(data) {

			$('.paging').children().remove();
			$.unblockUI();

			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.calcList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {
					grid.data.parse(data.calcList);
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {

				grid.data.parse(data.calcList);

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
			swal("데이터를 가져오지 못했습니다.");
			$("#excel_download").attr("disabled", true);
			$.unblockUI();
		}
	});
}


//엑셀 파일 체크
function checkFileType(filePath) {
	var fileFormat = filePath.split(".");
	if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1)  {
		return true;
	} else {
		return false;
	}

}

// 엑셀 업로드
function excelupload() {
	let file = $("#excelFile").val();

	if (file == "" || file == null) {
		swal("파일을 선택해주세요.");

		return false;
	} else if (!checkFileType(file)) {
		swal({
			text: "엑셀 파일만 업로드 가능합니다.(xlsx 또는 xls 파일만 가능합니다)",
			icon: "warning"
		});
		$("#upload_name").val('');
		$("#excelFile").val('');

		return false;
	}

	let form = $('#excelUploadForm')[0];
	let size = document.getElementById("excelFile").files[0].size;
	let upTime = Math.round(size / 150000);
	const frccd = $('#franchise_cd').val();
	const frcnm = $('#franchise_cd option:checked').text();

	// FormData 객체 생성
	let formData = new FormData(form);

	if (file == "" || file == null) {
		swal("파일을 선택해주세요.");

		return false;
	} else if (!checkFileType(file)) {
		swal({
			text: "엑셀 파일만 업로드 가능합니다.",
			icon: "warning"
		});
		$("#upload_name").val('');
		$("#excelFile").val('');

		return false;
	}
	swal({
		icon: "warning",
		title: "정말 업로드 하시겠습니까?",
		text: `'${$('.filebox .upload-hidden')[0].files[0].name}' 정산 엑셀을 '${frcnm}' 에 업로드합니다.`,
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {

			$.blockUI({
				message: '<img src="./resources/img/loading_spiner.png"/><br><br><p id="uiMsg">예상 대기시간은 ' + upTime + '분 입니다.',
				css: {
					backgroundColor: 'rgba(0,0,0,0.0)',
					color: '#000000',
					border: '0px solid #a00'
				}
			});
			$.ajax({
				url: 'ExcelUpload_DV_' + frccd,
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					$.unblockUI();
					swal({
						text: "\"" + data.fileName + "\" 파일이 정상 업로드 되었습니다. (성공 : " + data.success + "건 / 실패 : " + data.fail + "건)",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						$('#dv_view').val(frccd).trigger('change');
						//search_go();
						
						if (data.excel) {
							const url = "ExcelDownload_DV_ERR" + frccd;
							const content = {
								"data": JSON.stringify(data.excel)
							}
							post(url, content);
						}
					});
				},
				error: function(request, status, error) {
					$.unblockUI();
					const errCd = request.responseText;
					let errMsg = errCodeCheck(errCd);
					if (errMsg == false) {
						swal(
							"code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error
						).then(function() {
							location.reload();
							return;
						});
					}
					swal({
						title: "업로드 실패",
						text: errMsg,
						icon: "error"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
					});
				}
			})
		}
	});
	$("#upload_name").val('');
	$("#excelFile").val('');
}




