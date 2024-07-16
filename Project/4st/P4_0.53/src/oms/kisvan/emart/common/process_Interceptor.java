package oms.kisvan.emart.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : _process 매핑 메서드 호출 전 세션 확인
 */
public class process_Interceptor extends WebContentInterceptor {
	// controller로 보내기 전에 처리하는 인터셉터
	// 반환이 false라면 controller로 요청을 안함
	// 매개변수 Object는 핸들러 정보를 의미한다. ( RequestMapping , DefaultServletHandler )
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {

		try {
			HttpSession session = request.getSession();

			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");

			if (uSession != null) {
				return true;
			} else {
				throw new UrlNotFoundException();
			}

		} catch (Exception e) {
			throw new UrlNotFoundException();
		}
	}
}
