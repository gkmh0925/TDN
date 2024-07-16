package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_COMPANY_MASTER_OMS {
	private String CPN_TYPE; // *업체타입*(PK)
	private String IN_APP; // *INAPP 저장여부*(PK)
	private String CPN_CD; // *업체코드*(PK)
	private String CPN_BIZ_NO; // *사업자번호*
	private String CPN_NM; // *상호명*
	private String CEO_NM; // *대표자명*
	private String ZIP_CODE; // *우편번호*
	private String CPN_ADDR; // *업체지번주소*
	private String CPN_ROAD_ADDR; // *업체도로명주소*
	private String CPN_ADDR_DT; // *업체상세주소*
	private String CPN_IMAGE_URL; // *업체이미지URL*
	private String CPN_DESC; // *업체설명*
	private String OPN_DT; // *오픈일자*
	private String CPN_BIZ_TYPE; // *사업자유형*
	private String CPN_TEL; // *전화번호*
	private String HPNO; // *휴대폰번호*
	private String EMAIL; // *이메일*
	private String CPN_STATE; // *업체상태*
	private Timestamp STATE_MDF_DATE; // *상태변경일시*
	private String SETTLE_USE; // *정산사용유무*
	private String MEMO; // *비고*
	private String PROMO_DESC; // *홍보문구*
	private String MNG_NM; // *점장명*
	private String MNG_HPNO; // *휴대폰번호(점장)*
	private String FC_NM; // *담당FC명*
	private String MFC_TP_CD; // *MFC구분*
	private Integer REG_USER_CD; // *등록사용자*
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; // *수정사용자*
	private Timestamp MDF_DATE; // *수정일시*

	// JOIN 참조 컬럼
	private String DAY_START_TIME; // 주중 영업시작일
	private String DAY_END_TIME;// 주중 영업종료일
	private String ROAD_ADDR;
	private String ROAD_ADDR_DT;
	private String REG_USER_NM; // 등록사용자
	private String MDF_USER_NM; // 수정사용자
	private String DELIVERY_CH_CD; // 배송채널사 코드

	private Integer RN; // 행 넘버

	// 2021-10-16 추가 컬럼
	private String ORD_CH_NM; // 연동 채널사 이름
	private String DLV_CH_NM; // 연동 배달사 이름
	private String CPN_STATE_NM; // 연동 채널사 이름
	

}
