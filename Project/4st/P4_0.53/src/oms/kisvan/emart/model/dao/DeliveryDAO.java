package oms.kisvan.emart.model.dao;


//!@@0701 new

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_CATEGORY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_ORDER_HISTORY_OMS;
import oms.kisvan.emart.model.dto.EM_ORDER_MENU_OMS;
import oms.kisvan.emart.model.dto.EM_PRODUCT_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EmProductMasterOmsDTO;
import oms.kisvan.emart.model.dto.EmProductMasterOmsEntity;
import oms.kisvan.emart.model.dto.TB_DELIVERY_STATE;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;

/**
 * @Author : 김영현
 * @Date : 2024. 06. 27.
 * @Explan : 배달 서비스 상품 Data Access Object
 */
@Repository
public class DeliveryDAO {

	// 배달 서비스 상품 검색
	public List<TB_DELIVERY_STATE> selectDeliveryProductList(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("delivery.selectDeliveryProductList", paramMap);
	}
	
	// 검색된 배달 서비스 상품 총 개수 가져오기
	public int selectSearchDeliveryProductTotalCnt(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {

		return sqlSession.selectOne("delivery.selectSearchDeliveryProductTotalCnt", paramMap);
	}
	
	// 검색된 배달 서비스 상품 총 개수 가져오기
	public List<EM_REFERENCE_CODE_OMS> selectPrdStateReferenceList(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {

		return sqlSession.selectList("delivery.selectPrdStateReferenceList", paramMap);
	}
	
	public int insertDeliveryProduct(SqlSessionTemplate sqlSession, EmProductMasterOmsDTO product) {
		
		return sqlSession.insert("delivery.insertDeliveryProduct", product);
	}
	
	//join 없는 상품테이블 select 
	public EmProductMasterOmsEntity selectEmProductMasterOmsInfo(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {
		
		return sqlSession.selectOne("delivery.selectEmProductMasterOmsInfo", paramMap);		
	}
	
	//join한 상품 select
	public EmProductMasterOmsDTO selectDeliveryProductInfo(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {
		
		return sqlSession.selectOne("delivery.selectDeliveryProductInfo", paramMap);		
	}
	
	public int deleteEmProductMasterOmsByCpnCdAndPrdCd(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {
		
		return sqlSession.delete("delivery.deleteEmProductMasterOms", paramMap);
	}
	
	public List<EM_CATEGORY_MASTER_OMS> selectStoreCategoryListByChCd(SqlSessionTemplate sqlSession, String chCd) {
		
		return sqlSession.selectList("delivery.selectStoreCategoryListByChCd", chCd);
	}
	
	public List<EM_CATEGORY_MASTER_OMS> selectPrdCategoryListByChCd(SqlSessionTemplate sqlSession, String chCd) {
		
		return sqlSession.selectList("delivery.selectPrdCategoryListByChCd", chCd);
	}
	
	public List<EM_REFERENCE_CODE_OMS> selectEmReferenceCodeOmsByGroupCd(SqlSessionTemplate sqlSession, String groupCd) {
		
		return sqlSession.selectList("delivery.selectEmReferenceCodeOmsByGroupCd", groupCd);
	}
	
	public int updateEmProductMasterOms(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {
		
		return sqlSession.update("delivery.updateEmProductMasterOms", paramMap);
	}

}
