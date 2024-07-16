package oms.kisvan.emart.model.dto;

//!@@0701 new

import java.util.Date;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
public class EmProductMasterOmsDTO {
	
	private String chCd;
	private String prdCd;
	private String prdNm;
	private Long seq;
	private Integer stdAmt;
	private Integer saleAmt;
	private String prdState;
	private Date prdStateMdfDt;
	private String prdGuide;
	private String stoCtgrCd;
	private String prdCtgrCd;
	private String taxType;
	private String saleStartDt;
	private String saleEndDt;
	private String prdBaseImgUrl;
	private String prdDtImgUrl;
	private String useSalesAdult;
	private String prdType;
	private Integer regUserCd;
	private Date regDate;
	private Integer mdfUserCd;
	private Date mdfDate;
	private String workType;
	
	private String cpnNm;
	private String stoCtgrNm;
	

	//포맷팅된 String타입의 날짜
	private String regDateStr;
	private String prdStateMdfDtStr;
	private String mdfDateStr;
	

	
	
}
