package oms.kisvan.emart.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_CHANNEL_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_STORE_OPERATION_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 매장 Data Access Object
 */
@Repository
public class StoreDAO {

	// 매장 데이터 검색
	public List<EM_COMPANY_MASTER_OMS> storeSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("store.storeSearch", paramMap);
	}

	// 매장 상태 가져오기
	public List<EM_REFERENCE_CODE_OMS> GetStoreState(SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("store.GetReferenceCode");
	}

	// 가맹점 정보 불러오기
	public EM_COMPANY_MASTER_OMS getStoreDetailData(String stocd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("store.storeDetail", stocd);
	}

	// 매장 운영 정보 불러오기
	public EM_STORE_OPERATION_OMS getStoreOperationDetailData(String stocd, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("store.storeOpertaionDetail", stocd);
	}

	// 배달 대행 운영 정보 불러오기
	public List<EM_COMPANY_MASTER_OMS> getDeliveryChannelOperationDetailData(String stocd,
			SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("store.DeliveryChannelOperationDetail", stocd);
	}

	// 채널 매장 정보 불러오기
	public List<EM_CHANNEL_STORE_OPERATION_OMS> getChannelStoreOperation(String stocd, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("store.getChannelStoreOperation", stocd);
	}


	// 검색 매장 개수 가져오기
	public int getSearchStoreTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("store.getSearchStoreTotal", paramMap);
	}

	// 채널사 전송 값 가져오기
	public List<EM_REFERENCE_CODE_OMS> GetChannelFlag(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("store.GetChannelFlag");
	}

	// 매장별 배달사 리스트 가져오기
	public List<EM_COMPANY_MASTER_OMS> GetDeliveryList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("store.GetDeliveryList");
	}

}
