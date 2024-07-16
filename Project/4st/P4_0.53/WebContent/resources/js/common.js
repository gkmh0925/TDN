// 네비게이션 바
$(document).ready(function() {
	//버튼 포커스 해제
	$('.btn_search').focus(function() {
		$('.btn_search').blur();
	});
	
	
	
	$('.btn_excel').focus(function() {
		$('.btn_excel').blur();
	});

	//돋보기 클릭 시 검색창 띄우기
	let width = '1000';
	let height = '550';

	// 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
	let left = Math.ceil((window.screen.width - width) / 2);
	let top = Math.ceil((window.screen.height - height) / 4);

	let option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";

	$("img").click(function() {
		if ($(this).attr("id") == "snm_img") { //매장명

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#snm').focus();

			let url = "subSearch01";
			let name = "매장 상세 검색";
			let parent = window.open(url, name, option);

		} else if ($(this).attr("id") == "ocnm_img") { //채널사명

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#ocnm').focus();

			let url = "subSearch02";
			let name = "주문 상세 검색";
			window.open(url, name, option);

		} else if ($(this).attr("id") == "dcnm_img") {  //배송채널명

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#dcnm').focus();

			let url = "subSearch03";
			let name = "배송 상세 검색";
			window.open(url, name, option);

		} else if ($(this).attr("id") == "pnm_img") { //상품명

			const inptNm = $(this).attr("id").replace("_img", "");

			$('#inptNm').val(inptNm);
			$('#pnm').focus();

			let url = "subSearch04";
			let name = "상품 상세 검색";
			window.open(url, name, option);
		} else if ($(this).attr("id") == "ocnm_img_02") { //!@@240701 yh

			const inptNm = $(this).attr("id").replace("_img", "");
		
			$('#inptNm').val(inptNm);
			$('#ocnm').focus();

			let url = "subSearch02_02";
			let name = "채널사 상세 검색";
			window.open(url, name, option);
		}
		
	});

	$(".btn_reg").click(function() {
		width = '835';
		height = '365';

		top = Math.ceil((window.screen.height - height) / 4);
		option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";

		let url = "userRegister";
		let name = "계정 등록";
		let parent = window.open(url, name);
	});
	
	$(".btn_reg_category_lv1").click(function() {
		width = '835';
		height = '365';

		top = Math.ceil((window.screen.height - height) / 4);
		option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";

		let url = "categoryRegister";
		let name = "카테고리 등록";
		let parent = window.open(url, name);
	});
	
	
	$(".btn_reg_category_lv2").click(function() {
		width = '835';
		height = '365';

		top = Math.ceil((window.screen.height - height) / 4);
		option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";

		let url = "categoryLv2Register";
		let name = "카테고리 Lv2 등록";
		let parent = window.open(url, name);
	});
	
	//!@@0701 yh
	$(".btn_reg_deliveryProduct_lv1").click(function() {
		
		
		width = '835';
		height = '365';
		
		top = Math.ceil((window.screen.height - height) / 4);
		option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";
		
		let url = "deliveryProductRegister";
		let name = "배달서비스 상품 등록";
		let parent = window.open(url, name);
	});


	//김민현 수정
		$(".btn_reg_deliveryFee_lv1").click(function() {
		
		width = '835';
		height = '365';
		
		top = Math.ceil((window.screen.height - height) / 4);
		option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";
		
		let url = "deliveryFeeRegister";
		let name = "배달료 마스터 등록";
		let parent = window.open(url, name);
	});

});

// datepicker 선언, 날짜 저장 or 새로 불러오기
$(document).ready(function() {
	$('#dateType1').focus(function() {
		$(this).addClass('active_button');
		$('#dateType2').removeClass('active_button');
		$('#dateType3').removeClass('active_button');
	});
	$('#dateType2').focus(function() {
		$(this).addClass('active_button');
		$('#dateType1').removeClass('active_button');
		$('#dateType3').removeClass('active_button');
	});

	$('#dateType3').focus(function() {
		$(this).addClass('active_button');
		$('#dateType1').removeClass('active_button');
		$('#dateType2').removeClass('active_button');
	});
	
	let selDt = new Date();
	let edt = new Date(selDt.getFullYear() ,selDt.getMonth() + 1, selDt.getDate());
	
	$("#sdate").datepicker({
		onClose: function( selectedDate ) {
			selDt = new Date(selectedDate);
			edt = new Date(selDt.getFullYear() ,selDt.getMonth() + 1, selDt.getDate());

	        // 시작일(fromDate) datepicker가 닫힐때
	        // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
	        $("#edate").datepicker("option", "minDate", selectedDate);
	        $("#edate").datepicker("option", "maxDate", $.datepicker.formatDate('yy-mm-dd', edt));
	    }
    });
	$("#edate").datepicker();

	$("#sdate").datepicker('setDate', selDt);
	$("#edate").datepicker('setDate', selDt);
	
	$("#edate").datepicker("option", "minDate", selDt);
	$("#edate").datepicker("option", "maxDate", $.datepicker.formatDate('yy-mm-dd', edt));
	
});

// 오늘, 일주일, 1개월, 6개월, 12개월 기능
function setSearchDate(start) {
    const edate = $('#edate').val();
    const rpldate = edate.split('-');
    let edt = new Date(rpldate[0], rpldate[1] - 1, rpldate[2]); // edate 날짜

    let d = new Date();

    if (start == '1d') {
        edt = new Date(d.getFullYear(), d.getMonth() + 1, d.getDate());
        d = $.datepicker.formatDate('yy-mm-dd', d)

        $("#edate").datepicker("option", "minDate", d);
        $("#edate").datepicker("option", "maxDate", $.datepicker.formatDate('yy-mm-dd', edt));

        $('#sdate').val(d);
        $('#edate').val(d);

        return;

    } else if (start == '7d') {
        d = new Date(edt.getFullYear(), edt.getMonth(), edt.getDate() - 7);
        edt = new Date(d.getFullYear(), d.getMonth() + 1, d.getDate());

        $("#edate").datepicker("option", "minDate", d);
        $("#edate").datepicker("option", "maxDate", $.datepicker.formatDate('yy-mm-dd', edt));

    } else if (start == '1m') {
        const lastDayofsdate = (new Date(edt.getFullYear(), edt.getMonth(), 0)).getDate();
        const lastDayofedate = edt.getDate();

        if (lastDayofedate > lastDayofsdate) {
            d = new Date(edt.getFullYear(), edt.getMonth() - 1, lastDayofsdate);

            $("#edate").datepicker("option", "minDate", d);
            $("#edate").datepicker("option", "maxDate", $.datepicker.formatDate('yy-mm-dd', edt));
        } else {
            d = new Date(edt.getFullYear(), edt.getMonth() - 1, edt.getDate());

            $("#edate").datepicker("option", "minDate", d);
            $("#edate").datepicker("option", "maxDate", $.datepicker.formatDate('yy-mm-dd', edt));
        }
    } else if (start == '6m') { // 6개월 전의 날짜 계산       
        d.setMonth(d.getMonth() - 6);
        edt.setDate(edt.getDate() - 180); 
 		
 		d = new Date(edt.getFullYear(), edt.getMonth() , edt.getDate());
 		
    } else if (start == '12m') { // 12개월치 날짜 계산
        d.setMonth(d.getMonth() - 12);
        edt.setDate(edt.getDate() - 365); 

        d = new Date(edt.getFullYear(), edt.getMonth() + 1 , edt.getDate()); 
    }
    
    let endDate = $.datepicker.formatDate('yy-mm-dd', d);
    $('#sdate').val(endDate);
}

//엔터 키 입력 시 Search
function onEnterSearch() {
	let keyCode = window.event.keyCode;

	if (keyCode == 13) {
		search_go();
	}
}

//null 체크 함수
function isEmpty(value) {
	if (value == "" || value == " " || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
		return true
	} else {
		return false
	}
};

//not Null 체크 함수
function isNotEmpty(value) {
	const trimVal = value.trim();
	if (value == "" || value == " " || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
		return false
	} else {
		return true
	}
};

//numberformat 함수
function moneyFormat(num) {

	if (!num) return "0";

	let regexp = /\B(?=(\d{3})+(?!\d))/g;

	return num.toString().replace(regexp, ',');
}

//엑셀 파일 체크
function checkFileType(filePath) {
	var fileFormat = filePath.split(".");
	if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsb") > -1) {
		return true;
	} else {
		return false;
	}
}

// 할인금액 포멧
function disMoneyFormat(num){
	let dis_amt = num;
	
	if (!dis_amt) return "0";

	let regexp = /\B(?=(\d{3})+(?!\d))/g;

	if(dis_amt == 0){
		dis_amt = dis_amt.toString().replace(regexp, ',');
	}else {
		dis_amt = "-"+dis_amt.toString().replace(regexp, ',');
	}

	return dis_amt;
}
//dateFormat 함수
function datetimeFormat(num) {

	if (!num) return "";

	let formatNum = '';
	// 공백제거
	num = num.replace(/\s/gi, "");

	try {
		if (num.length == 4) {
			formatNum = num.replace(/(\d{2})(\d{2})/, '$1:$2');
		}else if (num.length == 6) {
			formatNum = num.replace(/(\d{2})(\d{2})(\d{2})/, '$1:$2:$3');
		}else if (num.length == 8) {
			formatNum = num.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
		}else if(num.length == 14){
			formatNum = num.replace(/(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/, '$1-$2-$3 $4:$5:$6');
		}
	} catch (e) {
		formatNum = num;
		console.log(e);
	}
	return formatNum;
}

// 사업자번호 포멧
function biznoFormat(num) {

	if (!num) return "";

	let formatNum = '';

	// 공백제거
	num = num.replace(/\s/gi, "");

	try {
		if (num.length == 10) {
			formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
		}
	} catch (e) {
		formatNum = num;
		console.log(e);
	}
	return formatNum;
}


//unixTimeStamp 변환 함수
function unixTimeFormat(num) {

	if (!num) return "";

	const date = new Date(num);
	const year = date.getFullYear();
	const month = "0" + (date.getMonth() + 1);
	const day = "0" + date.getDate();
	const hour = "0" + date.getHours();
	const minute = "0" + date.getMinutes();

	const formatDate = year + "-" + month.substr(-2) + "-" + day.substr(-2) + " " + hour.substr(-2) + ":" + minute.substr(-2);

	return formatDate;
}

//phoneNumber 변환 함수
function phoneNumberFormat(num) {

	if (!num) return "";

	let formatNum = '';

	if (num.length == 12) {
		formatNum = num.replace(/(\d{4})(\d{4})(\d{4})/, "$1-$2-$3");

	} else if(num.length == 8){
		formatNum = num.replace(/(\d{4})(\d{4})/, "$1-$2");
	} else if(num.length == 10){
		if(num.substr(0,2) == "02"){
			formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3");
		}else{
			formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
		}
	}else{
			formatNum = num.replace(/(\d{2,3})(\d{3,4})(\d{4})/, "$1-$2-$3");
		}

	return formatNum;
}

//javascript POST 전송
function post(path, params, method = 'post') {

	const form = document.createElement('form');
	form.method = method;
	form.action = path;

	for (const key in params) {
		if (params.hasOwnProperty(key)) {
			const hiddenField = document.createElement('input');
			hiddenField.type = 'hidden';
			hiddenField.name = key;
			hiddenField.value = params[key];

			form.appendChild(hiddenField);
		}
	}

	document.body.appendChild(form);
	form.submit();
}

//2차원 배열 중복 제거
function multiDimensionalUnique(arr) {
	var uniques = [];
	var itemsFound = {};
	for (var i = 0, l = arr.length; i < l; i++) {
		var stringified = JSON.stringify(arr[i]);
		if (itemsFound[stringified]) { continue; }
		uniques.push(arr[i]);
		itemsFound[stringified] = true;
	}
	return uniques;
}

//매장 번호 하이폰
function inputTelNumber(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let tel = "";
	
	if(number.length == 0){
		tel = "";
	}else if (number.length < 4) {
		return number;
	} else if (number.length < 9) {
		tel += number.substr(0, 4);
		tel += "-";
		tel += number.substr(4);
	} else if (number.length < 10) {
		tel += number.substr(0, 2);
		tel += "-";
		tel += number.substr(2, 3);
		tel += "-";
		tel += number.substr(5);
	} else if (number.length < 11){
		if(number.substr(0,2) == "02"){
			tel += number.substr(0, 2);
			tel += "-";
			tel += number.substr(2, 4);
			tel += "-";
			tel += number.substr(6, 4);
		}else{
			tel += number.substr(0, 3);
			tel += "-";
			tel += number.substr(3, 3);
			tel += "-";
			tel += number.substr(6, 4);
		}
	}else{
		tel += number.substr(0, 3);
		tel += "-";
		tel += number.substr(3, 4);
		tel += "-";
		tel += number.substr(7);
	}
	obj.value = tel;
}

//핸드폰 번호 하이폰
function inputPhoneNumber(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let phone = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 7) {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3);
	} else if (number.length < 11) {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3, 3);
		phone += "-";
		phone += number.substr(6);
	} else {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3, 4);
		phone += "-";
		phone += number.substr(7);
	}
	obj.value = phone;
}

//시간 입력
function inputTime(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let time = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 5) {
		time += number.substr(0, 2);
		time += ":";
		time += number.substr(2);
	}
	obj.value = time;
}

//날짜 입력
function inputDate(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let date = "";

	if (number.length < 5) {
		return number;
	} else if (number.length < 7) {
		date += number.substr(0, 4);
		date += "-";
		date += number.substr(4);
	} else {
		date += number.substr(0, 4);
		date += "-";
		date += number.substr(4, 2);
		date += "-";
		date += number.substr(6);
	}
	obj.value = date;
}

//브레이크 타임 입력
function inputbreak(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let time = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 5) {
		time += number.substr(0, 2);
		time += "-";
		time += number.substr(2);
	}
	obj.value = time;
}
// 지번, 도로명 주소 하나만 return
/*function addrCheck(land, road){
	if(isNotEmpty(land)){
		return land;
	}else if(isNotEmpty(road)){
		return road;
	}
}

function addrDtCheck(landDt, roadDt){
	if(isNotEmpty(landDt)){
		return landDt;
	}else if(isNotEmpty(roadDt)){
		return roadDt;
	}
}*/

//핸드폰 번호 안심/기본 체크
function phoneCheck(basic, safe) {
	if (isNotEmpty(safe)) {
		return phoneNumberFormat(safe);
	}else if (isNotEmpty(basic)) {
		return phoneNumberFormat(basic);
	} else{
		return "";
	}
}

// 업로드 버튼 닫기
function uploadClose(){
	$('.overlay').fadeOut(100);
	$('.hide_div').removeClass('show_div');
	$('.shide_div').removeClass('show_div');//채널사수수료정산조회 샘플엑셀다운로드에사용
	$('#cancel_reason').val('');
}

// 에러 코드별 분기
function errCodeCheck(errCd) {
	let errMsg = "";
	if (errCd == '1') {
		errMsg = "수정에 실패했습니다.";
	} else if (errCd == '2') {
		errMsg = "업로드에 실패했습니다.";
	} else if (errCd == '3') {
		errMsg = "세션이 만료되었습니다. 로그인 후 이용해주세요";
	}else if (errCd == '4'){
		errMsg = "등록에 실패했습니다.";
	}else if (errCd == '12899') {
		errMsg = "문자열의 값이 너무 큽니다.";
	} else if (errCd == '01722') {
		errMsg = "문자가 부적합합니다.";
	} else if (errCd == '00001') {
		errMsg = "중복된 데이터가 존재합니다.";
	} else if (errCd == '01400') {
		errMsg = "비어있는 필수 값이 존재합니다.";
	} else if (errCd == '01407') {
		errMsg = "비어있는 값이 존재합니다.";
	} else if (errCd == '01403') {
		errMsg = "DB에 데이터가 존재하지 않습니다.";
	} else if (errCd == '00905') {
		errMsg = "입력 된 데이터가 없습니다.";
	}  else {
		return false;
	}
	return errMsg;
}

// serializeObject() 
jQuery.fn.serializeObject = function() {
	var obj = null;
	try {
		if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			var arr = this.serializeArray();
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}//if ( arr ) {
		}
	} catch (e) {
		alert(e.message);
	} finally {
	}

	return obj;
};
