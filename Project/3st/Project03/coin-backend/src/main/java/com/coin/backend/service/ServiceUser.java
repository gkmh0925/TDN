package com.coin.backend.service;

import com.coin.backend.dto.DtoUser;

import java.util.List;

public interface ServiceUser {
    List<DtoUser> userList();   //사용자 목록 조회 ALL
    List<DtoUser> userIdList(String id); //사용자 로그인 유효성 검사
    List<DtoUser> userJoin(String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn); //사용자 회원가입
    List<DtoUser> userUpdate(int no, String id, String pw, String ph, String mail, String cKey, String sKey, String keyYn, String adYn); //사용자 업데이트
    List<DtoUser> userDelete(String id); //사용자 삭제
    List<DtoUser> userLogin(String id, String pw); //사용자 로그인 유효성 검사
    List<DtoUser> userKey(String id);   //사용자 key 조회
    List<DtoUser> userId(String ip);   //사용자 ID 조회
    List<DtoUser> userIpUpdate(String id, String ip);   //사용자 IP 업데이트
    List<DtoUser> userKeyUpdate(String id, String cKey, String sKey, String keyYn); //사용자 Key 업데이트

}
