package oms.kisvan.emart.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.common.CommonFunc;
import oms.kisvan.emart.common.UrlNotFoundException;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_CHANNEL_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_STORE_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.StoreService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 매장 컨트롤러
 */
@Controller
public class StoreController {

	@Setter(onMethod_ = {@Autowired})
	private StoreService service;

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : storeList
	 * @return : String
	 * @Explan : 매장 검색 화면
	 */
	@RequestMapping("/storeList")
	public String storeList(Model model, HttpSession session) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		// 콜센터 검색 방지
		if (uSession.getUSER_LV().equals("C")) {
			throw new UrlNotFoundException();
		}

		List<EM_REFERENCE_CODE_OMS> stcode = service.GetStoreState();

		model.addAttribute("stcode", stcode);

		return "/oms_sub01_01";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : storeSearch
	 * @return : ModelAndView
	 * @Explan : 매장 리스트 가져오기
	 */
	@PostMapping("/getStoreList")
	@ResponseBody
	public ModelAndView storeSearch(@RequestParam Map<String, Object> paramMap, Criteria cri, ModelAndView mav)
			throws Exception {

		paramMap.get("scd");
		paramMap.get("occd");
		paramMap.get("dccd");
		paramMap.get("sst");
		paramMap.put("cri", cri);

		List<EM_COMPANY_MASTER_OMS> storeList = service.storeSearch(paramMap);
		

		int total = service.getSearchStoreTotal(paramMap);
		
		
		for (int i = 0; i < storeList.size(); i++) {
			storeList.get(i).setCPN_ADDR(CommonFunc.addrMasking(storeList.get(i).getCPN_ADDR()));
			storeList.get(i).setCPN_TEL(CommonFunc.telMasking(storeList.get(i).getCPN_TEL()));
			storeList.get(i).setHPNO(CommonFunc.telMasking(storeList.get(i).getHPNO()));
		}

		mav.addObject("storeList", storeList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : storeList_detail
	 * @return : String
	 * @Explan : 매장 상세 화면
	 */
	@RequestMapping("/storeList_detail")
	public String storeList_detail(@RequestParam String stocd, Model model) {

		// 매장 상태
		List<EM_REFERENCE_CODE_OMS> stcode = service.GetStoreState();
		// 매장 기본 정보
		EM_COMPANY_MASTER_OMS sdView = service.getStoreDetailData(stocd);
		// 매장 운영 정보
		EM_STORE_OPERATION_OMS sodView = service.getStoreOperationDetailData(stocd);
		// 매장 상권 정보
		List<EM_CHANNEL_STORE_OPERATION_OMS> csoView = service.getChannelStoreOperation(stocd);
		// 채널사 전송 플래그
		List<EM_REFERENCE_CODE_OMS> chflag = service.GetChannelFlag();
		// 배달대행사정보
		List<EM_COMPANY_MASTER_OMS> dvlList = service.GetDeliveryList();

		model.addAttribute("stcode", stcode);
		model.addAttribute("sdView", sdView);
		model.addAttribute("sodView", sodView);
		model.addAttribute("csoView", csoView);
		model.addAttribute("chflag", chflag);
		model.addAttribute("dvlList", dvlList);

		return "oms_sub01_01_detail";
	}
}
