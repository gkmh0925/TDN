package oms.kisvan.emart.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 수정 Data Access Object
 */
@Repository
public class ModifyDAO {
	// ############################ 수정모드 ############################

	// ================== DETAIL 웹 수정 ==================
	// 매장 상세 수정
	public Object setUpdateSub01_01detail(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateSub01_01detail", map);
	}
	
	// 매장 상세 수정
	public Object setUpdateSub02_80detail(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateSub02_80detail", map);
	}
	
	// 매장 상세 수정
	public Object setDeleteSub02_80detail(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.delete("modify.setDeleteSub02_80detail", map);
	}

	// 상품 상세 수정
	public Object setUpdateSub02_01detail(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateSub02_01detail", map);
	}

	// 채널사별 매장 정보 수정
	public Object setChannelStoreOper(SqlSessionTemplate sqlSession, HashMap<String, Object> paramMap) {
		return sqlSession.update("modify.setChannelStoreOper", paramMap);
	}

	// 채널사,배달대행사 상세 수정
	public Object setUpdateChannelData(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("modify.setUpdateChannelData", map);
	}
	// 채널사수수료마스터 수정
	public Object setUpdateChannelFeeData(SqlSessionTemplate sqlSession, List<HashMap<String, Object>> list) {
		return sqlSession.update("modify.setUpdateChannelFeeData", list);
	}
	// 배달사수수료마스터 수정
	public Object setUpdateDeliveryFeeData(SqlSessionTemplate sqlSession, List<HashMap<String, Object>> list) {
		return sqlSession.update("modify.setUpdateDeliveryFeeData", list);
	}
	//채널사,배달사 수수료마스터 삭제
	public Object delFeeMasterData(SqlSessionTemplate sqlSession,HashMap<String,Object> map) {
		return sqlSession.delete("modify.delFeeMasterData",map);
	}

}

