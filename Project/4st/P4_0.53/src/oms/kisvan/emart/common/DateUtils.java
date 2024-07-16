package oms.kisvan.emart.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;



/**
 * @Author : 김영현
 * @Date : 2024. 7. 1.
 * @Explan : 날짜 관련 유틸리티
 */
@Component("DateUtil")
public class DateUtils {
	
	public String DateToDateStrByFormat(Date date) {
		
		if(date == null)
			return "";
		String pattern = "yyyy-MM-dd HH:mm:ss"; // 원하는 날짜 형식 패턴 정의
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(date);		
		return formattedDate;
	}
}
