package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.Setter;
import oms.kisvan.emart.common.CommonFunc;
import oms.kisvan.emart.common.UrlNotFoundException;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_CATEGORY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_CHANNEL_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_OPTION_OMS;
import oms.kisvan.emart.model.dto.EM_PRODUCT_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EM_USER_AUTH_OMS;
import oms.kisvan.emart.model.dto.EmProductMasterOmsDTO;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.dto.TB_DELIVERY_STATE;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;
import oms.kisvan.emart.model.dto.TB_STOCK_CHECK;
import oms.kisvan.emart.model.service.CategoryService;
import oms.kisvan.emart.model.service.DeliveryService;
import oms.kisvan.emart.model.service.SearchService;
import oms.kisvan.emart.model.service.StoreService;


//!@@0701 new

/**
 * @Author : 김영현
 * @Date : 2024. 6. 27.
 * @Explan : 배달 서비스 상품 리스트 컨트롤러
 */
@Controller
public class DeliveryController {

	@Setter(onMethod_ = @Autowired)
	private DeliveryService service;
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 6. 27.
	 * @Explan : 배달 서비스 상품 리스트 메인 페이지 호출
	 */

	// 배달 서비스 상품 리스트 관리 메인 페이지
	@RequestMapping("/deliveryProductMain")
	public String deliveryProductMain(Model model, HttpServletRequest request, HttpSession session) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		return "/oms_sub02_03";
	}
	
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 6. 27.
	 * @Explan : 배달 서비스 상품 리스트 조회(검색)
	 */
	@PostMapping("/getDeliveryProductList")
	@ResponseBody
	public ModelAndView getDeliveryProductList(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws IOException, NumberFormatException {

		paramMap.put("cri", cri);

		// 주문 내역 검색 메소드
		List<TB_DELIVERY_STATE> deliveryList = null;

		try {
			deliveryList = service.searchDeliveryProductList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int total = service.getSearchDeliveryProductTotalCnt(paramMap);

		mav.addObject("deliveryList", deliveryList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));
		mav.setViewName("jsonView");
		return mav;
	}
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 6. 28.
	 * @Explan : 배달 서비스 상품 등록 페이지 호출
	 */
	@RequestMapping(value = "/deliveryProductRegister")
	public String deliveryProductRegister(Model model, HttpSession session, @RequestParam Map<String, Object> paramMap) {
		
		paramMap.put("group_cd", "PRD_STATE");		
		//상품 상태
		List<EM_REFERENCE_CODE_OMS> prdStateRefList = service.getPrdStateReferenceList(paramMap);
		
		paramMap.put("group_cd", "PRD_TAX_TYPE");
		//과세 구분
		List<EM_REFERENCE_CODE_OMS> taxTypeRefList = service.getPrdStateReferenceList(paramMap);
		
		paramMap.put("group_cd", "ITEM_PICKUP_CODE");
		//업무 방식
		List<EM_REFERENCE_CODE_OMS> workTypeRefList = service.getPrdStateReferenceList(paramMap);
		
		
		model.addAttribute("prdStateList", prdStateRefList);
		model.addAttribute("taxTypeList", taxTypeRefList);
		model.addAttribute("workTypeList", workTypeRefList);
 		return "oms_sub02_03_insert";
	}
	
	
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 6. 28.
	 * @Explan : 배달 서비스 상품 등록
	 */	
	@PostMapping("/registerDeliveryProduct")
	@ResponseBody
//	public ResponseEntity<Object> registerDeliveryProduct(Model model, HttpSession session, @RequestBody EM_PRODUCT_MASTER_OMS epmo, @RequestParam Map<String, Object> paramMap) {
	public ResponseEntity<Object> registerDeliveryProduct(Model model, HttpSession session, @RequestBody EmProductMasterOmsDTO epmoDTO) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		Integer userCd = uSession.getUSER_CD();
		epmoDTO.setRegUserCd(userCd);
 		return service.createDeliveryProduct(epmoDTO);
	}
	
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 6. 29.
	 * @Explan : 배달 서비스 상품 상세페이지 호출
	 */	
	@PostMapping("/deliveryProductDetail")
	public String deliveryProductDetail(@RequestParam HashMap<String, Object> paramMap, Model model) {
		
		String chCd = String.valueOf(paramMap.get("chCd"));

		
		//상품 단일 정보
		EmProductMasterOmsDTO emProductMasterOmsDTO = service.getDeliveryProductInfo(paramMap);		
		//매장 카테고리 정보
		List<EM_CATEGORY_MASTER_OMS> emCategoryMasterOmsList = service.getDeliveryProductStoreCategoryList(chCd);	
		//과세 구분 정보
		List<EM_REFERENCE_CODE_OMS> emTaxTypeReferenceCodeOmsList = service.getReferenceCodeByGroupCd("PRD_TAX_TYPE");
		//상품 상태 정보
		List<EM_REFERENCE_CODE_OMS> emPrdStateReferenceCodeOmsList = service.getReferenceCodeByGroupCd("PRD_STATE");					
		//업무 방식 정보
		List<EM_REFERENCE_CODE_OMS> emWorkTypeReferenceCodeOmsList = service.getReferenceCodeByGroupCd("ITEM_PICKUP_CODE");
		//상품 타입 정보 (특정 채널사만 필요)
		List<EM_CATEGORY_MASTER_OMS> emPrdTypeReferenceCodeOmsList = service.getDeliveryProductPrdCategoryList(chCd);
		//미성년구매유무
//		List<EM_REFERENCE_CODE_OMS> emUseAdultReferenceCodeOmsList = service.getReferenceCodeByGroupCd();
		
		model.addAttribute("product", emProductMasterOmsDTO); //상품 단일 정보
		model.addAttribute("categoryList", emCategoryMasterOmsList); //채널사별 매장 카테고리 리스트 
		model.addAttribute("taxTypeList", emTaxTypeReferenceCodeOmsList);	//과세구분 리스트
		model.addAttribute("prdStateList", emPrdStateReferenceCodeOmsList);	//상품 상태 리스트		
		model.addAttribute("workTypeList", emWorkTypeReferenceCodeOmsList);	//업무 방식 리스트
		model.addAttribute("prdCategoryList", emWorkTypeReferenceCodeOmsList);	//상품 타입 리스트

//		model.addAttribute("useAdultList", emWorkTypeReferenceCodeOmsList);	//상품 타입 리스트

	    return "oms_sub02_03_detail";
	}
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 6. 29.
	 * @Explan : 배달 서비스 상품 삭제
	 */	
	@PostMapping("/removeDeliveryProduct")
	@ResponseBody
	public ResponseEntity<Object> removeDeliveryProduct(Model model, HttpSession session, @RequestParam Map<String, Object> paramMap) {
		ResponseEntity<Object> res = service.removeDeliveryProduct(paramMap);

	    return res;
	}
	
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 7. 1.
	 * @Explan : 배달 서비스 상품 수정
	 */	
	@PostMapping("/modifyDeliveryProduct")
	@ResponseBody
	public ResponseEntity<Object> modifyDeliveryProduct(Model model, HttpSession session, @RequestParam Map<String, Object> paramMap) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		
		return service.modifyDeliveryProductInfo(paramMap, uSession);
	}
	
	
	/**
	 * @Author : 김영현
	 * @Date : 2024. 7. 2.
	 * @Explan : 채널사 마다 다른 카테고리 관련 정보 요청
	 */	
	@PostMapping("/getCategoryListOfOrderCompany")
	@ResponseBody
	public ModelAndView getCategoryListOfOrderCompany(Model model, HttpSession session, @RequestParam String chCd, ModelAndView mav) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		
		List<EM_CATEGORY_MASTER_OMS> stoCtgrList = service.getDeliveryProductStoreCategoryList(chCd);
		List<EM_CATEGORY_MASTER_OMS> prdCtgrList = service.getDeliveryProductPrdCategoryList(chCd);
		
		mav.addObject("stoCtgrList", stoCtgrList);
		mav.addObject("prdCtgrList", prdCtgrList);
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	
	
	
	
}
