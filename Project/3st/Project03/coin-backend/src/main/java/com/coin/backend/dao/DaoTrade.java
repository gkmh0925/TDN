package com.coin.backend.dao;

import com.coin.backend.dto.DtoKimchiTrade;
import com.coin.backend.dto.DtoPersonalTrade;
import com.coin.backend.dto.DtoTrade;

import java.util.List;

public interface DaoTrade {
    // DB에서 Trade 리스트 전부 조회
    List<DtoTrade> tradeList();

    // personalTrade Insert
    int personalInsert(String userNo, String lowPrice, String lowCount, String hightPrice, String hightCount, String status);

    // PersonalTrade Select
    List<DtoPersonalTrade> personalTradeList(String userNo);

    // personalTrade Update
    int personalUpdate(String no, String status);

    // personalTrade Select Status
    List<DtoPersonalTrade> personalSelectStatusYet(String userNo);
    int personalUpdateDone(String no, String status, String orderId);
    List<DtoKimchiTrade> kimchiInsert(String userNo, String tradeBidPersent, String tradeBidCount, String tradeAskPersent, String tradeAskCount, String tradeStatus);
    List<DtoKimchiTrade> kimchiSelect(String userNo, String tradeStatus);
    List<DtoKimchiTrade> kimchiSelectStatus(String userNo);
    int kimchiUpdate(String no, String status);
    int kimchiUpdateDone(String no, String status, String orderId);

}
