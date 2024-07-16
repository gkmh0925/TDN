package oms.kisvan.emart.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.CategoryDAO;
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
 * @Explan : 상품 서비스
 */
@Service
public class CategoryService {

	@Setter(onMethod_ = @Autowired)
	private CategoryDAO dao;
	
	

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 상품 검색
	public List<EM_CATEGORY_MASTER_OMS> categorySearch(Map<String, Object> paramMap) {
		
		return dao.categorySearch(paramMap, sqlSession);
	}
	
	
	// 상품 검색
	public List<EM_CATEGORY_MASTER_OMS> categoryLv2Search(Map<String, Object> paramMap) {
		return dao.categoryLv2Search(paramMap, sqlSession);
	}

	// 행사 타입 가져오기
	public List<EM_REFERENCE_CODE_OMS> GetPolicyCode() {

		return dao.GetPolicyCode(sqlSession);
	}

	// 매장 내 상품 분류 가져오기
	public List<EM_CATEGORY_MASTER_OMS> GetCategory() {
		return dao.GetCategory(sqlSession);
	}
	
	// 한개 가져오기
		public EM_CATEGORY_MASTER_OMS GetCtgrCd(String CTGR_CD) {
			return dao.GetCtgrCd(CTGR_CD, sqlSession);
		}
		
		public EM_CATEGORY_MASTER_OMS GetCtgrInfo(HashMap<String, String> paramMap) {
			return dao.GetCtgrInfo(paramMap, sqlSession);
		}
		
	// 전체 가져오기
	public List<EM_CATEGORY_MASTER_OMS> GetAllCpnCd() {
		return dao.GetAllCpnCd(sqlSession);
	}		
	
	
	public int CategoryInsert(EM_CATEGORY_MASTER_OMS tu) {
		return dao.CategoryInsert(sqlSession, tu);
	}
	
	public int CategoryLv2Insert(EM_CATEGORY_MASTER_OMS tu) {
		return dao.CategoryLv2Insert(sqlSession, tu);
	}
		
		
				
		
				

	// 검색된 상품 개수
	public int getSearchCategoryTotal(Map<String, Object> paramMap) {
		return dao.getSearchCategoryTotal(paramMap, sqlSession);
	}
				
	// 검색된 상품 개수
	public int getSearchCategoryLv2Total(Map<String, Object> paramMap) {
		return dao.getSearchCategoryLv2Total(paramMap, sqlSession);
	}

	// 카테고리 cd 이름으로 조회, 가져오기
	public String getCategoryCode(String ctgrNm) {
		return dao.getCategoryCode(ctgrNm, sqlSession);
	}


	// 매장 내 상품 분류 가져오기
	public List<EM_COMPANY_MASTER_OMS> getStoreOrder(Map<String, Object> paramMap) {
		return dao.getStoreOrder(paramMap, sqlSession);
	}
	
	
	// 중복확인
	public int isDuplicateCategory(EM_CATEGORY_MASTER_OMS uao) {
		return dao.isDuplicateCategory(uao, sqlSession);
	}
	
	
	//!@@0701 yh
	/**
	 * @Author : 김영현
	 * @Date : 2024. 06. 29.
	 * @Explan : 주문사 매장 카테고리 리스트 가져오기
	 */
	public List<EM_CATEGORY_MASTER_OMS> getOrderCompanyStoreCategoryList(Map<String, Object> paramMap) {
		return dao.getOrderCompanyStoreCategoryList(paramMap, sqlSession);
	}	
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 06. 29.
	 * @Explan : 주문사 상품 카테고리 리스트 가져오기
	 */
	public List<EM_CATEGORY_MASTER_OMS> getOrderCompanyProductCategoryList(Map<String, Object> paramMap) {
		return dao.getOrderCompanyProductCategoryList(paramMap, sqlSession);
	}		
	
}
