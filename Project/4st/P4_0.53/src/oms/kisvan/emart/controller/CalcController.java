package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_DELIVERY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.EM_RELAY_TOTAL_OMS;

 

import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.CalcService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 정산 컨트롤러
 */
@Controller  
public class CalcController {
	@Setter(onMethod_ = {@Autowired})
	private CalcService service;

	// 임시
	@RequestMapping("/oms_sub04_02")
	public String oms_sub04_02() {

		return "/oms_sub04_02";
	}

	// 임시
	@RequestMapping("/oms_sub04_03")
	public String oms_sub04_03() {

		return "/oms_sub04_03";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : ordFeeList
	 * @return : ModelAndView
	 * @Explan : 주문채널사 정산 엑셀 화면
	 */
	@RequestMapping("/ordFeeList")
	public ModelAndView ordFeeList(ModelAndView mav) {

		List<EM_COMPANY_MASTER_OMS> channelList = service.getChannelList("ORDER");
		
		mav.addObject("channelList", channelList);

		mav.setViewName("oms_sub04_04");

		return mav;
	}
	
	

	/**
	 * @Author : 김선옥
	 * @Date : 2023. 12. 13.
	 * @Method : RelayFee
	 * @return : String
	 * @Explan : 중계수수료 정산조회
	 */
	@RequestMapping("/RelayFee")
	public String RelayFee(ModelAndView mav) {
		return "oms_sub04_10";
	}
	
	

	/**
	 * @Author : 김선옥
	 * @Date : 2023. 12. 15.
	 * @Method : orderSearch
	 * @return : ModelAndView
	 * @Explan : 중계수수료 정산 엑셀데이터 가져오기
	 */
	@PostMapping("/oms_sub04_10_process")
	@ResponseBody
	public ModelAndView oms_sub04_10_process(Criteria cri,@RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {
		paramMap.put("cri", cri); // 페이징
		 	
		// 주문 내역 검색 메소드
		List<EM_RELAY_TOTAL_OMS> calcList = service.getCalculate_RELAY_LIST(paramMap);
		//정산내역을 가지고 온다.
		int total = service.getCalculate_RELAY_CNT(paramMap);
		mav.addObject("calcList", calcList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));
		mav.setViewName("jsonView");

		return mav;
	}
 
	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : orderSearch
	 * @return : ModelAndView
	 * @Explan : 주문채널사 정산 엑셀 데이터 가져오기
	 */
	@PostMapping("/getOrdFeeList")
	@ResponseBody
	public ModelAndView orderSearch(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.get("ch_view"); // 조회 배달사 코드
//		paramMap.get("scd"); // 매장 코드
//		paramMap.get("ono"); // 주문 번호
//		paramMap.get("chono"); // 채널사 주문 번호
		paramMap.get("sdate"); // 시작일
		paramMap.get("edate"); // 종료일
		paramMap.put("cri", cri); // 페이징

		// 주문 내역 검색 메소드
		List<?> calcList = service.getCalculate_ORD_List(paramMap);

		int total = service.getCalculate_ORD_CNT(paramMap);

		mav.addObject("calcList", calcList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : dlvFeeList
	 * @return : ModelAndView
	 * @Explan : 배달대행사 엑셀 화면
	 */
	@RequestMapping("/dlvFeeList")
	public ModelAndView dlvFeeList(ModelAndView mav) {

		List<EM_COMPANY_MASTER_OMS> deliveryList = service.getChannelList("DELIVERY");

		mav.addObject("deliveryList", deliveryList);

		mav.setViewName("oms_sub04_05");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : deliverySearch
	 * @return : ModelAndView
	 * @Explan : 배달배행사 정산 엑셀 가져오기
	 */
	@PostMapping("/getDlvFeeList")
	@ResponseBody
	public ModelAndView deliverySearch(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.get("dv_view"); // 조회 배달사 코드
//		paramMap.get("scd"); // 매장 코드
//		paramMap.get("ono"); // 주문 번호
//		paramMap.get("dvono"); // 배달사 주문 번호
		paramMap.get("sdate"); // 시작일
		paramMap.get("edate"); // 종료일
		paramMap.put("cri", cri); // 페이징

		// 주문 내역 검색 메소드
		List<?> calcList = service.getCalculate_DV_List(paramMap);

		int total = service.getCalculate_DV_CNT(paramMap);

		mav.addObject("calcList", calcList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

//임시
	@RequestMapping("/oms_sub04_06")
	public String oms_sub04_06() {

		return "/oms_sub04_06";
	}

	// 임시
	@RequestMapping("/oms_sub04_07")
	public String oms_sub04_07() {

		return "/oms_sub04_07";
	}

	/**
	 * @Author : 문충일
	 * @Date : 2023. 11. 16.
	 * @Method : totalCalcList
	 * @return : ModelAndView
	 * @Explan : 배달배행사 정산 엑셀 가져오기
	 */
	@RequestMapping("/totalCalcList")
	public String totalCalcList() {

		return "/oms_sub04_08";
	}

	@PostMapping("/oms_sub04_08_process")
	@ResponseBody
	public ModelAndView oms_sub04_08_process(@RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.get("sdate"); // 시작일
		paramMap.get("edate"); // 종료일
		

		// 주문 내역 검색 메소드
		List<EM_DELIVERY_TOTAL_OMS> calcList = service.getCalculate_TOTAL(paramMap);

		mav.addObject("calcList", calcList);

		mav.setViewName("jsonView");

		return mav;
	}
	

	/**
	 * @Author : 김선옥
	 * @Date : 2022. 12. 19.
	 * @Method : RelayFee_Campare
	 * @return : String
	 * @Explan : 대사 화면
	 */
	@RequestMapping("/RelayFee_Campare")
	public String RelayFee_Campare() {

		return "/oms_sub04_11";
	}
	
	/**
	 * @Author : 김선옥
	 * @Date : 2023. 12. 20.
	 * @Method : getRelayCompareData
	 * @return : ModelAndView
	 * @Explan : 대사 데이터 가져오기
	 */
	@PostMapping("/getRelayCompareData")
	@ResponseBody
	public ModelAndView getRelayCompareData(@RequestParam HashMap<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		//paramMap.get("comp_data"); // 대사 대상
		//paramMap.get("sdate"); // 시작일
		//paramMap.get("edate"); // 종료일

		// 주문 내역 검색 메소드
		List<?> calcList = service.getRelayCompareData(paramMap);

		mav.addObject("calcList", calcList);

		mav.setViewName("jsonView");

		return mav;
	}
}