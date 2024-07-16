package oms.kisvan.emart.model.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.ModifyDAO;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 수정 서비스
 */
@Service
public class ModifyService {

	@Setter(onMethod_ = @Autowired)
	private ModifyDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 매장 수정
	public Object setUpdateSub01_01detail(HashMap<String, Object> map) {
		return dao.setUpdateSub01_01detail(sqlSession, map);
	}
	
	// 매장 수정
	public Object setUpdateSub02_80detail(HashMap<String, Object> map) {
		return dao.setUpdateSub02_80detail(sqlSession, map);
	}
	
	// 매장 수정
	public Object setDeleteSub02_80detail(HashMap<String, Object> map) {
		return dao.setDeleteSub02_80detail(sqlSession, map);
	}

	// 상품 수정
	public Object setUpdateSub02_01detail(HashMap<String, Object> map) {
		return dao.setUpdateSub02_01detail(sqlSession, map);
	}

	// 채널사별 매장 운영 정보 수정
	public Object setChannelStoreOper(HashMap<String, Object> paramMap) {
		return dao.setChannelStoreOper(sqlSession, paramMap);
	}

	// 채널사,배달대행사  정보 수정
	public Object setUpdateChannelData(HashMap<String, Object> map) {
		return dao.setUpdateChannelData(sqlSession, map);
	}
	// 채널사 수수료마스터 정보 수정
	public Object setUpdateChannelFeeData(List<HashMap<String, Object>> list) {
		return dao.setUpdateChannelFeeData(sqlSession, list);
	}
	// 배달사 수수료마스터 정보 수정
	public Object setUpdateDeliveryFeeData(List<HashMap<String, Object>> list) {
		return dao.setUpdateDeliveryFeeData(sqlSession, list);
	}
	//채널사,배달사 수수료마스터 삭제버튼
	public Object delFeeMasterData(HashMap<String, Object> map) {
		return dao.delFeeMasterData(sqlSession, map);
	}
	
}
