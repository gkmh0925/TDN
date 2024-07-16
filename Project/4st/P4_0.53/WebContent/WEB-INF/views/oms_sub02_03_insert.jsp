<!-- !@@0701 new -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>배달서비스 상품 등록</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="./resources/codebase/grid.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/sub02_03_insert.css">
<script src="./resources/js/common.js"></script>
<script src="./resources/js/sub02_03_insert.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">
				상품관리<b class="greater">&gt;</b>배달 서비스 상품 리스트 관리<b class="greater">&gt;</b>배달서비스 상품 등록
			</div>
			<img src="./resources/img/Calculate Management Program.png" id="top_logo" />
		</header>
		<div class='div_header_right'>
			<div class="btn_div">
				<button type="button" class='btn_insert' id="btn_insert" onclick='registerDeliveryProduct();'>저장</button>
				<button class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</div>

		<div class='main_div container'>
			<table id="f_table">
				<tr>
					<th colspan='2' class='mtitle'>배달서비스 상품 등록</th>
				</tr>

				<tr>
					<th class='stitle'>채널사명<p id="emp">*</p></th>
					<td>
						<div class="search_form">
							<input class='search_box' type="search" id='ch_nm' name='ch_nm' readonly /><img src="./resources/img/icon_search.png" id='ocnm_img_02' /> <input type='text' name='ch_cd' id='ch_cd' hidden />
						</div> <input type="hidden" value="" id="ch_cd" readonly />
					</td>
				</tr>
				<tr>
					<th class='stitle'>상품코드<p id="emp">*</p></th>
					<td><input type="text" name="prd_cd" id="prd_cd" autocomplete="off" placeholder="숫자" /></td>
				</tr>
				<tr>
					<th class='stitle'>상품명<p id="emp">*</p></th>
					<td><input type="text" name="prd_nm" id="prd_nm" autocomplete="off" placeholder="상품명" /></td>
				</tr>
				<tr>
					<th class='stitle'>매장카테고리명<p id="emp">*</p></th>
					<td><select class="select_box" title="채널사코드" id="sto_ctgr" name="sto_ctgr">
							<option value="">-선택-</option>
					</select></td>
				</tr>

				<tr>
					<th class='stitle'>판매단가<p id="emp">*</p></th>
					<td><input type="text" name="std_amt" id="std_amt" autocomplete="off" placeholder="숫자" /></td>
				</tr>			
				<tr>
<!-- 					레퍼런스 코드 필요 -->
					<th class='stitle'>과세구분<p id="emp">*</p></th>
					<td><select class="select_box" title="과세구분" name="tax_type" id="tax_type">
							<option value="">-선택-</option>
							<c:forEach items="${taxTypeList}" var="taxType">
								<option value="${taxType.DEFINITION_NM}">${taxType.DEFINITION_NM}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				
				<tr>
					<th class='stitle'>상품상태<p id="emp">*</p></th>
					<td><select class="select_box" title="과세구분" name="prd_state" id="prd_state">
							<option value="">-선택-</option>
							<c:forEach items="${prdStateList}" var="prdState">
								<option value="${prdState.DEFINITION_NM}">${prdState.DEFINITION_NM}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<th class='stitle'>업무방식<p id="emp">*</p></th>
					<td><select class="select_box" title="과세구분" name="work_type" id="work_type">
							<option value="">-선택-</option>
							<c:forEach items="${workTypeList}" var="workType">
								<option value="${workType.DEFINITION_NM}">${workType.DEFINITION_NM}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<th class='stitle'>세일단가</th>
					<td><input type="text" name="sale_amt" id="sale_amt" autocomplete="off" placeholder="숫자" /></td>
				</tr>
				<tr>
					<th class='stitle'>정렬순서</th>
					<td><input type="text" name="seq" id="seq" autocomplete="off" placeholder="숫자" /></td>
				</tr>
		
				<tr>
					<th class='stitle'>상품설명</th>
					<td><input type="text" name="prd_explain" id="prd_explain" autocomplete="off" placeholder="상품설명" /></td>
				</tr>
	
			</table>



<!-- 			배민 상세 -->
			<table id='f_table' class='detail_table bm_detail_table' style="display: none;">
				<tr>
					<th colspan='2' class='mtitle'>배달의민족 상세 정보</th>
				</tr>
				<tr>
					<th class='stitle'>판매기간</th>
					<td><input type='text' class='input_date' id='sdate' name='sale_start_dt' autocomplete='off' readonly /> ~ <input type='text' class='input_date' id='edate' name='sale_end_dt' autocomplete='off' readonly /></td>
				</tr>
				<tr>
					<th class='stitle'>상품 대표 이미지 URL</th>
					<td><input type="text" name="prd_base_img_url" id="prd_base_img_url" autocomplete="off" placeholder="상품 대표 이미지 URL" /></td>
				</tr>
				<tr>
					<th class='stitle'>상품 상세 이미지 URL</th>
					<td><input type="text" name="prd_dt_img_url" id="prd_dt_img_url" autocomplete="off" placeholder="상품 상세 이미지 URL" /></td>
				</tr>
				<tr>
<!-- 				레퍼런스 코드 필요 -->
					<th class='stitle'>미성년자구매유무</th>
					<td><select class="select_box" title="과세구분" name="use_sales_adult" id="use_sales_adult">
							<option value="">-선택-</option>
							<option value="0">성인</option>
							<option value="1">전연령</option>
					</select></td>
				</tr>
				<tr>
					<th class='stitle'>취급상품종류</th>
					<td><input type="text" name="prd_type" id="prd_type" autocomplete="off" placeholder="취급상품종류" /></td>
				</tr>
				<tr>
					<th class='stitle'>상품카테고리</th>
					<td>
						<select class="select_box" title="상품카테고리" name="prd_ctgr" id="prd_ctgr">
							
						</select>
					</td>
				</tr>
			</table>

<!-- 			요기요 상세 -->
<!-- 			추후 요청 사항에 따라 추가 가능성 -->
			<table id='f_table' class='detail_table ygy_detail_table' style="display: none;">
				<tr>
					<th colspan='2' class='mtitle'>요기요 상세 정보</th>
				</tr>
<!-- 				<tr> -->
<!-- 					<th class='stitle'>상품 대표 이미지 URL</th> -->
<!-- 					<td><input type="text" name="prd_base_img_url" id="prd_base_img_url" autocomplete="off" placeholder="상품 대표 이미지 URL" /></td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<th class='stitle'>상품 상세 이미지 URL</th> -->
<!-- 					<td><input type="text" name="prd_dt_img_url" id="prd_dt_img_url" autocomplete="off" placeholder="상품 상세 이미지 URL" /></td> -->
<!-- 				</tr> -->
			</table>
			
		

		</div>
	</div>
	

</body>
<script>




function clear(){
	
	//채널사 마다 다를 수 있는 카테고리 셀렉트 요소들 초기화
	const stoCtgrSelectElement = document.getElementById('sto_ctgr');
	for (let i = stoCtgrSelectElement.options.length - 1; i >= 1; i--) {
		stoCtgrSelectElement.options.remove(i);
	}
	const prdCtgrSelectElement = document.getElementById('prd_ctgr');
	for (let i = prdCtgrSelectElement.options.length - 1; i >= 1; i--) {
		prdCtgrSelectElement.options.remove(i);
	}
	
	
	
	//현재 페이지의 모든 input 요소 초기화
	const inputs = document.querySelectorAll('input');
	for (const input of inputs) {
// 		console.log(input);
		if(input.id === 'ch_nm' || input.id === 'ch_cd') continue;
		  input.value = ''; // 텍스트 입력 요소 초기화
		  if (input.type === 'number') {
		    input.value = 0; // 숫자 입력 요소 초기화
		  } 
		  else if (input.type === 'checkbox' || input.type === 'radio') {
		    input.checked = false; // 체크박스 및 라디오 버튼 초기화
		  }
		  else if(input.type === 'select'){
			input.selectedIndex = 0; // 첫 번째 옵션을 선택
		  }
	}
	
	
	//현재 페이지의 모든 select 요소 초기화
	const selects = document.querySelectorAll('select');
	for (const select of selects) {
		
			select.selectedIndex = 0; // 첫 번째 옵션을 선택
	}
	

	//상세 정보 테이블 모두 감추기
	const detailTableArr = Array.from(document.getElementsByClassName('detail_table'));
	console.log(detailTableArr);
	detailTableArr.forEach(function(table){	
		table.style.display = 'none';
			
	});
	
}

function initCtgrList(cpnCd){

	
	$.ajax ({
		url: 'getCategoryListOfOrderCompany',
		dataType: 'json',
	    type: 'POST',
	    async : false,
	    data: {
	    	chCd : cpnCd
	    		},
	    success: function (data) {
	    	
	    	console.log("매장 카테고리 그리기");
	    	
	    	console.log(data);
	    	
	    	
			$.unblockUI();
			
			const stoCtgrLength = Object.keys(data.stoCtgrList).length;			
			const prdCtgrLength = Object.keys(data.prdCtgrList).length;
			
			if(stoCtgrLength > 0){
				setStoreCategory(data.stoCtgrList);
			}
			
			if(prdCtgrLength > 0){
				setProductCategory(data.prdCtgrList);
			}
	       }, 
	       error: function(e) { 
	       	swal("카테고리 정보를 가져오지 못했습니다."); 
	       } 
	     }
	);
	
}





//페이지의 입력 내용 초기화
// function clear(){
	
// 	//현재 페이지의 모든 input 요소 초기화
// 	const inputs = document.querySelectorAll('input');
// 	for (const input of inputs) {
// 		console.log(input);
// 		if(input.id === 'ch_nm' || input.id === 'ch_cd') continue;
// 		  input.value = ''; // 텍스트 입력 요소 초기화
// 		  if (input.type === 'number') {
// 		    input.value = 0; // 숫자 입력 요소 초기화
// 		  } 
// 		  else if (input.type === 'checkbox' || input.type === 'radio') {
// 		    input.checked = false; // 체크박스 및 라디오 버튼 초기화
// 		  }
// 		  else if(input.type === 'select'){
// 			input.selectedIndex = 0; // 첫 번째 옵션을 선택
// 		  }
// 	}
	
// 	//현재 페이지의 모든 select 요소 초기화
// 	const selects = document.querySelectorAll('select');
// 	for (const select of selects) {
// 		select.selectedIndex = 0;
// 	}
	
// 	//상세 정보 테이블 모두 감추기
// 	const detailTableArr = Array.from(document.getElementsByClassName('detail_table'));
// 	console.log(detailTableArr);
// 	detailTableArr.forEach(function(table){	
// 		table.style.display = 'none';
			
// 	});
	
// }
// 채널사 세부정보 입력창 띄우기
function displayDetailInfo(cpnCd){
	
// 	clear(); //페이지의 입력 내용 초기화
	
	switch(cpnCd){
	case "07":
		console.log("배민");
		const bmDetailInfoTb = Array.from(document.getElementsByClassName('bm_detail_table'))[0];
		bmDetailInfoTb.style.display = 'table';  //다시 상세창 출력시키기
		break;
	case "01":
		console.log("요기요");
		const ygyDetailInfoTb = Array.from(document.getElementsByClassName('ygy_detail_table'))[0];
		ygyDetailInfoTb.style.display = 'table'; //다시 상세창 출력시키기
		break;
	}
}


// 채널사 매장 카테고리 option 세팅
function setStoreCategory(ctgrList){
	
	console.log("옵션 그리기!");
	const selectElement = document.getElementById('sto_ctgr');
	selectElement.innerHTML = '';
	let newOption = document.createElement('option');
	newOption.text = '-선택-';
	newOption.value = '';
  	selectElement.appendChild(newOption);
	if(ctgrList != null && ctgrList.length > 0){	
		ctgrList.forEach(item => {
		  newOption = document.createElement('option');
		  newOption.text = item.ctgr_nm;
		  newOption.value = item.ctgr_cd;
		  
		  selectElement.appendChild(newOption);
		});
	}

}


function setProductCategory(ctgrList){
	
	console.log("옵션 그리기!");
	const selectElement = document.getElementById('prd_ctgr');
	selectElement.innerHTML = '';
	let newOption = document.createElement('option');
	newOption.text = '-선택-';
	newOption.value = '';
  	selectElement.appendChild(newOption);
	if(ctgrList != null && ctgrList.length > 0){	
		ctgrList.forEach(item => {
		  newOption = document.createElement('option');
		  newOption.text = item.ctgr_nm;
		  newOption.value = item.ctgr_cd;
		  
		  selectElement.appendChild(newOption);
		});
	}

}




// 채널사 선택시 매장 카테고리 옵션 업데이트
function updateCtgrTypeOptions(chCd) {

	$.ajax ({
		url: 'oms_sub02_03_process',
		dataType: 'json',
	    type: 'POST',
	    async : false,
	    data: {
	    	chCd
	    		},
	    success: function (data) {
	    	
	    	console.log("매장 카테고리 그리기");
	    	
	    	console.log(data);
	    	
	    	
			$.unblockUI();
			
			const stoCtgrLength = Object.keys(data.stoCategoryList).length;			
			const prdCtgrLength = Object.keys(data.prdCtegoryList).length;
			
			if(stoCtgrLength > 0){
				setStoreCategory(data.stoCategoryList);
			}
			
			if(prdCtgrLength > 0){
				setProductCategory(data.prdCtegoryList);
			}
	       }, 
	       error: function(e) { 
	       	swal("카테고리 정보를 가져오지 못했습니다."); 
	       } 
	     }
	);
}


function registerDeliveryProduct() {
	
	console.log("상품 등록");
	const ch_cd = $("#ch_cd").val();
	const prd_cd = $("#prd_cd").val();
	const prd_nm = $("#prd_nm").val();
	const sto_ctgr = $("#sto_ctgr").val();
	const std_amt = $("#std_amt").val();
	const sale_amt = $("#sale_amt").val();
	const seq = $("#seq").val();
	const tax_type = $("#tax_type").val();
	const prd_state = $("#prd_state").val();
	const prd_explain = $("#prd_explain").val();
	const work_type = $("#work_type").val();

	let sdate;
	let edate;
	let prd_base_img_url;
	let prd_dt_img_url;
	let use_sales_adult;
	let prd_type;
	let prd_ctgr;
	if(ch_cd === '07'){
		sdate = $("#sdate").val();
		edate = $("#edate").val();
		prd_base_img_url = $("#prd_base_img_url").val();
		prd_dt_img_url = $("#prd_dt_img_url").val();
		use_sales_adult = $("#use_sales_adult").val();
		prd_type = $("#prd_type").val();
		prd_ctgr = $("#prd_ctgr").val();		
	}
	
	
	console.log("값");
	console.log(tax_type);
	console.log(prd_state);
    
    if (isEmpty(ch_cd)) {

        swal({
            text: '채널사를 선택해주세요.',
            icon: "info"
        });
        return;
    	
    }else if(isEmpty(prd_cd)){
        swal({
            text: '상품코드를 입력해주세요.',
            icon: "info"
        });
        return;
    }else if (isEmpty(prd_nm)) {
        swal({
            text: '상품명을 입력해주세요.',
            icon: "info"
        });
        return;
    } else if(isEmpty(sto_ctgr)){
        swal({
            text: '매장 카테고리를 선택해주세요.',
            icon: "info"
        });
        return;
    }else if (isEmpty(std_amt)) {

        swal({
            text: '판매단가를 입력해주세요.',
            icon: "info"
        });
        return;
    }  else if (isEmpty(tax_type)) {
        swal({
        	
            text: '과세 구분을 선택주세요.',
            icon: "info"
        });
        return;
    } else if(isEmpty(prd_state)){
    	console.log(prd_state);
        swal({
        
            text: '상품 상태를 선택주세요.',
            icon: "info"
        });
        return;
    } else if (isEmpty(work_type)) {
        swal({
            text: '업무방식을 선택주세요.',
            icon: "info"
        });
        return;
    }
    
   
    //상품코드 숫자 체크
    if(!isNumeric(prd_cd)){
        swal({
            text: '상품코드는 숫자만 가능합니다.',
            icon: "info"
        });
        return;
    }
    
    //판매단가 숫자 체크
    if(!isNumeric(std_amt)){
        swal({
            text: '판매단가는 숫자만 가능합니다.',
            icon: "info"
        });
        return;
    }
    
    //세일단가 숫자 체크
    if(!isNumeric(sale_amt)){
        swal({
            text: '세일단가는 숫자만 가능합니다.',
            icon: "info"
        });
        return;
    }
    
    //정렬 순서 숫자 체크
    if(!isNumeric(seq)){
        swal({
            text: '정렬 순서는 숫자만 가능합니다.',
            icon: "info"
        });
        return;
    }
    
    
    
    const data = {
    		chCd : ch_cd,
    		prdCd : prd_cd,
    		prdNm : prd_nm,
    		stoCtgrCd : sto_ctgr,
    		stdAmt : std_amt,
        	saleAmt : sale_amt,
        	seq : seq,
        	taxType : tax_type,
        	prdState : prd_state,
        	prdExplain : prd_explain,
        	workType : work_type,   	
        	saleStartDt : sdate,
        	saleEndDt : edate,
        	prdBaseImgUrl : prd_base_img_url,
        	prdDtImgUrl : prd_dt_img_url,
        	useSalesAdult : use_sales_adult,
        	prdType : prd_type,
        	prdCtgrCd : prd_ctgr
    };
    
    const jsonData = JSON.stringify(data);
    console.log(jsonData);
    
    
 
    $.ajax({
        url: "registerDeliveryProduct",
        type: "POST",
        async: false,
        contentType: 'application/json; charset=utf-8',
        data: jsonData,
        success: function(data) {
       	  	swal({
                 text: "상품 등록이 완료되었습니다.",
                 icon: "success"
             }).then(function() {
            	 window.location.replace("deliveryProductMain");
//                  opener.parent.search_go();
//                  window.close();
             });
        },
    	error: function(e) {
    		console.log(e);
    		swal({
                text: e.responseJSON.message,
                icon: "error"
            });
			$.unblockUI();
		}
    });	 
}

function isNumeric(num) {
	if(num == null || num == '') 
		return true;
	else
		return /\d+/.test(num); // 기본적인 숫자 체크
}

document.addEventListener('DOMContentLoaded', function() {
    var numericInputs = document.querySelectorAll('.num_input');

    numericInputs.forEach(function(input) {
        input.addEventListener('keydown', function(event) {
            if (event.key !== 'Backspace' && isNaN(Number(event.key))) {
                event.preventDefault();
            }
        });
    });
});

</script>
</html>
