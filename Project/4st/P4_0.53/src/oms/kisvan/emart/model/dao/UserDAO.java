package oms.kisvan.emart.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EM_USER_AUTH_OMS;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 유저 Data Access Object
 */
@Repository
public class UserDAO {

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : loginCheck
	 * @return : EM_USER_ACCOUNT_OMS
	 * @Explan : 로그인 정보 확인
	 */
	public EM_USER_ACCOUNT_OMS loginCheck(SqlSessionTemplate sqlSession, EM_USER_ACCOUNT_OMS uao) {
		/*
		 * Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
		 * StringBuffer query = new StringBuffer(); EM_USER_ACCOUNT_OMS rtn = new
		 * EM_USER_ACCOUNT_OMS();
		 * 
		 * query.append("SELECT USER_CD, USER_NM, ACCOUNT, PASSWORD, USER_LV, CPN_CD ");
		 * query.append("FROM EM_USER_ACCOUNT_OMS ");
		 * query.append("WHERE ACCOUNT = ? AND PASSWORD = ? ");
		 * 
		 * try { conn =
		 * DriverManager.getConnection("jdbc:oracle:thin:@10.98.76.23:6521/DLDB",
		 * "emartdev", "emartdev09"); stmt = conn.prepareStatement(query.toString());
		 * 
		 * stmt.setString(1, uao.getACCOUNT()); stmt.setString(2, uao.getPASSWORD());
		 * 
		 * rs = stmt.executeQuery();
		 * 
		 * if(rs.next()) { rtn.setUSER_CD(rs.getInt("USER_CD"));
		 * rtn.setUSER_NM(rs.getString("USER_NM"));
		 * rtn.setACCOUNT(rs.getString("ACCOUNT"));
		 * rtn.setPASSWORD(rs.getString("PASSWORD"));
		 * rtn.setUSER_LV(rs.getString("USER_LV"));
		 * rtn.setCPN_CD(rs.getString("CPN_CD")); } } catch(Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * 
		 * return rtn;
		 */
		return sqlSession.selectOne("user.loginCheck", uao);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserInsert
	 * @return : int
	 * @Explan : 유저 등록
	 */
	public int UserInsert(SqlSessionTemplate sqlSession, EM_USER_ACCOUNT_OMS tu) {
		return sqlSession.insert("user.userInsert", tu);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserUpdate
	 * @return : int
	 * @Explan : 유저 수정
	 */
	public int UserUpdate(SqlSessionTemplate sqlSession, EM_USER_ACCOUNT_OMS tbu) {
		return sqlSession.insert("user.userUpdate", tbu);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : userSearchList
	 * @return : List<EM_USER_ACCOUNT_OMS>
	 * @Explan : 유저 검색
	 */
	public List<EM_USER_ACCOUNT_OMS> userSearchList(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {
		return sqlSession.selectList("user.userSearchList", paramMap);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : UserDetail
	 * @return : EM_USER_ACCOUNT_OMS
	 * @Explan : 유저 상세
	 */
	public EM_USER_ACCOUNT_OMS UserDetail(SqlSessionTemplate sqlSession, String user_nm) {

		return sqlSession.selectOne("user.userDetail", user_nm);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getSearchUserTotal
	 * @return : int
	 * @Explan : 검색된 계정 개수
	 */
	public int getSearchUserTotal(SqlSessionTemplate sqlSession, Map<String, Object> paramMap) {

		return sqlSession.selectOne("user.getSearchUserTotal", paramMap);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getUserCheck
	 * @return : int
	 * @Explan : 유저 아이디 중복 체크
	 */
	public int getUserCheck(SqlSessionTemplate sqlSession, String user_id) {

		return sqlSession.selectOne("user.getUserCheck", user_id);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getNmCheck
	 * @return : int
	 * @Explan : 유저 이름 중복 체크
	 */
	public int getNmCheck(SqlSessionTemplate sqlSession, String user_nm) {

		return sqlSession.selectOne("user.getNmCheck", user_nm);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : setSMSauthentication
	 * @return : int
	 * @Explan : 인증번호 저장
	 */
	public int setSMSauthentication(SqlSessionTemplate sqlSession, EM_USER_ACCOUNT_OMS smsIns) {

		return sqlSession.update("setSMSauthentication", smsIns);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : SMSauthCheck
	 * @return : EM_USER_ACCOUNT_OMS
	 * @Explan : 인증번호 체크
	 */
	public EM_USER_ACCOUNT_OMS SMSauthCheck(SqlSessionTemplate sqlSession, EM_USER_ACCOUNT_OMS uao) {

		return sqlSession.selectOne("SMSauthCheck", uao);
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 30.
	 * @Method : getUserLevel
	 * @return : List<EM_USER_AUTH_OMS>
	 * @Explan : 유저 레벨 가져오기
	 */
	public List<EM_USER_AUTH_OMS> getUserLevel(SqlSessionTemplate sqlSession) {

		return sqlSession.selectList("getUserLevel");
	}
}
