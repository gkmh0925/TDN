package oms.kisvan.emart.model.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_DELIVERY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_BAEMIN_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_BAROGO_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_CHAINLOGIS_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_DEALVER_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_EMART24_DV_PU_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_KAKAO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_KISAPP_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_LOGIALL_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_NAVER_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_POS_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_SPIDOR_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_VROONG_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_PU_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SYNTH_DV_CH_VIEW;
import oms.kisvan.emart.model.dto.EM_SYNTH_ORD_CH_VIEW;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;
import oms.kisvan.emart.model.dto.EM_RELAY_TOTAL_OMS;


/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 엑셀 Data Access Object
 */
@Repository
public class ExcelDAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	
	// 매장 리스트 엑셀 다운로드
	public List<EM_COMPANY_MASTER_OMS> getStoreExcel(HashMap<String, Object> paramMap) {

		return sqlSession.selectList("excel.storeList_excel", paramMap);
	}
	//채널리스트 엑셀 다운로드
	public List<EM_COMPANY_MASTER_OMS> getchannelExcel(HashMap<String, Object> paramMap) {
		
		return sqlSession.selectList("excel.channel_excel", paramMap);
	}

	// 상품 리스트 엑셀 다운로드
	public List<EM_MENU_MASTER_OMS> getProductExcel(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.productList_excel", paramMap);
	}

	// 주문 리스트 엑셀 다운로드
	public List<TB_ORDER_STATE> getOrderExcel(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.orderList_excel", paramMap);
	}

	// POS 리스트 엑셀 다운로드
	public List<EM_SETTLE_POS_ORD_OMS> getPosExcel(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.posList_excel", paramMap);
	}

	// 요기요 배달  월별 시퀀스 SELECT
	public int ExcelUpload_RELAY_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_RELAY_STARTINDEX",month);
	}
		
	// 요기요 배달  월별 시퀀스 SELECT
	public int ExcelUpload_YGYD_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_YGYD_STARTINDEX",month);
	}
	
	// 요기요 픽업 월별 시퀀스 SELECT
	public int ExcelUpload_YGYP_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_YGYP_STARTINDEX",month);
	}
	// 카카오  월별 시퀀스 SELECT
	public int ExcelUpload_KAKAO_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_KAKAO_STARTINDEX",month);
	}
	// 네이버  월별 시퀀스 SELECT
	public int ExcelUpload_NAVER_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_NAVER_STARTINDEX",month);
	}
	// KIS 월별 시퀀스 SELECT
	public int ExcelUpload_KIS_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_KIS_STARTINDEX",month);
	}
	// 배민 월별 시퀀스 SELECT
	public int ExcelUpload_BAEMIN_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_BAEMIN_STARTINDEX",month);
	}
	// 이마트24 월별 시퀀스 SELECT
	public int ExcelUpload_EMART24_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_EMART24_STARTINDEX",month);
	}
	// 부릉  월별 시퀀스 SELECT
	public int ExcelUpload_VROONG_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_VROONG_STARTINDEX",month);
	}
	// 바로고 월별 시퀀스 SELECT
	public int ExcelUpload_BAROGO_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_BAROGO_STARTINDEX",month);
	}
	// Logiall(생각대로) 월별 시퀀스 SELECT
	public int ExcelUpload_LOGIALL_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_LOGIALL_STARTINDEX",month);
	}
	// Dealver(딜버) 월별 시퀀스 SELECT
	public int ExcelUpload_DEALVER_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_DEALVER_STARTINDEX",month);
	}
	// SPIDOR 스파이더 월별 시퀀스 SELECT
	public int ExcelUpload_SPIDOR_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_SPIDOR_STARTINDEX",month);
	}
	// 체인로지스 월별 시퀀스 SELECT
	public int ExcelUpload_CHAIN_STARTINDEX(String month) {
		return sqlSession.selectOne("excel.ExcelUpload_CHAIN_STARTINDEX",month);
	}
	
	
	String dbUser, dbPass = null;
	
//	private void getDbProperties() {
//		Configuration conf = sqlSessionFactory.getConfiguration();
//		DataSource ds = conf.getEnvironment().getDataSource();
//		try {
//			DatabaseMetaData dmd = ds.getConnection().getMetaData();
//			dbUser = dmd.getUserName();
//			dbPass = "??????????????";			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	Connection conn = null;
	PreparedStatement stmt = null;
	private void getOraConnection(String query) {
		try {
			//localhost
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522/k7", "scott", "1");
			//test
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.98.76.23:6521/DLDB", "emartdev", "emartdev09");
			//real
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.98.76.23:6521/DLDB", "emart24", "emart2409");
			stmt = conn.prepareStatement(query);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 요기요 배달 엑셀 업로드
		public Object ExcelUpload_RELAY(List<Map<String, Object>> list) {
			boolean result = true;

			SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

			try {
				for (Map<String, Object> param : list) {
					session.insert("excel.ExcelUpload_RELAY", param);
				}
			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				result = false;
			} finally {
				session.flushStatements();
				session.close();
			}
			return result;
		}

		
		
	
	// 요기요 배달 엑셀 업로드
	public Object ExcelUpload_YGYD(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_YGYD", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 요기요 픽업 엑셀 업로드
	public Object ExcelUpload_YGYP(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_YGYP", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 네이버 엑셀 업로드
	public Object ExcelUpload_NAVER(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_NAVER", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 카카오 엑셀 업로드
	public Object ExcelUpload_KAKAO(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_KAKAO", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// KIS 엑셀 업로드
	public Object ExcelUpload_KIS(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_KIS", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}
	
	// 배달의민족 엑셀 업로드
	public Object ExcelUpload_BAEMIN(List<Map<String, Object>> list) {
		//외 넣었을까??
		//getDbProperties();
		//System.out.println(dbUser + " : " + dbPass);
		boolean result = true;
		try {
			getOraConnection(makeQueryBaeminExcelUpload());
			int executeCnt = 1;
			for(Map<String, Object> param : list) {
				Integer commSdSupplyAmt = (Integer) param.get("COMM_SD_SUPPLY_AMT");
				Integer commSdPreferentialAmt = (Integer) param.get("COMM_SD_PREFERENTIAL_AMT");
				Integer commPaySettleVat = (Integer) param.get("COMM_PAY_SETTLE_VAT");
				Integer commRelayFeeSupplyAmt = (Integer) param.get("COMM_RELAY_FEE_SUPPLY_AMT");
				Integer commRelayFeeVat = (Integer) param.get("COMM_RELAY_FEE_VAT");
				Integer commBmRdFeeSupplyAmt = (Integer) param.get("COMM_BM_RD_FEE_SUPPLY_AMT");
				Integer commBmRdFeeVat = (Integer) param.get("COMM_BM_RD_FEE_VAT");
				
				//수수료VAT포함 값을 계산하기위함
				Integer commVatIncluded = commSdSupplyAmt +
									commSdPreferentialAmt +
									commPaySettleVat +
									commRelayFeeSupplyAmt+ 
									commRelayFeeVat +
									commBmRdFeeSupplyAmt +
									commBmRdFeeVat;
				int idx = 0;
				stmt.setObject(++idx, param.get("SETTLE_MONTH"));
				stmt.setObject(++idx, param.get("ORD_DATE"));
				stmt.setObject(++idx, param.get("CH_ORD_NO"));
				stmt.setObject(++idx, param.get("CH_CD"));
				stmt.setObject(++idx, param.get("SET_BAEMIN_DV_SEQ"));
				stmt.setObject(++idx, param.get("BRANCH_NM"));
				stmt.setObject(++idx, param.get("EXP_PAY_DATE"));
				stmt.setObject(++idx, param.get("PAY_DATE"));
				stmt.setObject(++idx, param.get("PAY_TIME"));
				stmt.setObject(++idx, param.get("SETTLE_STD_DATE"));
				stmt.setObject(++idx, param.get("ORD_ID"));
				stmt.setObject(++idx, param.get("ORD_STATE"));
				stmt.setObject(++idx, param.get("SALE_ITEM_AMT"));
				stmt.setObject(++idx, param.get("SALE_DV_AMT"));
				stmt.setObject(++idx, param.get("SALE_ENVELOPE_AMT"));
				stmt.setObject(++idx, param.get("SALE_PARTIAL_REFUND"));
				stmt.setObject(++idx, param.get("SALE_SELLER_CPN_AMT"));
				stmt.setObject(++idx, param.get("SALE_SUM"));
				stmt.setObject(++idx, param.get("SETTLE_PAY_CHARGE_TARGET_AMT"));
				stmt.setObject(++idx, param.get("RELAY_FEE_CHARGE_TARGET_AMT"));
				stmt.setObject(++idx, param.get("COMM_SD_SUPPLY_AMT"));
				stmt.setObject(++idx, param.get("COMM_SD_PREFERENTIAL_AMT"));
				stmt.setObject(++idx, param.get("COMM_PAY_SETTLE_VAT"));
				stmt.setObject(++idx, param.get("COMM_RELAY_FEE_SUPPLY_AMT"));
				stmt.setObject(++idx, param.get("COMM_RELAY_FEE_VAT"));
				stmt.setObject(++idx, param.get("COMM_BM_RD_FEE_SUPPLY_AMT"));
				stmt.setObject(++idx, param.get("COMM_BM_RD_FEE_VAT"));
				stmt.setObject(++idx, commVatIncluded);
				stmt.setObject(++idx, param.get("SETTLE_AMT"));
				stmt.setObject(++idx, param.get("USER_CD"));
				
				stmt.addBatch();
				
				executeCnt++;
				if(executeCnt == 2000) {
					stmt.executeBatch();
					conn.commit();
					stmt.clearBatch();
					executeCnt = 0;
				}
			}
			stmt.executeBatch();
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			try{conn.rollback();}catch(Exception e1) { e.printStackTrace(); }
			result = false;
		} finally {
			try {
				stmt.clearBatch(); stmt.close(); stmt = null;
				conn.close(); conn = null;
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
	}
	
	private String makeQueryBaeminExcelUpload() {
		StringBuffer buf = new StringBuffer();
		buf.append("INSERT INTO EM_SETTLE_BAEMIN_DV_ORD_OMS ( ");
		buf.append("            SETTLE_MONTH, ORD_DATE, CH_ORD_NO, CH_CD, SET_BAEMIN_DV_SEQ, ");
		buf.append("            BRANCH_NM, EXP_PAY_DATE, PAY_DATE, PAY_TIME, SETTLE_STD_DATE, ");
		buf.append("            ORD_ID, ORD_STATE, SALE_ITEM_AMT, SALE_DV_AMT, SALE_ENVELOPE_AMT, ");
		buf.append("            SALE_PARTIAL_REFUND, SALE_SELLER_CPN_AMT, SALE_SUM, SETTLE_PAY_CHARGE_TARGET_AMT, RELAY_FEE_CHARGE_TARGET_AMT, ");
		buf.append("            COMM_SD_SUPPLY_AMT, COMM_SD_PREFERENTIAL_AMT, COMM_PAY_SETTLE_VAT, COMM_RELAY_FEE_SUPPLY_AMT, COMM_RELAY_FEE_VAT, ");
		buf.append("            COMM_BM_RD_FEE_SUPPLY_AMT, COMM_BM_RD_FEE_VAT, COMM_VAT_INCLUDED, SETTLE_AMT, USER_CD, REG_DATE ");
		buf.append("  ) VALUES ( ");
		buf.append("?, ?, ?, ?, ?, ");
		buf.append("?, ?, ?, ?, ?, ");
		buf.append("?, ?, ?, ?, ?, ");
		buf.append("?, ?, ?, ?, ?, ");
		buf.append("?, ?, ?, ?, ?, ");
		buf.append("?, ?, ?, ?, ?, SYSDATE)");
		
		return buf.toString();
	}
	// 배달의민족 엑셀 업로드
//	public Object ExcelUpload_BAEMIN(List<Map<String, Object>> list) {
//		boolean result = true;
//		
//		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
//		
//		try {
//			for (Map<String, Object> param : list) {
//				session.insert("excel.ExcelUpload_BAEMIN", param);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			session.rollback();
//			result = false;
//		} finally {
//			session.flushStatements();
//			session.close();
//		}
//		return result;
//	}
	// 이마트24앱 엑셀 업로드
	public Object ExcelUpload_EMART24(List<Map<String, Object>> list) {
		boolean result = true;
		
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_EMART24", param);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 부릉 엑셀 업로드
	public Object ExcelUpload_VROONG(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_VROONG", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 바로고 엑셀 업로드
	public boolean ExcelUpload_BAROGO(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_BAROGO", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 생각대로 엑셀 업로드
	public Object ExcelUpload_LOGIALL(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_LOGIALL", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 딜버 엑셀 업로드
	public Object ExcelUpload_DEALVER(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_DEALVER", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}

	// 스파이더 엑셀 업로드
	public Object ExcelUpload_SPIDOR(List<Map<String, Object>> list) {
		boolean result = true;

		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_SPIDOR", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}
	
	// 체인로지스 엑셀 업로드
	public Object ExcelUpload_CHAIN(List<Map<String, Object>> list) {
		boolean result = true;
		
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try {
			for (Map<String, Object> param : list) {
				session.insert("excel.ExcelUpload_CHAIN", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			result = false;
		} finally {
			session.flushStatements();
			session.close();
		}
		return result;
	}
	// 요기요 배달 엑셀샘플다운로드
	public List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getExcel_Sample_Data_YGYD(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcel_Sample_Data_YGYD", paramMap);
	}

	// 엑셀 데이터 SELECT
	// 요기요 배달
	public List<EM_RELAY_TOTAL_OMS> getExcelData_RELAY(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_RELAY", paramMap);
	}
	// 요기요 배달
	public List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getExcelData_YGYD(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_YGYD", paramMap);
	}

	// 요기요 픽업
	public List<EM_SETTLE_YOGIYO_PU_ORD_OMS> getExcelData_YGYP(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_YGYP", paramMap);
	}

	// 네이버
	public List<EM_SETTLE_NAVER_ORD_OMS> getExcelData_NAVER(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_NAVER", paramMap);
	}

	// 카카오
	public List<EM_SETTLE_KAKAO_DV_ORD_OMS> getExcelData_KAKAO(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_KAKAO", paramMap);
	}

	// KIS
	public List<EM_SETTLE_KISAPP_ORD_OMS> getExcelData_KIS(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_KIS", paramMap);
	}
	
	// 배달의민족
	public List<EM_SETTLE_BAEMIN_DV_ORD_OMS> getExcelData_BAEMIN(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_BAEMIN", paramMap);
	}
	//이마트24앱
	public List<EM_SETTLE_EMART24_DV_PU_OMS> getExcelData_EMART24(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_EMART24", paramMap);
	}

	// 부릉
	public List<EM_SETTLE_VROONG_DV_OMS> getExcelData_VROONG(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_VROONG", paramMap);
	}

	// 바로고
	public List<EM_SETTLE_BAROGO_DV_OMS> getExcelData_BAROGO(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_BAROGO", paramMap);
	}

	// 생각대로
	public List<EM_SETTLE_LOGIALL_DV_OMS> getExcelData_LOGIALL(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_LOGIALL", paramMap);
	}

	// 딜버
	public List<EM_SETTLE_DEALVER_DV_OMS> getExcelData_DEALVER(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_DEALVER", paramMap);
	}

	// 스파이더크래프트
	public List<EM_SETTLE_SPIDOR_DV_OMS> getExcelData_SPIDOR(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_SPIDOR", paramMap);
	}
	
	// 체인로지스
	public List<EM_SETTLE_CHAINLOGIS_DV_OMS> getExcelData_CHAIN(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_CHAIN", paramMap);
	}

	// 배달정산합계
	public List<EM_DELIVERY_TOTAL_OMS> getExcelData_TOTAL(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("excel.getExcelData_TOTAL", paramMap);
	}

	// 주문채널사 취합 엑셀
	public List<EM_SYNTH_ORD_CH_VIEW> getExcelSynthesis_Order(HashMap<String, String> paramMap) {
		return sqlSession.selectList("excel.getExcelSynthesis_Order", paramMap);
	}

	// 배달대행사 취합 엑셀
	public List<EM_SYNTH_DV_CH_VIEW> getExcelSynthesis_Delivery(HashMap<String, String> paramMap) {
		return sqlSession.selectList("excel.getExcelSynthesis_Delivery", paramMap);
	} 
}