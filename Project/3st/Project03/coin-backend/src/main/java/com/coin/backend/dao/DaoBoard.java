package com.coin.backend.dao;

import com.coin.backend.dto.DtoReply;
import com.coin.backend.dto.DtoInfoBoard;
import com.coin.backend.dto.DtoNoticeBoard;

import java.util.List;

public interface DaoBoard {

    //NoticeBoard
    //List<DtoNoticeBoard> noticeBoardList(); //페이징, 검색 없는 boardList
    List<DtoNoticeBoard> noticeBoardList(int startRow, int num, String type, String keyword); //페이징, 검색기능 boardList // int startRow, int num,
    int noticeBoardCount(String type, String keyword); //전체개수
    List<DtoNoticeBoard> noticeBoardDetail(int no);
    List<DtoNoticeBoard> insertNoticeBoard(String title, String writer, String content, String boardType);
    List<DtoNoticeBoard> updateNoticeBoard(int no, String title, String writer, String content, String boardType);
    List<DtoNoticeBoard> deleteNoticeBoard(int no);
    List<DtoNoticeBoard> noticeReadCount(int no);
    List<DtoNoticeBoard> noticeLikeCount(int no);




    //InfoBoard
//    List<DtoInfoBoard> infoBoardList(); //페이징, 검색 없는 boardList
    List<DtoInfoBoard> infoBoardList(int startRow, int num, String type, String keyword); //페이징, 검색기능 boardList // int startRow, int num,
    int infoBoardCount(String type, String keyword); //전체개수
    List<DtoInfoBoard> infoBoardDetail(int no);
    List<DtoInfoBoard> insertInfoBoard(String title, String writer, String content, String boardType, String url);
    List<DtoInfoBoard> updateInfoBoard(int no, String title, String writer, String content, String boardType);
    List<DtoInfoBoard> deleteInfoBoard(int no);
    List<DtoInfoBoard> infoReadCount(int no);
    List<DtoInfoBoard> infoLikeCount(int no);

    //info reply
    List<DtoReply> replyList(int no);
    List<DtoReply> insertReply(int infoNo, int userNo, String content);
    List<DtoReply> updateReply(int no, String content);
    List<DtoReply> deleteReply(int no);




}


/* 게시 글 수정과 삭제 할 때 비밀번호가 맞는지 체크하는 메서드
 *
 * - 게시 글의 비밀번호가 맞으면 : true를 반환
 * - 게시 글의 비밀번호가 맞지 않으면 : false를 반환
 **/
//    public boolean isPassCheck(int no, String pass);
//    // BoardDao를 이용해 게시 글을 수정하는 메서드
//    public abstract void updateBoard(Board board);
//    // BoardDao를 이용해 no에 해당하는 게시 글을 삭제하는 메서드
//    public abstract void deleteBoard(int no);
