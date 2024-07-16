package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import oms.kisvan.emart.common.UrlNotFoundException;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_CMS_OPERATION_OMS;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.ChannelService;

@Controller
public class ChannelController {
	@Setter(onMethod_ = {@Autowired})
	private ChannelService service;
	//채널사관리 페이지
	@RequestMapping("/orderchannelList")
	public String oms_sub05_02(Model model, HttpSession session) {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		
		if(!uSession.getUSER_LV().equals("M") && !uSession.getUSER_LV().equals("A")) {
			throw new UrlNotFoundException(); //채널사 페이지 접근권한 마스터~관리자 외엔 보이지 않게 처리
		}
		
		List<EM_REFERENCE_CODE_OMS> stcode = service.GetCPNState();
		model.addAttribute("stcode", stcode); //업체운영상태 (오픈,중지)

		return "/oms_sub05_02";
	}
//채널사 검색
	@RequestMapping("/oms_sub05_02_process")
	@ResponseBody
	public ModelAndView OrderChannelSearch(String cpncd, String sst, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("cpntype", "ORDER");// 업체타입
		paramMap.put("cpncd", cpncd);//업체코드
		paramMap.put("sst", sst);//업체상태구분 select box(옵션박스(오픈,중지))
		paramMap.put("cri", cri);//페이징을위한 데이터 수 구하기 1~200개의 데이터까지 불러옴

		List<EM_COMPANY_MASTER_OMS> ordChList = service.ChannelSearch(paramMap);// 전체 검색문

		int total = service.getOrderChannelTotal(paramMap); //매장 검색 정보 총 게시글 개수 확인 채널사

		mav.addObject("ordChList", ordChList); //전체검색리스트                
		mav.addObject("pageMaker", new PagingDTO(cri, total)); //페이징 처리
		mav.setViewName("jsonView");

		return mav;
	}
//배달대행사 페이지
	@RequestMapping("/deliverychannelList")
	public String oms_sub05_03(Model model, HttpSession session) {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		
		if(!uSession.getUSER_LV().equals("M") && !uSession.getUSER_LV().equals("A")) {
			throw new UrlNotFoundException();
		}		
		List<EM_REFERENCE_CODE_OMS> stcode = service.GetCPNState();
		model.addAttribute("stcode", stcode);

		return "/oms_sub05_03";
	}
//배달대행사 검색
	@RequestMapping("/oms_sub05_03_process")
	@ResponseBody
	public ModelAndView DeliverySearch(String cpncd, String sst, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		paramMap.put("cpntype", "DELIVERY");
		paramMap.put("cpn", cpncd);
		paramMap.put("sst", sst);
		paramMap.put("cri", cri);

		List<EM_COMPANY_MASTER_OMS> dvlList = service.ChannelSearch(paramMap);

		int total = service.getOrderDeliveryTotal(paramMap);

		mav.addObject("dvlList", dvlList);
		mav.addObject("pageMaker", new PagingDTO(cri, total));
		mav.setViewName("jsonView");

		return mav;
	}

//채널사 수수료등록
	@RequestMapping("/channel_fee_register")
	@ResponseBody
	public void ChannelFeeRegister(@RequestBody HashMap<String, Object> regVal, HttpSession session,
	        HttpServletResponse response) throws IOException {
	    List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) regVal.get("list");

	    EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
	    for (HashMap<String, Object> map : list) {
	        map.put("REG_USER_CD", uSession.getUSER_CD()); // 업체등록시 등록사용자를 저장
	    }

	    try {
	        service.setInsetChannelDataFee(list); // insert 데이터
	        response.getWriter().print(true); // insert 데이터를 전송시 오휴가 없을경우 success (실행)  
	    } catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
			
			System.out.println("ERROR = = = = = = = " + e.getMessage());
			
			if (matcher.find()) {
				String errCd = matcher.group(1).trim();
				response.setStatus(400);
				response.getWriter().write(errCd);
			} else {
				response.setStatus(400);
				response.getWriter().write("4");
			}
		}
	}
//배달사 수수료등록
	@RequestMapping("/delivery_fee_register")
	@ResponseBody
	public void DeliveryFeeRegister(@RequestBody HashMap<String, Object> regVal, HttpSession session,
			HttpServletResponse response) throws IOException {
		List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) regVal.get("list");
		
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		for (HashMap<String, Object> map : list) {
			map.put("REG_USER_CD", uSession.getUSER_CD()); // 업체등록시 등록사용자를 저장
		}
		
		try {
			service.setInsetDeliveryDataFee(list); // insert 데이터
			response.getWriter().print(true); // insert 데이터를 전송시 오휴가 없을경우 success (실행)  
		} catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
			
			System.out.println("ERROR = = = = = = = " + e.getMessage());
			
			if (matcher.find()) {
				String errCd = matcher.group(1).trim();
				response.setStatus(400);
				response.getWriter().write(errCd);
			} else {
				response.setStatus(400);
				response.getWriter().write("4");
			}
		}
	}
//채널사,배달사 등록
	@RequestMapping("/channel_register")
	@ResponseBody
	public void ChannelRegister(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		map.put("REG_USER_CD", uSession.getUSER_CD()); // 업체등록시 등록사용자를 저장

		try {
			service.setInsetChannelData(map); // insert 데이터
			response.getWriter().print(true); // insert 데이터를 전송시 오류가 없을경우 success (실행)  

		} catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("ERROR = = = = = = = " + e.getMessage());

			if (matcher.find()) {
				String errCd = matcher.group(1).trim();
				response.setStatus(400);
				response.getWriter().write(errCd);
			} else {
				response.setStatus(400);
				response.getWriter().write("1");
			}
		}
	}
	//채널사  상세(수수료마스터 포함)
	@RequestMapping(value = "/oms_sub05_02_detail")
	public String oms_sub05_02_detail(Model model,String CPN_CD, @RequestParam HashMap<String, Object> paramMap)
			throws IOException, NumberFormatException {
		//업체상태구분 Open(오픈),Stop(중지)를 불러온다
		List<EM_REFERENCE_CODE_OMS> stcode = service.GetCPNState();
		//수수료타입을 가져옴
		List<EM_REFERENCE_CODE_OMS> cmsCardApplyType = service.GetCMS_CARD_APPLY_TYPE_ORDER(paramMap);
		//수수료타입을 가져옴
		List<EM_REFERENCE_CODE_OMS> cmsCalcType = service.GetCMS_CALC_TYPE();
		//채널사 상세페이지 정보를 불러오기
	//	String cpncd = (String) paramMap.get("cpncd");
		paramMap.put("CPN_CD", CPN_CD);
		EM_COMPANY_MASTER_OMS cmo = service.getORDCPN_Detail(CPN_CD);
		List <EM_CMS_OPERATION_OMS> coo = service.getFEE_ORDCPN_Detail(paramMap);
		List <EM_CMS_OPERATION_OMS> coo_card = service.getCARD_FEE_ORDCPN_Detail(paramMap);


		model.addAttribute("stcode", stcode); //업체상태구분값
		model.addAttribute("cmsCardApplyType", cmsCardApplyType); //수수료마스터 수수료타입 구분값/기본
		model.addAttribute("cmsCalcType", cmsCalcType); //수수료마스터 수수료금액 타입 구분값
		model.addAttribute("ORDER", cmo);     //채널사 cpntype
		model.addAttribute("FEE_ORDER", coo);     //채널사 수수료마스터 상세정보데이터
		model.addAttribute("CARD_FEE_ORDER", coo_card);     //채널사 수수료마스터 상세정보데이터

		return "oms_sub05_02_detail";
	}
	//배달대행사 상세(수수료마스터 포함)
	@RequestMapping(value = "/oms_sub05_03_detail")
	public String oms_sub05_03_detail(Model model, String CPN_CD, @RequestParam HashMap<String, Object> paramMap)
			throws IOException, NumberFormatException {

		//업체상태구분 Open(오픈),Stop(중지)를 불러온다
				List<EM_REFERENCE_CODE_OMS> stcode = service.GetCPNState();
				//수수료타입을 가져옴
				List<EM_REFERENCE_CODE_OMS> cmsApplyType = service.GetCMS_APPLY_TYPE_DELIVERY();
				//수수료타입을 가져옴
				List<EM_REFERENCE_CODE_OMS> cmsCalcType = service.GetCMS_CALC_TYPE();
				//채널사 상세페이지 정보를 불러오기
			//	String cpncd = (String) paramMap.get("cpncd");
				paramMap.put("CPN_CD", CPN_CD);
				EM_COMPANY_MASTER_OMS cmo = service.getDLVCPN_Detail(CPN_CD);
				List <EM_CMS_OPERATION_OMS> coo = service.getFEE_DELIVERY_Detail(paramMap);

		model.addAttribute("stcode", stcode);
		model.addAttribute("cmsApplyType", cmsApplyType); //수수료마스터 수수료타입 구분값
		model.addAttribute("cmsCalcType", cmsCalcType); //수수료마스터 수수료금액 타입 구분값
		model.addAttribute("DELIVERY", cmo);			//배달사 cpntype
		model.addAttribute("FEE_DELIVERY", coo);     //배달사 수수료마스터 상세정보데이터

		return "oms_sub05_03_detail";
	}
}
