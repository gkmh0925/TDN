package com.coin.backend.service;

import com.coin.backend.api.bithumb.Api_Client;

import java.util.HashMap;

public interface ServiceApi {
    String Balance(String cKey, String sKey);
    String userTransactions(String cKey, String sKey);
    public String TradePlace(String cKey, String sKey, String tradeCount, String tradePrice, String tradeType);
    String TradeMarket(String cKey, String sKey, String tradeCount, String url);
    String TradeStopLimit(String cKey, String sKey, String tradeCount, String tradePrice, String watchPrice);
}
