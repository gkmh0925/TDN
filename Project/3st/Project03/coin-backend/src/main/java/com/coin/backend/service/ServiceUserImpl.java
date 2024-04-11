package com.coin.backend.service;

import com.coin.backend.dao.DaoUser;
import com.coin.backend.dto.DtoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUserImpl implements ServiceUser{

    @Autowired
    private DaoUser daoUser;

    @Override
    public List<DtoUser> userList() {
        return daoUser.userList();
    }

    @Override
    public List<DtoUser> userIdList(String id) {
        return daoUser.userIdList(id);
    }

    @Override
    public List<DtoUser> userJoin(String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn) {
        return daoUser.userJoin(id, pw, ph, mail, cKey, sKey, keyYn, adYn);
    }

    @Override
    public List<DtoUser> userUpdate(int no, String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn) {
        return daoUser.userUpdate(no, id, pw, ph, mail, cKey, sKey, keyYn, adYn);
    }

    @Override
    public List<DtoUser> userDelete(String id) {
        return daoUser.userDelete(id);
    }

    @Override
    public List<DtoUser> userLogin(String id, String pw) {
        return daoUser.userLogin(id, pw);
    }

    @Override
    public List<DtoUser> userKey(String id){
        return daoUser.userKey(id);
    }

    @Override
    public List<DtoUser> userId(String ip){
        return daoUser.userId(ip);
    }

    @Override
    public List<DtoUser> userIpUpdate(String id, String ip){
        return daoUser.userIpUpdate(id, ip);
    }

    @Override
    public List<DtoUser> userKeyUpdate(String id, String cKey, String sKey, String keyYn) {
        return daoUser.userKeyUpdate(id, cKey, sKey, keyYn);
    }
}
