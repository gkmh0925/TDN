package oms.kisvan.emart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.common.CommonFunc;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.SearchService;

@Controller
public class SearchController {

	@Setter(onMethod_ = {@Autowired})
	private SearchService service;

	// 상세 검색 팝업창
	@GetMapping("/subSearch01")
	public String subSearch01() {

		return "oms_sub_search01";
	}
	
	// 상세 검색 팝업창
		@GetMapping("/subSearch04")
		public String subSearch04() {

			return "oms_sub_search04";
		}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getSubSearch01
	 * @return : ModelAndView
	 * @Explan : 매장 상세 검색
	 */
	@PostMapping("/getSubSearch01")
	@ResponseBody
	public ModelAndView getSubSearch01(Criteria cri, ModelAndView mav) throws Exception {

		List<EM_COMPANY_MASTER_OMS> tbsView = service.getSearchStore(cri);

		int total = service.getSearchStoreTotal(cri);

		for (int i = 0; i < tbsView.size(); i++) {
			tbsView.get(i).setCPN_ADDR(CommonFunc.addrMasking(tbsView.get(i).getCPN_ADDR()));
			tbsView.get(i).setCPN_TEL(CommonFunc.telMasking(tbsView.get(i).getCPN_TEL()));
		}

		mav.addObject("tbsView", tbsView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}
	
	@PostMapping("/getSubSearch04")
	@ResponseBody
	public ModelAndView getSubSearch04(Criteria cri, ModelAndView mav) throws Exception {

		List<EM_COMPANY_MASTER_OMS> tbsView = service.getSearchStoreCpnType(cri);

		int total = service.getSearchStoreCpnTypeTotal(cri);

		for (int i = 0; i < tbsView.size(); i++) {
			tbsView.get(i).setCPN_ADDR(CommonFunc.addrMasking(tbsView.get(i).getCPN_ADDR()));
			tbsView.get(i).setCPN_TEL(CommonFunc.telMasking(tbsView.get(i).getCPN_TEL()));
		}

		mav.addObject("tbsView", tbsView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 상세 검색 팝업창
	@GetMapping("/subSearch02")
	public String subSearch02() {

		return "oms_sub_search02";
	}
	
	// 상세 검색 팝업창 !@@0701 yh
	@GetMapping("/subSearch02_02")
	public String subSearch02_02() {
	
		return "oms_sub_search02_02";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getSubSearch02
	 * @return : ModelAndView
	 * @Explan : 주문채널사 상세 검색
	 */
	@RequestMapping("/getSubSearch02")
	@ResponseBody
	public ModelAndView getSubSearch02(Criteria cri, ModelAndView mav) throws Exception {

		List<EM_COMPANY_MASTER_OMS> tocView = service.getSearchChannel(cri);

		int total = service.getSearchChannelTotal(cri);

		mav.addObject("tocView", tocView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	// 상세 검색 팝업창
	@GetMapping("/subSearch03")
	public String subSearch03() {

		return "oms_sub_search03";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getSubSearch03
	 * @return : ModelAndView
	 * @Explan : 배달대행사 상세 검색
	 */
	@RequestMapping("/getSubSearch03")
	@ResponseBody
	public ModelAndView getSubSearch03(Criteria cri, ModelAndView mav) throws Exception {

		List<EM_COMPANY_MASTER_OMS> tdcView = service.getSearchDelivery(cri);

		int total = service.getSearchDeliveryTotal(cri);

		mav.addObject("tdcView", tdcView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}


	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getSubSearch04
	 * @return : ModelAndView
	 * @Explan : 상품 상세 검색
	
	@RequestMapping("/getSubSearch04")
	@ResponseBody
	public ModelAndView getSubSearch04(Criteria cri, ModelAndView mav) throws Exception {

		List<EM_MENU_MASTER_OMS> tpmView = service.getSearchProduct(cri);

		int total = service.getSearchProductTotal(cri);

		mav.addObject("tpmView", tpmView);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	} */

}
