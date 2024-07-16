let grid;

$(document).ready(function() {
	//오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
		$('#cancel_reason').val('');
	});

	var fileTarget = $('.filebox .upload-hidden');
	fileTarget.on('change', function() {
		// 값이 변경되면 
		if (window.FileReader) {
			// modern browser 
			var filename = $(this)[0].files[0].name;
		} else {
			// old IE
			var filename = $(this).val().split('/').pop().split('\\').pop();
			// 파일명만 추출 
		} // 추출한 파일명 삽입
		$(this).siblings('.upload-name').val(filename);
	});
	
	// dhtmlx 기본 양식
	grid = new dhx.Grid("grid", {
    columns: [
    	{ "id": "rn", "header": [{ "text": "순번", "align": "center" }] },
	    { "id": "str_CD", "header": [{ "text": "점포코드", "align": "center" }] },
	    { "id": "str_NM", "header": [{ "text": "점포명", "align": "center" }] },
	    { "id": "sto_TYPE_NM", "header": [{ "text": "직가맹점유형", "align": "center" }] },
	    { "id": "pickup_NM", "header": [{ "text": "서비스구분", "align": "center" }] },
	    { "id": "ord_DATE", "header": [{ "text": "주문일자", "align": "center" }] },
	    { "id": "ord_DT", "header": [{ "text": "배달일자", "align": "center" }] },
	    { "id": "delivery_TIME", "header": [{ "text": "배달시간", "align": "center" }] },
	    { "id": "ch_ORD_NO", "header": [{ "text": "주문번호", "align": "center" }] },
	    { "id": "npay_ORD_NO", "header": [{ "text": "네이버 주문번호", "align": "center" }] },
	    { "id": "order_NM", "header": [{ "text": "주문플랫폼", "align": "center" }] },
	    { "id": "delivery_NM", "header": [{ "text": "배달업체", "align": "center" }] },
	    { "id": "ord_AMT_B", "header": [{ "text": "POS등록액", "align": "center" }]},
	    { "id": "ord_AMT_A", "header": [{ "text": "플랫폼매출액", "align": "center" }]},
	    { "id": "add_DEPOSIT", "header": [{ "text": "추가입금액", "align": "center" }]},
	    { "id": "deliver_FEE", "header": [{ "text": "고객부담금", "align": "center" }]},
	    { "id": "deliver_EVENT_FUND", "header": [{ "text": "배달료행사지원금", "align": "center" }]},
	    { "id": "levy", "header": [{ "text": "추가부담금", "align": "center" }]},
	    { "id": "delivery_AMT", "header": [{ "text": "배달대행료1", "align": "center" }]},
	    { "id": "delivery_SUBSIDY_BEFOR", "header": [{ "text": "배달지원금(수정전)", "align": "center" }]},
	    { "id": "delivery_SUBSIDY_AFTER", "header": [{ "text": "배달지원금(수정후)", "align": "center" }]},
	    { "id": "platform_FEE", "header": [{ "text": "플랫폼수수료1", "align": "center" }]},
	    { "id": "platform_CNT", "header": [{ "text": "플랫폼매출건수", "align": "center" }] },
	    { "id": "delivery_CNT", "header": [{ "text": "배달실행건수", "align": "center" }] },
	    { "id": "kis_FEE_BEFOR", "header": [{ "text": "kis중계수수료(수정전)", "align": "center" }]},
	    { "id": "kis_FEE_AFTER", "header": [{ "text": "kis중계수수료(수정후)", "align": "center" }]},
	    { "id": "bigo", "header": [{ "text": "비고", "align": "center" }] },
	    { "id": "is_REFLECTION", "header": [{ "text": "반영여부", "align": "center" }] }
	    
	    
	],
    autoWidth: true,
    adjust: true,
    selection: false,
    resizable: true
});

	
	
	
	
	grid.events.on("cellDblClick", function () {
	
        eventHandler("cellDblClick", arguments);
    });
    
    	let thisDt = new Date();
	let year = thisDt.getFullYear();
	let month = ("0" + (thisDt.getMonth() + 1)).slice(-2);
	$('#month').val(`${year}년 ${month}월`);
	$('#month').monthpicker();
});

// 업로드 버튼 클릭
function upload_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
	$('#cancel_reason').val('');
	$('#cancel_reason').focus();
}

//엑셀 다운
function ExcelDown() {
	
	const month = $("#month").val().replaceAll(/[^0-9]/g, ''); //대사연월
	const stype = $('#stype option:selected').val();		   //MFC 구분
	const scd = $('#scd').val();	// 매장명
	const occd = $('#occd').val();	// 채널사명
	const ono = $('#ono').val();	// 주문번호

	const path = 'storeOrdNoDetailsExcel';
	const params = {
		month,
		stype,
		scd,
		occd,
		ono
	}

	post(path, params);
}

// 검색 값 초기화
function refresh() {
	$('#pnm').val('');
	$('#pcd').val('');
	$('#etyp').val('');
	$('#sinp').val('');
	$('#snm').val('');
	$('#ono').val('');
	$('#ocnm').val('');
	$('#scd').val('');
	$('#occd').val('');
}



//검색 쿼리
function search_go(num) {

	const month = $("#month").val().replaceAll(/[^0-9]/g, ''); //대사연월
	const stype = $('#stype option:selected').val();		   //MFC 구분
	const scd = $('#scd').val();	// 매장명
	const occd = $('#occd').val();	// 채널사명
	const ono = $('#ono').val();	// 주문번호


	
	const pageNum = num;


	if (isEmpty(num)) {
		$.blockUI({
			message: '<img src="./resources/img/loading_spiner.png"/>',
			css: {
				backgroundColor: 'rgba(0,0,0,0.0)',
				color: '#000000',
				border: '0px solid #a00'
			}
		});
	}


	$.ajax({
		url: 'getStoreOrdNoDetailsInfo',
		dataType: 'json',
		type: 'GET',
		data: {
			month,
			stype,
			scd,
			occd,
			ono,
			pageNum
		},
		success: function(data) {
			
			$('.ajaxTr').remove();
			$('.paging').children().remove();
			$.unblockUI();

			const a = '';
			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.storeOrdNoDetailsInfo).length;
			
			if (obLength === 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {
				
				grid.data.parse(data.storeOrdNoDetailsInfo);
				
				/*if (prev) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + 1 + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left_fin.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + (startPage - 1) + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left.png"/></a></li>');
				}
				for (let num = startPage; num <= endPage; num++) {
					if (num === page) {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn active">' + num + '</a></li>');
					} else {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn">' + num + '</a></li>');
					}
				}
				if (next) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + (endPage + 1) + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + totalPage + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right_fin.png"/></a></li>');
				}*/
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


//엑셀 파일 체크
function checkFileType(filePath) {
	var fileFormat = filePath.split(".");
	if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1) {
		return true;
	} else {
		return false;
	}

}


