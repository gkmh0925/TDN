package com.coin.backend.service;


import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

public class ServiceMessage {
    final DefaultMessageService messageService;

    public ServiceMessage(String INSERT_API_KEY, String INSERT_API_SECRET_KEY) {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize(INSERT_API_KEY, INSERT_API_SECRET_KEY, "https://api.coolsms.co.kr");
    }

    public void sendOne(String from, String msg, String type) {
        String res;
        System.out.println(from);
        System.out.println(type);

        if(type.equals("personal")){
            res =  "Hedge Mode : " + msg;
        } else if(type.equals("kimchi")){
            res =  "Premium Mode : " + msg;
        } else{
            return;
        }
        System.out.println(res);


        Message message = new Message();
        message.setFrom("01020093966");
        message.setTo(from);
        message.setText(res);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

    }
}
