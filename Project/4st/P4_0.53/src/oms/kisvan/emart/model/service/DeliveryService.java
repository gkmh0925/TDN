package oms.kisvan.emart.model.service;


import java.text.SimpleDateFormat;
import java.util.Date;

//!@@0701 new

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.Setter;
import oms.kisvan.emart.common.DateUtils;
import oms.kisvan.emart.model.dao.DeliveryDAO;
import oms.kisvan.emart.model.dao.OrderDAO;
import oms.kisvan.emart.model.dto.EM_CATEGORY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_ORDER_HISTORY_OMS;
import oms.kisvan.emart.model.dto.EM_ORDER_MENU_OMS;
import oms.kisvan.emart.model.dto.EM_PRODUCT_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_REFERENCE_CODE_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EmProductMasterOmsDTO;
import oms.kisvan.emart.model.dto.EmProductMasterOmsEntity;
import oms.kisvan.emart.model.dto.ErrorResponse;
import oms.kisvan.emart.model.dto.TB_DELIVERY_STATE;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;

@Service
public class DeliveryService {

	@Setter(onMethod_ = @Autowired)
	private DeliveryDAO dao;
	
	@Setter(onMethod_ = @Autowired)
	private DateUtils dateUtils;


	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	// 배달 상품 리스트 검색
	public List<TB_DELIVERY_STATE> searchDeliveryProductList(Map<String, Object> paramMap) throws Exception {
		return dao.selectDeliveryProductList(paramMap, sqlSession);
	}
	
	// 검색된 배달 상품 총 개수
	public int getSearchDeliveryProductTotalCnt(Map<String, Object> paramMap) {

		return dao.selectSearchDeliveryProductTotalCnt(sqlSession, paramMap);
	}
	
	// 상품 상태 레퍼런스 데이터 가져오기
	public List<EM_REFERENCE_CODE_OMS> getPrdStateReferenceList(Map<String, Object> paramMap) {

		return dao.selectPrdStateReferenceList(sqlSession, paramMap);
	}
	
	// 배달 상품 등록
	public ResponseEntity<Object> createDeliveryProduct(EmProductMasterOmsDTO epmoDTO) {
		
		ResponseEntity<Object> res = verifyInput(epmoDTO);
		if(res != null) {
			return res;
		}		
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chCd", epmoDTO.getChCd());
		paramMap.put("prdCd", epmoDTO.getPrdCd());
		
		EmProductMasterOmsEntity existsProduct;
		existsProduct = dao.selectEmProductMasterOmsInfo(sqlSession, paramMap);
		if(existsProduct != null)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 존재하는 상품코드입니다"));
		}
	
		dao.insertDeliveryProduct(sqlSession, epmoDTO);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
	private ResponseEntity<Object> verifyInput(EmProductMasterOmsDTO epmoDTO) {
		
		//상품코드 검증
		String pattern = "^[0-9]{1,30}$";		
		boolean isMatch = Pattern.matches(pattern, epmoDTO.getPrdCd());
		if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 상품코드를 입력해주세요"));
		
		//상품명 검증
		pattern = "^[가-힣a-zA-Z0-9]{1,20}$";		
		isMatch = Pattern.matches(pattern, epmoDTO.getPrdNm());
		if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 상품명을 입력해주세요"));
		
				
		//판매 단가 검증
		pattern = "^[0-9]{1,8}$";		
		Integer stdAmt = epmoDTO.getStdAmt();
		if(stdAmt != null) {
			String stdAmtStr = String.valueOf(stdAmt);
			isMatch = Pattern.matches(pattern, stdAmtStr);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 판매 단가를 입력해주세요"));
		}

		
		//세일 단가 검증
		pattern = "^[0-9]{1,8}$";	
		Integer saleAmt = epmoDTO.getSaleAmt();
		if(saleAmt != null) {
			String saleAmtStr = String.valueOf(saleAmt);	
			isMatch = Pattern.matches(pattern, saleAmtStr);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 세일 단가를 입력해주세요"));			
		}
		
		
		//정렬 순서 검증
		pattern = "^[0-9]{1,10}$";
		Long seq = epmoDTO.getSeq();
		if(seq != null) {
			String seqStr = String.valueOf(seq);
			isMatch = Pattern.matches(pattern, seqStr);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 정렬 순서를 입력해주세요"));			
		}

			
		//상품 설명 검증
	
		String prdGuide = epmoDTO.getPrdGuide();
		if(prdGuide != null && !prdGuide.equals("")) {
			pattern = "^[가-힣a-zA-Z0-9]{1,30}$";		
			isMatch = Pattern.matches(pattern, prdGuide);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 상품 설명을 입력해주세요"));
		}

		
		//상품 대표 이미지 URL 검증
		String prdBaseImgUrl = epmoDTO.getPrdBaseImgUrl();
		if(prdBaseImgUrl != null && !prdBaseImgUrl.equals("")) {
			pattern = "^[0-9]{1,200}$";		
			isMatch = Pattern.matches(pattern, prdBaseImgUrl);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 대표 이미지 URL을 입력해주세요"));
		}

		
		//상품 상세 이미지 URL 검증
		String prdDtImgUrl = epmoDTO.getPrdDtImgUrl();
		if(prdDtImgUrl != null && !prdDtImgUrl.equals("")) {
			pattern = "^[0-9]{1,200}$";		
			isMatch = Pattern.matches(pattern, prdDtImgUrl);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 상세 이미지 URL을 입력해주세요"));
		}
		
		
		//취급 상품 종류 검증
		String prdType = epmoDTO.getPrdType();
		if(prdType != null && !prdType.equals("")) {
			pattern = "^[0-9]{1,50}$";		
			isMatch = Pattern.matches(pattern, prdType);
			if(!isMatch) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("올바른 취급 상품 종류를 입력해주세요"));
		}

		
		return null;
	}

	// 상품 단일 조회
	public EmProductMasterOmsDTO getEmProductMasterOmsInfo(Map<String, Object> paramMap) {
		
		EmProductMasterOmsEntity existsProduct = dao.selectEmProductMasterOmsInfo(sqlSession, paramMap);	
		return EmProductMasterOmsEntity.toDTO(existsProduct); 
	}
	
	// 배달 상품 단일 조회
	public EmProductMasterOmsDTO getDeliveryProductInfo(Map<String, Object> paramMap) {
		
		EmProductMasterOmsDTO existsProductDTO = dao.selectDeliveryProductInfo(sqlSession, paramMap);	
		
		//Date 포맷팅하여 set 
		existsProductDTO.setRegDateStr(dateUtils.DateToDateStrByFormat(existsProductDTO.getRegDate()));
		existsProductDTO.setPrdStateMdfDtStr(dateUtils.DateToDateStrByFormat(existsProductDTO.getPrdStateMdfDt()));
		existsProductDTO.setMdfDateStr(dateUtils.DateToDateStrByFormat(existsProductDTO.getMdfDate()));
				
		return existsProductDTO; 
	}
	
	public ResponseEntity<Object> removeDeliveryProduct(Map<String, Object> paramMap){
		
		EmProductMasterOmsEntity existsProduct;
		existsProduct = dao.selectEmProductMasterOmsInfo(sqlSession, paramMap);
		if(existsProduct == null)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("존재 하지 않는 상품입니다"));
		}
		dao.deleteEmProductMasterOmsByCpnCdAndPrdCd(sqlSession, paramMap);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	public List<EM_CATEGORY_MASTER_OMS> getDeliveryProductStoreCategoryList(String chCd){
		
		return dao.selectStoreCategoryListByChCd(sqlSession, chCd);
	}
	
	public List<EM_CATEGORY_MASTER_OMS> getDeliveryProductPrdCategoryList(String chCd){
		
		return dao.selectPrdCategoryListByChCd(sqlSession, chCd);
	}
	
	public List<EM_REFERENCE_CODE_OMS> getReferenceCodeByGroupCd(String groupCd){
		
		return dao.selectEmReferenceCodeOmsByGroupCd(sqlSession, groupCd);
	}
	
	
	public ResponseEntity<Object> modifyDeliveryProductInfo(Map<String,Object> paramMap, EM_USER_ACCOUNT_OMS account){
		
		EmProductMasterOmsEntity existsProduct = dao.selectEmProductMasterOmsInfo(sqlSession, paramMap);
		
		if(existsProduct == null) { //수정하려는 상품이 없다면
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("존재하지 않는 상품입니다"));
		}
		
		paramMap.put("mdfUserCd", account.getUSER_CD()); //수정자
		
		String modifyPrdState = String.valueOf(paramMap.get("prdState"));
		String currentPrdState = existsProduct.getPrdState();
		if(!modifyPrdState.equals(currentPrdState)) { //상품 상태가 변경됬다면
			
	        Date date = new Date(System.currentTimeMillis());
	        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	        
			paramMap.put("prdStateChange", "true"); //상품 상태 변경 되었음을 체크		
		}
		
		 // 날짜 객체 생성

		
		
		int updatedCnt = dao.updateEmProductMasterOms(sqlSession, paramMap);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
	

}
