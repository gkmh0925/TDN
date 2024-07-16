package oms.kisvan.emart.model.dto;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreOrdNoDetailsInfoDTO {
    private int RN;
    private String STR_CD; // 매장코드
    private String STR_NM; // 매장명
    private String STO_TYPE_NM;
    private String PICKUP_NM;
    private String ORD_DATE;
    private String ORD_DT;
    private String DELIVERY_TIME;
    private String CH_ORD_NO;
    private String NPAY_ORD_NO;
    private String ORDER_NM;
    private String DELIVERY_NM;
    private String ORD_AMT_B; // 금액 필드를 String으로 변경하여 포맷팅 적용
    private String ORD_AMT_A;
    private String ADD_DEPOSIT;
    private String DELIVER_FEE;
    private String DELIVER_EVENT_FUND;
    private String LEVY;
    private String DELIVERY_AMT;
    private String DELIVERY_SUBSIDY_BEFOR;
    private String DELIVERY_SUBSIDY_AFTER;
    private String PLATFORM_FEE;
    private String PLATFORM_CNT;
    private String DELIVERY_CNT;
    private String KIS_FEE_BEFOR;
    private String KIS_FEE_AFTER;
    private String BIGO;
    private String IS_REFLECTION;
    
    // ord_AMT_B 설정
    public void setORD_AMT_B(int ordAmtB) {
        this.ORD_AMT_B = formatAmount(ordAmtB);
    }

    // ord_AMT_A 설정
    public void setORD_AMT_A(int ordAmtA) {
        this.ORD_AMT_A = formatAmount(ordAmtA);
    }

    // add_DEPOSIT 설정
    public void setADD_DEPOSIT(int addDeposit) {
        this.ADD_DEPOSIT = formatAmount(addDeposit); // 문자열을 정수로 변환 후 포맷팅
    }

    // deliver_FEE 설정
    public void setDELIVER_FEE(int deliver_FEE) {
        this.DELIVER_FEE = formatAmount(deliver_FEE);
    }

    // deliver_EVENT_FUND 설정
    public void setDELIVER_EVENT_FUND(int deliverEventFund) {
        this.DELIVER_EVENT_FUND = formatAmount(deliverEventFund);
    }

    // levy 설정
    public void setLEVY(int levy) {
        this.LEVY = formatAmount(levy);
    }

    // delivery_AMT 설정
    public void setDELIVERY_AMT(int DELIVERY_AMT) {
        this.DELIVERY_AMT = formatAmount(DELIVERY_AMT);
    }

    // delivery_SUBSIDY_BEFOR 설정
    public void setDELIVERY_SUBSIDY_BEFOR(int deliverySubsidyBefor) {
        this.DELIVERY_SUBSIDY_BEFOR = formatAmount(deliverySubsidyBefor); // 문자열을 정수로 변환 후 포맷팅
    }

    // delivery_SUBSIDY_AFTER 설정
    public void setDELIVERY_SUBSIDY_AFTER(int deliverySubsidyAfter) {
        this.DELIVERY_SUBSIDY_AFTER = formatAmount(deliverySubsidyAfter); // 문자열을 정수로 변환 후 포맷팅
    }

    // platform_FEE 설정
    public void setPLATFORM_FEE(int platformFee) {
        this.PLATFORM_FEE = formatAmount(platformFee);
    }

    // kis_FEE_BEFOR 설정
    public void setKIS_FEE_BEFOR(int kisFeeBefor) {
        this.KIS_FEE_BEFOR = formatAmount(kisFeeBefor);
    }

    // kis_FEE_AFTER 설정
    public void setKIS_FEE_AFTER(int kisFeeAfter) {
        this.KIS_FEE_AFTER = formatAmount(kisFeeAfter);
    }

    // 숫자를 천 단위 콤마로 포맷팅하는 메서드
    private String formatAmount(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }
    
    // ORD_DATE 설정
    public void setORD_DATE(String ordDate) {
        // "yyyyMMdd" -> "yyyy-MM-dd"
        this.ORD_DATE = formatDate(ordDate, "yyyyMMdd", "yyyy-MM-dd");
    }

    // ORD_DT 설정
    public void setORD_DT(String ordDt) {
        // "yyyyMMdd" -> "yyyy-MM-dd"
        this.ORD_DT = formatDate(ordDt, "yyyyMMdd", "yyyy-MM-dd");
    }

    // DELIVERY_TIME 설정
    public void setDELIVERY_TIME(String deliveryTime) {
        // "HHmm" -> "HH:mm"
        this.DELIVERY_TIME = formatTime(deliveryTime.trim(), "HHmm", "HH:mm");
    }

    // 날짜 포맷팅 메서드
    private String formatDate(String inputDate, String inputFormat, String outputFormat) {
        if (inputDate == null || inputDate.isEmpty()) {
            return null;
        }
        return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(inputFormat))
                       .format(DateTimeFormatter.ofPattern(outputFormat));
    }

    // 시간 포맷팅 메서드
    private String formatTime(String inputTime, String inputFormat, String outputFormat) {
    	
        if (inputTime == null || inputTime.isEmpty()) {
            return null;
        } else if(inputTime.length() == 6) { 
        	return LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HHmmss"))
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    	}else {
        	return LocalTime.parse(inputTime, DateTimeFormatter.ofPattern(inputFormat))
                    .format(DateTimeFormatter.ofPattern(outputFormat));
        }
        
    }
    
}
