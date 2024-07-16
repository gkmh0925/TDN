package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_MENU_MASTER_OMS {
	private String MENU_CD; // *메뉴코드*(PK)
	private String MENU_NM; // *메뉴명*
	private Integer CTGR_CD; // *카테고리코드*(FK)
	private String MENU_DESC; // *메뉴상세설명*
	private Integer PRICE; // *단가*
	private String MENU_STATE; // *메뉴상태*
	private Timestamp MENU_STATE_MDF_DATE; // *메뉴상태수정일시*
	private Integer SEQUENCE; // *정렬순서*
	private String IMAGE_URL; // *메뉴 이미지 URL*
	private String NUTRITION_INFO_URL; // *영양정보 URL*
	private Integer REG_USER_CD; // *등록사용자*
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; // *수정사용자*
	private Timestamp MDF_DATE; // *수정일시*

	private String CTGR_NM; // 카테고리명
	private String REG_USER_NM; // 등록자명
	private String MDF_USER_NM; // 수정자명
	private String LINK_MENU_CD; // 메뉴코드에 해당되는 아이템
	private String MENU_STATE_NM; // 메뉴상태명
	
	private String MFC_TP_CD; // MFC구분

	private Integer RN; // 행 넘버
	
	


}