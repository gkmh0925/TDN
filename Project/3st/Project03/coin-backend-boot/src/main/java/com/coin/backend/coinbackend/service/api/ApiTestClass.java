package com.coin.backend.coinbackend.service.api;

import com.coin.backend.coinbackend.service.api.bithumb.Api_Client;

import java.util.HashMap;

public class ApiTestClass {
    public static void main(String args[]) {
		Api_Client api = new Api_Client("f2c17879fe3746932ba09acab8a08f4d",
			"65beddbce011b5ef9ba34afa44f2805e");
	
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("order_currency", "USDT");
		rgParams.put("payment_currency", "KRW");

	
		try {
		    String result = api.callApi("/public/candlestick/BTC_KRW/24h", rgParams);
			System.out.println(" ");
			System.out.println(" ");
			System.out.println(" ");
		    System.out.println(result);
			System.out.println(" ");
			System.out.println(" ");
			System.out.println(" ");

		} catch (Exception e) {
		    e.printStackTrace();
		}
		
    }
}

