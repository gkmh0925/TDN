package com.coin.backend.service;

import java.util.List;
import java.util.Map;

import com.coin.backend.dto.DtoReply;
import com.coin.backend.dto.DtoInfoBoard;
import com.coin.backend.dto.DtoNoticeBoard;

public interface ServiceBoard {
    //NoticeBoard
//    List<DtoNoticeBoard> noticeBoardList(); //페이징, 검색 없는 boardList
    Map<String, Object> noticeBoardList(int pageNum, String type, String keyword); //페이징, 검색기능 boardList //int startRow, int num,
//    int noticeBoardCount(String type, String keyword); //전체개수
    List<DtoNoticeBoard> noticeBoardDetail(int no);
    List<DtoNoticeBoard> insertNoticeBoard(String title, String writer, String content, String boardType);
    List<DtoNoticeBoard> updateNoticeBoard(int no, String title, String writer, String content, String boardType);
    List<DtoNoticeBoard> deleteNoticeBoard(int no);
    List<DtoNoticeBoard> noticeReadCount(int no);
    List<DtoNoticeBoard> noticeLikeCount(int no);



    //InfoBoard
//    List<DtoInfoBoard> infoBoardList(); //페이징, 검색 없는 boardList
    Map<String, Object> infoBoardList(int pageNum, String type, String keyword); //페이징, 검색기능 boardList //int startRow, int num,
    //    int infoBoardListCount(String type, String keyword); //전체개수
    List<DtoInfoBoard> infoBoardDetail(int no);
    List<DtoInfoBoard> insertInfoBoard(String title, String writer, String content, String boardType, String url);
    List<DtoInfoBoard> updateInfoBoard(int no, String title, String writer, String content, String boardType);
    List<DtoInfoBoard> deleteInfoBoard(int no);
    List<DtoInfoBoard> infoReadCount(int no);
    List<DtoInfoBoard> infoLikeCount(int no);

    //reply
    List<DtoReply> replyList(int no);
    List<DtoReply> insertReply(int infoNo, int userNo, String content);
    List<DtoReply> updateReply(int no, String content);
    List<DtoReply> deleteReply(int no);

}
