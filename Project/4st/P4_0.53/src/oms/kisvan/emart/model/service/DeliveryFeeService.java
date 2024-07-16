package oms.kisvan.emart.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.DeliveryFeeDAO;
import oms.kisvan.emart.model.dao.PosDAO;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_DELIVERY_FEE_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;

@Service
public class DeliveryFeeService {
	@Setter(onMethod_ = @Autowired)
	private DeliveryFeeDAO DeliveryFeeDAO;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	// 배달료 마스터 페이지
	public List<EM_DELIVERY_FEE_MASTER_OMS> deliveryFeeList(HashMap<String, Object> paramMap) {
		return DeliveryFeeDAO.deliveryFeeList(paramMap);
	}
	
	// 배달료 마스터 검색일자 조회
	public ResponseEntity<Object> getDeliveryFeeInfo(HashMap<String, Object> paramMap) {
		
		HashMap<String, Object> resMap = new HashMap<>();
		
	    resMap.put("deliveryFeeListInfo", DeliveryFeeDAO.deliveryFeeList(paramMap));
	    //resMap.put("pageMaker", new PagingDTO((Criteria)paramMap.get("cri"), DeliveryFeeDAO.getdeliveryFeeListCnt(paramMap)));
	    
		return ResponseEntity.status(HttpStatus.OK).body(resMap);
	}
}