package oms.kisvan.emart.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : 동일 세션 동일 아이피인지 체크
 */
public class Session_IP_Check implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {

		HttpSession session = request.getSession();

		try {

			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

			String ip = request.getHeader("X-Forwarded-For");

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

			if (!uSession.getIP_ADDRESS().equals(ip)) {

				mav = new ModelAndView("/error2");
				mav.addObject("errCode", 101);
				throw new ModelAndViewDefiningException(mav);
			}
		} catch (Exception e) {
			mav = new ModelAndView("/error2");
			mav.addObject("errCode", 100);
			throw new ModelAndViewDefiningException(mav);
		}
	}
}
