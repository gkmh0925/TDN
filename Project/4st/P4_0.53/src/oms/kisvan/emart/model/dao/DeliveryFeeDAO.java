package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_DELIVERY_FEE_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;

/**
 * @Author : 김민현
 * @Date : 2024. 07. 10.
 * @Explan : 배달료 부담금 마스터 Data Access Object
 */

@Repository
public class DeliveryFeeDAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	// 배달료 마스터 검색일자 조회
	public List<EM_DELIVERY_FEE_MASTER_OMS> deliveryFeeList (HashMap<String, Object> paramMap) {
		return sqlSession.selectList("deliveryFee.deliveryFeeList",paramMap);
	}

}
