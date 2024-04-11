package com.coin.backend.service;

import com.coin.backend.api.bithumb.Api_Client;
import com.coin.backend.dao.DaoSms;
import com.coin.backend.dao.DaoSmsImpl;
import com.coin.backend.dao.DaoTrade;
import com.coin.backend.dto.DtoKimchiTrade;
import com.coin.backend.dto.DtoPersonalTrade;
import com.coin.backend.dto.DtoSms;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service
public class ServiceObserver{

    @Autowired
    private DaoTrade daoTrade;

    @Autowired
    DaoSms daoSms;

    public void personalObserver(String userNo, String cKey, String sKey, String fromPh){
        String observRes = null; // 돌다 걸리면 시장가api 태우고 그 결과담기
        DtoPersonalTrade dtoPersonalTrade = null; // 유저가 페이지에서 입력한 내용
        float usdtPrice = 0; // USDT 현재가격
        float lowPrice = 0; // 손실청산가
        String lowCount = ""; // 손실청산개수
        float hightPrice = 0; // 이득청산가
        String hightCount = ""; // 이득청산개수
        DtoSms dtoSms = null; //SMS
        String msg = ""; // SMS 메세지 내용


        // 유저가 입력한 정보 DB에서 조회해서 가져오기
        try {
            List<DtoPersonalTrade> userInput = daoTrade.personalSelectStatusYet(userNo);
            dtoPersonalTrade = (DtoPersonalTrade) userInput.get(0);
            lowPrice = Float.parseFloat(dtoPersonalTrade.getLowPrice());
            lowCount = dtoPersonalTrade.getLowCount();
            hightPrice = Float.parseFloat(dtoPersonalTrade.getHightPrice());
            hightCount = dtoPersonalTrade.getHightCount();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException){
            // 가장 최근 excution건이 미체결이 아니면 return
            return;
        } catch (Exception e) {
            System.out.println(e);
        }

        // USDT 현재 가격 가져오기
        ServiceApi serviceApi = new ServiceApiImpl();
        try {
            Api_Client api = new Api_Client(cKey, sKey);
            HashMap<String, String> rgParams = new HashMap<String, String>();
            String data = api.callApi("/public/transaction_history/USDT_KRW?count=1", rgParams);

            Map<String, List<String>> result = new ObjectMapper().readValue(data, HashMap.class);   // 반환 타입이 리스트
            usdtPrice = Float.parseFloat(((((((result.get("data").toString()).replace("]","")).replace("[","")).split(",")[3]).replace("price=",""))).replace(" ",""));    // 왜 리스트 get(0) 안됨? tlqkf

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 한무감시 로직 Start
        if( usdtPrice <= lowPrice){ // usdt 가격이 손실청산 가격보다 낮아졌어
            System.out.println("usdtPrice: " + usdtPrice);
            System.out.println("lowPrice: " + lowPrice);
            observRes = serviceApi.TradeMarket(cKey, sKey, lowCount, "/trade/market_sell");

            HashMap<String, String> result;
            try {
                result = new ObjectMapper().readValue(observRes, HashMap.class);

                System.out.println("==== status ====");
                System.out.println(result.get("status"));
                System.out.println(result.get("order_id"));

                if(result.get("status").equals("0000")){
                    msg = "체결완료";
                    int update = daoTrade.personalUpdateDone(dtoPersonalTrade.getNo(), "체결완료", result.get("order_id")); // PersonalTarde 테이블 Status=체결완료로  업데이트
                }else if(result.get("status").equals("5600")){
                    msg = "보유 USDT 부족";
                    int update = daoTrade.personalUpdate(dtoPersonalTrade.getNo(), "보유 USDT 부족"); // 김프 테이블 Status 업데이트 해주자
                }
                //SMS Start
                List<DtoSms> sms = daoSms.selectKey();
                dtoSms = (DtoSms) sms.get(0);
                ServiceMessage serviceMessage = new ServiceMessage(dtoSms.getInsert_api_key(), dtoSms.getInsert_api_secret_key());
                serviceMessage.sendOne(fromPh, msg, "personal");
                //SMS End
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if( usdtPrice >= hightPrice){ // usdt 가격이 이득청산 가격보다 높아졌어
            System.out.println("usdtPrice: " + usdtPrice);
            System.out.println("hightPrice: " + hightPrice);
            observRes = serviceApi.TradeMarket(cKey, sKey, hightCount, "/trade/market_sell");

            HashMap<String, String> result;
            try {
                result = new ObjectMapper().readValue(observRes, HashMap.class);

                System.out.println("==== status ====");
                System.out.println(result.get("status"));
                System.out.println(result.get("order_id"));

                if(result.get("status").equals("0000")){
                    msg = "체결완료";
                    int update = daoTrade.personalUpdateDone(dtoPersonalTrade.getNo(), "체결완료", result.get("order_id")); // PersonalTarde 테이블 Status=체결완료로  업데이트
                }else if(result.get("status").equals("5600")){
                    msg = "보유 USDT 부족";
                    int update = daoTrade.personalUpdate(dtoPersonalTrade.getNo(), "보유 USDT 부족"); // 김프 테이블 Status 업데이트 해주자
                }
                //SMS Start
                List<DtoSms> sms = daoSms.selectKey();
                dtoSms = (DtoSms) sms.get(0);
                ServiceMessage serviceMessage = new ServiceMessage(dtoSms.getInsert_api_key(), dtoSms.getInsert_api_secret_key());
                serviceMessage.sendOne(fromPh, msg, "personal");
                //SMS End
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        personalObserver(userNo, cKey, sKey, fromPh);
    }


    public void observer(String userNo, String cKey, String sKey, String fromPh){

        DtoKimchiTrade dtoKimchiTrade = null; //유저가 페이지에서 입력한 정보
        float price = 0;  //USDT 가격
        float premium = 0; //김프
        float value = 0;    //환율
        String apiResult = null; // API 결과
        Api_Client api = new Api_Client(cKey, sKey); //api 객체 생성
        DtoSms dtoSms = null; //SMS
        String msg = ""; // SMS 메세지 내용
        

    //유저가 김프 정보 입력한거 가져와 Start
        try {
            List<DtoKimchiTrade> userInput = daoTrade.kimchiSelectStatus(userNo);
            dtoKimchiTrade = (DtoKimchiTrade) userInput.get(0);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException){
            // 미체결 없으면 return 해라.
            return;
        } catch (Exception e) {
            System.out.println(e);
        }
    //유저가 김프 정보 입력한거 가져와 End

    // 자 USDT 가격 조회해보자. Start
        try {
            HashMap<String, String> rgParams = new HashMap<String, String>();
            String data = api.callApi("/public/transaction_history/USDT_KRW?count=1", rgParams);
            Map<String, List<String>> result = new ObjectMapper().readValue(data, HashMap.class);   //타입 리스트라 리스트로 받아야함
            price = Float.parseFloat(((((((result.get("data").toString()).replace("]","")).replace("[","")).split(",")[3]).replace("price=",""))).replace(" ",""));    // 왜 리스트 get(0) 안됨? ㅅㅂ
        } catch (Exception e) {
            e.printStackTrace();
        }
    // 자 USDT 가격 조회해보자. End

    // 환율 가져오자 Start
        URL url = null;
        try {
            url = new URL("https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=calculator&pkid=141&q=%ED%99%98%EC%9C%A8&where=m&u1=keb&u6=standardUnit&u7=0&u3=USD&u4=KRW&u8=down&u2=1");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            //System.out.println(in.readLine().replaceAll(" ",""));
            String line = "";
            StringBuilder data = new StringBuilder();
            while ((line = in.readLine()) != null) {
                data.append(line);
            }
            Map<String, List<String>> result = new ObjectMapper().readValue(data.toString().replaceAll(" ",""), HashMap.class);   //타입 리스트라 리스트로 받아야함
            value = Float.parseFloat(result.get("country").toString().replace("[","").replace("]","").replace("{","").replace("}","").split(" ")[3].replace(",","").replace("value=",""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            System.out.println(e);
        }
    // 환율 가져오자 End

    // 김프 구하자. Start
        premium = ((price / value - 1) * 100);
    // 김프 구하자. End

    // 옵저버 메인 로직 Start
        float bidPersent = Float.parseFloat(dtoKimchiTrade.getTradeBidPersent());
        float askPersent = Float.parseFloat(dtoKimchiTrade.getTradeAskPersent());

        if(premium <= bidPersent){ // 김프가 사고싶은(매수 입력한) 프리미엄 퍼센트보다 낮아졌따!
            ServiceApi serviceApi = new ServiceApiImpl();   // 시장가 매수 API Service
            apiResult = serviceApi.TradeMarket(cKey, sKey, dtoKimchiTrade.getTradeBidCount(), "/trade/market_buy");
            HashMap<String, String> result;
            try {
                result = new ObjectMapper().readValue(apiResult, HashMap.class);

                System.out.println("==== status ====");
                System.out.println(result.get("status"));
                System.out.println(result.get("order_id"));
                if(result.get("status").equals("0000")){
                    msg = "매수완료";
                    int update = daoTrade.kimchiUpdateDone(dtoKimchiTrade.getNo(), msg, result.get("order_id"));// 김프 테이블 Status 업데이트 해주자
                }else if(result.get("status").equals("5600")){
                    msg = "보유 KRW 부족";
                    int update = daoTrade.kimchiUpdate(dtoKimchiTrade.getNo(), msg);// 김프 테이블 Status 업데이트 해주자
                }

                //SMS Start
                List<DtoSms> sms = daoSms.selectKey();
                dtoSms = (DtoSms) sms.get(0);
                ServiceMessage serviceMessage = new ServiceMessage(dtoSms.getInsert_api_key(), dtoSms.getInsert_api_secret_key());
                serviceMessage.sendOne(fromPh, msg, "kimchi");
                //SMS End

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(premium >= askPersent){ // 김프가 팔고싶은(매도 입력한) 프리미엄 퍼센트보다 높아졌따!
            ServiceApi serviceApi = new ServiceApiImpl();   // 시장가 매도 API Service
            apiResult = serviceApi.TradeMarket(cKey, sKey, dtoKimchiTrade.getTradeAskCount(), "/trade/market_sell");
            HashMap<String, String> result;
            try {
                result = new ObjectMapper().readValue(apiResult, HashMap.class);

                System.out.println("==== status ====");
                System.out.println(result.get("status"));
                System.out.println(result.get("order_id"));

                if(result.get("status").equals("0000")){
                    msg = "매도완료";
                    int update = daoTrade.kimchiUpdateDone(dtoKimchiTrade.getNo(), msg, result.get("order_id"));// 김프 테이블 Status 업데이트 해주자
                }else if(result.get("status").equals("5600")){
                    msg = "보유 USDT 부족";
                    int update = daoTrade.kimchiUpdate(dtoKimchiTrade.getNo(), msg);// 김프 테이블 Status 업데이트 해주자
                }
                //SMS Start
                List<DtoSms> sms = daoSms.selectKey();
                dtoSms = (DtoSms) sms.get(0);
                ServiceMessage serviceMessage = new ServiceMessage(dtoSms.getInsert_api_key(), dtoSms.getInsert_api_secret_key());
                serviceMessage.sendOne(fromPh, msg, "kimchi");
                //SMS End
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    // 옵저버 메인 로직 End
        observer(userNo, cKey, sKey, fromPh);

    }
}
