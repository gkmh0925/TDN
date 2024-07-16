package oms.kisvan.emart.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.MainDAO;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 메인 서비스
 */
@Service
public class MainService {

	@Setter(onMethod_ = @Autowired)
	private MainDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 당일 매출 정보
	public List<TB_ORDER_STATE> selectTodayCountList() {
		return dao.selectTodayCountList(sqlSession);
	}

	// 전일 매출 정보
	public List<TB_ORDER_STATE> selectYesCountList() {
		return dao.selectYesCountList(sqlSession);
	}

	// 배송채널명
	public List<EM_COMPANY_MASTER_OMS> selectDeliveryNumber() {
		return dao.selectDeliveryNumber(sqlSession);
	}

	// 주문채널명
	public List<EM_COMPANY_MASTER_OMS> selectFranchiseNumber() {
		return dao.selectFranchiseNumber(sqlSession);
	}

}
