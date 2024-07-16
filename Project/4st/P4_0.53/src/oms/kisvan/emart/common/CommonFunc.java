package oms.kisvan.emart.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * @Author : 이준혁
 * @Date : 2022. 9. 2.
 * @Explan : 공용 메서드
 */
public class CommonFunc {
	static Cell cell = null;

	// 주소 마스킹(신주소, 구주소, 도로명 주소 숫자만 전부 마스킹)
	public static String addrMasking(String address) throws Exception { // 신(구)주소, 도로명 주소
		String regex = "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|";
		String newRegx = "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))";
		Matcher matcher = Pattern.compile(regex).matcher(address);
		Matcher newMatcher = Pattern.compile(newRegx).matcher(address);
		if (matcher.find()) {
			return address.replaceAll("\\d", "*");
		} else if (newMatcher.find()) {
			return address.replaceAll("\\d", "*");
		}
		return address;
	}

	// 휴대폰번호 마스킹(가운데 숫자 4자리 마스킹)
	public static String telMasking(String num) {

		if (StringUtils.isBlank(num))
			return num;

		num = num.replaceAll("[-]", "");

		String regex = "";

		if (num.length() == 12) {
			regex = "(\\d{4})-?(\\d{4})-?(\\d{4})$";
		} else if (num.length() == 8) {
			regex = "(\\d{4})-?(\\d{4})$";
		} else if (num.length() == 10) {
			if (num.substring(0, 2) == "02") {
				regex = "(\\d{2})-?(\\d{4})-?(\\d{4})$";
			} else {
				regex = "(\\d{3})-?(\\d{3})-?(\\d{4})$";
			}
		} else {
			regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
		}

		Matcher matcher = Pattern.compile(regex).matcher(num);
		if (matcher.find()) {
			return matcher.group(1) + "-" + matcher.group(2) + "-" + "****";
		} else {
			return "";
		}
	}

	// 이름 가운데 글자 마스킹
	public static String nameMasking(String name) throws Exception { // 한글만 (영어, 숫자 포함 이름은 제외)

		if (name.length() > 4) {
			return name;
		}

		String regex = "(^[가-힣]+)$";
		Matcher matcher = Pattern.compile(regex).matcher(name);
		if (matcher.find()) {
			int length = name.length();
			String middleMask = "";
			if (length > 2) {
				middleMask = name.substring(1, length - 1);
			} else { // 이름이 외자
				middleMask = name.substring(1, length);
			}
			String dot = "";
			for (int i = 0; i < middleMask.length(); i++) {
				dot += "*";
			}
			if (length > 2) {
				return name.substring(0, 1) + middleMask.replace(middleMask, dot) + name.substring(length - 1, length);
			} else { // 이름이 외자 마스킹 리턴
				return name.substring(0, 1) + middleMask.replace(middleMask, dot);
			}
		}
		return name;
	}

	// 이메일 마스킹(앞2자리 이후 '@'전까지 마스킹)
	public static String emailMasking(String email) throws Exception {
		if (StringUtils.isBlank(email)) {
			return email;
		}
		String regex = "([0-9a-zA-Z_\\.-]+)@";
		Matcher matcher = Pattern.compile(regex).matcher(email);
		if (matcher.find()) {
			String target = matcher.group(1);
			int length = target.length();
			if (length > 2) {
				char[] c = new char[length - 2];

				Arrays.fill(c, '*');
				return email.replace(target, target.substring(0, 2) + String.valueOf(c));
			}
		}
		return email;
	}

	// 계좌번호 마스킹(뒤 5자리)
	public static String accountNoMasking(String accountNo) throws Exception { // 계좌번호는 숫자만 파악하므로
		String regex = "(^[0-9]+)$";
		Matcher matcher = Pattern.compile(regex).matcher(accountNo);
		if (matcher.find()) {
			int length = accountNo.length();
			if (length > 5) {
				char[] c = new char[5];
				Arrays.fill(c, '*');
				return accountNo.replace(accountNo, accountNo.substring(0, length - 5) + String.valueOf(c));
			}
		}
		return accountNo;
	}

	// 생년월일 마스킹(8자리)
	public static String birthMasking(String birthday) throws Exception {
		String regex = "^((19|20)\\d\\d)?([-/.])?(0[1-9]|1[012])([-/.])?(0[1-9]|[12][0-9]|3[01])$";
		Matcher matcher = Pattern.compile(regex).matcher(birthday);
		if (matcher.find()) {
			return birthday.replace("[0-9]", "*");
		}
		return birthday;
	}

	// 카드번호 가운데 8자리 마스킹
	public static String cardMasking(String cardNo) throws Exception { // 카드번호 16자리 또는 15자리 '-'포함/미포함 상관없음
		String regex = "(\\d{4})-?(\\d{4})-?(\\d{4})-?(\\d{3,4})$";
		Matcher matcher = Pattern.compile(regex).matcher(cardNo);
		if (matcher.find()) {
			String target = matcher.group(2) + matcher.group(3);
			int length = target.length();
			char[] c = new char[length];
			Arrays.fill(c, '*');
			return cardNo.replace(target, String.valueOf(c));
		}
		return cardNo;
	}

	// 연월일시 포멧
	public static String datetimeFormat(String num) {

		if (StringUtils.isBlank(num))
			return "";

		String formatNum = "";

		// 공백제거
		num = num.replaceAll("/\\s/gi", "");

		if (num.length() == 4) {
			formatNum = num.replaceAll("(\\d{2})(\\d{2})", "$1:$2");
		} else if (num.length() == 6) {
			formatNum = num.replaceAll("(\\d{2})(\\d{2})(\\d{2})", "$1:$2:$3");
		} else if (num.length() == 8) {
			formatNum = num.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
		} else if (num.length() == 14) {
			formatNum = num.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$1-$2-$3 $4:$5:$6");
		} else {
			return num;
		}
		return formatNum;
	}

	// 휴대폰번호 마스킹(가운데 숫자 4자리 마스킹)
	public static String telFormat(String num) {

		if (StringUtils.isBlank(num))
			return num;

		num = num.replaceAll("[-]", "");

		String regex = "";

		if (num.length() == 12) {
			regex = "(\\d{4})-?(\\d{4})-?(\\d{4})$";
		} else if (num.length() == 8) {
			regex = "(\\d{4})-?(\\d{4})$";
		} else if (num.length() == 10) {
			if (num.substring(0, 2) == "02") {
				regex = "(\\d{2})-?(\\d{4})-?(\\d{4})$";
			} else {
				regex = "(\\d{3})-?(\\d{3})-?(\\d{4})$";
			}
		} else {
			regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
		}

		Matcher matcher = Pattern.compile(regex).matcher(num);
		if (matcher.find()) {
			return matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
		} else {
			return "";
		}
	}

	public static String biznoFormat(String num) {

		if (StringUtils.isBlank(num))
			return "";

		String formatNum = "";

		// 공백제거
		num = num.replaceAll("/\\s/gi", "");

		if (num.length() == 10) {
			formatNum = num.replaceAll("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
		} else {
			return num;
		}
		return formatNum;
	}

	public static String unixTimeFormat(Timestamp date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = sdf.format(new Date(date.getTime()));

		return str;
	}
	
	

	public static String amountNullCheck(String num) {

		if (StringUtils.isBlank(num)) {
			num = "0";
		} else {
			return num;
		}

		return num;
	}

	// HTML태그 특수문자 변경
	public static String HTMLtoReplace(String str) {

		if (str == null) {
			return null;
		}

		String returnStr = str;

		returnStr = returnStr.replaceAll("<br>", "\n");

		returnStr = returnStr.replaceAll("&gt;", ">");

		returnStr = returnStr.replaceAll("&lt;", "<");

		returnStr = returnStr.replaceAll("&quot;", "\"");

		returnStr = returnStr.replaceAll("&nbsp;", " ");

		returnStr = returnStr.replaceAll("&amp;", "&");

		return returnStr;
		

	}

	public static String stringNullCheck(String str) {
		if (StringUtils.isBlank(str)) {
			str = "";
		}
		
		return str;
	}
	
	public static String numberNullCheck(String num) {
		if (StringUtils.isBlank(num)) {
			num = "0";
		}

		return num;
	}

	public static Integer integerNullCheck(Integer integer) {
		if (integer == null) {
			integer = 0;
		}

		return integer;
	}
	
	public static Boolean IsEmptyString(String value) {
		//값이 null 또는 값이 없으면 true
		return (value == null || value.trim().isEmpty());
	}
	
	public static Boolean IsHasString(String value) {
		//값이 null 또는 값이 없으면 true
		return (value != null && !value.trim().isEmpty());
	}
		
	// EXCEL 데이터 검증
	public static boolean valueCheck(String type, String data, boolean nec) {
		if (nec) {
			if (StringUtils.isBlank(data)) {
				return true;
			} else {
				switch (type) {
				case "settle":
					if (data.getBytes().length > 20) {
						return true;
					} else {
						return false;
					}
				case "ord_id":
					if (data.length() != 20) {
						return true;
					} else {
						return false;
					}
				case "datetime":
					try {
						if (data.length() < 12 || data.length() > 14) {
							return true;
						} else {
							if (data.length() == 12) {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMddHHmm"); // 검증할 날짜 포맷
																											// 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							} else {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMddHHmmss"); // 검증할 날짜 포맷
																											// 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							}
						}
					} catch (Exception e) {
						return true;
					}
				case "date":
					try {
						
						if (data.length() != 8) {
							return true;
						} else {
							SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd"); // 검증할 날짜 포맷 설정
							dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
							dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
							return false;
						}
					} catch (Exception e) {
						return true;
					}
				case "time":
					try {
						if (data.length() != 6 && data.length() != 4) {
							return true;
						} else {
							if (data.length() == 6) {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("HHmmss"); // 검증할 날짜 포맷 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							} else if (data.length() == 4) {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("HHmm"); // 검증할 날짜 포맷 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							} else {
								return true;
							}
						}
					} catch (Exception e) {
						return true;
					}
				case "biz_no":
					if (data.length() != 10) {
						return true;
					} else {
						return false;
					}
				case "sto_id":
					if (data.length() != 5) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			if (StringUtils.isBlank(data)) {
				return false;
			} else {
				switch (type) {
				case "settle":
					if (data.getBytes().length > 20) {
						return true;
					} else {
						return false;
					}
				case "ord_id":
					if (data.length() != 20) {
						return true;
					} else {
						return false;
					}
				case "datetime":
					try {
						if (data.length() < 12 || data.length() > 14) {
							return true;
						} else {
							if (data.length() == 12) {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMddHHmm"); // 검증할 날짜 포맷
																											// 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							} else {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMddHHmmss"); // 검증할 날짜 포맷
																											// 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							}
						}
					} catch (Exception e) {
						return true;
					}
				case "date":
					try {
						if (data.length() != 8) {
							return true;
						} else {
							SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd"); // 검증할 날짜 포맷 설정
							dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
							dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
							return false;
						}
					} catch (Exception e) {
						return true;
					}
				case "time":
					try {
						if (data.length() != 6 && data.length() != 4) {
							return true;
						} else {
							if (data.length() == 6) {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("HHmmss"); // 검증할 날짜 포맷 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							} else if (data.length() == 4) {
								SimpleDateFormat dateFormatParser = new SimpleDateFormat("HHmm"); // 검증할 날짜 포맷 설정
								dateFormatParser.setLenient(false); // false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
								dateFormatParser.parse(data); // 대상 값 포맷에 적용되는지 확인
								return false;
							} else {
								return true;
							}
						}
					} catch (Exception e) {
						return true;
					}
				case "biz_no":
					if (data.length() != 10) {
						return true;
					} else {
						return false;
					}
				case "sto_id":
					if (data.length() != 5) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}
}