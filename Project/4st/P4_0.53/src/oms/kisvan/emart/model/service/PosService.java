package oms.kisvan.emart.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.PosDAO;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_POS_ORD_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 포스 서비스
 */
@Service
public class PosService {

	@Setter(onMethod_ = @Autowired)
	private PosDAO dao;

	// 레퍼런스 코드 리스트 가져오기
	public List<EM_REFERENCE_CODE_OMS> getReferenceCode(String stat) {
		return dao.getReferenceCode(stat);
	}

	// 포스 데이터 리스트 가져오기
	public List<EM_SETTLE_POS_ORD_OMS> getPosList(HashMap<String, Object> paramMap) {
		return dao.getPosList(paramMap);
	}

	// 대사 데이터 가져오기
	public List<?> getCompareData(HashMap<String, Object> paramMap) {
		String ch_view = (String) paramMap.get("comp_data");

		List<?> compareList = new ArrayList<>();

		switch (ch_view) {
		// 중계
		case "relay":
			compareList = dao.getCompareData_Relay(paramMap);
			break;
		// 주문채널
		case "order":
			compareList = dao.getCompareData_Order(paramMap);
			break;
		// 배달대행
		case "delivery":
			compareList = dao.getCompareData_Delivery(paramMap);
			break;
		}
		return compareList;
	}

	// 포스 데이터 리스트 총 개수
	public int getPosList_CNT(HashMap<String, Object> paramMap) {
		return dao.getPosList_CNT(paramMap);
	}
}
