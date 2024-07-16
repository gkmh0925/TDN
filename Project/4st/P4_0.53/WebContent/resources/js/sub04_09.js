let grid;
// dhtmlx 기본 양식 (중계)

const relayGrid = {
	columns: [
	    {
	        minWidth: 100, id: "ord_dt_p", header: [{ text: "POS데이터", align: "center", colspan: 9 }, { text: "주문일자", align: "center" }, { content: "selectFilter" }],
	        template: function (text) {
	            return datetimeFormat(text);
	        },
	        mark: function (cell, data, row, col) {
	            return row.ord_dt_p != row.ord_time_r ? "error_data_mark" : ""
	        }
	    },
	    {
	        minWidth: 100, id: "chnel_cd_p", header: ["", { text: "채널사코드", align: "center" }, { content: "selectFilter"}],
	        mark: function (cell, data, row, col) {
	            return row.chnel_cd_p != row.franchise_code_r ? "error_data_mark" : ""
	        },
	        tooltipTemplate: (value, row, col) => row.chnel_nm_p
	    },
	    {
	        minWidth: 180, id: "em_ord_no_p", header: ["", { text: "이마트주문번호", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.em_ord_no_p != row.ord_id_r ? "error_data_mark" : ""
	        }
	    },
	    { minWidth: 180, id: "dtord_no_p", header: ["", { text: "상세주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
            	const dtordNoP = Number(row.dtord_no_p);//pos  DTORD_NO
            	const dtordNoR = Number(row.dtord_no_r);//중계    DTORD_NO
            	if(row.mfc_tp_cd_p == 0 && row.chnel_cd_p == 08 && row.em_ord_no_p == row.ord_id_r) {
            		return ""
            	} else{
            		return dtordNoP != dtordNoR ? "error_data_mark" : ""
            	}  
            }, type: "number" 
        },
	    {
	        minWidth: 100, id: "str_cd_p", header: ["", { text: "점포코드", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.str_cd_p != row.store_id_r ? "error_data_mark" : ""
	        }
	    },
	    {
	        minWidth: 120, id: "sale_store_p", header: ["", { text: "매출발생점포코드", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.sale_store_p != row.sale_store_r ? "error_data_mark" : ""
	        }
	    },
	    {
	        minWidth: 150, id: "ord_amt_p", header: ["", { text: "주문금액(배달비제외)", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.ord_amt_p != row.act_amt_r ? "error_data_mark" : ""
	        }, type: "number", format: "#,#"
	    },
	    { minWidth: 100, id: "ord_stat_p", header: ["", { text: "주문상태", align: "center" }, { content: "selectFilter" }], 
	    	tooltipTemplate: (value, row, col) => row.ord_stat_nm_p 
	    },
	    {
	        minWidth: 100, id: "state_result_p", header: ["", { text: "주문상태구분", align: "center" }, { content: "selectFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.state_result_p != row.state_result_r ? "error_data_mark" : ""
	        }
	    },
//	    {
//	    	minWidth: 100, id: "ex", header: ["", { text: "ex", align: "center" }, { content: "selectFilter" }],
//	    //	 editorType: "combobox", editorConfig: { newOptions: true }, options: ["1", "1-2", "more"]
//	    },
	    { maxWidth: 1, id: "", header: [{ text: "", align: "center", rowspan: 3 }, { text: "", align: "center" }],
	    	mark: function (cell, data, row, col) {
  	          return "compare_col"
	        }
	    },
	    {
	        minWidth: 100, id: "ord_time_r", header: [{ text: "중계데이터", align: "center", colspan: 9 }, { text: "주문일자", align: "center" }, { content: "selectFilter" }],
	        template: function (text) {
	            return datetimeFormat(text);
	        },
	        mark: function (cell, data, row, col) {
	            return row.ord_dt_p != row.ord_time_r ? "error_data_mark" : ""
	        }
	    },
	    {
	        minWidth: 100, id: "franchise_code_r", header: ["", { text: "채널사코드", align: "center" }, { content: "selectFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.chnel_cd_p != row.franchise_code_r ? "error_data_mark" : ""
	        },
	        tooltipTemplate: (value, row, col) => row.franchise_name_r
	    },
	    {
	        minWidth: 180, id: "ord_id_r", header: ["", { text: "이마트주문번호", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.em_ord_no_p != row.ord_id_r ? "error_data_mark" : ""
	        }
	    },
	    { minWidth: 180, id: "dtord_no_r", header: ["", { text: "상세주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
            	const dtordNoP = Number(row.dtord_no_p);//pos DTORD_NO
            	const dtordNoR = Number(row.dtord_no_r);//중계    DTORD_NO
            	if(row.mfc_tp_cd_r == 0 && row.franchise_code_r == 08 && row.em_ord_no_p == row.ord_id_r) {
            		return ""
            	} else{
            		return dtordNoP != dtordNoR ? "error_data_mark" : ""
            	}  
            }, type: "number" 
        },
	    {
	        minWidth: 100, id: "store_id_r", header: ["", { text: "점포코드", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.str_cd_p != row.store_id_r ? "error_data_mark" : ""
	        }
	    },
	    {
	        minWidth: 120, id: "sale_store_r", header: ["", { text: "매출발생점포코드", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.sale_store_p != row.sale_store_r ? "error_data_mark" : ""
	        }
	    },
	    {
	        minWidth: 150, id: "act_amt_r", header: ["", { text: "주문금액(배달비제외)", align: "center" }, { content: "inputFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.ord_amt_p != row.act_amt_r ? "error_data_mark" : ""
	        }, type: "number", format: "#,#"
	    },
	    { minWidth: 100, id: "ord_state_r", header: ["", { text: "주문상태", align: "center" }, { content: "selectFilter" }] },
	    {
	        minWidth: 100, id: "state_result_r", header: ["", { text: "주문상태구분", align: "center" }, { content: "selectFilter" }],
	        mark: function (cell, data, row, col) {
	            return row.state_result_p != row.state_result_r ? "error_data_mark" : ""
	        }
	    }
	],
	css: "alternate_row",
	headerRowHeight: 30,
	rowHeight: 30,
	autoWidth: true,
	selection: true,
	resizable: true,
	sortable: true,
	editable: true
	
};
////수정모드 true/false
//let editStat = false;
////수정된 form 저장
//let mdfForm = new Array();
//function edit(){
//	grid.edit(grid.data.getId(1),grid.config.columns[9].id);
//	id:"ex";
// 	editorType: "combobox"; 
//	editorConfig: { newOptions: true }; 
// 	options: ["1", "1-2", "more"] ; 
//	
//}
// dhtmlx 기본 양식 (주문채널)
const orderGrid = {
    columns: [
        {
            minWidth: 100, id: "ord_time_r", header: [{ text: "중계데이터", align: "center", colspan: 10 }, { text: "주문일자", align: "center" }, { content: "selectFilter" }],
            template: function (text) {
                return datetimeFormat(text);
            },
            mark: function (cell, data, row, col) { 
				return row.ord_time_r != row.ord_dt_c ? "error_data_mark" : "" 
			}
        },
        {
            minWidth: 100, id: "franchise_code_r", header: ["", { text: "채널사코드", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.chnel_cd_c != row.franchise_code_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 180, id: "ord_id_r", header: ["", { text: "이마트주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.em_ord_no_c != row.ord_id_r ? "error_data_mark" : ""
            }
        },
        { minWidth: 180, id: "dtord_no_r", header: ["", { text: "상세주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
            	const dtordNoC = Number(row.dtord_no_c);//채널사 DTORD_NO
            	const dtordNoR = Number(row.dtord_no_r);//중계    DTORD_NO
            	if(row.mfc_tp_cd_c == 0 && row.franchise_code_r == 08 && row.em_ord_no_c == row.ord_id_r) {
            		return ""
            	} else{
            		return dtordNoC != dtordNoR ? "error_data_mark" : ""
            	}  
            }, type: "number" 
        },
        {
            minWidth: 100, id: "store_id_r", header: ["", { text: "점포코드", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.str_cd_c != row.store_id_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 120, id: "sale_store_r", header: ["", { text: "매출발생점포코드", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.sale_store_c != row.sale_store_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 150, id: "act_amt_r", header: ["", { text: "주문금액(배달비제외)", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.ord_amt_c != row.act_amt_r ? "error_data_mark" : ""
            }, type: "number", format: "#,#"
        },
        { minWidth: 100, id: "ord_state_r", header: ["", { text: "주문상태값", align: "center" }, { content: "selectFilter" }],
        	tooltipTemplate: (value, row, col) => row.ord_state_nm_r
        },
        {
            minWidth: 100, id: "state_result_r", header: ["", { text: "주문상태구분", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.state_result_c != row.state_result_r ? "error_data_mark" : ""
            }
        },
        { maxWidth: 1, id: "", header: [{ text: "", align: "center", rowspan: 3 }, { text: "", align: "center" }],
	    	mark: function (cell, data, row, col) {
  	          return "compare_col"
	        }
	    },
        {
            minWidth: 100, id: "ord_dt_c", header: [{ text: "채널사데이터", align: "center", colspan: 9 }, { text: "주문일자", align: "center" }, { content: "selectFilter" }],
            template: function (text) {
                return datetimeFormat(text);
            },
            mark: function (cell, data, row, col) { 
				return row.ord_time_r != row.ord_dt_c ? "error_data_mark" : "" 
			}
        },
        {
            minWidth: 100, id: "chnel_cd_c", header: ["", { text: "채널사코드", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.chnel_cd_c != row.franchise_code_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 180, id: "em_ord_no_c", header: ["", { text: "이마트주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.em_ord_no_c != row.ord_id_r ? "error_data_mark" : ""
            }
        },
        { minWidth: 180, id: "dtord_no_c", header: ["", { text: "상세주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
            	const dtordNoC = Number(row.dtord_no_c);
            	const dtordNoR = Number(row.dtord_no_r);	
            	if(row.mfc_tp_cd_c !=0){
        			return dtordNoC != dtordNoR ? "error_data_mark" : ""
            	} else if(row.mfc_tp_cd_c == 0 && row.franchise_code_r == 08 && row.em_ord_no_c == row.ord_id_r) {
            		return ""
            	} else{
            		return dtordNoC != dtordNoR ? "error_data_mark" : ""
            	}      	
        }, type: "number" 
        },
        {
            minWidth: 100, id: "str_cd_c", header: ["", { text: "점포코드", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.str_cd_c != row.store_id_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 120, id: "sale_store_c", header: ["", { text: "매출발생점포코드", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.sale_store_c != row.sale_store_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 150, id: "ord_amt_c", header: ["", { text: "주문금액(배달비제외)", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.ord_amt_c != row.act_amt_r ? "error_data_mark" : ""
            }, type: "number", format: "#,#"
        },
        { minWidth: 100, id: "ord_state_c", header: ["", { text: "주문상태값", align: "center" }, { content: "selectFilter" }],
        	tooltipTemplate: (value, row, col) => row.ord_state_nm_c
        },
        {
            minWidth: 100, id: "state_result_c", header: ["", { text: "주문상태구분", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.state_result_c != row.state_result_r ? "error_data_mark" : ""
            }
        }
	],
    spans: [
        { row: "1", column: "population", rowspan: "9", text: "1" }
    ],
    css: "alternate_row",
    headerRowHeight: 30,
    rowHeight: 30,
    autoWidth: true,
    selection: true,
    resizable: true,
    sortable: true
};
// dhtmlx 기본 양식 (배달)
const deliveryGrid = {
    columns: [
        {
            minWidth: 100, id: "ord_time_r", header: [{ text: "중계데이터", align: "center", colspan: 5 }, { text: "주문일자", align: "center" }, { content: "selectFilter" }],
            template: function (text) {
                return datetimeFormat(text);
            },
            mark: function (cell, data, row, col) { 
				return row.ord_time_r != row.ord_dt_d ? "error_data_mark" : "" 
			}
        },
        {
            minWidth: 100, id: "franchise_code_r", header: ["", { text: "채널사코드", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.chnel_cd_c != row.franchise_code_r ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 180, id: "ord_id_r", header: ["", { text: "이마트주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.ord_id_r != row.em_ord_no_d ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 150, id: "act_amt_r", header: ["", { text: "주문금액(배달비제외)", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.act_amt_r != row.act_amt_d ? "error_data_mark" : ""
            }, type: "number", format: "#,#"
        },
        {
            minWidth: 150, id: "tot_amt_r", header: ["", { text: "주문총금액(배달비포함)", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.tot_amt_r != row.tot_amt_d ? "error_data_mark" : ""
            }, type: "number", format: "#,#"
        },
        {
            minWidth: 100, id: "state_result_r", header: ["", { text: "주문상태구분", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.state_result_r != row.state_result_d ? "error_data_mark" : ""
            }
        },
        { maxWidth: 1, id: "", header: [{ text: "", align: "center", rowspan: 3 }, { text: "", align: "center" }],
	    	mark: function (cell, data, row, col) {
  	          return "compare_col"
	        }
	    },
        {
            minWidth: 100, id: "ord_dt_d", header: [{ text: "배달대행사 데이터", align: "center", colspan: 6 }, { text: "접수일자", align: "center" }, { content: "selectFilter" }],
            template: function (text) {
                return datetimeFormat(text);
            },
            mark: function (cell, data, row, col) { 
				return row.ord_time_r != row.ord_dt_d ? "error_data_mark" : "" 
			}
        },
        {
	        minWidth: 100, id: "chnel_cd_d", header: ["", { text: "배달사코드", align: "center" }, { content: "selectFilter" }],
	        tooltipTemplate: (value, row, col) => row.chnel_nm_d
	    },
        {
            minWidth: 180, id: "em_ord_no_d", header: ["", { text: "이마트주문번호", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.ord_id_r != row.em_ord_no_d ? "error_data_mark" : ""
            }
        },
        {
            minWidth: 150, id: "act_amt_d", header: ["", { text: "배달료(기본)", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.act_amt_r != row.act_amt_d ? "error_data_mark" : ""
            }, type: "number", format: "#,#"
        },
        {
            minWidth: 150, id: "tot_amt_d", header: ["", { text: "배달료(할증포함)", align: "center" }, { content: "inputFilter" }],
            mark: function (cell, data, row, col) {
                return row.tot_amt_r != row.tot_amt_d ? "error_data_mark" : ""
            }, type: "number", format: "#,#"
        },
        {
            minWidth: 100, id: "state_result_d", header: ["", { text: "주문상태구분", align: "center" }, { content: "selectFilter" }],
            mark: function (cell, data, row, col) {
                return row.state_result_r != row.state_result_d ? "error_data_mark" : ""
            }
        },
       
	],
    spans: [
        { row: "1", column: "population", rowspan: 9, text: "1" }
    ],
    css: "alternate_row",
    headerRowHeight: 30,
    rowHeight: 30,
    autoWidth: true,
    selection: true,
    resizable: true,
    sortable: true
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
	
	grid = new dhx.Grid("grid", relayGrid);

	$('#comp_data').on("change", function() {
		$('.paging').empty();
		if ($('#comp_data option:selected').val() == "relay") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", relayGrid);

		} else if ($('#comp_data option:selected').val() == "order") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", orderGrid);

		} else if ($('#comp_data option:selected').val() == "delivery") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", deliveryGrid);

		}

	});

	/*grid.events.on("cellDblClick", function() {
		dblClickHandler("cellDblClick", arguments);
	});*/
		
	/* MonthPicker Set */
	let thisDt = new Date();
	let year = thisDt.getFullYear();
	let month = ("0" + (thisDt.getMonth() + 1)).slice(-2);
	$('#month').val(`${year}년 ${month}월`);
	$('#month').monthpicker();
});



//검색 쿼리
function search_go(num) {
	const month = $("#month").val().replaceAll(/[^0-9]/g, '');
	const comp_data = $('#comp_data option:selected').val();
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
		url: 'getCompareData',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			comp_data,
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



// 수정 버튼 클릭
//function edit() {
//	const td = $('.tdInput, .tdTextAr').parent();
//	$('#edit_button').css('display', 'none');
//	$('#cancel_button').css('display', 'inline');
//	$('#save_button').css('display', 'inline');
//	$(grid).addClass('row');
//	$('#warn_p').css('display','inline-block');
//	//수정모드 true
//	editStat = true;
//
//	swal("수정 모드가 켜졌습니다.");
//}

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