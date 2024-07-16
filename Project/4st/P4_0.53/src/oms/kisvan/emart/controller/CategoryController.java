package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EM_USER_AUTH_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.dto.TB_STOCK_CHECK;
import oms.kisvan.emart.model.service.CategoryService;
import oms.kisvan.emart.model.service.SearchService;
import oms.kisvan.emart.model.service.StoreService;

@Controller
// 상품 관리 컨트롤러
public class CategoryController {

	@Setter(onMethod_ = @Autowired)
	private CategoryService service;
	
	@Setter(onMethod_ = {@Autowired})
	private StoreService service2;
 
	// 카테고리 lv1 관리 메인 페이지
	@RequestMapping("/categoryList")
	public String oms_sub02_80(Model model, HttpServletRequest request, HttpSession session) {

//		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		
		List<EM_REFERENCE_CODE_OMS> plcode = service.GetPolicyCode();

		model.addAttribute("plcode", plcode);

		return "/oms_sub02_80";
	}

	// AJAX 검색 처리 컨트롤러
	@PostMapping("/oms_sub02_80_process")
	@ResponseBody
	public ModelAndView categorySearch(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws Exception {

		paramMap.put("cri", cri); // 페이징

		// 카테고리 리스트 검색 메소드
		List<EM_CATEGORY_MASTER_OMS> categoryList = service.categorySearch(paramMap);

		int total = service.getSearchCategoryTotal(paramMap);

		for (int i = 0; i < categoryList.size(); i++) {
			categoryList.get(i).setCH_CD(CommonFunc.nameMasking(categoryList.get(i).getCH_CD()));
		}
		

		mav.addObject("categoryList", categoryList);

		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}
	
	@PostMapping("/categoryList_detail")
	public String categoryList_detail(@RequestParam HashMap<String, String> paramMap, Model model) {

	    // ctgrcd 가져오기
	    EM_CATEGORY_MASTER_OMS getGetCtgrCd = service.GetCtgrInfo(paramMap);
	    
	    model.addAttribute("getGetCtgrCd", getGetCtgrCd);
	    
	    return "oms_sub02_80_detail";
	}
	
	@RequestMapping(value = "/categoryRegister")
	public String insertPage(Model model, HttpSession session) {
		
		List<EM_CATEGORY_MASTER_OMS> GetAllCpnCd = service.GetAllCpnCd();
		
		model.addAttribute("GetAllCpnCd", GetAllCpnCd);
		
 		return "oms_sub02_80_insert";
	}
	
	@RequestMapping(value = "/categoryInsert")
	@ResponseBody
	public void CategoryInsert(Model model, HttpServletResponse response, EM_CATEGORY_MASTER_OMS uao,
			@RequestParam HashMap<String, String> paramMap, HttpSession session) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");


		// XSS 방지
		paramMap.forEach((key, value) -> {
			value = XssPreventer.escape(value);
			paramMap.replace(key, value);
		});


		
		uao.setCTGR_CD(Integer.parseInt(paramMap.get("ctgr_cd")));
		uao.setCTGR_NM(paramMap.get("ctgr_nm"));		
		uao.setSEQ(Integer.parseInt(paramMap.get("seq")));
		uao.setCTGR_INFO(paramMap.get("ctgr_info"));
		uao.setCH_CD(paramMap.get("ch_cd"));
		uao.setCTGR_TYPE(paramMap.get("ctgr_type"));
		uao.setREG_USER_CD(uSession.getUSER_CD());

		int isDupl = service.isDuplicateCategory(uao);
		
		if(isDupl == 0) {
			
			int categoryInsert = service.CategoryInsert(uao);

			if (categoryInsert > 0) {
				response.getWriter().print(true);
			} else {
				response.getWriter().print(false);
			}
		}else {
			response.getWriter().print("dupl");
		}
	}
	
	// 카테고리 lv2 관리 메인 페이지
	@RequestMapping("/categoryLv2List")
	public String oms_sub02_81(Model model, HttpServletRequest request, HttpSession session) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		
		List<EM_REFERENCE_CODE_OMS> plcode = service.GetPolicyCode();

		

		model.addAttribute("plcode", plcode);

		return "/oms_sub02_81";
	}
	
	
	//!@@0701 yh
	/**
	 * @Author : 김영현
	 * @Date : 2024. 06. 28.
	 * @Explan : 채널사의 카테고리 정보 가져오기
	 */
	@PostMapping("/oms_sub02_03_process")
	@ResponseBody
	public ModelAndView oms_sub02_03_process(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws Exception {

		paramMap.put("cri", cri); // 페이징
		List<EM_CATEGORY_MASTER_OMS> stoCategoryList = service.getOrderCompanyStoreCategoryList(paramMap); //매장 카테고리 가져오기
		List<EM_CATEGORY_MASTER_OMS> prdCategoryList = service.getOrderCompanyProductCategoryList(paramMap); //상품 카테고리 가져오기

	
		mav.addObject("stoCategoryList", stoCategoryList);
		mav.addObject("prdCtegoryList", prdCategoryList);
		mav.setViewName("jsonView");
		return mav;
	}
	
	
	
	// AJAX 검색 처리 컨트롤러
	@PostMapping("/oms_sub02_81_process")
	@ResponseBody
	public ModelAndView categoryLv2Search(Criteria cri, @RequestParam Map<String, Object> paramMap, ModelAndView mav)
			throws Exception {

		paramMap.put("cri", cri); // 페이징

		// 카테고리 리스트 검색 메소드
		List<EM_CATEGORY_MASTER_OMS> categoryList = service.categoryLv2Search(paramMap);

		int total = service.getSearchCategoryLv2Total(paramMap);

		for (int i = 0; i < categoryList.size(); i++) {
			categoryList.get(i).setCH_CD(CommonFunc.nameMasking(categoryList.get(i).getCH_CD()));
		}
		

		mav.addObject("categoryList", categoryList);

		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/categoryLv2Insert")
	@ResponseBody
	public void CategoryLv2Insert(Model model, HttpServletResponse response, EM_CATEGORY_MASTER_OMS uao,
			@RequestParam HashMap<String, String> paramMap, HttpSession session) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");


		// XSS 방지
		paramMap.forEach((key, value) -> {
			value = XssPreventer.escape(value);
			paramMap.replace(key, value);
		});

		uao.setCTGR_TYPE(paramMap.get("ctgr_type"));
		uao.setCH_CD(paramMap.get("ch_cd"));
		uao.setCTGR_NM(paramMap.get("ctgr_nm"));
		uao.setSEQ(Integer.parseInt(paramMap.get("seq")));
		uao.setREG_USER_CD(uSession.getUSER_CD());
		
		int categoryLv2Insert = service.CategoryLv2Insert(uao);

		if (categoryLv2Insert > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}
	
	
	@PostMapping("/categoryLv2List_detail")
	public String categoryList_Lv2detail(@RequestParam String ctgrcd, Model model) {
	
	    // ctgrcd 가져오기
	    EM_CATEGORY_MASTER_OMS getGetCtgrCd = service.GetCtgrCd(ctgrcd);
	    
	    model.addAttribute("getGetCtgrCd", getGetCtgrCd);
	    
	    return "oms_sub02_81_detail";
	}
	
	@RequestMapping(value = "/categoryLv2Register")
	public String insertLv2Page(Model model, HttpSession session) {
		
		List<EM_CATEGORY_MASTER_OMS> GetAllCpnCd = service.GetAllCpnCd();
		
		model.addAttribute("GetAllCpnCd", GetAllCpnCd);
 		return "oms_sub02_81_insert";
	}

}
