package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;
import oms.kisvan.emart.model.service.MainService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 메인 컨트롤러
 */
@Controller
public class MainController {

	@Setter(onMethod_ = {@Autowired})
	private MainService service;

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : indexPage
	 * @return : String
	 * @Explan : 로그인 화면
	 */
	@RequestMapping(value = "/")
	public String indexPage(HttpSession session) {
		session.invalidate();
		return "oms_index";
	}

	// 네비게이션 바
	@RequestMapping("/navibar")
	public String navibar() {

		return "oms_subnav";
	}

	// OMS_MAIN 페이지
	@RequestMapping(value = "/main")
	public ModelAndView main(ModelAndView mav, HttpSession session) throws IOException {
		// 금일 리스트
		List<TB_ORDER_STATE> todayList = service.selectTodayCountList();
		// 전일 리스트
		List<TB_ORDER_STATE> yesList = service.selectYesCountList();
		// 채널사 리스트
		List<EM_COMPANY_MASTER_OMS> franchList = service.selectFranchiseNumber();
		// 배달 대행 리스트
		List<EM_COMPANY_MASTER_OMS> deliveryList = service.selectDeliveryNumber();

		mav.addObject("todayList", todayList);
		mav.addObject("yesList", yesList);

		session.setAttribute("franchList", franchList);
		session.setAttribute("deliveryList", deliveryList);

		mav.setViewName("oms_main");

		return mav;
	}
}
