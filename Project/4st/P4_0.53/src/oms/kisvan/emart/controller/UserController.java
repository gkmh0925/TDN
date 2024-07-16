package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.Setter;
import oms.kisvan.emart.common.SessionConfig;
import oms.kisvan.emart.common.Sha256Util;
import oms.kisvan.emart.common.UrlNotFoundException;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EM_USER_AUTH_OMS;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.service.UserService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : 유저 컨트롤러
 */
@Controller
@SessionAttributes("UserInfo")
public class UserController {

	@Setter(onMethod_ = {@Autowired})
	private UserService service;

	@Autowired
	@Qualifier("Sha256Util")
	private Sha256Util enc;

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : loginCustomer
	 * @return : ModelAndView
	 * @Explan : 로그인 체크 로직
	 */
	@RequestMapping(value = "loginCheck")
	@ResponseBody
	public ModelAndView loginCustomer(@RequestParam String ACCOUNT, @RequestParam String PASSWORD, HttpSession session,
			HttpServletRequest request, ModelAndView mav) {

		EM_USER_ACCOUNT_OMS uao = new EM_USER_ACCOUNT_OMS();

		uao.setACCOUNT(ACCOUNT);
		uao.setPASSWORD(PASSWORD);

		EM_USER_ACCOUNT_OMS loginCheck = service.loginCheck(uao);

		if (loginCheck != null) {
			String ip = request.getHeader("X-Forwarded-For");

			// ip체크
			if (ip == null) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null) {
				ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
			}
			if (ip == null) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null) {
				ip = request.getRemoteAddr();
			}

			loginCheck.setIP_ADDRESS(ip);

			// 중복 세션 확인
			String result = SessionConfig.getSessionidCheck("UserInfo", loginCheck.toString());
			session.setMaxInactiveInterval(60 * 60);

			if (StringUtils.isNotBlank(result)) {
				mav.addObject("duplication", true);
			}

			System.out.println(ip + " Client Login");

			// 세션 추가
			session.setAttribute("UserInfo", loginCheck); // sms check 인증 값 user info -> 권한레벨 USER_LV

			mav.addObject("logStat", true);

		} else {
			mav.addObject("logStat", false);
		}

		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : memberLogout
	 * @return : String
	 * @Explan : 로그아웃
	 */
	@RequestMapping(value = "/logoutCustomer")
	public String memberLogout(@ModelAttribute("UserInfo") EM_USER_ACCOUNT_OMS UserInfo, SessionStatus sessionStatus) {
		sessionStatus.setComplete();

		return "redirect:/";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : userList
	 * @return : String
	 * @Explan : 유저 검색 페이지
	 */
	@RequestMapping("/userList")
	public String userList(HttpSession session) {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		// 마스터 권한 체크
		if (!uSession.getUSER_LV().equals("M")) {
			throw new UrlNotFoundException();
		}

		return "/oms_sub05_01";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserSearch
	 * @return : ModelAndView
	 * @Explan : 유저 검색
	 */
	@PostMapping(value = "/getUserList")
	@ResponseBody
	public ModelAndView UserSearch(HttpSession session, String userNm, Criteria cri,
			@RequestParam Map<String, Object> paramMap, ModelAndView mav) throws IOException, NumberFormatException {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		// 마스터 권한 체크
		if (!uSession.getUSER_LV().equals("M")) {
			throw new UrlNotFoundException();
		}

		paramMap.put("userNm", userNm);
		paramMap.put("cri", cri);

		// 유저 리스트
		List<EM_USER_ACCOUNT_OMS> tuaccount = service.UserSearchList(paramMap);

		// 페이징 위한 총 개수
		int total = service.getSearchUserTotal(paramMap);

		mav.addObject("tuaccount", tuaccount);
		mav.addObject("pageMaker", new PagingDTO(cri, total));

		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserDetail
	 * @return : String
	 * @Explan : 유저 상세 페이지
	 */
	@RequestMapping(value = "/userList_detail")
	public String UserDetail(HttpSession session, Model model, @RequestParam String user_cd)
			throws IOException, NumberFormatException {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
		// 마스터 권한 체크
		if (!uSession.getUSER_LV().equals("M")) {
			throw new UrlNotFoundException();
		}

		EM_USER_ACCOUNT_OMS tuaccount = service.UserDetail(user_cd);
		List<EM_USER_AUTH_OMS> userLv = service.getUserLevel();

		model.addAttribute("userLv", userLv);
		model.addAttribute("tuaccount", tuaccount);
		return "oms_sub05_01_detail";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserUpdate
	 * @return : void
	 * @Explan : 유저 정보 수정
	 */
	@RequestMapping(value = "/userUpdate")
	@ResponseBody
	public void UserUpdate(Model model, HttpServletResponse response, EM_USER_ACCOUNT_OMS uao,
			@RequestParam HashMap<String, String> paramMap, HttpSession session) throws Exception {
		if (paramMap.get("pwd").length() < 15) {
			String encryUserPwd = enc.encryData(paramMap.get("pwd"));
			uao.setPASSWORD(encryUserPwd); // 암호화 된 데이터를 객체에 넣어줌
		} else {
			uao.setPASSWORD(paramMap.get("pwd"));
		}

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		// XSS 방지
		paramMap.forEach((key, value) -> {
			value = XssPreventer.escape(value);
			paramMap.replace(key, value);
		});

		uao.setUSER_CD(Integer.parseInt(paramMap.get("user_cd")));
		uao.setUSER_LV(paramMap.get("userLv"));
		uao.setACCOUNT(paramMap.get("user_id"));
		uao.setUSER_NM(paramMap.get("user_nm"));
		uao.setPHONE(paramMap.get("phone"));
		uao.setUSER_EMAIL(paramMap.get("email"));
		uao.setUSER_ADDR(paramMap.get("addr"));
		uao.setUSER_ADDR_DETAIL(paramMap.get("addr_dt"));
		uao.setMDF_USER_CD(uSession.getUSER_CD());
		uao.setCPN_CD(paramMap.get("cpncd"));

		int userUpdate = service.UserUpdate(uao);

		if (userUpdate > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : insertPage
	 * @return : String
	 * @Explan : 유저 등록 페이지
	 */
	@RequestMapping(value = "/userRegister")
	public String insertPage(Model model, HttpSession session) {

		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		if (!uSession.getUSER_LV().equals("M")) {
			throw new UrlNotFoundException();
		}

		List<EM_USER_AUTH_OMS> userLv = service.getUserLevel();

		model.addAttribute("userLv", userLv);

		return "oms_sub05_01_insert";
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : userCheck
	 * @return : ModelAndView
	 * @Explan : 계정 중복 체크
	 */
	@RequestMapping(value = "/userCheck")
	@ResponseBody
	public ModelAndView userCheck(String user_id, ModelAndView mav) throws IOException, NumberFormatException {

		int checkId = service.getUserCheck(user_id);

		mav.addObject("userId", checkId);

		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : nmCheck
	 * @return : ModelAndView
	 * @Explan : 이름 중복 체크
	 */
	@RequestMapping(value = "/nmCheck")
	@ResponseBody
	public ModelAndView nmCheck(String user_nm, ModelAndView mav) throws IOException, NumberFormatException {

		int checkNd = service.getNmCheck(user_nm);

		mav.addObject("userNm", checkNd);

		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserInsert
	 * @return : void
	 * @Explan : 유저 등록
	 */
	@RequestMapping(value = "/userInsert")
	@ResponseBody
	public void UserInsert(Model model, HttpServletResponse response, EM_USER_ACCOUNT_OMS uao,
			@RequestParam HashMap<String, String> paramMap, HttpSession session) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		String phone = String.join("", paramMap.get("phone").split("-"));

		// XSS 방지
		paramMap.forEach((key, value) -> {
			value = XssPreventer.escape(value);
			paramMap.replace(key, value);
		});

		uao.setUSER_LV(paramMap.get("userLv"));
		uao.setACCOUNT(paramMap.get("user_id"));
		uao.setPASSWORD(paramMap.get("pwd"));
		uao.setUSER_NM(paramMap.get("user_nm"));
		uao.setPHONE(phone);
		uao.setUSER_EMAIL(paramMap.get("email"));
		uao.setREG_USER_CD(uSession.getUSER_CD());
		uao.setUSER_ADDR(paramMap.get("addr"));
		uao.setUSER_ADDR_DETAIL(paramMap.get("addr_dt"));
		uao.setCPN_CD(paramMap.get("cpncd"));

		int userInsert = service.UserInsert(uao);

		if (userInsert > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}
}
