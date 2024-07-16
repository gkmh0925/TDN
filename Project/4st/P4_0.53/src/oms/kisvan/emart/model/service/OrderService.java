package oms.kisvan.emart.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.OrderDAO;
import oms.kisvan.emart.model.dto.EM_ORDER_HISTORY_OMS;
import oms.kisvan.emart.model.dto.EM_ORDER_MENU_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;

@Service
public class OrderService {

	@Setter(onMethod_ = @Autowired)
	private OrderDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 주문 내역 검색
	public List<TB_ORDER_STATE> orderSearch(Map<String, Object> paramMap) throws Exception {
		return dao.orderSearch(paramMap, sqlSession);
	}

	// ################################DETAIL################################

	// 주문 상세 정보
	public TB_ORDER_STATE orderDetail(TB_ORDER_STATE tState) {
		return dao.orderDetail(sqlSession, tState);
	}

	// 주문 메뉴 정보
	public List<EM_ORDER_MENU_OMS> orderMenu(TB_ORDER_STATE tState) {
		return dao.orderMenu(sqlSession, tState);
	}

	// 주문 이력 정보
	public List<EM_ORDER_HISTORY_OMS> orderHistory(TB_ORDER_STATE tState) {
		return dao.orderHistory(sqlSession, tState);
	}

	// 검색된 주문 내역 총 개수
	public int getSearchOrderTotal(Map<String, Object> paramMap) {

		return dao.getSearchOrderTotal(sqlSession, paramMap);
	}

	// 주문 내역 상태값
	public List<EM_REFERENCE_CODE_OMS> getOrderStateList() {
		return dao.getOrderStateList(sqlSession);
	}
}
