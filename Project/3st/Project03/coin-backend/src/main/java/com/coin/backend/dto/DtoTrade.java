package com.coin.backend.dto;

import java.sql.Timestamp;

public class DtoTrade {

    private String id;
    private long tradeNo;
    private int userNo;
    private String orderId;
    private Timestamp tradeDate;
    private String tradeType;
    private String tradePriceType;
    private String tradePrice;
    private String tradeCount;
    private String tradeMoney;
    private String watchPrice;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(long tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradePriceType() {
        return tradePriceType;
    }

    public void setTradePriceType(String tradePriceType) {
        this.tradePriceType = tradePriceType;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(String tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(String tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public String getWatchPrice() {
        return watchPrice;
    }

    public void setWatchPrice(String watchPrice) {
        this.watchPrice = watchPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
