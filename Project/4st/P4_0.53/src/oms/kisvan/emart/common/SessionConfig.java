package oms.kisvan.emart.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : 중복 로그인 체크 클래스
 */
@WebListener
public class SessionConfig implements HttpSessionListener {
	private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

	// 중복로그인 지우기
	public synchronized static String getSessionidCheck(String type, String compareId) {
		String result = "";
		for (String key : sessions.keySet()) {
			HttpSession value = sessions.get(key);
			if (value != null && value.getAttribute(type) != null
					&& value.getAttribute(type).toString().equals(compareId)) {
				System.out.println("중복 로그인 감지");
				result = key.toString();
			}
		}
		removeSessionForDoubleLogin(result);
		return result;
	}

	private static void removeSessionForDoubleLogin(String userId) {
		System.out.println("유저 세션 삭제 : " + userId);
		if (userId != null && userId.length() > 0) {
			sessions.get(userId).invalidate();
			sessions.remove(userId);
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		sessions.put(hse.getSession().getId(), hse.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		if (sessions.get(hse.getSession().getId()) != null) {
			sessions.get(hse.getSession().getId()).invalidate();
			sessions.remove(hse.getSession().getId());
		}
	}
}