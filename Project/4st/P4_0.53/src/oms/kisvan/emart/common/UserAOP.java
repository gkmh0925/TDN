package oms.kisvan.emart.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : 패스워드 암호화 AOP
 */
@Service
@Aspect
public class UserAOP {

	@Autowired
	@Qualifier("Sha256Util")
	private Sha256Util enc;

	// 로그인 할때
	@Pointcut("execution(* oms.kisvan.emart.model.service.UserService.loginCheck(..))")
	public void LoginPointcut() {
	}

	// 계정등록할때
	@Pointcut("execution(* oms.kisvan.emart.model.service.UserService.UserInsert(..))")
	public void PasswordEncryption() {
	}

	// 계정상세정보
	@Pointcut("execution(* oms.kisvan.emart.model.service.UserService.UserDetail(..))")
	public void PasswordDecryption() {

	}

	// SMS업데이트
	@Pointcut("execution(* oms.kisvan.emart.model.service.UserService.setSMSauthentication(..))")
	public void setSMSauthentication() {

	}

	// 회원 로그인
	@Before("LoginPointcut()")
	public void setSMSauthentication(JoinPoint jp) throws Exception {

		this.UserInsertPointcut(jp);
	}

	// SMS업데이트
	@Before("setSMSauthentication()")
	public void UserLoginPointcut(JoinPoint jp) throws Exception {

		this.UserInsertPointcut(jp);
	}

	@Before("PasswordEncryption()")
	public void UserInsertPointcut(JoinPoint jp) throws Exception {
		// 비밀번호 암호화를 위해 Id/ PW를 추출
		EM_USER_ACCOUNT_OMS tu = ((EM_USER_ACCOUNT_OMS) jp.getArgs()[0]);
		String PASSWORD = tu.getPASSWORD();

		String encryUserPwd = enc.encryData(PASSWORD);

		tu.setPASSWORD(encryUserPwd); // 암호화 된 데이터를 객체에 넣어줌
		return;
	}

	/*
	 * @Before("PasswordDecryption()") public void UserDetailPointcut(JoinPoint jp)
	 * throws Exception { this.UserInsertPointcut(jp); return; }
	 */

}
