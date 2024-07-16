package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_DELIVERY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_BAEMIN_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_BAROGO_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_CHAINLOGIS_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_DEALVER_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_EMART24_DV_PU_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_KAKAO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_KISAPP_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_LOGIALL_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_NAVER_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_SPIDOR_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_VROONG_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_PU_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_RELAY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.COMPARE_CAL_RELAY;
 

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 정산 Data Access Object
 */
@Repository
public class CalcDAO {
	// 채널사 리스트 가져오기
	public List<EM_COMPANY_MASTER_OMS> getChannelList(SqlSessionTemplate sqlSession, String stat) {
		return sqlSession.selectList("calc.getChannelList", stat);
	}
	// 중계수수료 가져오기
	public List<EM_RELAY_TOTAL_OMS> getCalculateList_RELAY(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_RELAY", paramMap);
	}
		
	// 요기요 배달 가져오기
	public List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getCalculateList_YGYD(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_YGYD", paramMap);
	}

	// 요기요 픽업 가져오기
	public List<EM_SETTLE_YOGIYO_PU_ORD_OMS> getCalculateList_YGYP(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_YGYP", paramMap);
	}

	// 네이버 가져오기
	public List<EM_SETTLE_NAVER_ORD_OMS> getCalculateList_NAVER(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_NAVER", paramMap);
	}

	// 카카오 가져오기
	public List<EM_SETTLE_KAKAO_DV_ORD_OMS> getCalculateList_KAKAO(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_KAKAO", paramMap);
	}

	// KIS(Emart24) 가져오기
	public List<EM_SETTLE_KISAPP_ORD_OMS> getCalculateList_KIS(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_KIS", paramMap);
	}
	
	// 배달의민족 가져오기
	public List<EM_SETTLE_BAEMIN_DV_ORD_OMS> getCalculateList_BAEMIN(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_BAEMIN", paramMap);
	}
	// 이마트24(인앱) 가져오기
	public List<EM_SETTLE_EMART24_DV_PU_OMS> getCalculateList_EMART24(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_EMART24", paramMap);
	}
	
	

	// 부릉 가져오기
	public List<EM_SETTLE_VROONG_DV_OMS> getCalculateList_VROONG(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_VROONG", paramMap);
	}

	// 바로고 가져오기
	public List<EM_SETTLE_BAROGO_DV_OMS> getCalculateList_BAROGO(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_BAROGO", paramMap);
	}

	// 생각대로 가져오기
	public List<EM_SETTLE_LOGIALL_DV_OMS> getCalculateList_LOGIALL(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_LOGIALL", paramMap);
	}

	// 딜버 가져오기
	public List<EM_SETTLE_DEALVER_DV_OMS> getCalculateList_DEALVER(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_DEALVER", paramMap);
	}

	// 스파이더 크래프트 가져오기
	public List<EM_SETTLE_SPIDOR_DV_OMS> getCalculateList_SPIDOR(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_SPIDOR", paramMap);
	}
	
	// 체인로지스 가져오기
	public List<EM_SETTLE_CHAINLOGIS_DV_OMS> getCalculateList_CHAIN(Map<String, Object> paramMap,
			SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculateList_CHAIN", paramMap);
	}

	// 배달정산합계 가져오기
	public List<EM_DELIVERY_TOTAL_OMS> getCalculate_TOTAL(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getCalculate_TOTAL", paramMap);
	}

	// ####################################### 페이징 COUNT 가져오기
	// #######################################
	
	public int getCalculateList_RELAY_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_RELAY_cnt", paramMap);
	}
	
	public int getCalculateList_YGYD_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_YGYD_cnt", paramMap);
	}

	public int getCalculateList_YGYP_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_YGYP_cnt", paramMap);
	}

	public int getCalculateList_NAVER_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_NAVER_cnt", paramMap);
	}

	public int getCalculateList_KAKAO_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_KAKAO_cnt", paramMap);
	}

	public int getCalculateList_KIS_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_KIS_cnt", paramMap);
	}
	
	public int getCalculateList_BAEMIN_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_BAEMIN_cnt", paramMap);
	}
	public int getCalculateList_EMART24_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_EMART24_cnt", paramMap);
	}

	public int getCalculateList_VROONG_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_VROONG_cnt", paramMap);
	}

	public int getCalculateList_BAROGO_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_BAROGO_cnt", paramMap);
	}

	public int getCalculateList_LOGIALL_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_LOGIALL_cnt", paramMap);
	}

	public int getCalculateList_DEALVER_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_DEALVER_cnt", paramMap);
	}

	public int getCalculateList_SPIDOR_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_SPIDOR_cnt", paramMap);
	}
	
	public int getCalculateList_CHAIN_CNT(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("calc.getCalculateList_CHAIN_cnt", paramMap);
	}

	 

	// 포스<>중계 대사 데이터 가져오기
	public List<COMPARE_CAL_RELAY> getRelayCompareData_Relay(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("calc.getRelayCompareData_Relay", paramMap);
	} 
}
