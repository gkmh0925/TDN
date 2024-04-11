package com.coin.backend.dao;

import com.coin.backend.dto.DtoUser;

import java.util.List;

public interface DaoUser {
    List<DtoUser> userList();
    List<DtoUser> userIdList(String id);
    List<DtoUser> userJoin(String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn);
    List<DtoUser> userUpdate(int no, String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn);
    List<DtoUser> userDelete(String id);
    List<DtoUser> userLogin(String id, String pw);
    List<DtoUser> userKey(String id);
    List<DtoUser> userId(String ip);
    List<DtoUser> userIpUpdate(String id, String ip);
    List<DtoUser> userKeyUpdate(String id, String cKey, String sKey, String keyYn);
}
