package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_CATEGORY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_OPTION_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.TB_STOCK_CHECK;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 상품 Data Access Object
 */
@Repository
public class CategoryDAO {

	// 상품 검색
	public List<EM_CATEGORY_MASTER_OMS> categorySearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("category.categorySearch", paramMap);
	}
	
	// 상품 검색
		public List<EM_CATEGORY_MASTER_OMS> categoryLv2Search(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
			return sqlSession.selectList("category.categoryLv2Search", paramMap);
		}

	// 행사 타입 가져오기
	public List<EM_REFERENCE_CODE_OMS> GetPolicyCode(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("category.GetPolicyCode");
	}

	// 매장 내 상품 분류 가져오기
	public List<EM_CATEGORY_MASTER_OMS> GetCategory(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("category.GetCategory");
	}
	
	// 
		public EM_CATEGORY_MASTER_OMS GetCtgrCd(String CTGR_CD, SqlSessionTemplate sqlSession) {
			return sqlSession.selectOne("category.GetCtgrCd", CTGR_CD);
		}
		
		public EM_CATEGORY_MASTER_OMS GetCtgrInfo(HashMap<String, String> paramMap, SqlSessionTemplate sqlSession) {
			return sqlSession.selectOne("category.GetCtgrInfo", paramMap);
		}

		public List<EM_CATEGORY_MASTER_OMS> GetAllCpnCd(SqlSessionTemplate sqlSession) {
			return sqlSession.selectList("category.GetAllCpnCd");
		}
		
		// 카테고리 등록
		public int CategoryInsert(SqlSessionTemplate sqlSession, EM_CATEGORY_MASTER_OMS tu) {
			return sqlSession.insert("category.categoryInsert", tu);
		}
		
		public int CategoryLv2Insert(SqlSessionTemplate sqlSession, EM_CATEGORY_MASTER_OMS tu) {
			return sqlSession.insert("category.categoryLv2Insert", tu);
		}
		
		

// 검색된 상품 총 개수 가져오기
	public int getSearchCategoryTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("category.getSearchCategoryTotal", paramMap);
	}
		
		public int getSearchCategoryLv2Total(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

			return sqlSession.selectOne("category.getSearchCategoryLv2Total", paramMap);
		}

// 카테고리 코드 가져오기
	public String getCategoryCode(String ctgrNm, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("category.getCategoryCode", ctgrNm);
	}


	public List<EM_COMPANY_MASTER_OMS> getStoreOrder(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("category.getStoreOrder");
	}

	
	public int isDuplicateCategory(EM_CATEGORY_MASTER_OMS uao, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("category.isDuplicateCategory", uao);
	}
	
	//!@@0701 yh
	/**
	 * @Author : 김영현
	 * @Date : 2024. 06. 28.
	 * @Explan : 주문사 매장 카테고리 리스트 가져오기
	 */
	public List<EM_CATEGORY_MASTER_OMS> getOrderCompanyStoreCategoryList(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("category.getOrderCompanyStoreCategoryList", paramMap);
	}
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 06. 29.
	 * @Explan : 주문사 상품 카테고리 리스트 가져오기
	 */
	public List<EM_CATEGORY_MASTER_OMS> getOrderCompanyProductCategoryList(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("category.getOrderCompanyProductCategoryList", paramMap);
	}
}
