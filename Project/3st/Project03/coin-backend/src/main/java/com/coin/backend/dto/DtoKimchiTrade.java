package com.coin.backend.dto;

import java.sql.Timestamp;

public class DtoKimchiTrade {
    private String id;
    private String no;
    private String userNo;
    private String orderId;
    private Timestamp tradeDate;
    private Timestamp orderDate;
    private String tradeBidPersent;
    private String tradeBidCount;
    private String tradeAskPersent;
    private String tradeAskCount;
    private String tradeStatus;

    private String ph;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
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

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getTradeBidPersent() {
        return tradeBidPersent;
    }

    public void setTradeBidPersent(String tradeBidPersent) {
        this.tradeBidPersent = tradeBidPersent;
    }

    public String getTradeBidCount() {
        return tradeBidCount;
    }

    public void setTradeBidCount(String tradeBidCount) {
        this.tradeBidCount = tradeBidCount;
    }

    public String getTradeAskPersent() {
        return tradeAskPersent;
    }

    public void setTradeAskPersent(String tradeAskPersent) {
        this.tradeAskPersent = tradeAskPersent;
    }

    public String getTradeAskCount() {
        return tradeAskCount;
    }

    public void setTradeAskCount(String tradeAskCount) {
        this.tradeAskCount = tradeAskCount;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }
}
