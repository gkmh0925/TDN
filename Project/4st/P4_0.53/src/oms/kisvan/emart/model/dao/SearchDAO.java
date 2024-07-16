package oms.kisvan.emart.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 검색 Data Access Object
 */
@Repository
public class SearchDAO {

	// 매장 검색
	public List<EM_COMPANY_MASTER_OMS> getSearchStore(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchStore", cri);
	}
	
	// 매장 검색 + cpncd
	public List<EM_COMPANY_MASTER_OMS> getSearchStoreCpnType(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.getSearchStoreCpnType", cri);
	}

	// 채널사 검색
	public List<EM_COMPANY_MASTER_OMS> getSearchChannel(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchChannel", cri);
	}

	// 배달 대행 검색
	public List<EM_COMPANY_MASTER_OMS> getSearchDelivery(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchDelivery", cri);
	}

	// 상품 검색
	public List<EM_MENU_MASTER_OMS> getSearchProduct(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("search.SearchProduct", cri);
	}

	// ###################################PAGING#####################################
	// StoreSearch 총 게시글 개수 확인
	public int getSearchStoreTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchStoreTotal", cri);
	}
	
	// StoreSearch 총 게시글 개수 확인 +cpn
		public int getSearchStoreCpnTypeTotal(Criteria cri, SqlSessionTemplate sqlSession) {

			return sqlSession.selectOne("search.getSearchStoreCpnTypeTotal", cri);
		}

	// ChannelSearch 총 게시글 개수 확인
	public int getSearchChannelTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchChannelTotal", cri);
	}

	// DeliverySearch 총 게시글 개수 확인
	public int getSearchDeliveryTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchDeliveryTotal", cri);
	}

	// ProductSearch 총 게시글 개수 확인
	public int getSearchProductTotal(Criteria cri, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("search.getSearchProductTotal", cri);
	}

}
