package com.coin.backend.coinbackend.controller;
import com.coin.backend.coinbackend.dto.database.DatabaseDto;
import com.coin.backend.coinbackend.service.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  //@Controller (프레젠테이션 레이어, 웹 요청과 응답을 처리함)
@RequestMapping("/db") // 'http://localhost:8080/db' url로 요청받으면 여기서 처리! 이후에 추가 url 아래로직 타
public class DatabaseController {

    @Autowired  //빈으로 등록된 객체를 참조하는 어노테이션 쉽게말해 SampleService 사용할거다 이말임. 참조할놈들 빈 등록(어노테이션 등록) 안해주면 에러난다
    private DatabaseService databaseService;

    @RequestMapping(value="/user", method = {RequestMethod.POST, RequestMethod.GET}) //SELECT * FROM user
    private List<DatabaseDto> user() {
        return databaseService.selectUser();
    }

    @RequestMapping(value="/login", method = {RequestMethod.POST, RequestMethod.GET})   //SELECT * FROM user WHERE UserId=? AND UserPw=?
    private List<DatabaseDto> login(@RequestBody DatabaseDto databaseDto) {
        return databaseService.selectLogin(databaseDto);
    }
}