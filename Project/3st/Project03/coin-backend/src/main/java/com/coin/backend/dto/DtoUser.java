package com.coin.backend.dto;

import java.util.List;

public class DtoUser {
    private int no;
    private String id;
    private String pw;
    private String ph;
    private String mail;
    private String cKey;
    private String sKey;
    private String adYn;
    private String keyYn;
    private String ip;

    public String getAdYn() {
        return adYn;
    }

    public void setAdYn(String adYn) {
        this.adYn = adYn;
    }

    public String getKeyYn() {
        return keyYn;
    }

    public void setKeyYn(String keyYn) {
        this.keyYn = keyYn;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }

    public String getsKey() {
        return sKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
