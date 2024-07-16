package oms.kisvan.emart.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.StoreDAO;
import oms.kisvan.emart.model.dto.EM_CHANNEL_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_STORE_OPERATION_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 매장 서비스
 */
@Service
public class StoreService {

	@Setter(onMethod_ = @Autowired)
	private StoreDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 매장 검색
	public List<EM_COMPANY_MASTER_OMS> storeSearch(Map<String, Object> paramMap) {
		return dao.storeSearch(paramMap, sqlSession);
	}

	// 매장 상태 리스트
	public List<EM_REFERENCE_CODE_OMS> GetStoreState() {
		return dao.GetStoreState(sqlSession);
	}

	// ##################Detail0101##################
	// 가맹점 정보 불러오기
	public EM_COMPANY_MASTER_OMS getStoreDetailData(String stocd) {

		return dao.getStoreDetailData(stocd, sqlSession);
	}

	// 매장 운영 정보 불러오기
	public EM_STORE_OPERATION_OMS getStoreOperationDetailData(String stocd) {

		return dao.getStoreOperationDetailData(stocd, sqlSession);
	}

	// 배달 대행 운영 정보 불러오기
	public List<EM_COMPANY_MASTER_OMS> getDeliveryChannelOperationDetailData(String stocd) {

		return dao.getDeliveryChannelOperationDetailData(stocd, sqlSession);
	}

	// 채널 매장 정보 불러오기
	public List<EM_CHANNEL_STORE_OPERATION_OMS> getChannelStoreOperation(String stocd) {
		return dao.getChannelStoreOperation(stocd, sqlSession);
	}

	// 검색된 매장 개수
	public int getSearchStoreTotal(Map<String, Object> paramMap) {
		return dao.getSearchStoreTotal(paramMap, sqlSession);
	}

	// 채널사 전송 값
	public List<EM_REFERENCE_CODE_OMS> GetChannelFlag() {
		return dao.GetChannelFlag(sqlSession);
	}

	// 배달사 리스트 가져오기
	public List<EM_COMPANY_MASTER_OMS> GetDeliveryList() {
		return dao.GetDeliveryList(sqlSession);
	}

}
