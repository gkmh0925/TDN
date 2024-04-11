package com.coin.backend.coinbackend.dao;

import com.coin.backend.coinbackend.dto.database.DatabaseDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper //Mybatis 어노테이션 //@Mapper 어노테이션을 통해 스프링부트가 해당 클래스를 mapper로 인식할 수 있으며 해당 interface를 MyBatis mapper 빈으로 등록해줄 수 있다.
public interface DatabaseDao {  //DAO 는 Data Access Object의 약자로, DB에 접근하기 위한 객체(인터페이스)이다. // 한줄요약 : 마이바티스가 이놈 바라보고 명령 듣는다.
    List<DatabaseDto> selectUser();   //SELECT * FROM user
    List<DatabaseDto> selectLogin(DatabaseDto sampleUserDto);  //SELECT * FROM user WHERE UserId=? AND UserPw=?
}

/*
 * @Mapper 어노테이션을 붙인 DAO
 * DAO는 DB에 접근하기 위한 객체이다.
 * 그 접근을 @Mapper 어노테이션이 도움.
 * List(SampleUserDto 객체를 담는) 형탱의 데이터를 return받는 메서드들을 선언해두고
 * Service에서 DAO 인터페이스를 참조하여 사용.
 *
 * 중요 : DB(Mybatis 사용하는 .xml)에 접근하기 위한 객체
 *
 * */