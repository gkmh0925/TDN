package oms.kisvan.emart.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : 유저 권한 체크 (어드민 | 마스터)
 */
public class sub05_Interceptor extends WebContentInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {

		try {
			HttpSession session = request.getSession();

			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

			if (uSession.getUSER_LV().equals("M") || uSession.getUSER_LV().equals("A")) {
				return true;
			} else {
				throw new UrlNotFoundException();
			}

		} catch (Exception e) {
			throw new UrlNotFoundException();
		}
	}
}
