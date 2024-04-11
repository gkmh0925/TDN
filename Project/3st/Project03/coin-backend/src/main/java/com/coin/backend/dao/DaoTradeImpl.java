package com.coin.backend.dao;

import com.coin.backend.dto.DtoKimchiTrade;
import com.coin.backend.dto.DtoPersonalTrade;
import com.coin.backend.dto.DtoTrade;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DaoTradeImpl implements DaoTrade {
    private static final String NAME_SPACE = "com.coin.backend.mapper.TradeMapper";
    private static final String PERSONAL_NAME_SPACE = "com.coin.backend.mapper.PersonalTradeMapper";
    private static final String KIMCHI_NAME_SPACE = "com.coin.backend.mapper.KimchiTradeMapper";

    @Autowired
    private SqlSessionTemplate sqlSession;

    // DB에서 Trading 리스트 전부 조회
    @Override
    public List<DtoTrade> tradeList() {
        return sqlSession.selectList(NAME_SPACE + ".tradeList");
    }

    // personalTrade Insert
    public int personalInsert(String userNo, String lowPrice, String lowCount, String hightPrice, String hightCount, String status) {
        System.out.println("================ Insert DAO ===================");
        System.out.println("userNo: " + userNo);
        System.out.println("lowPrice: " + lowPrice);
        System.out.println("lowCount: " + lowCount);
        System.out.println("hightPrice: " + hightPrice);
        System.out.println("hightCount: " + hightCount);
        System.out.println("status: " + status);
        System.out.println("===============================================");

        Map<String, String> insertMap = new HashMap<>();
        insertMap.put("userNo", userNo);
        insertMap.put("lowPrice", lowPrice);
        insertMap.put("lowCount", lowCount);
        insertMap.put("hightPrice", hightPrice);
        insertMap.put("hightCount", hightCount);
        insertMap.put("status", status);

        return sqlSession.insert(PERSONAL_NAME_SPACE+".persTradeInsert", insertMap);
    }

    // PersonalTrade Select
    public List<DtoPersonalTrade> personalTradeList(String userNo) {
        Map<String, String> selectMap = new HashMap<>();
        selectMap.put("userNo", userNo);
        return sqlSession.selectList(PERSONAL_NAME_SPACE+".persTradeList", selectMap);
    }

    // PersonalTrade Update (취소요청)
    public int personalUpdate(String no, String status) {
        Map<String, String> updateMap = new HashMap<>();
        updateMap.put("no", no);
        updateMap.put("status", status);
        return sqlSession.update(PERSONAL_NAME_SPACE+".persTradeUpdate", updateMap);
    }

    public List<DtoPersonalTrade> personalSelectStatusYet(String userNo) {
        Map<String, String> selectStatusYerMap = new HashMap<>();
        selectStatusYerMap.put("userNo", userNo);
        return sqlSession.selectList(PERSONAL_NAME_SPACE + ".persSelectStatusYet", selectStatusYerMap);
    }


    public int personalUpdateDone(String no, String status, String orderId) {
        Map<String, String> updateMap = new HashMap<>();
        updateMap.put("no", no);
        updateMap.put("status", status);
        updateMap.put("orderId", orderId);
        System.out.println(updateMap);
        return sqlSession.update(PERSONAL_NAME_SPACE + ".persTardeDoneUpdate", updateMap);
    }

	@Override
    public List<DtoKimchiTrade> kimchiInsert(String userNo, String tradeBidPersent, String tradeBidCount, String tradeAskPersent, String tradeAskCount, String tradeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("tradeBidPersent", tradeBidPersent);
        map.put("tradeBidCount", tradeBidCount);
        map.put("tradeAskPersent", tradeAskPersent);
        map.put("tradeAskCount", tradeAskCount);
        map.put("tradeStatus", tradeStatus);
        return sqlSession.selectList(KIMCHI_NAME_SPACE + ".kimchiInsert", map);
    }
    @Override
    public List<DtoKimchiTrade> kimchiSelect(String userNo, String tradeStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("tradeStatus", tradeStatus);
        return sqlSession.selectList(KIMCHI_NAME_SPACE + ".kimchiSelect", map);
    }
    @Override
    public List<DtoKimchiTrade> kimchiSelectStatus(String userNo) {
        Map<String, String> map = new HashMap<>();
        map.put("userNo", userNo);
        return sqlSession.selectList(KIMCHI_NAME_SPACE + ".kimchiSelectStatus", map);
    }

    @Override
    public int kimchiUpdate(String no, String status) {
        Map<String, String> updateMap = new HashMap<>();
        updateMap.put("no", no);
        updateMap.put("status", status);
        System.out.println(updateMap);
        return sqlSession.update(KIMCHI_NAME_SPACE + ".kimchiUpdate", updateMap);
    }

    @Override
    public int kimchiUpdateDone(String no, String status, String orderId) {
        Map<String, String> updateMap = new HashMap<>();
        updateMap.put("no", no);
        updateMap.put("status", status);
        updateMap.put("orderId", orderId);
        System.out.println(updateMap);
        return sqlSession.update(KIMCHI_NAME_SPACE + ".kimchiUpdateDone", updateMap);
    }
}
