<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

<head>
<title>배달 상세 검색</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub_search.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/codebase/grid.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub_search.css" />
<script>
let grid;

//document.ready()시 input 포커스
$(document).ready(function(){
	$('#keyword').focus();

    $('#type').change(function(){
        $('#keyword').focus();
    });

    grid = new dhx.Grid("grid", {
	    columns: [
	        { maxWidth: 60, id: "rn", header: [{ text: "배달대행사 리스트", colspan: 5, align: "center" }, { text: "순번", align: "center" }] },
	        { id: "cpn_nm", header: ["", { text: "배달대행사명", align: "center" }] },
	        { id: "cpn_state", header: ["", { text: "배달대행사상태", align: "center" }] },
	        { id: "cpn_tel", header: ["", { text: "콜센터번호", align: "center" }],
				template: function(text) {
					return phoneNumberFormat(text);
				}
	        },
	        { hidden: true, id: "cpn_cd", header: ["", { text: "배달대행사코드", align: "center" }] }
	    ],
	    autoWidth: true,
	    selection: false,
	    resizable: true
	});
	
	grid.events.on("cellDblClick", function () {
		dblClickHandler("cellDblClick", arguments);
    });
});

//조회 버튼 클릭 시 Search
function search_go(num) {
	if(isEmpty(num)){
		$.blockUI({ 
			message: '<img src="./resources/img/loading_spiner.png"/>',
			css: {
				backgroundColor: 'rgba(0,0,0,0.0)',
				color: '#000000', 
				border: '0px solid #a00'
				} 
			});
	}
	
	const type = $('#type').val();
	const keyword = $('#keyword').val();
	const pageNum = num;
	
	$.ajax ({
		url: 'getSubSearch03',
		dataType: 'json',
	    type: 'POST',
	    async : false,
	    data: {
	    		type,
	    		keyword,
	    		pageNum
	    		},
	    success: function (data) {
			$('.paging').children().remove();
			$.unblockUI();
			
			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;
	    	
			let obLength = Object.keys(data.tdcView).length;
			
			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {
					grid.data.parse(data.tdcView);
				});
				$(".btn_excel").attr("disabled", true);
			} else if (obLength > 0) {
				
				grid.data.parse(data.tdcView);
				
		    	if(prev){
	    			$('.paging').append('<li><a href="#" onclick="search_go(' + 1 +'); return false;" class="page-prev"><img src="./resources/img/list_arrow_left_fin.png"/></a></li>');
	    			$('.paging').append('<li><a href="#" onclick="search_go(' + (startPage - 1) +'); return false;" class="page-prev"><img src="./resources/img/list_arrow_left.png"/></a></li>');
	    		}
		    	for (let num = startPage; num <= endPage; num++) {
	                if (num == page) {
	                	$('.paging').append('<li><a href="#" onclick="search_go('+ num +'); return false;" class="page-btn active">' + num + '</a></li>');
	                } else {
	                	$('.paging').append('<li><a href="#" onclick="search_go('+ num +'); return false;" class="page-btn">' + num + '</a></li>');
	                }
	             }
		    	if(next){
		    		$('.paging').append('<li><a href="#" onclick="search_go('+ (endPage + 1) +'); return false;" class="page-next"><img src="./resources/img/list_arrow_right.png"/></a></li>');
		    		$('.paging').append('<li><a href="#" onclick="search_go('+ totalPage +'); return false;" class="page-next"><img src="./resources/img/list_arrow_right_fin.png"/></a></li>');
	    		}
			}
        }, 
        error: function(e) { 
        	swal("데이터를 가져오지 못했습니다."); 
        } 
	});
}

//더블클릭시 데이터 전송 후 종료
function dblClickHandler(event, arguments) {
	const dccd = arguments[0].cpn_cd;
	const dcnm = arguments[0].cpn_nm;

	opener.$("#dccd").val(dccd);
	opener.$("#dcnm").val(dcnm);

	window.close();
};
</script>
<body>
	<section class='div_header'>
		<div class='search_div' onkeyup='onEnterSearch()'>
			<select class='select_box' id="type">
				<option value='dcnm'>배달대행사명</option>
				<option value='dccd'>배달대행사코드</option>
			</select> <input class="search_input" id="keyword" type="text" autocomplete="off" />
		</div>
		<div class="btn_div">
			<button type="button" class='btn_search' onclick='search_go();'>조회</button>
		</div>
	</section>
	<div class='main_div'>
		<div id="grid"></div>
	</div>
	<div class="page_nav">
		<ul class="paging"></ul>
	</div>
</body>
</html>
