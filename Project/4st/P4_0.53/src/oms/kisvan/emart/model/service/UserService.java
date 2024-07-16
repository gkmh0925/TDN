package oms.kisvan.emart.model.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.model.dao.UserDAO;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EM_USER_AUTH_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 유저 서비스
 */
@Service
public class UserService {

	@Setter(onMethod_ = @Autowired)
	private UserDAO dao;

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 로그인 정보 확인
	public EM_USER_ACCOUNT_OMS loginCheck(EM_USER_ACCOUNT_OMS uao) {
		return dao.loginCheck(sqlSession, uao);
	}

	// 유저 등록
	public int UserInsert(EM_USER_ACCOUNT_OMS tu) {
		return dao.UserInsert(sqlSession, tu);
	}

	// 유저 수정
	public int UserUpdate(EM_USER_ACCOUNT_OMS tbu) {
		return dao.UserUpdate(sqlSession, tbu);
	}

	// 유저 검색
	public List<EM_USER_ACCOUNT_OMS> UserSearchList(Map<String, Object> paramMap) {
		return dao.userSearchList(sqlSession, paramMap);
	}

	// 유저 상세
	public EM_USER_ACCOUNT_OMS UserDetail(String user_nm) {
		return dao.UserDetail(sqlSession, user_nm);
	}

	// 검색된 유저 수
	public int getSearchUserTotal(Map<String, Object> paramMap) {
		return dao.getSearchUserTotal(sqlSession, paramMap);
	}

	// 아이디 확인
	public int getUserCheck(String user_id) {
		return dao.getUserCheck(sqlSession, user_id);
	}

	// 사용자명 확인
	public int getNmCheck(String user_nm) {
		return dao.getNmCheck(sqlSession, user_nm);
	}

	// 인증번호 저장
	public int setSMSauthentication(EM_USER_ACCOUNT_OMS smsIns) {
		return dao.setSMSauthentication(sqlSession, smsIns);
	}

	// 인증번호 체크
	public EM_USER_ACCOUNT_OMS SMSauthCheck(EM_USER_ACCOUNT_OMS uao) {
		return dao.SMSauthCheck(sqlSession, uao);
	}

	public List<EM_USER_AUTH_OMS> getUserLevel() {
		return dao.getUserLevel(sqlSession);
	}
}
