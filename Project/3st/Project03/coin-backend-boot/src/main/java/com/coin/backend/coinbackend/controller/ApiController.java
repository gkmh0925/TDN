package com.coin.backend.coinbackend.controller;

import com.coin.backend.coinbackend.service.api.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController  //@Controller (프레젠테이션 레이어, 웹 요청과 응답을 처리함)
@RequestMapping("/api") // 'http://localhost:8080/api' url로 요청받으면 여기서 처리! 이후에 추가 url 아래로직 타
public class ApiController {
    @Autowired
    private RestApiService restApiService;

    @RequestMapping(value="/restApi", method = {RequestMethod.POST, RequestMethod.GET}) //SELECT * FROM user
    private String bituser() {
        return restApiService.restApi();
    }
}