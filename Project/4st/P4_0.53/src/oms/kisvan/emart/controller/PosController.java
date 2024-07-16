package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import oms.kisvan.emart.model.dto.EM_SETTLE_POS_ORD_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.PosService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 포스 컨트롤러
 */
@Controller
public class PosController {
	@Setter(onMethod_ = {@Autowired})
	private PosService service;

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : posList
	 * @return : String
	 * @Explan : 포스 데이터 검색 화면
	 */
	@RequestMapping("/posList")
	public String posList(Model model) {

		List<EM_REFERENCE_CODE_OMS> ordstate = service.getReferenceCode("P_ORD_STAT");

		// 주문 상태
		model.addAttribute("ordstate", ordstate);

		return "/oms_sub04_01";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : orderSearch
	 * @return : ModelAndView
	 * @Explan : 포스 데이터 검색
	 * 
	 */
	@PostMapping("/getPosList")
	@ResponseBody
	public ModelAndView orderSearch(Criteria cri, @RequestParam HashMap<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.put("cri", cri);
System.out.println(cri.toString());

System.out.println(paramMap.toString());
		// 주문 내역 검색 메소드
		List<EM_SETTLE_POS_ORD_OMS> posList = service.getPosList(paramMap);

		int total = service.getPosList_CNT(paramMap);

		mav.addObject("posList", posList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}
	
	

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : Compare
	 * @return : String
	 * @Explan : 대사 화면
	 */
	@RequestMapping("/compare")
	public String Compare() {

		return "/oms_sub04_09";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getCompareData
	 * @return : ModelAndView
	 * @Explan : 대사 데이터 가져오기
	 */
	@PostMapping("/getCompareData")
	@ResponseBody
	public ModelAndView getCompareData(@RequestParam HashMap<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.get("comp_data"); // 대사 대상
		paramMap.get("sdate"); // 시작일
		paramMap.get("edate"); // 종료일

		// 주문 내역 검색 메소드
		List<?> calcList = service.getCompareData(paramMap);

		mav.addObject("calcList", calcList);

		mav.setViewName("jsonView");

		return mav;
	}
	
	
	
	
	
}



