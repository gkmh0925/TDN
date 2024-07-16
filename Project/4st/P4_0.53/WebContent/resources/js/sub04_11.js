let grid;
// dhtmlx 기본 양식 (중계)
	
   
	
const relayGrid = {
	
	columns: [
	   
	    {
	        minWidth: 100, id: "ord_time_r", header: [{ text: "업로드한 데이터", align: "center", colspan: 6 },
	         { text: "주문일자", align: "center" }, 
	         { content: "selectFilter" }],	        
	        template: function (text) {
	            return datetimeFormat(text);
	        },
	    },
	 
	    {
	        minWidth: 180, id: "ord_id_r", header: ["", { text: "주문번호", align: "center" }, 
	        { content: "inputFilter" }],
	           mark: function (cell, data, row, col) {
	            return row.ord_id_r != row.ord_id ? "" : "error_data_mark"
	        },
	    },
	    {
	        minWidth: 120, id: "cpn_nm_r", header: ["", { text: "가맹점명", align: "center" },
	        { content: "selectFilter" }],  
	    },
	    {
	        minWidth: 100, id: "settle_month", header: ["", { text: "정산년월", align: "center" },
	         { content: "selectFilter" }],
	        
	    },
	   {
	        minWidth: 100, id: "delivery_date", header: ["", { text: "배송일자", align: "center" }, 
	      { content: "selectFilter" }],
	      template: function(text) {
				return datetimeFormat(text);
			}
	    },
	    {
	        minWidth: 150, id: "kis_fee", header: ["", { text: "KIS_FEE" , align: "center" }, 
	        { content: "inputFilter" }],
	     	mark:function(cell, data, row, col){
				return row.kis_fee != row.kis_fee ? "error_data_mark" : "error_data_mark"
			 },
	       type: "number", format: "#,#" 
	      
	        
	    },
	    { maxWidth: 1, id: "", header: [{ text: "", align: "center", rowspan: 3 }, { text: "", align: "center" }],
	    	mark: function (cell, data, row, col) {
  	          return "compare_col"
	        }
	    },
	    {
	        minWidth: 100, id: "ord_time", header: [{ text: "중계데이터", align: "center", colspan: 6 },
	         { text: "주문일자", align: "center" }, { content: "selectFilter" }],
	        template: function (text) {
	            return datetimeFormat(text);
	        },
	    },
	    {
	        minWidth: 100, id: "ord_id", header: ["", { text: "주문번호", align: "center" },
	         { content: "inputFilter" }],
	           // mark: function (cell, data, row, col) {
	            //return row.ord_id_r != row.ord_id ? "" : "error_data_mark"
	        //},
	    },
	     { minWidth: 180, id: "cpn_nm", header: ["", { text: "가맹점명", align: "center" },
	      { content: "selectFilter" }],
        },
	    {
	        minWidth: 100, id: "ord_state", header: ["", { text: "주문상태", align: "center" },
	         { content: "selectFilter" }],	         
	    },
	   
	    {
	        minWidth: 120, id: "franchise_name", header: ["", { text: "채널사명", align: "center" }, 
	        { content: "selectFilter" }],	         
	    },
	    {
	        minWidth: 120, id: "state_result", header: ["", { text: "주문상태", align: "center" },
	         { content: "selectFilter" }],
	         	mark:function(cell, data, row, col){
				return row.state_result != row.state_result ? "error_data_mark" : "error_data_mark"
			 },
	         
	    },
	 
	],
	css: "alternate_row",
	rowCss: function(row) {  return row.ord_id_r != row.ord_id ? "" : "err_row"  },
	headerRowHeight: 30,
	rowHeight: 30,
	autoWidth: true,
	selection: true,
	resizable: true,
	sortable: true,
	editable: true
	
};


$(document).ready(function() {

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
	
	/*
	 grid = new dhx.Grid("grid", {
		columns: [
			{minWidth: 60,maxWidth:90, id: "ord_time", header: [{ text: "순번", align: "center"}], align: "center" },
			{minWidth: 150, id: "delivery_date", header: [{ text: "배송일자", align: "center" }], align:"center" ,
			template: function(text) {
				return datetimeFormat(text);
			} },
			{ minWidth: 100,id: "cpn_cd", header: [{ text: "회사코드", align: "center" }], align:"center" },
			{ minWidth: 200, id: "cpn_nm", header: [{ text: "회사명", align: "center" }] , align:"center" },
			{ minWidth: 200,id: "ord_id", header: [{ text: "주문번호", align: "center" }], align:"center"  },
			{ minWidth: 100, id: "kis_fee", header: [{ text: "수수료", align: "center" }], type: "number", format: "#,#" }
			
		
			 
		],
		//css: "alternate_row",
		//rowCss: function(row) { return row.cpn_nm == "합계" ? "tot_row" : "" },
		headerRowHeight: 50,
		rowHeight: 30,
		autoWidth: true,
		selection: false,
		resizable: true,
		sortable: false
	});
	*/
	
	if (grid) {
			grid.destructor();
		}
		grid = new dhx.Grid("grid", relayGrid);
	 
	 
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
/*function ExcelDown() {
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');

	let path = 'ExcelDownload_Check';

	const params = {
		month
	}
	post(path, params);
}*/

//검색 쿼리
function search_go(num) {
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');
	//const comp_data = $('#comp_data option:selected').val();
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
		url: 'getRelayCompareData',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			 
			month,
			stype
		},
		success: function(data) {
			debugger;
			$.unblockUI();

			let obLength = Object.keys(data.calcList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {debugger;
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


//취소 버튼 클릭시
function cancel() {
	const td = $('.tdInput, .tdTextAr').parent();
	$('#cancel_button').css('display', 'none');
	$('#save_button').css('display', 'none');
	$('#edit_button').css('display', 'inline');
	$(".editCompleted").removeClass("editCompleted");
	$(grid).removeClass('row');
	$('#warn_p').css('display','none');
	//Form 리셋

	location.reload();
	mdfForm.length = 0;
	editStat = false;

}

//상세정보 페이지 이동
function dblClickHandler(event, arguments) {

	swal("준비중입니다.");
};