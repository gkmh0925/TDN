package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_POS_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_PU_ORD_OMS;

@Repository
public class PosDAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 레퍼런스 코드 가져오기
	public List<EM_REFERENCE_CODE_OMS> getReferenceCode(String stat) {
		return sqlSession.selectList("pos.getReferenceCode", stat);
	}

	// 포스 데이터 가져오기
	public List<EM_SETTLE_POS_ORD_OMS> getPosList(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("pos.getPosList", paramMap);
	}

	// 포스<>중계 대사 데이터 가져오기
	public List<EM_COMPANY_MASTER_OMS> getCompareData_Relay(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("pos.getCompareData_Relay", paramMap);
	}

	// 중계<>주문채널사 대사 데이터 가져오기
	public List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getCompareData_Order(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("pos.getCompareData_Order", paramMap);
	}

	// 중계<>배달대행사 대사 데이터 가져오기
	public List<EM_SETTLE_YOGIYO_PU_ORD_OMS> getCompareData_Delivery(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("pos.getCompareData_Delivery", paramMap);
	}

	/* ################## 페이징 ##################### */
	// 검색된 포스 데이터 개수 가져오기
	public int getPosList_CNT(HashMap<String, Object> paramMap) {
		return sqlSession.selectOne("pos.getPosList_CNT", paramMap);
	}
}
