package oms.kisvan.emart.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.ChannelDAO;
import oms.kisvan.emart.model.dto.EM_CMS_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 주문채널 서비스
 */
@Service
public class ChannelService {

	@Setter(onMethod_ = @Autowired)
	private ChannelDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 주문채널사 검색
	public List<EM_COMPANY_MASTER_OMS> ChannelSearch(Map<String, Object> paramMap) {
		return dao.ChannelSearch(paramMap, sqlSession);
	}

	// 주문채널사 상태
	public List<EM_REFERENCE_CODE_OMS> GetCPNState() {
		return dao.GetCPNState(sqlSession);
	}
	// 채널사 ,배달대행사 상세 수수료마스터 금액type
	public List<EM_REFERENCE_CODE_OMS> GetCMS_CALC_TYPE() {
		return dao.GetCMS_CALC_TYPE(sqlSession);
	}
////////////////////////////////채널사 마스터테이블///////////////////////////////////////////////////////
	// 주문채널사 기본 상세
	public EM_COMPANY_MASTER_OMS getORDCPN_Detail(String CPN_CD) {
		return dao.getORDCPN_Detail(CPN_CD, sqlSession);
	}
	// 주문채널사 중계수수료 상세--
	public List<EM_CMS_OPERATION_OMS> getFEE_ORDCPN_Detail(Map<String, Object> paramMap) {
		return dao.getFEE_ORDCPN_Detail(paramMap, sqlSession);
	}
	// 주문채널사 카드수수료 상세--
	public List<EM_CMS_OPERATION_OMS> getCARD_FEE_ORDCPN_Detail(Map<String, Object> paramMap) {
		return dao.getCARD_FEE_ORDCPN_Detail(paramMap, sqlSession);
	}
	// 채널사 상세 수수료마스터 수수료 CMS_APPLY_TYPE 
	public List<EM_REFERENCE_CODE_OMS> GetCMS_CARD_APPLY_TYPE_ORDER(HashMap<String, Object> paramMap) { 
		return dao.GetCMS_CARD_APPLY_TYPE_ORDER(sqlSession,paramMap); 
	}
////////////////////////////////배달대행사 마스터테이블/////////////////////////////////////////////////////////	
	// 배송채널사 상세
	public EM_COMPANY_MASTER_OMS getDLVCPN_Detail(String CPN_CD) {
		return dao.getDLVCPN_Detail(CPN_CD, sqlSession);
	}
	// 배달대행사 수수료상세--
	public List<EM_CMS_OPERATION_OMS> getFEE_DELIVERY_Detail(Map<String, Object> paramMap) {
		return dao.getFEE_DELIVERY_Detail(paramMap, sqlSession);
		
	}
	// 배달사 상세 수수료마스터 수수료  CMS_APPLY_TYPE
	public List<EM_REFERENCE_CODE_OMS> GetCMS_APPLY_TYPE_DELIVERY() {
		return dao.GetCMS_APPLY_TYPE_DELIVERY(sqlSession);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
	// 검색된 주문채널사 개수
	public int getOrderChannelTotal(Map<String, Object> paramMap) {
		return dao.getOrderChannelTotal(paramMap, sqlSession);
	}
	// 검색된 배달대행사 개수
	public int getOrderDeliveryTotal(Map<String, Object> paramMap) {
		return dao.getOrderDeliveryTotal(paramMap, sqlSession);
	}

	// ################################ 채널 등록 ##############################
// 배달, 채널사 수정
//	public Object setUpdateChannelData(HashMap<String, Object> map) {
//		return dao.setUpdateChannelData(map, sqlSession);
//	}
//	public Object setUpdateDeliveryChannelData(HashMap<String, Object> map) {
//		return dao.setUpdateDeliveryChannelData(map, sqlSession);
//	}

	// 배달, 채널사 등록
	public Object setInsetChannelData(HashMap<String, Object> map) {
		return dao.setInsetChannelData(map, sqlSession);
	}
	// 채널사 수수료 등록
//	public Object setInsetChannelDataFee(HashMap<String, Object> map) {
//		return dao.setInsetChannelData(map, sqlSession);
//	}
	// 채널사 수수료 등록
	public Object setInsetChannelDataFee(List<HashMap<String, Object>> list) {
		return dao.setInsetChannelDataFee(sqlSession, list);
	}
	// 배달사 수수료 등록
	public Object setInsetDeliveryDataFee(List<HashMap<String, Object>> list) {
		return dao.setInsetDeliveryDataFee(sqlSession, list);
	}

}
