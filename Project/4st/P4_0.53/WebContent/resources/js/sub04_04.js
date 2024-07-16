let grid;
//dhtmlx 기본 양식 (요기요 배달)
const ygydGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{
			minWidth: 100, id: "ord_time", header: [{ text: "주문일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "refund_time", header: [{ text: "환불일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "pay_expected_date", header: [{ text: "지급예정 일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "biz_no", header: [{ text: "사업자번호", align: "center" }],
			template: function(text) {
				return biznoFormat(text);
			}
		},
		{ minWidth: 100, id: "store_nm", header: [{ text: "음식점명", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "매장코드", align: "center" }] },
		{ minWidth: 100, id: "ord_id", header: [{ text: "주문ID", align: "center" }] },
		{ minWidth: 100, id: "ch_ord_no", header: [{ text: "주문번호", align: "center" }] },
		{ minWidth: 100, id: "item_sum_amt", header: [{ text: "상품가 합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "min_ord_add_pay", header: [{ text: "최소주문 추가결제액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "deliver_fee", header: [{ text: "배달료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "ord_tot_amt", header: [{ text: "주문 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "ord_online_amt", header: [{ text: "주문 금액 구분 : 온라인 주문", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "ord_offline_amt", header: [{ text: "주문 금액 구분 : 현장 주문", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "restaurant_self_dis", header: [{ text: "레스토랑 자체할인", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 220, id: "pmt_dis_yogiyo_levy_amt", header: [{ text: "프로모션 할인금액 : 요기요 부담", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "pmt_dis_franchisee_levy_amt", header: [{ text: "프로모션 할인금액 : 프랜차이즈 부담", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 220, id: "pmt_dis_restaurant_levy_amt", header: [{ text: "프로모션 할인금액 : 레스토랑 부담", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 280, id: "ord_relay_fee_rate", header: [{ text: "주문중개이용료 : 주문중개이용료율(+VAT)", align: "center" }] },
		{ minWidth: 250, id: "ord_relay_fee_tot_amt", header: [{ text: "주문중개이용료 : 주문중개이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 220, id: "pay_relay_fee_rate", header: [{ text: "결제이용료 : 결제이용료율(+VAT)", align: "center" }] },
		{ minWidth: 200, id: "pay_relay_fee_tot_amt", header: [{ text: "결제이용료 : 결제이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "delivery_agent_fee", header: [{ text: "배달대행이용료 : 배달대행이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "tot_sum_fee", header: [{ text: "이용료 전체 합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 280, id: "od_deli_fee_rest_self_fund", header: [{ text: "OD배달료 매출 : 레스토랑 자체펀딩 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "yo_time_fee_tot_yo_time_fee", header: [{ text: "요타임딜이용료 : 요타임딜이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "REFERRAL_ADVERTISEMENT_FEE", header: [{ text: "추천광고이용료 : 추천광고이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "settle_amt", header: [{ text: "정산 금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "pay_type", header: [{ text: "결제유형", align: "center" }] },
		{ minWidth: 100, id: "pay_type_detail", header: [{ text: "결제유형 상세", align: "center" }] },
		{ minWidth: 100, id: "pay_form", header: [{ text: "결제형태", align: "center" }] },
		{ minWidth: 100, id: "pay_form_detail", header: [{ text: "결제형태 상세", align: "center" }] },
		{ minWidth: 150, id: "delivery_type", header: [{ text: "배달 유형(VD/OD)", align: "center" }] },
		{ minWidth: 100, id: "franchisee_id", header: [{ text: "프랜차이즈 ID", align: "center" }] },
		{ minWidth: 100, id: "cpn_nm", header: [{ text: "회사명", align: "center" }] },
		{ minWidth: 100, id: "biz_nm", header: [{ text: "사업자명", align: "center" }] },
		{ minWidth: 100, id: "delivery_address1", header: [{ text: "배달주소1", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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
//dhtmlx 기본 양식 (요기요 픽업)
const ygypGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{
			minWidth: 100, id: "ord_time", header: [{ text: "주문일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "refund_time", header: [{ text: "환불일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "pay_expected_date", header: [{ text: "지급예정 일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "biz_no", header: [{ text: "사업자번호", align: "center" }],
			template: function(text) {
				return biznoFormat(text);
			}
		},
		{ minWidth: 100, id: "store_nm", header: [{ text: "음식점명", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "매장코드", align: "center" }] },
		{ minWidth: 100, id: "ord_id", header: [{ text: "주문ID", align: "center" }] },
		{ minWidth: 100, id: "ch_ord_no", header: [{ text: "주문번호", align: "center" }] },
		{ minWidth: 100, id: "item_sum_amt", header: [{ text: "상품가 합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "min_ord_add_pay", header: [{ text: "최소주문 추가결제액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "deliver_fee", header: [{ text: "배달료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "ord_tot_amt", header: [{ text: "주문 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "ord_online_amt", header: [{ text: "주문 금액 구분 : 온라인 주문", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "ord_offline_amt", header: [{ text: "주문 금액 구분 : 현장 주문", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "restaurant_self_dis", header: [{ text: "레스토랑 자체할인", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 220, id: "pmt_dis_yogiyo_levy_amt", header: [{ text: "프로모션 할인금액 : 요기요 부담", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "pmt_dis_franchisee_levy_amt", header: [{ text: "프로모션 할인금액 : 프랜차이즈 부담", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 220, id: "pmt_dis_restaurant_levy_amt", header: [{ text: "프로모션 할인금액 : 레스토랑 부담", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 280, id: "ord_relay_fee_rate", header: [{ text: "주문중개이용료 : 주문중개이용료율(+VAT)", align: "center" }] },
		{ minWidth: 250, id: "ord_relay_fee_tot_amt", header: [{ text: "주문중개이용료 : 주문중개이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 220, id: "pay_relay_fee_rate", header: [{ text: "결제이용료 : 결제이용료율(+VAT)", align: "center" }] },
		{ minWidth: 200, id: "pay_relay_fee_tot_amt", header: [{ text: "결제이용료 : 결제이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "delivery_agent_fee", header: [{ text: "배달대행이용료 : 배달대행이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "tot_sum_fee", header: [{ text: "이용료 전체 합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 280, id: "od_deli_fee_rest_self_fund", header: [{ text: "OD배달료 매출 : 레스토랑 자체펀딩 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "yo_time_fee_tot_yo_time_fee", header: [{ text: "요타임딜이용료 : 요타임딜이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 250, id: "REFERRAL_ADVERTISEMENT_FEE", header: [{ text: "추천광고이용료 : 추천광고이용료 총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "settle_amt", header: [{ text: "정산 금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "pay_type", header: [{ text: "결제유형", align: "center" }] },
		{ minWidth: 100, id: "pay_type_detail", header: [{ text: "결제유형 상세", align: "center" }] },
		{ minWidth: 100, id: "pay_form", header: [{ text: "결제형태", align: "center" }] },
		{ minWidth: 100, id: "pay_form_detail", header: [{ text: "결제형태 상세", align: "center" }] },
		{ minWidth: 150, id: "delivery_type", header: [{ text: "배달 유형(VD/OD)", align: "center" }] },
		{ minWidth: 100, id: "franchisee_id", header: [{ text: "프랜차이즈 ID", align: "center" }] },
		{ minWidth: 100, id: "cpn_nm", header: [{ text: "회사명", align: "center" }] },
		{ minWidth: 100, id: "biz_nm", header: [{ text: "사업자명", align: "center" }] },
		{ minWidth: 100, id: "delivery_address1", header: [{ text: "배달주소1", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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
//dhtmlx 기본 양식 (네이버)
const naverGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "settle_no", header: [{ text: "주문번호", align: "center" }] },
		{ minWidth: 150, id: "item_ord_id", header: [{ text: "상품주문번호", align: "center" }] },
		{ minWidth: 100, id: "ch_cd", header: [{ text: "채널사코드", align: "center" }] },
		{ minWidth: 100, id: "gubun", header: [{ text: "구분", align: "center" }] },
		{ minWidth: 100, id: "item_nm", header: [{ text: "상품명", align: "center" }] },
		{ minWidth: 100, id: "buyer_nm", header: [{ text: "구매자명", align: "center" }] },
		{
			minWidth: 120, id: "ord_date", header: [{ text: "정산기준일", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 120, id: "settle_expected_date", header: [{ text: "정산예정일", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 120, id: "settle_complet_date", header: [{ text: "정산완료일", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 150, id: "tax_return_date", header: [{ text: "세금신고기준일", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 120, id: "act_amt", header: [{ text: "결제금액(A)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 300, id: "naver_pay_order_cms", header: [{ text: "네이버페이 예약주문관리 수수료(B)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "sale_link_cms", header: [{ text: "매출연동 수수료(D)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "sale_link_cms_gubun", header: [{ text: "매출연동 수수료 구분", align: "center" }] },
		{ minWidth: 180, id: "free_installment_fee", header: [{ text: "무이자할부 수수료 (E)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "settle_state", header: [{ text: "정산상태", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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
//dhtmlx 기본 양식 (카카오)
const kakaoGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "brand_nm", header: [{ text: "브랜드명", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "매장코드", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "매장명", align: "center" }] },
		{
			minWidth: 100, id: "ord_time", header: [{ text: "주문일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "adjustment_date", header: [{ text: "조정일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 140, id: "cmd_ord_no", header: [{ text: "CMS주문번호", align: "center" }] },
		{ minWidth: 160, id: "ch_ord_no", header: [{ text: "카카오주문번호", align: "center" }] },
		{ minWidth: 100, id: "ord_amt", header: [{ text: "주문금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "deliver_fee", header: [{ text: "배달료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "dis_amt", header: [{ text: "할인금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "act_amt", header: [{ text: "결제금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "cancel_amt", header: [{ text: "최소금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "pay_type", header: [{ text: "결제수단", align: "center" }] },
		{ minWidth: 120, id: "prepaid_card", header: [{ text: "선결제카드", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "prepaid_kakao_pay", header: [{ text: "선결제카카오페이", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "prepaid_kakao_money", header: [{ text: "선결제카카오머니", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "after_pay", header: [{ text: "후결제", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "brand_levy_amt", header: [{ text: "브랜드 부담금", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "kakao_levy_amt", header: [{ text: "카카오 부담금", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "franchisee_levy_amt", header: [{ text: "가맹점 부담금", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "etc_levy_amt", header: [{ text: "기타 부담금", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "cms_ord_relay_cms", header: [{ text: "cms 주문중개 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "kakao_ord_relay_cms", header: [{ text: "카카오 주문중개 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 150, id: "ord_relay_cms_sum", header: [{ text: "주문중개 수수료 합", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "cms_dis_relay_cms", header: [{ text: "cms 할인중개 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "kakao_dis_relay_cms", header: [{ text: "카카오 할인중개 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "dis_relay_cms_sum", header: [{ text: "할인중개 수수료 합", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 200, id: "inicis_cms", header: [{ text: "아니시스 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 180, id: "kakao_pay_cms", header: [{ text: "카카오페이 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "pg_cms", header: [{ text: "PG 수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "inicis_back_cms", header: [{ text: "이니시스 백마진", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 140, id: "e_cupon_cms", header: [{ text: "E쿠폰수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "tot_cms_amt", header: [{ text: "수수료총액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "deposit_amt", header: [{ text: "입급액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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
// 키스
const kisGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "gubun", header: [{ text: "구분", align: "center" }] },
		{ minWidth: 100, id: "ch_nm", header: [{ text: "채널사명", align: "center" }] },
		{ minWidth: 180, id: "dtord_no", header: [{ text: "상세주문번호", align: "center" }], type: "number" },
		{
			minWidth: 100, id: "ord_date", header: [{ text: "주문일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 100, id: "ord_time", header: [{ text: "주문일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{
			minWidth: 120, id: "pay_expected_date", header: [{ text: "지급예정일", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "ch_ord_no", header: [{ text: "주문번호", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "점포코드", align: "center" }] },
		{ minWidth: 100, id: "store_nm", header: [{ text: "점포명", align: "center" }] },
		{
			minWidth: 120, id: "biz_no", header: [{ text: "사업자번호", align: "center" }],
			template: function(text) {
				return biznoFormat(text);
			}
		},
		{ minWidth: 120, id: "menu_tot_amt", header: [{ text: "상품가합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "deliver_fee", header: [{ text: "배달료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "act_amt", header: [{ text: "결제금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "cms_amt", header: [{ text: "수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 80, id: "vat_amt", header: [{ text: "VAT", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "pay_amt", header: [{ text: "지급액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "bigo", header: [{ text: "비고", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{
			minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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

// 배달의민족
const baeMinGrid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "ord_date", header: [{ text: "주문일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
//		{ minWidth: 100, id: "branch_no", header: [{ text: "지점번호", align: "center" }] },
		{ minWidth: 100, id: "branch_nm", header: [{ text: "상호명", align: "center" }] },
		{ minWidth: 100, id: "exp_pay_date", header: [{ text: "지급예정일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "pay_date", header: [{ text: "결제일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "pay_time", header: [{ text: "결제시각", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "settle_std_date", header: [{ text: "정산기준일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "set_baemin_dv_seq", header: [{ text: "배민배달시퀀스", align: "center" }], type: "number", format: "#,#" },
//		{ minWidth: 100, id: "baemin_settle_type", header: [{ text: "정산유형", align: "center" }] },
//		{ minWidth: 100, id: "baemin_settle_cycle", header: [{ text: "정산주기", align: "center" }] },
//		{ minWidth: 100, id: "sale_scale", header: [{ text: "매출규모", align: "center" }] },
		{ minWidth: 100, id: "ch_cd", header: [{ text: "채널사코드", align: "center" }] },
		{ minWidth: 100, id: "ord_id", header: [{ text: "주문서번호", align: "center" }] },
		{ minWidth: 100, id: "ch_ord_no", header: [{ text: "배민주문번호", align: "center" }] },
		{ minWidth: 100, id: "ord_state", header: [{ text: "주문상태", align: "center" }] },
//		{ minWidth: 100, id: "item_nm", header: [{ text: "상품명", align: "center" }] },
//		{ minWidth: 100, id: "item_option_nm", header: [{ text: "옵션명", align: "center" }] },
//		{ minWidth: 100, id: "item_ord_no", header: [{ text: "상품옵션주문번호", align: "center" }] },
//		{ minWidth: 100, id: "delivery_type", header: [{ text: "배송방식", align: "center" }] },
//		{ minWidth: 100, id: "delivery_subject", header: [{ text: "배달주체", align: "center" }] },
		{ minWidth: 100, id: "sale_item_amt", header: [{ text: "매출상품금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_dv_amt", header: [{ text: "매출배달팁", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_envelope_amt", header: [{ text: "매출봉투값", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_partial_pefund", header: [{ text: "매출부분환불금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_seller_cpn_amt", header: [{ text: "매출셀러부담쿠폰금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_sum", header: [{ text: "매출합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "settle_pay_charge_target_amt", header: [{ text: "결제정산수수료 부과대상 금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "relay_fee_charge_target_amt", header: [{ text: "중개이용료 부과대상 금액 금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_sd_supply_amt", header: [{ text: "기준수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_sd_preferential_amt", header: [{ text: "우대수수료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_pay_settle_vat", header: [{ text: "결제정산수수료(부가세)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_relay_fee_supply_amt", header: [{ text: "중개이용료(공급가액)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_relay_fee_vat", header: [{ text: "중개이용료(부가세)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_bm_rd_fee_supply_amt", header: [{ text: "배민라이더스 이용료(공급가액)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_bm_rd_fee_vat", header: [{ text: "배민라이더스 이용료(부가세)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "comm_vat_included", header: [{ text: "차감금액(수수료VAT포함)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "settle_amt", header: [{ text: "정산금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "user_nm", header: [{ text: "사용자명", align: "center" }] },
		{ minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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


//이마트24앱
const emart24Grid = {
	columns: [
		{ minWidth: 60, maxWidth: 60, id: "rn", header: [{ text: "순번", align: "center" }] },
		{ minWidth: 100, id: "division", header: [{ text: "구분", align: "center" }] },
		{ minWidth: 100, id: "ord_date", header: [{ text: "주문일자", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "store_nm", header: [{ text: "점포명", align: "center" }] },
		{ minWidth: 100, id: "store_id", header: [{ text: "점포코드", align: "center" }] },
		{ minWidth: 100, id: "ch_cd", header: [{ text: "채널사코드", align: "center" }] },		
		{ minWidth: 100, id: "ord_id", header: [{ text: "주문ID", align: "center" }] },
		{ minWidth: 100, id: "ch_ord_no", header: [{ text: "이마트앱주문번호", align: "center" }] },
		{ minWidth: 100, id: "ch_ord_dt_no", header: [{ text: "상세주문번호", align: "center" }] },
		{ minWidth: 100, id: "set_emart24_dv_seq", header: [{ text: "이마트앱배달 시퀀스", align: "center" }], type: "number", format: "#,#" },
		{
			minWidth: 100, id: "ord_time", header: [{ text: "주문일시", align: "center" }],
			template: function(text) {
				return datetimeFormat(text);
			}
		},
		{ minWidth: 100, id: "sale_item_amt", header: [{ text: "상품가 합계", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_delv_fee", header: [{ text: "배달료", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "sale_pay_amt", header: [{ text: "결제금액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 120, id: "cms_vat_included", header: [{ text: "수수료(VAT포함)", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "settle_pay_amt", header: [{ text: "지급액", align: "center" }], type: "number", format: "#,#" },
		{ minWidth: 100, id: "note", header: [{ text: "비고", align: "center" }] },
		{ minWidth: 100, id: "user_nm", header: [{ text: "등록자명", align: "center" }] },
		{ minWidth: 100, id: "reg_date", header: [{ text: "등록일시", align: "center" }],
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





$(document).ready(function() {
//	오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
		$('.shide_div').removeClass('show_div');
		$('#cancel_reason').val('');
	});

	// 오늘, 1주일, 1개월
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

	$('#franchise_cd').on("change", function() {
		channelChange();
	});
	$('#channel').on("change", function() {
		channelChange();
	});

	grid = new dhx.Grid("grid", ygydGrid);

	$('#ch_view').on("change", function() {
		$('.paging').empty();
		if ($('#ch_view option:selected').val() == "0101") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", ygydGrid);

		} else if ($('#ch_view option:selected').val() == "0102") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", ygypGrid);

		} else if ($('#ch_view option:selected').val() == "02") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", kakaoGrid);

		} else if ($('#ch_view option:selected').val() == "03") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", naverGrid);

		} else if ($('#ch_view option:selected').val() == "04") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", kisGrid);
			
		} else if ($('#ch_view option:selected').val() == "07") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", baeMinGrid);
		}else if ($('#ch_view option:selected').val() == "08") {
			if (grid) {
				grid.destructor();
			}
			grid = new dhx.Grid("grid", emart24Grid);
		}

	});
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
	const frccd = $('#ch_view option:selected').val();

	let path = 'ExcelDownload_ORD_' + frccd;

	const params = {
		/*scd,
		ono,
		chono,
		sdate,
		edate*/
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
//샘플 엑셀 다운
function ExcelDown_settle() {
	const channel = $('#channel option:selected').val() + $('#addsample_selectbox').val();
//	const frccd = $('#ch_view option:selected').val();
//	const sfrccd = $('#channel').val() + $('#addsample_selectbox').val();
	const addnm = $('#addsample_selectbox option:checked').val() == "" ? "" : $('#addsample_selectbox option:checked').text();
	const frcnm = $('#channel option:checked').text() + addnm;
	if (channel == "01") {
		swal({
			text: "추가옵션을 선택해주세요."
		});

		return;
	}
	let path = 'Excel_Sample_Download_ORD_'+ channel ; //sfrccd ;
	

	const params = {
		channel
	}
	post(path, params);
}

//검색 쿼리
function search_go(num) {
//	const sdate = $("#sdate").val().replaceAll('-', '');
//	const edate = $("#edate").val().replaceAll('-', '');
	const tmonth = $("#tmonth").val().replaceAll(/[^0-9]/g, '');
	const ch_view = $('#ch_view option:selected').val();
	

//	if (sdate > edate) {
//		swal(
//			"시작일이 종료일보다 클 수 없습니다."
//		).then(function() {
//			$('#sdate').focus();
//		});
//
//		return
//	}input~input 방식일때 사용

	$.blockUI({
		message: '<img src="./resources/img/loading_spiner.png"/>',
		css: {
			backgroundColor: 'rgba(0,0,0,0.0)',
			color: '#000000',
			border: '0px solid #a00'
		}
	});

	/*const scd = $('#scd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();
	const ordst = $('#ordst').val();*/
	const pageNum = num;

	$.ajax({
		url: 'getOrdFeeList',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			ch_view,
			/*scd,
			ono,
			chono,
			ordst,
			sdate,
			edate,*/
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
	const frccd = $('#franchise_cd').val() + $('#add_selectbox').val();
	const addnm = $('#add_selectbox option:checked').val() == "" ? "" : $('#add_selectbox option:checked').text();
	const frcnm = $('#franchise_cd option:checked').text() + addnm;
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
	
	if (frccd == "01") {
		swal({
			text: "추가옵션을 선택해주세요."
		});

		return;
	}
	swal({
		icon: "warning",
		title: "정말 업로드 하시겠습니까?",
		text: `'${$('.filebox .upload-hidden')[0].files[0].name}' 정산 엑셀을 '${frcnm}' 에 업로드합니다.`,
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
				url: 'ExcelUpload_ORD_' + frccd,
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
						$('#ch_view').val(frccd).trigger('change');
						//search_go();

						if (data.excel) {
							const url = "ExcelDownload_ORD_ERR" + frccd;
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



// 업로드 채널 선택
function channelChange() {
	const selectItem = $("#franchise_cd").val();
	const selectSampleItem = $("#channel").val();

	if (selectItem == "01") {
		$('#add_selectbox').append("<option class='upload_option temp_option' value='01'>배달</option><option class='upload_option temp_option' value='02'>픽업</option>");
	}else if(selectSampleItem == "01"){
		$('#addsample_selectbox').append("<option class='upload_option temp_option' value='01'>배달</option><option class='upload_option temp_option' value='02'>픽업</option>");
	}
	else {
		$('.temp_option').remove();
	}
}

