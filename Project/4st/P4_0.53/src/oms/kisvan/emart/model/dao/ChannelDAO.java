package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_CMS_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 주문채널 Data Access Object
 */
@Repository
public class ChannelDAO {

	// 주문채널사 검색
	public List<EM_COMPANY_MASTER_OMS> ChannelSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.ChannelSearch", paramMap);
	}

	// 레퍼런스 코드 가져오기 (업체 상태)
	public List<EM_REFERENCE_CODE_OMS> GetCPNState(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.GetCPNState");
	}
	// 채널사 레퍼런스 코드 가져오기 (CMS_APPLY_TYPE_ORDER 가져오기)
	  public List<EM_REFERENCE_CODE_OMS>
	  GetCMS_CARD_APPLY_TYPE_ORDER(SqlSessionTemplate sqlSession,HashMap<String, Object> paramMap) { return
	  sqlSession.selectList("Channel.GetCMS_CARD_APPLY_TYPE_ORDER",paramMap); }
	 
	// 배달사 레퍼런스 코드 가져오기 (CMS_APPLY_TYPE_ORDER 가져오기)
	public List<EM_REFERENCE_CODE_OMS> GetCMS_APPLY_TYPE_DELIVERY(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.GetCMS_APPLY_TYPE_DELIVERY");
	}
	// 레퍼런스 코드 가져오기 (CMS_CALC_TYPE 가져오기)
	public List<EM_REFERENCE_CODE_OMS> GetCMS_CALC_TYPE(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.GetCMS_CALC_TYPE");
	}

	// 주문채널사 기본상세
	public EM_COMPANY_MASTER_OMS getORDCPN_Detail(String CPN_CD, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("Channel.getORDCPN_Detail", CPN_CD);
	}
	// 주문채널사 중계 수수료부분 상세--
	public List<EM_CMS_OPERATION_OMS> getFEE_ORDCPN_Detail(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.getFEE_ORDCPN_Detail", paramMap);
	}
	// 주문채널사 카드 수수료부분 상세
	public List<EM_CMS_OPERATION_OMS> getCARD_FEE_ORDCPN_Detail(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("Channel.getCARD_FEE_ORDCPN_Detail", paramMap);
	}
	
	// 배송채널사 상세
	public EM_COMPANY_MASTER_OMS getDLVCPN_Detail(String CPN_CD, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("Channel.getDLVCPN_Detail", CPN_CD);
	}
	// 배달대행사수수료부분 상세--
		public List<EM_CMS_OPERATION_OMS> getFEE_DELIVERY_Detail(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
			return sqlSession.selectList("Channel.getFEE_DELIVERY_Detail", paramMap);
		}

	// 주문채널사 합계
	public int getOrderChannelTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("Channel.getOrderChannelTotal", paramMap);
	}
	// 배달대행사 합계
	public int getOrderDeliveryTotal(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("Channel.getOrderDeliveryTotal", paramMap);
	}

	// ################################ 채널 등록 ##############################
	// 배달, 주문채널사 수정
//	public Object setUpdateChannelData(HashMap<String, Object> map, SqlSessionTemplate sqlSession) {
//		return sqlSession.update("Channel.setUpdateChannelData", map);
//	}
//	public Object setUpdateDeliveryChannelData(HashMap<String, Object> map, SqlSessionTemplate sqlSession) {
//		return sqlSession.update("Channel.setUpdateDeliveryChannelData", map);
//	}

	// 배달, 주문채널사 등록
	public Object setInsetChannelData(HashMap<String, Object> map, SqlSessionTemplate sqlSession) {
		return sqlSession.insert("Channel.setInsetChannelData", map);
	}
	// 주문채널사 수수료 등록
//	public Object setInsetChannelDataFee(HashMap<String, Object> map, SqlSessionTemplate sqlSession) {
//		return sqlSession.insert("Channel.setInsetChannelDataFee", map);
//	}
	// 주문채널사 수수료 등록
	public Object setInsetChannelDataFee(SqlSessionTemplate sqlSession, List<HashMap<String, Object>> list) {
		return sqlSession.insert("Channel.setInsetChannelDataFee", list);
	}
	// 배달대행사 수수료 등록
	public Object setInsetDeliveryDataFee(SqlSessionTemplate sqlSession, List<HashMap<String, Object>> list) {
		return sqlSession.insert("Channel.setInsetDeliveryDataFee", list);
	}
}
