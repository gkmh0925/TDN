package com.coin.backend.coinbackend.service.api;
import com.coin.backend.coinbackend.dto.api.restapi.RestApiDataDto;
import com.coin.backend.coinbackend.dto.api.restapi.RestApiDto;
import com.coin.backend.coinbackend.service.api.bithumb.Api_Client;
import com.coin.backend.coinbackend.service.jackson.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestApiService {

    public String restApi() {
        HashMap<String, String> resData; //api response MapData
        ObjectMapper mapper = new ObjectMapper();   //Json 데이터 변환에 필요한 lib 객체 (이런것도 있구나... 할것)
        String data = null;
        Api_Client api = new Api_Client("f2c17879fe3746932ba09acab8a08f4d","65beddbce011b5ef9ba34afa44f2805e"); //api 통신에 필요한 필수 requestData (key)
        HashMap<String, String> rgParams = new HashMap<String, String>();   //api 통신에 필요한 requestData

        try {
            resData = new ObjectMapper().readValue(api.callApi("/public/candlestick/BTC_KRW/24h", rgParams), HashMap.class);

            if(resData.get("status").equals("0000")){    //정상 : 0000
                data = mapper.writeValueAsString(resData.get("data"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
