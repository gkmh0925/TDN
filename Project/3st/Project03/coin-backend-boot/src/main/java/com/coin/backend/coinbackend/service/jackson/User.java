package com.coin.backend.coinbackend.service.jackson;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String name;
    private int age;
    private List<String> messages;

    @Override
    public String toString(){
        String result = "";
        result = "[name:" + name + ",age:" + age + ",messages:" + messages + "]";

        return result;
    }
}
