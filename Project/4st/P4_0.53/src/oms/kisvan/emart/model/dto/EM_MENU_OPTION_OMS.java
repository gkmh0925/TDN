package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_MENU_OPTION_OMS {
	private String OPTION_CD;// *옵션코드*
	private String MENU_CD;// *메뉴코드*
	private String OPTION_NM;// *옵션명*
	private char IS_REQUIRED;// *필수여부*
	private Integer SEQUENCE;// *정렬순서*
	private char IS_MULTI_SELECTABLE;// *다수 아이템 선택 가능 여부*
	private Integer MIN_SELECT_COUNT;// *최소 선택 개수*
	private Integer MAX_SELECT_COUNT;// *최대 선택 개수*
	private char IS_COUNTABLE;// *주문시 아이템에 수량을 입력 받을지 여부*
}