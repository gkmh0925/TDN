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
        { maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
        { minWidth: 140, id: "dv_seq_cd", header: [{ text: "배달료 마스터 코드", align: "center" }] },
        { minWidth: 140, id: "start_month", header: [{ text: "적용시작월", align: "center" }] },
        { minWidth: 140, id: "end_month", header: [{ text: "적용종료월", align: "center" }] },
        { minWidth: 140, id: "master_nm", header: [{ text: "마스터명", align: "center" }] },
        { minWidth: 140, id: "master_type", header: [{ text: "마스터타입", align: "center" }] },
		{ minWidth: 140, id: "amt", header: [{ text: "금액", align: "center" }] },

        { minWidth: 140, id: "reg_user_cd", header: [{ text: "등록사용자", align: "center" }] },
        { minWidth: 140, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
            template: function (text, row, col) {
                return unixTimeFormat(text); 
            }
        },
        { minWidth: 140, id: "mdf_user_cd", header: [{ text: "수정사용자", align: "center" }] },
        { minWidth: 140, id: "mdf_date", header: [{ text: "수정일시", align: "center" }],
            template: function (text, row, col) {
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
	$('#cancel_reason').val('');
	$('#cancel_reason').focus();
}


// 검색 값 초기화
function refresh() {
	$('#pnm').val('');
	$('#pcd').val('');
	$('#etyp').val('');
	$('#sinp').val('');
	$('#snm').val('');
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
	

	let param = data= {
			sdate,edate
		}
	console.log(JSON.stringify(param))
	
	

	$.ajax({
		url: 'deliveryFeeInfo',
		dataType: 'json',
		type: 'GET',
		async: false,
		
		//data: {
		//	stype,
		//	scd,
		//	ono,
		//	chono,
		//	emono,
		//	occd,
		//	ordst,
		//	sdate,
		//	edate,
		//	pageNum
		//},
		
		data : {
			sdate,
		    edate,
		},
		success: function(data) {
			
			console.log("data : " + data.deliveryFeeListInfo)
			
			$('.paging').children().remove();
			$.unblockUI();
			
			/*const page = data.pageMaker.cri.pageNum;
			let startPage = data.pageMaker.startPage;
			let endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;*/

			let obLength = Object.keys(data.deliveryFeeListInfo).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {
					grid.data.parse(data.deliveryFeeListInfo);
				});
				$(".btn_excel").attr("disabled", true);
			} else if (obLength > 0) {

				grid.data.parse(data.deliveryFeeListInfo);
				
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
			console.log(e)
			$(".btn_excel").attr("disabled", true);
			$.unblockUI();
		}
	});
}

//상세정보 페이지 이동
function eventHandler(event, arguments) {


	const ctgrcd = arguments[0].ctgr_cd;

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "categoryLv2List_detail";
	form.style.display = "none";

	let Inputscd = document.createElement("input");
	Inputscd.type = "text";
	Inputscd.name = "ctgrcd";
	Inputscd.value = ctgrcd;
	

	form.appendChild(Inputscd);

	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);

};

//엑셀 파일 체크
/*function checkFileType(filePath) {
	var fileFormat = filePath.split(".");
	if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1) {
		return true;
	} else {
		return false;
	}

}

// 샘플 엑셀 다운로드
function sampleDownload() {
	const path = 'SampleDown';
	const params = {
		stat: 'Menu'
	}
	post(path, params);
}*/

/*// 엑셀 업로드
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
		text: "기존 메뉴를 지우고 새로 갱신합니다.",
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
				url: 'productUpload',
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					const scd = $('#scd').val();
					$.unblockUI();
					swal({
						text: "\"" + data + "\" 파일이 정상 업로드 되었습니다.",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						location.reload();
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
}*/

/*// 엑셀 부분 업로드
function excelpartupload() {
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
		text: "메뉴를 추가합니다.",
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
				url: 'productPartUpload',
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					const scd = $('#scd').val();
					$.unblockUI();
					swal({
						text: "\"" + data + "\" 파일이 정상 업로드 되었습니다.",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						if (isNotEmpty(scd)) {
							search_go();
						}else{
							location.reload();
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
			});
		}
	});
	$("#upload_name").val('');
	$("#excelFile").val('');
}*/



