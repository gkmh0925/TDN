package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;
import oms.kisvan.emart.model.service.OrderService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 주문 내역 컨트롤러
 */
@Controller
public class OrderController {

	@Setter(onMethod_ = {@Autowired})
	private OrderService service;

	// 주문 내역 관리 메인 페이지
	@RequestMapping("/orderList")
	public String orderList(Model model) {

		// 주문 상태 가져오기
		List<EM_REFERENCE_CODE_OMS> ordstate = service.getOrderStateList();

		model.addAttribute("ordstate", ordstate);

		return "/oms_sub03_01";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : orderSearch
	 * @return : ModelAndView
	 * @Explan : 주문 내역 검색
	 */
	@PostMapping("/getOrderList")
	@ResponseBody
	public ModelAndView orderSearch(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.put("cri", cri);

		// 주문 내역 검색 메소드
		List<TB_ORDER_STATE> orderList = null;

		try {
			orderList = service.orderSearch(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int total = service.getSearchOrderTotal(paramMap);

		mav.addObject("orderList", orderList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

}
