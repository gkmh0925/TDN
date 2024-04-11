package com.coin.backend.dto;

import java.sql.Timestamp;

public class DtoPersonalTrade {

    private String id;
    private String no;
    private String userNo;
    private String orderId;
    private Timestamp tradeDate;
    private Timestamp orderDate;
    private String lowPrice;
    private String lowCount;
    private String hightPrice;
    private String hightCount;
    private String status;

    private String ph;

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

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

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getLowCount() {
        return lowCount;
    }

    public void setLowCount(String lowCount) {
        this.lowCount = lowCount;
    }

    public String getHightPrice() {
        return hightPrice;
    }

    public void setHightPrice(String hightPrice) {
        this.hightPrice = hightPrice;
    }

    public String getHightCount() {
        return hightCount;
    }

    public void setHightCount(String hightCount) {
        this.hightCount = hightCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
