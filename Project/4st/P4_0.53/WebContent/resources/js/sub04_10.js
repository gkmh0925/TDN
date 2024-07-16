let grid;
 
//dhtmlx 기본 양식 (중계수수료 정산)
 
/*
const RelayGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		 
		{
			minWidth: 100, id: "ORD_DATE", header: [{ text: "주문일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "DELIVERY_DATE", header: [{ text: "배송일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 60, id: "CPN_CD", header: [{ text: "매장코드", align: "center" }],},
		{ minWidth: 100, id: "CPN_NM", header: [{ text: "매장명", align: "center" }] },
		{ minWidth: 100, id: "ORD_ID", header: [{ text: "주문ID", align: "center" }] },
		{ minWidth: 150, id: "KIS_FEE", header: [{ text: "KISS_FEE", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "APPLY_GUBUN", header: [{ text: "비고", align: "center" }] },
		
		{ minWidth: 100, id: "USER_NM", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "REG_DATE", header: [{ text: "등록일시", align: "center" }],
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

*/


$(document).ready(function() {
//	오버레이 클릭
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
		 grid = new dhx.Grid("grid", {
		columns: [
			 
			{minWidth: 60,maxWidth:90, id: "rn", header: [{ text: "순번", align: "center"}], align: "center" },
			{minWidth: 150, id: "ord_date", header: [{ text: "주문일자",  align: "center" }] ,align:"center" ,
			template: function(text) {
				return datetimeFormat(text);
			}},
			{minWidth: 150, id: "delivery_date", header: [{ text: "배송일자", align: "center" }], align:"center" ,
			template: function(text) {
				return datetimeFormat(text);
			} },
			{ minWidth: 100,id: "cpn_cd", header: [{ text: "회사코드", align: "center" }], align:"center"  },
			{ minWidth: 200, id: "cpn_nm", header: [{ text: "회사명", align: "center" }] , align:"center" },
			{ minWidth: 200,id: "ord_id", header: [{ text: "주문번호", align: "center" }], align:"center"  ,
			footer: [{ text: '합계', align: 'center'}] ,
			  //mark: function (cell, data, row, col) { return cell =='d' ? "my_custom_mark" : "" }
			},
			{ minWidth: 100, id: "kis_fee", header: [{ text: "수수료", align: "center" }], type: "number", format: "#,#",
			 footer: [{ content: 'sum', align: 'right',  color:'red'}]
			 
			 },
			{ minWidth: 100, id: "apply_gubun", header: [{ text: "비교", align: "center" }] , align:"center"},
			{ minWidth: 100, id: "user_nm", header: [{ text: "등록자이름",  align: "center" }] , align:"center" },
			{ minWidth: 150, id: "reg_date", header: [{ text: "등록일자", align: "center" }], 
			template: function(text) {
				return unixTimeFormat(text);
			}
		}],
		
		css: "alternate_row",
		//rowCss: function(row) { return row.ord_id == "합계" ? "tot_row" : "" },
		headerRowHeight: 50,
		rowHeight: 30,
		autoWidth: true,
		selection: false,
		resizable: true,
		sortable: false
    	 
	});
	
	grid.events.on("cellDblClick", function() {
		dblClickHandler("cellDblClick", arguments);
	});
	
	 
	/*
	
	if (grid) {
			grid.destructor();
		}
	grid = new dhx.Grid("grid", RelayGrid);
	
	*/
	
	/* MonthPicker Set 조회시사용*/
	let thisDt = new Date();
	let year = thisDt.getFullYear();
	let month = ("0" + (thisDt.getMonth() + 1)).slice(-2);
	$('#tmonth').val(`${year}년 ${month}월`);
	$('#tmonth').monthpicker();
	

});

// 업로드 버튼 클릭
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
/*  const sdate = $("#sdate").val().replaceAll('-', '');
	const edate = $("#edate").val().replaceAll('-', '');
	const scd = $('#scd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();*/
	const tmonth = $("#tmonth").val().replaceAll(/[^0-9]/g, '');
	//const frccd = $('#ch_view option:selected').val();

	let path = 'ExcelDownload_ORD_' + 10;

	const params = {
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


//샘플 엑셀 다운
function ExcelDown_settle_Relay() {
	
	let path = 'Excel_Sample_Download_Relay' ; //sfrccd ;	
	let channel ="01";
	const params = {
	channel	
	}
	post(path, params);
	
	$('.overlay').fadeOut();
	$('.shide_div').removeClass('show_div');
}

//검색 쿼리
function search_go(num) {
	const tmonth = $("#tmonth").val().replaceAll(/[^0-9]/g, '');	

	$.blockUI({
		message: '<img src="./resources/img/loading_spiner.png"/>',
		css: {
			backgroundColor: 'rgba(0,0,0,0.0)',
			color: '#000000',
			border: '0px solid #a00'
		}
	});

	const pageNum = num;

	$.ajax({
		url: 'oms_sub04_10_process',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			tmonth,
			pageNum
		},
		success: function(data) {
			
			$('.paging').children().remove();
			$.unblockUI();

			const a = '';
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
	if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1) {
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
			text: "엑셀 파일만 업로드 가능합니다.",
			icon: "warning"
		});
		$("#upload_name").val('');
		$("#excelFile").val('');

		return false;
	}

	let form = $('#excelUploadForm')[0];	
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');
	let size = document.getElementById("excelFile").files[0].size;
//	let upTime = Math.round(size / 150000);
	let upTime = Math.round(size / 1200000);
	
	
	
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
		text: `'${$('.filebox .upload-hidden')[0].files[0].name}' 정산 엑셀을 업로드합니다.`,
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
				url: 'ExcelUpload_Cal_' + 10,
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData ,
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
						//$('#ch_view').val(frccd).trigger('change');
						//search_go();
						debugger
						if (data.excel) {
							debugger
							const url = "ExcelDownload_ORD_ERR" + 10;
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
 
