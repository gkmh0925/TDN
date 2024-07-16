package oms.kisvan.emart.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 메인 화면 Data Access Object
 */
@Repository
public class MainDAO {

	// 당일 매출 데이터 가져오기
	public List<TB_ORDER_STATE> selectTodayCountList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectTodayCountList");
	}

	// 전일 매출 데이터 가져오기
	public List<TB_ORDER_STATE> selectYesCountList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectYesCountList");
	}

	// 배달사 콜센터 번호 가져오기
	public List<EM_COMPANY_MASTER_OMS> selectDeliveryNumber(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectDeliveryNumber");
	}

	// 주문채널사 콜센터 번호 가져오기
	public List<EM_COMPANY_MASTER_OMS> selectFranchiseNumber(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("main.selectFranchiseNumber");
	}

}
