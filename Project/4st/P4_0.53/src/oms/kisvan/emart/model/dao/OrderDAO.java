package oms.kisvan.emart.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_ORDER_HISTORY_OMS;
import oms.kisvan.emart.model.dto.EM_ORDER_MENU_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 주문 내역 Data Access Object
 */
@Repository
public class OrderDAO {

	// 주문 내역 검색
	public List<TB_ORDER_STATE> orderSearch(Map<String, Object> paramMap, SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("order.orderSearch", paramMap);
	}

	// 주문 상세 정보
	public TB_ORDER_STATE orderDetail(SqlSessionTemplate sqlSession, TB_ORDER_STATE tState) {
		return sqlSession.selectOne("order.orderDetail", tState);
	}

	// 주문 메뉴 정보
	public List<EM_ORDER_MENU_OMS> orderMenu(SqlSessionTemplate sqlSession, TB_ORDER_STATE tState) {
		return sqlSession.selectList("order.orderMenu", tState);
	}

	// 주문 이력 정보
	public List<EM_ORDER_HISTORY_OMS> orderHistory(SqlSessionTemplate sqlSession, TB_ORDER_STATE tState) {
		return sqlSession.selectList("order.orderHistory", tState);
	}


	// 검색된 주문 내역 총 개수 가져오기
	public int getSearchOrderTotal(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {

		return sqlSession.selectOne("order.getSearchOrderTotal", paramMap);
	}

	// 주문 내역 상태 가져오기
	public List<EM_REFERENCE_CODE_OMS> getOrderStateList(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("order.getOrderStateList");
	}
}
