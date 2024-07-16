package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.StoreMonthDetailsInfoDTO;
import oms.kisvan.emart.model.dto.StoreOrdNoDetailsInfoDTO;

@Repository
public class StoreCalculateDAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	//점별/주문번호별 상세 조회
	public List<StoreOrdNoDetailsInfoDTO> getStoreOrdNoDetailsInfo(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("pos.getStoreOrdNoDetailsInfo", paramMap);
	}
	
	/* ################## 페이징 ##################### */
	// 검색된 포스 데이터 개수 가져오기
	public int getPosList_CNT(HashMap<String, Object> paramMap) {
		return sqlSession.selectOne("pos.getStoreOrdNoDetailsCnt", paramMap);
	}

	public List<StoreMonthDetailsInfoDTO> getStoreMonthDetailsInfo(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("pos.getStoreMonthDetailsInfo", paramMap);
	}

	public int getStoreMonthDetailsCnt(HashMap<String, Object> paramMap) {
		return sqlSession.selectOne("pos.getStoreMonthDetailsCnt", paramMap);
	}
	
}
