package com.coin.backend.controller;

import com.coin.backend.dto.DtoKimchiTrade;
import com.coin.backend.dto.DtoPersonalTrade;
import com.coin.backend.dto.DtoTrade;
import com.coin.backend.dto.DtoUser;
import com.coin.backend.service.ServiceObserver;
import com.coin.backend.service.ServiceTrade;
import com.coin.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControllerTrading {
    @Autowired
    private ServiceTrade service;

    @Autowired
    private ServiceObserver observer;

    @Autowired
    private ServiceUser serviceUser;

    // Trading List 전체 반환
    @RequestMapping(value={"/tradeList"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoTrade> tradingList(){
        return service.tradeList();
    }

    // personalTrade insert
    @RequestMapping(value={"/personalInsert"}, method={RequestMethod.POST})
    @ResponseBody
    private int personalInsert(@RequestBody DtoPersonalTrade dtoPersonalTrade){
        String userNo = dtoPersonalTrade.getUserNo();
        String lowPrice = dtoPersonalTrade.getLowPrice();
        String lowCount = dtoPersonalTrade.getLowCount();
        String hightPrice = dtoPersonalTrade.getHightPrice();
        String hightCount = dtoPersonalTrade.getHightCount();
        String status = dtoPersonalTrade.getStatus();

        System.out.println("============== Insert Controller ==============");
        System.out.println("userNo: " + userNo);
        System.out.println("lowPrice: " + lowPrice);
        System.out.println("lowCount: " + lowCount);
        System.out.println("hightPrice: " + hightPrice);
        System.out.println("hightCount: " + hightCount);
        System.out.println("status: " + status);
        System.out.println("===============================================");

        return service.personalInsert(userNo, lowPrice, lowCount, hightPrice, hightCount, status);
    }

    // personalTrade Select All Where no=userNo
    @RequestMapping(value={"/personalTradeList"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoPersonalTrade> personalTradeList(@RequestBody DtoPersonalTrade dtoPersonalTrade){
        return service.personalTradeList(dtoPersonalTrade.getUserNo());
    }

    // personalTrade Update
    @RequestMapping(value={"/personalUpdate"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private int personalUpdate(@RequestBody DtoPersonalTrade dtoPersonalTrade){
        return service.personalUpdate(dtoPersonalTrade.getNo(), dtoPersonalTrade.getStatus());
    }

    // personalTrade Start
    @RequestMapping(value={"/personalExecution"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private void personalExecution(@RequestBody DtoPersonalTrade dtoPersonalTrade){
        List<DtoUser> list = serviceUser.userKey(dtoPersonalTrade.getId());  //key 가쥬와
        String cKey = list.get(0).getcKey();
        String sKey = list.get(0).getsKey();

        observer.personalObserver(dtoPersonalTrade.getUserNo(), cKey, sKey, dtoPersonalTrade.getPh());
    }

    // kimchiTrade List 저장
    @RequestMapping(value={"/kimchiInsert"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoKimchiTrade> kimchiInsert(@RequestBody DtoKimchiTrade dtoKimchiTrade){
        return service.kimchiInsert(dtoKimchiTrade.getUserNo(), dtoKimchiTrade.getTradeBidPersent(), dtoKimchiTrade.getTradeBidCount(), dtoKimchiTrade.getTradeAskPersent(), dtoKimchiTrade.getTradeAskCount(), dtoKimchiTrade.getTradeStatus());
    }

    // personalTrade Select
    @RequestMapping(value={"/kimchiSelect"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoKimchiTrade> kimchiSelect(@RequestBody DtoKimchiTrade dtoKimchiTrade){
        return service.kimchiSelect(dtoKimchiTrade.getUserNo(), dtoKimchiTrade.getTradeStatus());
    }

    // kimchiTrade List 업데이트
    @RequestMapping(value={"/kimchiUpdate"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private int kimchiUpdate(@RequestBody DtoKimchiTrade dtoKimchiTrade){
        return service.kimchiUpdate(dtoKimchiTrade.getNo(), dtoKimchiTrade.getTradeStatus());
    }

    // kimchiTrade Start
    @RequestMapping(value={"/kimchiExecution"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private void kimchiExecution(@RequestBody DtoKimchiTrade dtoKimchiTrade){


        List<DtoUser> list = serviceUser.userKey(dtoKimchiTrade.getId());  //key 가쥬와
        String cKey = list.get(0).getcKey();
        String sKey = list.get(0).getsKey();

        observer.observer(dtoKimchiTrade.getUserNo(), cKey, sKey, dtoKimchiTrade.getPh());
    }

}
