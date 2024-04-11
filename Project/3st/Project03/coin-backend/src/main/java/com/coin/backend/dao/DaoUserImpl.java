package com.coin.backend.dao;

import com.coin.backend.dto.DtoUser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DaoUserImpl implements DaoUser{

    private static final String NAME_SPACE = "com.coin.backend.mapper.UserMapper";

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<DtoUser> userList() {
        return sqlSession.selectList(NAME_SPACE + ".userList");
    }

    @Override
    public List<DtoUser> userIdList(String id){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        return sqlSession.selectList(NAME_SPACE + ".userIdList", map);
    }

    @Override
    public List<DtoUser> userLogin(String id, String pw){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("pw", pw);
        return sqlSession.selectList(NAME_SPACE + ".userLogin", map);
    }

    @Override
    public List<DtoUser> userJoin(String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("pw", pw);
        map.put("ph", ph);
        map.put("mail", mail);
        map.put("cKey", cKey);
        map.put("sKey", sKey);
        map.put("keyYn", keyYn);
        map.put("adYn", adYn);
        return sqlSession.selectList(NAME_SPACE + ".userJoin", map);
    }

    @Override
    public List<DtoUser> userUpdate(int no, String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("pw", pw);
        map.put("ph", ph);
        map.put("mail", mail);
        map.put("cKey", cKey);
        map.put("sKey", sKey);
        map.put("keyYn", keyYn);
        map.put("adYn", adYn);
        map.put("no", String.valueOf(no));
        return sqlSession.selectList(NAME_SPACE + ".userUpdate", map);
    }

    @Override
    public List<DtoUser> userDelete(String id){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        System.out.println(map);
        return sqlSession.selectList(NAME_SPACE + ".userDelete", map);
    }

    @Override
    public List<DtoUser> userKey(String id){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        return sqlSession.selectList(NAME_SPACE + ".userKey", map);
    }

    @Override
    public List<DtoUser> userId(String ip){
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);
        return sqlSession.selectList(NAME_SPACE + ".userId", map);
    }

    @Override
    public List<DtoUser> userIpUpdate(String id, String ip){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("ip", ip);
        return sqlSession.selectList(NAME_SPACE + ".userIpUpdate", map);
    }

    @Override
    public List<DtoUser> userKeyUpdate(String id, String cKey, String sKey, String keyYn){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("cKey", cKey);
        map.put("sKey", sKey);
        map.put("keyYn", keyYn);

        System.out.println(id);
        System.out.println(cKey);
        System.out.println(sKey);
        System.out.println(keyYn);
        return sqlSession.selectList(NAME_SPACE + ".userKeyUpdate", map);
    }
}
