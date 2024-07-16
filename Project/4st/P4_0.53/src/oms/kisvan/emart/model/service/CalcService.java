package oms.kisvan.emart.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.CalcDAO;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_DELIVERY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.EM_RELAY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.COMPARE_CAL_RELAY;


/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 정산 서비스
 */
@Service
public class CalcService {

	@Setter(onMethod_ = @Autowired)
	private CalcDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 채널사 리스트 가져오기
	public List<EM_COMPANY_MASTER_OMS> getChannelList(String stat) {
		return dao.getChannelList(sqlSession, stat);
	}
	
	public List<EM_RELAY_TOTAL_OMS> getCalculate_RELAY_LIST(Map<String, Object> paramMap) {
		List<EM_RELAY_TOTAL_OMS> calcList = new ArrayList<>();
		calcList = dao.getCalculateList_RELAY(paramMap, sqlSession);
		return calcList;
	}
	
	
	// 주문채널사 정산 엑셀 리스트 가져오기
	public List<?> getCalculate_ORD_List(Map<String, Object> paramMap) {
		String ch_view = (String) paramMap.get("ch_view");

		List<?> calcList = new ArrayList<>();

		switch (ch_view) {
		// 요기요 배달
		case "0101":
			calcList = dao.getCalculateList_YGYD(paramMap, sqlSession);
			break;
		// 요기요 픽업
		case "0102":
			calcList = dao.getCalculateList_YGYP(paramMap, sqlSession);
			break;
		// 카카오
		case "02":
			calcList = dao.getCalculateList_KAKAO(paramMap, sqlSession);
			break;
		// 네이버
		case "03":
			calcList = dao.getCalculateList_NAVER(paramMap, sqlSession);
			break;
		// KIS
		case "04":
			calcList = dao.getCalculateList_KIS(paramMap, sqlSession);
			break;
		// 배달의민족
		case "07":
			calcList = dao.getCalculateList_BAEMIN(paramMap, sqlSession);
			break;
		//이마트24앱
		case "08":
			calcList = dao.getCalculateList_EMART24(paramMap, sqlSession);
			break;
	}

		return calcList;
	}
	
	public int getCalculate_RELAY_CNT(Map<String, Object> paramMap) {
		
		int cnt = dao.getCalculateList_RELAY_CNT(paramMap, sqlSession);
		
		return cnt;
	
	}

	// 정산 엑셀 리스트 행 개수
	public int getCalculate_ORD_CNT(Map<String, Object> paramMap) {

		String ch_view = (String) paramMap.get("ch_view");

		int cnt = 0;

		switch (ch_view) {
		case "0101":
			cnt = dao.getCalculateList_YGYD_CNT(paramMap, sqlSession);
			break;
		case "0102":
			cnt = dao.getCalculateList_YGYP_CNT(paramMap, sqlSession);
			break;
		case "02":
			cnt = dao.getCalculateList_KAKAO_CNT(paramMap, sqlSession);
			break;
		case "03":
			cnt = dao.getCalculateList_NAVER_CNT(paramMap, sqlSession);
			break;
		case "04":
			cnt = dao.getCalculateList_KIS_CNT(paramMap, sqlSession);
			break;
		case "07":
			cnt = dao.getCalculateList_BAEMIN_CNT(paramMap, sqlSession);
			break;
		case "08":
			cnt = dao.getCalculateList_EMART24_CNT(paramMap, sqlSession);
			break;
		}
		return cnt;
	}

	// 배달대행사 정산 엑셀 리스트 가져오기
	public List<?> getCalculate_DV_List(Map<String, Object> paramMap) {
		String dv_view = (String) paramMap.get("dv_view");

		List<?> calcList = new ArrayList<>();

		switch (dv_view) {
		// 부릉
		case "01":
			calcList = dao.getCalculateList_VROONG(paramMap, sqlSession);
			break;
		// 바로고
		case "02":
			calcList = dao.getCalculateList_BAROGO(paramMap, sqlSession);
			break;
		// 생각대로
		case "03":
			calcList = dao.getCalculateList_LOGIALL(paramMap, sqlSession);
			break;
		// 딜버
		case "04":
			calcList = dao.getCalculateList_DEALVER(paramMap, sqlSession);
			break;
		// 스파이더
		case "05":
			calcList = dao.getCalculateList_SPIDOR(paramMap, sqlSession);
			break;
		// 체인로지스
		case "08":
			calcList = dao.getCalculateList_CHAIN(paramMap, sqlSession);
			break;
		}

		return calcList;
	}

	// 배달대행사 엑셀 행 개수 가져오기
	public int getCalculate_DV_CNT(Map<String, Object> paramMap) {

		String dv_view = (String) paramMap.get("dv_view");

		int cnt = 0;

		switch (dv_view) {
		case "01":
			cnt = dao.getCalculateList_VROONG_CNT(paramMap, sqlSession);
			break;
		case "02":
			cnt = dao.getCalculateList_BAROGO_CNT(paramMap, sqlSession);
			break;
		case "03":
			cnt = dao.getCalculateList_LOGIALL_CNT(paramMap, sqlSession);
			break;
		case "04":
			cnt = dao.getCalculateList_DEALVER_CNT(paramMap, sqlSession);
			break;
		case "05":
			cnt = dao.getCalculateList_SPIDOR_CNT(paramMap, sqlSession);
			break;
		case "08":
			cnt = dao.getCalculateList_CHAIN_CNT(paramMap, sqlSession);
			break;
		}
		return cnt;
	}

	// 배달정산총계 가져오기
	public List<EM_DELIVERY_TOTAL_OMS> getCalculate_TOTAL(Map<String, Object> paramMap) {
		return dao.getCalculate_TOTAL(paramMap, sqlSession);
	}
	
	

	public List<COMPARE_CAL_RELAY> getRelayCompareData( HashMap<String, Object > paramMap){
		List<COMPARE_CAL_RELAY> calcList = new ArrayList<>();
		//String ch_v =(String ) paramMap.get(paramMap);
		
		return  dao.getRelayCompareData_Relay(paramMap, sqlSession);
		
	}
	
	
}
