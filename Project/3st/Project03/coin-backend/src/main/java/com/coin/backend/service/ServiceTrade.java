package com.coin.backend.service;

import com.coin.backend.dto.DtoKimchiTrade;
import com.coin.backend.dto.DtoPersonalTrade;
import com.coin.backend.dto.DtoTrade;

import java.util.List;

public interface ServiceTrade {
    // Trading 리스트 전부 조회
    List<DtoTrade> tradeList();
    // personalTrade Insert
    int personalInsert(String userNo, String lowPrice, String lowCount, String hightPrice, String hightCount, String status);

    // personalTrade Select
    List<DtoPersonalTrade> personalTradeList(String userNo);

    // personalTrade Update
    int personalUpdate(String no, String status);

    List<DtoKimchiTrade> kimchiInsert(String userNo, String tradeBidPersent, String tradeBidCount, String tradeAskPersent, String tradeAskCount, String tradeStatus);
    List<DtoKimchiTrade> kimchiSelect(String userNo, String tradeStatus);
    int kimchiUpdate(String no, String status);
}
