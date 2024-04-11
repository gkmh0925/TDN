package com.coin.backend.controller;

import com.coin.backend.dto.DtoTrade;
import com.coin.backend.dto.DtoUser;
import com.coin.backend.service.ServiceApi;
import com.coin.backend.service.ServiceUser;
import com.coin.backend.service.ServiceTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ControllerApi {

    @Autowired
    private ServiceApi serviceApi;

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ServiceTrade serviceTrade;

    @RequestMapping(value={"/balance"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private String Balance(@RequestBody DtoUser dtoUser) {

        String cKey = dtoUser.getcKey();
        String sKey = dtoUser.getsKey();

//        System.out.println("1ckey: " + cKey);
//        System.out.println("1skey: " + sKey);

        if(cKey == null && sKey == null){
            List<DtoUser> list = serviceUser.userKey(dtoUser.getId());  //key 가쥬와
            cKey = list.get(0).getcKey();
            sKey = list.get(0).getsKey();
        }
        return serviceApi.Balance(cKey, sKey);
    }

    @RequestMapping(value={"/userTransactions"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private String userTransactions(@RequestBody DtoUser dtoUser) {

        List<DtoUser> list = serviceUser.userKey(dtoUser.getId());  //key 가쥬와
        String cKey = list.get(0).getcKey();
        String sKey = list.get(0).getsKey();

        return serviceApi.userTransactions(cKey, sKey);
    }

    // 지정가 매수/매도
    @RequestMapping(value={"/tradePlace"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private String TradePlace(@RequestBody DtoTrade dtoTrade) {

        List<DtoUser> list = serviceUser.userKey(dtoTrade.getId());  //key 가쥬와
        String cKey = list.get(0).getcKey();
        String sKey = list.get(0).getsKey();

        String tradeCount = dtoTrade.getTradeCount();
        String tradePrice = dtoTrade.getTradePrice();
        String tradeType = dtoTrade.getTradeType();

        System.out.println("==============================");
        System.out.println("tradeCount: " + tradeCount);
        System.out.println("tradePrice: " + tradePrice);
        System.out.println("tradeType: " + tradeType);
        System.out.println("==============================");

        return serviceApi.TradePlace(cKey, sKey, tradeCount, tradePrice, tradeType);
    }

    // 시장가 매수/매도
    @RequestMapping(value={"/tradeMarket"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private String TradeMarket(@RequestBody DtoTrade dtoTrade){
        List<DtoUser> list = serviceUser.userKey(dtoTrade.getId());  //key 가쥬와
        String cKey = list.get(0).getcKey();
        String sKey = list.get(0).getsKey();

        String tradeCount = dtoTrade.getTradeCount();
        String url = dtoTrade.getUrl();

        return serviceApi.TradeMarket(cKey, sKey, tradeCount, url);
    }

    // 예약가 매수
    @RequestMapping(value={"/tradeStopLimit"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private String TradeStopLimit(@RequestBody DtoTrade dtoTrade){

        List<DtoUser> list = serviceUser.userKey(dtoTrade.getId());  //key 가쥬와
        String cKey = list.get(0).getcKey();
        String sKey = list.get(0).getsKey();

        String tradeCount = dtoTrade.getTradeCount();
        String tradePrice = dtoTrade.getTradePrice();
        String watchPrice = dtoTrade.getWatchPrice();

        return serviceApi.TradeStopLimit(cKey, sKey, tradeCount, tradePrice, watchPrice);
    }
}
