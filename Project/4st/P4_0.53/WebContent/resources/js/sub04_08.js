let grid;

$(document).ready(function() {
	
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
		$('#cancel_reason').val('');
	});

	//dhtmlx 기본 양식
	grid = new dhx.Grid("grid", {
		columns: [
			{ id: "cpn_nm", header: [{ text: "구분", align: "center" }, { text: "상세", align: "center" }], align: "center" },
			{ id: "platform_amt", header: [{ text: "플랫폼매출액", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "platform_fee", header: [{ text: "플랫폼수수료1", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "platform_fee2", header: [{ text: "플랫폼수수료2", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "custormer_charge", header: [{ text: "고객부담금", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "deliver_event_fund", header: [{ text: "배달료 행사지원금", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "additional_charge", header: [{ text: "추가부담금", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "delivery_agency_fee", header: [{ text: "배달대행료", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "deliver_support_fund", header: [{ text: "배달지원금", rowspan: 2, align: "center" }], type: "number", format: "#,#" },
			{ id: "kis_fee", header: [{ text: "KIS 중계수수료", rowspan: 2, align: "center" }], type: "number", format: "#,#" }
		],
		css: "alternate_row",
		rowCss: function(row) { return row.cpn_nm == "합계" ? "tot_row" : "" },
		headerRowHeight: 25,
		rowHeight: 30,
		autoWidth: true,
		selection: false,
		resizable: true,
		sortable: false
	});

	grid.events.on("cellDblClick", function() {
		dblClickHandler("cellDblClick", arguments);
	});
	
	/* MonthPicker Set */
	let thisDt = new Date();
	let year = thisDt.getFullYear();
	let month = ("0" + (thisDt.getMonth() + 1)).slice(-2);
	$('#month').val(`${year}년 ${month}월`);
	$('#month').monthpicker();
});

//엑셀 다운
function ExcelDown() {
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');
	const stype = $('#stype option:selected').val();
	let path = 'ExcelDownload_TOTAL';

	const params = {
		month,
		stype
	}
	post(path, params);
}

//정산 엑셀 다운
function ExcelDown_settle() {
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');
	const stype = $('#stype option:selected').val();
	/*const scd = $('#scd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();*/
	const channel = $('#channel option:selected').val();

	let path = 'ExcelDownload_Synthesis' ;

	const params = {
		channel,
		month,
		stype
	}
	post(path, params);
}

//검색 쿼리
function search_go(num) {
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');
	const stype = $('#stype option:selected').val();

	$.blockUI({
		message: '<img src="./resources/img/loading_spiner.png"/>',
		css: {
			backgroundColor: 'rgba(0,0,0,0.0)',
			color: '#000000',
			border: '0px solid #a00'
		}
	});

	$.ajax({
		url: 'oms_sub04_08_process',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			month,
			stype
		},
		success: function(data) {

			$.unblockUI();

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

// 엑셀 다운로드
function download_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
	$('#cancel_reason').val('');
	$('#cancel_reason').focus();
}

//상세정보 페이지 이동
function dblClickHandler(event, arguments) {
	/*const stocd = arguments[0].cpn_cd;

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "storeList_detail";
	form.style.display = "none";

	let Inputscd = document.createElement("input");
	Inputscd.type = "text";
	Inputscd.name = "stocd";
	Inputscd.value = stocd;

	form.appendChild(Inputscd);

	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);*/

	swal("준비중입니다.");
};