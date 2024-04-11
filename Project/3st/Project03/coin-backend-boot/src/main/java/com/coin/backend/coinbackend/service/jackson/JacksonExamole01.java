package com.coin.backend.coinbackend.service.jackson;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JacksonExamole01 {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        User user = setUser();

        try {
            //객체를 json형태의 외부 파일로 저장.
            mapper.writeValue(new File("JsonData/user.json"), user);

            //객체를 String 형태로 변환.
            String jsonInString01 = mapper.writeValueAsString(user);
            System.out.println(jsonInString01);
            System.out.println(" ");

            //객체를 String 형태로 변환 + 정렬.
            String jsonInString02 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            System.out.println(jsonInString02);
            System.out.println(" ");


            //외부 파일을 객체로 변환
            User readUser = mapper.readValue(new File("JsonData/user.json"), User.class);
            System.out.println(readUser);
            System.out.println(" ");

            String jsonInString03 = "{\"name\":\"JSON\",\"age\":10,\"messages\":[\"gaga0\",\"gaga1\",\"gaga2\",\"gaga3\"]}";

            //Json 형태의 String 데이터를 객체로 변환.
            User readStringUser = mapper.readValue(jsonInString03, User.class);
            System.out.println(readStringUser);
            System.out.println(" ");


        } catch (JsonGenerationException e){
            e.printStackTrace();
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static User setUser(){
        User user = new User();
        user.setName("JSON");
        user.setAge(10);

        List<String> list = new ArrayList<>();

        list.add("gdgd1");
        list.add("gdgd2");
        list.add("gdgd3");
        list.add("gdgd4");
        user.setMessages(list);

        return user;
    }
}


