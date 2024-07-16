let grid;

$(document).ready(function() {
	//오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
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
	//dhtmlx 기본 양식
	grid = new dhx.Grid("grid", {
	    columns: [
         	{ maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
			{ id: "cpn_cd", header: [{ text: "매장코드", align: "center" }] },
			{ id: "cpn_nm", header: [{ text: "매장명", align: "center" }] },
			{ id: "cpn_biz_type", header: [{ text: "사업자유형", align: "center" }] },
			{ id: "mfc_tp_cd", header: [{ text: "MFC구분", align: "center" }] },
			{ id: "cpn_state", header: [{ text: "업체상태", align: "center" }] },
			{ id: "cpn_addr", header: [{ text: "주소", align: "center" }] },
			{ id: "cpn_addr_dt", header: [{ text: "상세주소", align: "center" }] },
			{ id: "cpn_tel", header: [{ text: "전화번호", align: "center" }] },
			{ id: "mng_hpno", header: [{ text: "휴대폰번호(FC)", align: "center" }] },
			{ id: "day_start_time", header: [{ text: "영업시작시간", align: "center" }] },
			{ id: "day_end_time", header: [{ text: "영업종료시간", align: "center" }] },
			{ id: "ord_ch_nm", header: [{ text: "채널사명", align: "center" }] },
			{ id: "dlv_ch_nm", header: [{ text: "배달대행사명", align: "center" }] },
			{ id: "in_app", header: [{ text: "INAPP 저장여부", align: "center" }] },
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
	
	grid.events.on("cellDblClick", function () {
        eventHandler("cellDblClick", arguments);
    });
    
});

// 업로드 버튼 클릭
function upload_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
}

function sampleDownload() {
	const path = 'SampleDown';
	const params = {
		stat: 'Store'
	}
	post(path, params);
}

//엑셀 다운
function ExcelDown() {
	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const dccd = $('#dccd').val();
	const sst = $('#sst').val();

	const path = 'storeList_excel';
	const params = {
		scd,
		occd,
		dccd,
		sst
	}

	post(path, params);
}

//검색 쿼리
function search_go(num) {
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

	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const dccd = $('#dccd').val();
	const sst = $('#sst').val();
	const pageNum = num;

	$.ajax({
		url: 'getStoreList',
		dataType: 'json',
		type: 'POST',
		data: {
			scd,
			occd,
			dccd,
			sst,
			pageNum
		},
		success: function(data) {

			$('.ajaxTr').remove();
			$('.paging').children().remove();
			$.unblockUI();

			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.storeList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {
				
					grid.data.parse(data.storeList);
					
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

// 엑셀 업로드
function excelupload() {
	var file = $("#excelFile").val();
	var form = $('#excelUploadForm')[0];
	var size = document.getElementById("excelFile").files[0].size;
	var upTime = Math.round(size/60000);

	// FormData 객체 생성
	var formData = new FormData(form);

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
		text: "매장을 추가하거나 업데이트합니다.",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {
			$.blockUI({
				message: '<img src="./resources/img/loading_spiner.png"/><br><br><p id="uiMsg">예상 최대 대기시간은 '+upTime+'분 입니다.',
				css: {
					backgroundColor: 'rgba(0,0,0,0.0)',
					color: '#000000',
					border: '0px solid #a00'
				}
			});
			$.ajax({
				url: 'storeUpload',
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					$.unblockUI();
					swal({
						text: "\"" + data + "\" 파일이 정상 업로드 되었습니다.",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('파일선택');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						search_go();
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
			});
		}
	});
	$("#upload_name").val('');
	$("#excelFile").val('');
}

//상세정보 페이지 이동
function eventHandler(event, arguments) {
	const stocd = arguments[0].cpn_cd;

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

	document.body.removeChild(form);

};