package oms.kisvan.emart.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.SearchDAO;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 검색 서비스
 */
@Service
public class SearchService {

	@Setter(onMethod_ = @Autowired)
	private SearchDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 매장 상세 검색
	public List<EM_COMPANY_MASTER_OMS> getSearchStore(Criteria cri) {

		return dao.getSearchStore(cri, sqlSession);
	}
	
	// 매장 상세 검색 + cpncd
	public List<EM_COMPANY_MASTER_OMS> getSearchStoreCpnType(Criteria cri) {

		return dao.getSearchStoreCpnType(cri, sqlSession);
	}

	// 채널사 검색
	public List<EM_COMPANY_MASTER_OMS> getSearchChannel(Criteria cri) {

		return dao.getSearchChannel(cri, sqlSession);
	}

	// 배달 대행 검색
	public List<EM_COMPANY_MASTER_OMS> getSearchDelivery(Criteria cri) {

		return dao.getSearchDelivery(cri, sqlSession);
	}

	// 상품 검색
	public List<EM_MENU_MASTER_OMS> getSearchProduct(Criteria cri) {

		return dao.getSearchProduct(cri, sqlSession);
	}

	// 검색된 매장 개수
	public int getSearchStoreTotal(Criteria cri) {

		return dao.getSearchStoreTotal(cri, sqlSession);
	}
	
	// 검색된 매장 개수 +cpn
		public int getSearchStoreCpnTypeTotal(Criteria cri) {

			return dao.getSearchStoreCpnTypeTotal(cri, sqlSession);
		}


	// 검색된 주문채널 개수 확인
	public int getSearchChannelTotal(Criteria cri) {

		return dao.getSearchChannelTotal(cri, sqlSession);
	}

	// 검색된 배달사 개수
	public int getSearchDeliveryTotal(Criteria cri) {

		return dao.getSearchDeliveryTotal(cri, sqlSession);
	}

	// 검색된 상품 개수
	public int getSearchProductTotal(Criteria cri) throws Exception {

		return dao.getSearchProductTotal(cri, sqlSession);
	}

}