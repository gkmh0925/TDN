package com.coin.backend.service;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.HashMap;
import java.util.Map;

public class ServiceQrSocket implements Emitter.Listener{

    public void send(String ip, String id, String pw) throws Exception{
        try {
            //Socket socket = IO.socket("http://3.39.202.228:3000");
            //Socket socket = IO.socket("http://192.168.219.55:3000");
            Socket socket = IO.socket("http://localhost:3000");
            socket.connect();
            Map<String, String> param = new HashMap<>();

            if(id != null){
                param.put("status", "0000");
            }else{
                param.put("status", "9999");
            }
            param.put("id", id);
            param.put("pw", pw);
            param.put("ip", ip);

            socket.emit("qr",param);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void call(Object... arg0) {
        // TODO Auto-generated method stub
    }
}
