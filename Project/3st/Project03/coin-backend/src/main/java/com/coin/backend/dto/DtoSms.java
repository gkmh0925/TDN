package com.coin.backend.dto;

public class DtoSms {
    private int no;
    private String insert_api_key;
    private String insert_api_secret_key;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getInsert_api_key() {
        return insert_api_key;
    }

    public void setInsert_api_key(String insert_api_key) {
        this.insert_api_key = insert_api_key;
    }

    public String getInsert_api_secret_key() {
        return insert_api_secret_key;
    }

    public void setInsert_api_secret_key(String insert_api_secret_key) {
        this.insert_api_secret_key = insert_api_secret_key;
    }
}
