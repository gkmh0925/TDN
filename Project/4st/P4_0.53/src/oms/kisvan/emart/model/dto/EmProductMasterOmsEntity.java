package oms.kisvan.emart.model.dto;

import java.util.Date;

import lombok.Data;

//!@@0701 new

@SuppressWarnings("deprecation")
@Data
public class EmProductMasterOmsEntity {
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

	
	public static EmProductMasterOmsDTO toDTO(EmProductMasterOmsEntity entity) {
		
		EmProductMasterOmsDTO dto = new EmProductMasterOmsDTO();
		
		dto.setChCd(entity.getChCd());
		dto.setPrdCd(entity.getPrdCd());
		dto.setPrdNm(entity.getPrdNm());
		dto.setSeq(entity.getSeq());
		dto.setStdAmt(entity.getStdAmt());
		dto.setSaleAmt(entity.getSaleAmt());
		dto.setPrdState(entity.getPrdState());
		dto.setPrdStateMdfDt(entity.getPrdStateMdfDt());
		dto.setPrdGuide(entity.getPrdGuide());
		dto.setStoCtgrCd(entity.getStoCtgrCd());
		dto.setPrdCtgrCd(entity.getPrdCtgrCd());
		dto.setTaxType(entity.getTaxType());
		dto.setSaleStartDt(entity.getSaleStartDt());
		dto.setSaleEndDt(entity.getSaleEndDt());
		dto.setPrdBaseImgUrl(entity.getPrdBaseImgUrl());
		dto.setPrdDtImgUrl(entity.getPrdDtImgUrl());
		dto.setUseSalesAdult(entity.getUseSalesAdult());
		dto.setPrdType(entity.getPrdType());
		dto.setRegUserCd(entity.getRegUserCd());
		dto.setRegDate(entity.getRegDate());
		dto.setMdfUserCd(entity.getMdfUserCd());
		dto.setMdfDate(entity.getMdfDate());
		dto.setWorkType(entity.getWorkType());
		
		return dto;
	}
	
}
