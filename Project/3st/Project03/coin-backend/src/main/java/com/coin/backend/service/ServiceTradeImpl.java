package com.coin.backend.service;

import com.coin.backend.dao.DaoTrade;
import com.coin.backend.dto.DtoKimchiTrade;
import com.coin.backend.dto.DtoPersonalTrade;
import com.coin.backend.dto.DtoTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTradeImpl implements ServiceTrade {
    @Autowired
    private DaoTrade daoTrade;

    public List<DtoTrade> tradeList(){
        return daoTrade.tradeList();
    }
    // personalTrade Insert
    public int personalInsert(String userNo, String lowPrice, String lowCount, String hightPrice, String hightCount, String status) {
        System.out.println("=============== Insert Service ================");
        System.out.println("userNo: " + userNo);
        System.out.println("lowPrice: " + lowPrice);
        System.out.println("lowCount: " + lowCount);
        System.out.println("hightPrice: " + hightPrice);
        System.out.println("hightCount: " + hightCount);
        System.out.println("status: " + status);
        System.out.println("===============================================");
        return daoTrade.personalInsert(userNo, lowPrice, lowCount, hightPrice, hightCount, status);
    }

    // personalTrade Select
    public List<DtoPersonalTrade> personalTradeList(String userNo) {
        return daoTrade.personalTradeList(userNo);
    }

    // personalTrade Update
    public int personalUpdate(String no, String status) {
        return daoTrade.personalUpdate(no, status);
    }

    public List<DtoKimchiTrade> kimchiInsert(String userNo, String tradeBidPersent, String tradeBidCount, String tradeAskPersent, String tradeAskCount, String tradeStatus) {
        return daoTrade.kimchiInsert(userNo, tradeBidPersent, tradeBidCount, tradeAskPersent, tradeAskCount, tradeStatus);
    }
    public List<DtoKimchiTrade> kimchiSelect(String userNo, String tradeStatus) {
        return daoTrade.kimchiSelect(userNo, tradeStatus);
    }

    public int kimchiUpdate(String no, String status) {
        return daoTrade.kimchiUpdate(no, status);
    }

}
