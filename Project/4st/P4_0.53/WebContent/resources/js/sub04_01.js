let grid;

$(document).ready(function() {
	//dhtmlx 기본 양식	
	grid = new dhx.Grid("grid", {
	    columns: [
			{ maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
			{ id: "ord_dt", header: [{ text: "주문일자", align: "center" }],
				template: function(text) {
					return datetimeFormat(text);
				}
			},
			{ id: "chnel_cd", header: [{ text: "제휴사구분코드", align: "center" }] },
			{ id: "chnel_nm", header: [{ text: "제휴사명", align: "center" }] },
			{ id: "str_cd", header: [{ text: "점포코드", align: "center" }] },
			{ id: "str_nm", header: [{ text: "점포명", align: "center" }] },
			{ id: "ch_ord_no", header: [{ text: "채널사주문번호", align: "center" }], type: "number" },
			{ id: "sale_store", header: [{ text: "매출발생점포코드", align: "center" }] },
			{ id: "sale_store_nm", header: [{ text: "매출발생점포명", align: "center" }] },
			{ id: "dtord_no", header: [{ text: "상세주문번호", align: "center" }], type: "number" },
			{ id: "em_ord_no", header: [{ text: "이마트주문번호", align: "center" }] },
			{ id: "tranl_cd", header: [{ text: "배송업체", align: "center" }] },
			{ id: "ord_amt", header: [{ text: "주문금액(배달비제외)", align: "center" }], type: "number", format: "#,#" },
			{ id: "ord_stat", header: [{ text: "주문상태", align: "center" }] },
			{ id: "npay_ord_no", header: [{ text: "네이버 NPAY번호", align: "center" }] },
			{ id: "pickup_cd", header: [{ text: "픽업코드", align: "center" }] },
			{ id: "mfc_tp_cd", header: [{ text: "MFC구분", align: "center" }] },
			{ id: "user_nm", header: [{ text: "사용자코드", align: "center" }] },
			{ id: "reg_date", header: [{ text: "등록일시", align: "center" }],
				template: function(text) {
					return unixTimeFormat(text);
				}
			}
	    ],
	    autoWidth: true,
	    adjust: true,
	    selection: false,
	    resizable: true
	});
});

//엑셀 다운
function ExcelDown() {

	const sdate = $("#sdate").val().replaceAll('-', '');
	const edate = $("#edate").val().replaceAll('-', '');
	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();
	const emono = $('#emono').val();
	const ordst = $('#ordst').val();
	const stype = $('#stype option:selected').val();

	const path = 'posList_excel';
	const params = {
		stype,
		scd,
		ono,
		chono,
		emono,
		occd,
		ordst,
		sdate,
		edate
	}
	post(path, params);
}

//검색 쿼리
function search_go(num) {
	const sdate = $("#sdate").val().replaceAll('-', '');
	const edate = $("#edate").val().replaceAll('-', '');

	if (sdate > edate) {
		swal(
			"시작일이 종료일보다 클 수 없습니다."
		).then(function() {
			$('#sdate').focus();
		});

		return
	}

	$.blockUI({
		message: '<img src="./resources/img/loading_spiner.png"/>',
		css: {
			backgroundColor: 'rgba(0,0,0,0.0)',
			color: '#000000',
			border: '0px solid #a00'
		}
	});

	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();
	const emono = $('#emono').val();
	const ordst = $('#ordst').val();
	const stype = $('#stype option:selected').val();
	const pageNum = num;

	$.ajax({
		url: 'getPosList',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			stype,
			scd,
			ono,
			chono,
			emono,
			occd,
			ordst,
			sdate,
			edate,
			pageNum
		},
		success: function(data) {
			$('.paging').children().remove();
			$.unblockUI();
			
			const page = data.pageMaker.cri.pageNum;
			let startPage = data.pageMaker.startPage;
			let endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.posList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {
					grid.data.parse(data.posList);
				});
				$(".btn_excel").attr("disabled", true);
			} else if (obLength > 0) {

				grid.data.parse(data.posList);
				
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
				$(".btn_excel").attr("disabled", false);
			}
		},
		error: function(e) {
			swal("데이터를 가져오지 못했습니다.");
			$(".btn_excel").attr("disabled", true);
			$.unblockUI();
		}
	});
}