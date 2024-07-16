package oms.kisvan.emart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.Setter;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.service.ModifyService;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 수정 컨트롤러
 */
@Controller
public class ModifyController {

	@Setter(onMethod_ = {@Autowired})
	private ModifyService service;

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : oms_sub01_01edit
	 * @return : void
	 * @Explan : 매장 수정
	 */
	@RequestMapping(value = "/oms_sub01_01edit")
	@ResponseBody
	public void oms_sub01_01edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		map.put("session_user_cd", uSession.getUSER_CD());

		try {
			@SuppressWarnings("unchecked")
			HashMap<String, String> modiArr = (HashMap<String, String>) map.get("comp_master");
			// XSS 방지
			modiArr.forEach((key, value) -> {
				value = XssPreventer.escape(value);
				modiArr.replace(key, value);
			});

			map.put("comp_master", modiArr);

			service.setUpdateSub02_80detail(map);
			response.getWriter().print(true);

		} catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

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
	
	
	@RequestMapping(value = "/oms_sub02_80edit")
	@ResponseBody
	public void oms_sub02_80edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		Integer AAA = uSession.getUSER_CD();
		map.put("mdf_user_cd", AAA);
		
		map.put("mdf_user_cd", uSession.getUSER_CD());

		try {
			@SuppressWarnings("unchecked")
			HashMap<String, String> modiArr = (HashMap<String, String>) map.get("comp_master");  
			// XSS 방지
			modiArr.forEach((key, value) -> {
				value = XssPreventer.escape(value);
				modiArr.replace(key, value);
			});

			map.put("comp_master", modiArr);

			System.out.println(">>>>>>>>>"+map);
			
			
			service.setUpdateSub02_80detail(map);
			response.getWriter().print(true);

		} catch (Exception e) {
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

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
	
	
	@RequestMapping(value = "/oms_sub02_80remove") 
	@ResponseBody
	public void oms_sub02_80remove(@RequestBody HashMap<String, Object> map, HttpSession session,
	        HttpServletResponse response) throws IOException {

	    try {
	        // 삭제 요청을 처리하는 서비스 메서드 호출
	        service.setDeleteSub02_80detail(map);
	        
	        // 클라이언트에게 성공 메시지 응답
	        response.getWriter().print(true);


	    } catch (Exception e) {
 	        final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
 	        final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

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

	
	
//
//	@SuppressWarnings("null")
//	@RequestMapping(value = "/oms_sub01_01editArray")
//	@ResponseBody
//	public void oms_sub01_01editArray(@RequestParam String[][] modifyArr, HttpSession session,
//			HttpServletResponse response) throws IOException {
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		Map<String, String> map = null;
//		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//
//		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
//
//		String[] listKey = { "CH_CD", "CH_STATE", "CH_SEND_FLAG", "STO_CD" };
//		try {
//			for (int r = 0; r < modifyArr.length; r++) {
//				map = new HashMap<String, String>();
//				for (int c = 0; c < modifyArr[r].length; c++) {
//					if (modifyArr[r][c].equals("temp")) {
//						break;
//					}
//
//					map.put(listKey[c], modifyArr[r][c].toString());
//
//				}
//				result.add(map);
//			}
//
//			paramMap.put("chst_op", result);
//			paramMap.put("MDF_USER_CD", uSession.getUSER_CD());
//
//			service.setChannelStoreOper(paramMap);
//			response.getWriter().print(true);
//
//		} catch (Exception e) {
//			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
//			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
//
//			System.out.println("ERROR = = = = = = = " + e.getMessage());
//
//			if (matcher.find()) {
//				String errCd = matcher.group(1).trim();
//				response.setStatus(400);
//				response.getWriter().write(errCd);
//			} else {
//				response.setStatus(400);
//				response.getWriter().write("1");
//			}
//		}
//	}
//
//	@RequestMapping(value = "/oms_sub02_01edit")
//	@ResponseBody
//	public void oms_sub02_01edit(@RequestBody HashMap<String, Object> map, HttpSession session,
//			HttpServletResponse response) throws IOException {
//		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
//
//		map.put("mdf_user_cd", uSession.getUSER_CD());
//
//		try {
//			@SuppressWarnings("unchecked")
//			HashMap<String, String> modiArr = (HashMap<String, String>) map.get("menu_master");
//
//			modiArr.forEach((key, value) -> {
//				value = XssPreventer.escape(value);
//				modiArr.replace(key, value);
//			});
//
//			map.put("menu_master", modiArr);
//
//			service.setUpdateSub02_01detail(map);
//			response.getWriter().print(true);
//
//		} catch (Exception e) {
//			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
//			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
//
//			if (matcher.find()) {
//				String errCd = matcher.group(1).trim();
//				response.setStatus(400);
//				response.getWriter().write(errCd);
//			} else {
//				response.setStatus(400);
//				response.getWriter().write("1");
//			}
//		}
//	}
//
//	}
	//채널사  상세페이지 수정 페이지
	@RequestMapping(value = "/oms_sub05_02edit")
	@ResponseBody
	public void oms_sub05_02edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		map.put("MDF_USER_CD", uSession.getUSER_CD()); //수정시 사용자 이름을 불러오기위함

		try {
			service.setUpdateChannelData(map); // 데이터 수정시 업데이트
			response.getWriter().print(true);  // 수정시 오류가 없을경우 실행

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
	//채널사 수수료마스터 수정페이지
	@RequestMapping(value = "/oms_sub05_02edit_fee")
	@ResponseBody
	public void oms_sub05_02edit_fee(@RequestBody List<HashMap<String, Object>> list, HttpSession session,
	        HttpServletResponse response) throws IOException {
	    EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

	    for (HashMap<String, Object> map : list) {
	        map.put("MDF_USER_CD", uSession.getUSER_CD()); // 수정시 사용자 이름을 불러오기 위함
	    }

	    try {
	        service.setUpdateChannelFeeData(list); // 데이터 수정시 업데이트
	       
	        response.getWriter().print(true); // 수정시 오류가 없을 경우 실행

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
	//채널사,배달사 수수료마스터 삭제버튼
	@RequestMapping(value = "/oms_sub05del_fee")
	@ResponseBody
	public void oms_sub05_02del_fee(@RequestParam HashMap<String, Object> map, HttpSession session,
	        HttpServletResponse response) throws IOException {

	    service.delFeeMasterData(map); // 데이터 수정시 업데이트

	    response.getWriter().print(true); // 수정시 오류가 없을 경우 실행

	}
	


	
	
	//배달대행사기본정보 상세 수정 페이지
	@RequestMapping(value = "/oms_sub05_03edit")
	@ResponseBody
	public void oms_sub05_03edit(@RequestBody HashMap<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

		map.put("MDF_USER_CD", uSession.getUSER_CD());

		try {
			service.setUpdateChannelData(map);
			response.getWriter().print(true);

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
	//배달대행사 수수료마스터 수정페이지 
	@RequestMapping(value = "/oms_sub05_03edit_fee")
	@ResponseBody
	public void oms_sub05_03edit_fee(@RequestBody List<HashMap<String, Object>> list, HttpSession session,
	        HttpServletResponse response) throws IOException {
	    EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

	    for (HashMap<String, Object> map : list) {
	        map.put("MDF_USER_CD", uSession.getUSER_CD()); // 수정시 사용자 이름을 불러오기 위함
	    }

	    try {
	        service.setUpdateDeliveryFeeData(list); // 데이터 수정시 업데이트
	       
	        response.getWriter().print(true); // 수정시 오류가 없을 경우 실행

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

	
}
