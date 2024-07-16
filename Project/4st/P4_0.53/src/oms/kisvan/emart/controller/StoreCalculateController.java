package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.service.StoreCalculateService;

/**
 * @Author : 석호동
 * @Date : 2024. 6. 17.
 * @Explan : 정산내역 > 점별 조회
 */
@Controller
public class StoreCalculateController {
	
	private final StoreCalculateService storeCalculateService;
	
	public StoreCalculateController(StoreCalculateService storeCalculateService) {
		this.storeCalculateService = storeCalculateService;
	}
	
	/**
	 * @Author : 석호동
	 * @Date : 2024. 06. 17.
	 * @Method : StoreOrdNoDetails
	 * @return : String
	 * @Explan : 대사 화면
	 */
	@RequestMapping("/StoreOrdNoDetails")
	public String StoreOrdNoDetails() {

		return "/oms_sub04_12";
	}
	
	/**
	 * @Author : 석호동
	 * @Date : 2024. 6. 17.
	 * @Method : getStoreOrdNoDetailsInfo
	 * @return : ResponseEntity
	 * @Explan : 점별/주문번호별 상세 검색
	 * 
	 */
	
	@GetMapping("/getStoreOrdNoDetailsInfo")
	@ResponseBody
	public ResponseEntity<Object> getStoreOrdNoDetailsInfo(@RequestParam HashMap<String, Object> paramMap, Criteria cri)
			throws IOException, NumberFormatException {
		
		paramMap.put("cri", cri);
		return storeCalculateService.getStoreOrdNoDetailsInfo(paramMap);
	}
	
	/**
	 * @Author : 석호동
	 * @Date : 2024. 6. 17.
	 * @Method : storeOrdNoDetailsExcel
	 * @return : ResponseEntity
	 * @Explan : 점별/주문번호별 상세 화면 엑셀 다운로드
	 * 
	 */
	@PostMapping("/storeOrdNoDetailsExcel")
	@ResponseBody
	public ResponseEntity<Object> storeOrdNoDetailsExcel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws IOException, NumberFormatException {
		
		return storeCalculateService.storeOrdNoDetailsExcel(paramMap, response);
	}
	
	/**
	 * @Author : 석호동
	 * @Date : 2024. 06. 17.
	 * @Method : StoreOrdNoDetails
	 * @return : String
	 * @Explan : 점별/주문번호별 상세 화면
	 */
	@RequestMapping("/StoreMonthDetails")
	public String StoreMonthDetails() {

		return "/oms_sub04_13";
	}
	
	/**
	 * @Author : 석호동
	 * @Date : 2024. 6. 17.
	 * @Method : getStoreMonthDetailsInfo
	 * @return : ResponseEntity
	 * @Explan : 점별 월 정산 조회 화면
	 * 
	 */
	
	@GetMapping("/getStoreMonthDetailsInfo")
	@ResponseBody
	public ResponseEntity<Object> getStoreMonthDetailsInfo(@RequestParam HashMap<String, Object> paramMap, Criteria cri)
			throws IOException, NumberFormatException {
		
		paramMap.put("cri", cri);
		return storeCalculateService.getStoreMonthDetailsInfo(paramMap);
	}
	
	/**
	 * @Author : 석호동
	 * @Date : 2024. 6. 18.
	 * @Method : storeMonthDetailsExcel
	 * @return : ResponseEntity
	 * @Explan : 점별 월 정산 조회 화면 엑셀 다운로드
	 * 
	 */
	@PostMapping("/storeMonthDetailsExcel")
	@ResponseBody
	public ResponseEntity<Object> storeMonthDetailsExcel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws IOException, NumberFormatException {
		
		return storeCalculateService.storeMonthDetailsExcel(paramMap, response);
	}
	
}
