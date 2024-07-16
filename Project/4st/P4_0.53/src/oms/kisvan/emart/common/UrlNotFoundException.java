package oms.kisvan.emart.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : FORBIDDEN 에러 호출 클래스
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UrlNotFoundException extends RuntimeException {

}
