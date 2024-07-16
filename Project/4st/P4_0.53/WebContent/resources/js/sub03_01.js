let grid;

$(document).ready(function() {
	//dhtmlx 기본 양식
	grid = new dhx.Grid("grid", {
	    columns: [
	        { maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
	        { id: "ord_id", header: [{ text: "주문번호", align: "center" }] },
	        { id: "store_id", header: [{ text: "매장코드", align: "center" }] },
	        { id: "store_nm", header: [{ text: "매장명", align: "center" }] },
	        { id: "order_ch_nm", header: [{ text: "채널사명", align: "center" }], tooltipTemplate: (value, row, col) => row.franchise_code },
	        { id: "ord_state", header: [{ text: "주문상태", align: "center" }] },
	        { id: "pickup_name", header: [{ text: "주문방식", align: "center" }], tooltipTemplate: (value, row, col) => row.pickup_code },
	        { id: "deliver_fee", header: [{ text: "배송료", align: "center" }], type: "number", format: "#,#"  },
	        { id: "act_amt", header: [{ text: "결제금액", align: "center" }], type: "number", format: "#,#"  },
	        { id: "tot_amt", header: [{ text: "합계금액", align: "center" }], type: "number", format: "#,#"  },
	        { id: "tot_vat", header: [{ text: "부가세", align: "center" }], type: "number", format: "#,#"  },
	        { id: "ord_addr", header: [{ text: "주소", align: "center" }] },
	        { id: "ord_addr_detail", header: [{ text: "상세주소", align: "center" }] },
	        { id: "dtord_no", header: [{ text: "상세주문번호", align: "center" }], type: "number" },
	        { id: "phone", header: [{ text: "배송연락처", align: "center" }],
				template: function(text) {
					return phoneNumberFormat(text);
				}
	        },
	        { id: "dlv_ch_nm", header: [{ text: "배달대행사명", align: "center" }] },
	        { id: "car_number", header: [{ text: "차량정보", align: "center" }] },
	        { id: "expected_pickup_time", header: [{ text: "픽업예상시간", align: "center" }],
				template: function(text) {
					return datetimeFormat(text);
				}
			},
	        { id: "rst_msg", header: [{ text: "처리결과메시지", align: "center" }] },
	        { id: "regdate", header: [{ text: "등록일시", align: "center" }],
				template: function(text) {
					return unixTimeFormat(text);
				}
	        },
	        { id: "modi_date", header: [{ text: "최종변경일시", align: "center" }],
				template: function(text) {
					return unixTimeFormat(text);
				}
	        },
	        { hidden: true, id: "franchise_code", header: [{ text: "가맹점코드", align: "center" }] }
	    ],
	    autoWidth: true,
	    adjust: true,
	    selection: false,
	    resizable: true
	});
	
	grid.events.on("cellDblClick", function () {
        eventHandler("cellDblClick", arguments);
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
	const ordst = $('#ordst').val();

	const path = 'orderList_excel';
	const params = {
		scd,
		ono,
		occd,
		chono,
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
	const ordst = $('#ordst').val();
	const pageNum = num;

	$.ajax({
		url: 'getOrderList',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			scd,
			ono,
			chono,
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
			
			console.log(data);

			let obLength = Object.keys(data.orderList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {
					grid.data.parse(data.orderList);
				});
				$(".btn_excel").attr("disabled", true);
			} else if (obLength > 0) {

				grid.data.parse(data.orderList);
				
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