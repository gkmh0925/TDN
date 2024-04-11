package com.coin.backend.service;

import com.coin.backend.api.bithumb.Api_Client;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class ServiceApiImpl implements ServiceApi {
	public String Balance(String cKey, String sKey) {
		String result = null;

		Api_Client api = new Api_Client(cKey, sKey);

		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("currency", "ALL");

		try {
			result = api.callApi("/info/balance", rgParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String TradePlace(String cKey, String sKey, String tradeCount, String tradePrice, String tradeType) {
		String result = null;
		Api_Client api = new Api_Client(cKey, sKey);
		HashMap<String, String> rgParams = new HashMap<String, String>();

		rgParams.put("order_currency", "USDT");
		rgParams.put("payment_currency", "KRW");
		rgParams.put("units", tradeCount);
		rgParams.put("price", tradePrice);
		rgParams.put("type", tradeType);

		try {
			result = api.callApi("/trade/place", rgParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	public String TradeMarket(String cKey, String sKey, String tradeCount, String url) {
		String result = null;

		Api_Client api = new Api_Client(cKey, sKey);
		HashMap<String, String> rgParams = new HashMap<String, String>();

		rgParams.put("order_currency", "USDT");
		rgParams.put("payment_currency", "KRW");
		rgParams.put("units", tradeCount);

		try {
			result = api.callApi(url, rgParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	public String TradeStopLimit(String cKey, String sKey, String tradeCount, String tradePrice, String watchPrice) {
		String result = null;
		Api_Client api = new Api_Client(cKey, sKey);
		HashMap<String, String> rgParams = new HashMap<String, String>();

		rgParams.put("order_currency", "USDT");
		rgParams.put("payment_currency", "KRW");
		rgParams.put("watch_price", watchPrice);
		rgParams.put("price", tradePrice);
		rgParams.put("units", tradeCount);
		rgParams.put("type", "bid");

		try {
			result = api.callApi("/trade/stop_limit", rgParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}


	public String userTransactions(String cKey, String sKey) {
		String result = null;
		Api_Client api = new Api_Client(cKey, sKey);
		HashMap<String, String> rgParams = new HashMap<String, String>();

		rgParams.put("offset", "0");
		rgParams.put("count", "20");
		rgParams.put("searchGb", "0");
		rgParams.put("order_currency", "USDT");
		rgParams.put("payment_currency", "KRW");

		try {
			result = api.callApi("/info/user_transactions", rgParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
}

