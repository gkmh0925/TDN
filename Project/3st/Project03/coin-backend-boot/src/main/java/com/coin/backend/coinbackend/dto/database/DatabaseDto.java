package com.coin.backend.coinbackend.dto.database;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;


/*요 어노테이션 없으면 뱉을때 에러남...
* serializer 하는 과정에서 기본으로 접근 제한자가 public 이거나 getter/setter를 이용하기 때문에 인스턴스 필드를 private 등으로 선언하면 json으로 변환 과정에서 에러가 발생합니다.
출처: https://steady-hello.tistory.com/90 [컨닝페이퍼:티스토리]
* */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class DatabaseDto {  //DTO는 Data Transfer Object의 약자로, 계층 간 데이터 교환을 하기 위해 사용하는 객체이다. // 한줄요약 : 내가 쓸 데이터 담아둘 그릇

    private Integer UserNum;

    private String UserId;

    private String UserPw;

    private String UserName;

    private String UserPhNum1;

    private String UserPhNum2;

    private String UserPhNum3;

    private String UserEmail;

    private String UserSsn1;

    private String UserSsn2;

    private String Admin;

    private String JoinDate;

}
