package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import oms.kisvan.emart.model.service.DeliveryFeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_DELIVERY_FEE_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_POS_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.DeliveryFeeService;
import oms.kisvan.emart.model.service.DeliveryService;

/**
 * @Author : 김민현
 * @Date : 2024.07.15
 * @Explan : 정산내역관리 > 배달료 부담금 마스터
 */

@Controller
public class DeliveryFeeController {
	
	@Autowired
	private DeliveryFeeService deliveryFeeService;

	// 배달료 마스터 페이지
	@RequestMapping(value = "/deliveryFee")
	public String delivery(@RequestParam HashMap<String, Object> paramMap, Criteria cri, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.put("cri", cri);

		List<EM_DELIVERY_FEE_MASTER_OMS> deliveryFeeList = deliveryFeeService.deliveryFeeList(paramMap);

		mav.addObject("deliveryFeeList", deliveryFeeList);
		mav.setViewName("jsonView");

		return "/oms_sub_fee01";
	}
	
	// 배달료 마스터 조회
	@GetMapping("/deliveryFeeInfo")
	@ResponseBody
	public ResponseEntity<Object> deliveryFeeInfo(@RequestParam HashMap<String, Object> paramMap, Criteria cri)
			throws IOException, NumberFormatException {
		
		paramMap.put("cri", cri);
		
		return deliveryFeeService.getDeliveryFeeInfo(paramMap);
	}

	// 배달료 마스터 등록
	@RequestMapping(value = "/deliveryFeeRegister")
	public String deliveryProductRegister(Model model, HttpSession session,
			@RequestParam Map<String, Object> paramMap) {
		
		return "oms_sub_fee01_insert";
	}
}
