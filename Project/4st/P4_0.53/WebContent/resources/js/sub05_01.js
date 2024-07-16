let grid;

//오늘, 일주일, 1개월 css
$(document).ready(function() {
	grid = new dhx.Grid("grid", {
	    columns: [
	        { maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
	        { minWidth: 140, id: "account", header: [{ text: "계정", align: "center" }] },
	        { minWidth: 140, id: "user_nm", header: [{ text: "사용자명", align: "center" }] },
	        { minWidth: 140, id: "user_lv", header: [{ text: "사용자레벨", align: "center" }] },
	        { minWidth: 160, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
	        	template: function(text) {
	        		return unixTimeFormat(text);
	        	}
	        },
	        { minWidth: 160, id: "mdf_date", header: [{ text: "수정일시", align: "center" }],
	        	template: function(text) {
	        		return unixTimeFormat(text);
	        	} 
	        },
	        { hidden: true, id: "user_cd", header: [{ text: "유저코드", align: "center" }] }
	    ],
	    adjust: true,
	    selection: false,
	    resizable: true
	});
	
	grid.events.on("cellDblClick", function () {
		dblClickHandler("cellDblClick", arguments);
    });
});

//검색 쿼리
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
	
	const userNm = $("#userNm").val();
	const pageNum = num;
	
	$.ajax ({
		url: 'getUserList',
		dataType: 'json',
	    type: 'POST',
	    data: {
	    		userNm,
	    		pageNum
	    		},
	    success: function (data) {
	    	
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
			
			let obLength = Object.keys(data.tuaccount).length;
			
			if(obLength == 0){
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				}).then(function() {
					grid.data.parse(data.tuaccount);
				});
			}else if(obLength > 0){
			
		    	grid.data.parse(data.tuaccount);
		    	
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
        	swal({
				text: "데이터를 가져올 수 없습니다.",
				icon: "error"
			});
        	
        	$.unblockUI();
        } 
	});
}

// 로우 더블 클릭 시
function dblClickHandler(event, arguments) {
	const user_cd = arguments[0].user_cd;

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "userList_detail";
	form.style.display = "none";

	let Inputcd = document.createElement("input");
	Inputcd.type = "text";
	Inputcd.name = "user_cd";
	Inputcd.value = user_cd;

	form.appendChild(Inputcd);

	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);
};