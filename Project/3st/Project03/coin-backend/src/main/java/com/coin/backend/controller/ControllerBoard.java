package com.coin.backend.controller;

import com.coin.backend.dto.DtoReply;
import com.coin.backend.dto.DtoInfoBoard;
import com.coin.backend.dto.DtoNoticeBoard;
import com.coin.backend.service.ServiceBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ControllerBoard {

    @Autowired
    private ServiceBoard serviceBoard;	//board

    ///////////////////
    ////NoticeBoard////
    //리스트
/*
    @RequestMapping(value={"/noticeBoardList"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoNoticeBoard> noticeBoardList() {
        return serviceBoard.noticeBoardList();
    }
     private List<DtoNoticeBoard> noticeBoardDetail(@RequestBody DtoNoticeBoard dtoNoticeBoard) {
*/
    @RequestMapping(value={"/noticeBoardList"}, method=RequestMethod.POST)
    private  Map<String, Object> noticeBoardList(
            @RequestBody(required=false) Map<String, String> param){ //JSON데이터로 받는 RequestBody를 사용했다
        //아래는 프론트엔드에서 파라미터로 받는 방법//// let param2 = `pageNum=1&type=${this.searchType}&keyword=${this.keyword}`;
//        @RequestParam(value="pageNum", required=false,
//                    defaultValue="1") int pageNum,
//            @RequestParam(value="type", required=false,
//                    defaultValue="null") int type,
//            @RequestBody(required=false) Map<String, String> param,
//            @RequestParam(value="keyword", required=false,
//                    defaultValue="null") String keyword)
        
//        System.out.println("param : " + param);
//        System.out.println("type : " + type + ", keyword : " + keyword);
        return serviceBoard.noticeBoardList(Integer.parseInt(param.get("pageNum")),param.get("type"),param.get("keyword"));
//        return serviceBoard.noticeBoardList(pageNum, type, keyword); //@RequestParam 으로 받는 방법
    }


    //상세보기
    @RequestMapping(value={"/noticeBoardDetail"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoNoticeBoard> noticeBoardDetail(@RequestBody DtoNoticeBoard dtoNoticeBoard) {
        return serviceBoard.noticeBoardDetail(dtoNoticeBoard.getNo());
    }
    //글쓰기
    @RequestMapping(value={"/insertNoticeBoard"}, method = RequestMethod.POST)
    @ResponseBody
    private List<DtoNoticeBoard> insertNoticeBoard(@RequestBody DtoNoticeBoard dtoNoticeBoard){
        return serviceBoard.insertNoticeBoard(dtoNoticeBoard.getTitle(), dtoNoticeBoard.getWriter(), dtoNoticeBoard.getContent(), dtoNoticeBoard.getBoardType());
    }
    //수정하기
    @RequestMapping(value="/updateNoticeBoard", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoNoticeBoard> updateNoticeBoard(@RequestBody DtoNoticeBoard dtoNoticeBoard){
//        System.out.println(dtoNoticeBoard.getNo());
//        System.out.println(dtoNoticeBoard.getTitle());
//        System.out.println(dtoNoticeBoard.getWriter());
//        System.out.println(dtoNoticeBoard.getContent());
//        System.out.println(dtoNoticeBoard.getBoardType());
        return serviceBoard.updateNoticeBoard(dtoNoticeBoard.getNo(), dtoNoticeBoard.getTitle(), dtoNoticeBoard.getWriter(), dtoNoticeBoard.getContent(), dtoNoticeBoard.getBoardType());
    }
    //삭제하기
    @RequestMapping(value="/deleteNoticeBoard", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoNoticeBoard> deleteNoticeBoard(@RequestBody DtoNoticeBoard dtoNoticeBoard){
//        System.out.println(dtoNoticeBoard.getNo());
        return serviceBoard.deleteNoticeBoard(dtoNoticeBoard.getNo());
    }
    //게시판 조회수
    @RequestMapping(value="/noticeReadCount", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoNoticeBoard> noticeReadCount(@RequestBody DtoNoticeBoard dtoNoticeBoard){
        System.out.println("readcount controller" +dtoNoticeBoard.getNo());
        return serviceBoard.noticeReadCount(dtoNoticeBoard.getNo());
    }
    //게시판 좋아요
    @RequestMapping(value="/noticeLikeCount", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoNoticeBoard> noticeLikeCount(@RequestBody DtoNoticeBoard dtoNoticeBoard){
        System.out.println("noticeLikeCount controller" +dtoNoticeBoard.getNo());
        return serviceBoard.noticeLikeCount(dtoNoticeBoard.getNo());
    }


    ///////////////////
    /////InfoBoard/////
    //리스트
//    @RequestMapping(value={"/infoBoardList"}, method={RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    private List<DtoInfoBoard> infoBoardList() {
//        return serviceBoard.infoBoardList();
//    }

    @RequestMapping(value={"/infoBoardList"}, method=RequestMethod.POST)
    private  Map<String, Object> infoBoardList(
            @RequestBody(required=false) Map<String, String> param){ //JSON데이터로 받는 RequestBody를 사용했다
        return serviceBoard.infoBoardList(Integer.parseInt(param.get("pageNum")),param.get("type"),param.get("keyword"));
    }
    //상세보기
    @RequestMapping(value={"/infoBoardDetail"}, method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    private List<DtoInfoBoard> infoBoardDetail(@RequestBody DtoInfoBoard dtoInfoBoard) {
        return serviceBoard.infoBoardDetail(dtoInfoBoard.getNo());
    }
    //글쓰기
    @RequestMapping(value={"/insertInfoBoard"}, method = RequestMethod.POST)
    @ResponseBody
    private List<DtoInfoBoard> insertInfoBoard(@RequestBody DtoInfoBoard dtoInfoBoard){
        return serviceBoard.insertInfoBoard(dtoInfoBoard.getTitle(), dtoInfoBoard.getWriter(), dtoInfoBoard.getContent(), dtoInfoBoard.getBoardType(), dtoInfoBoard.getUrl());
    }
    //수정하기
    @RequestMapping(value="/updateInfoBoard", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoInfoBoard> updateInfoBoard(@RequestBody DtoInfoBoard dtoInfoBoard){
//        System.out.println(dtoInfoBoard.getNo());
//        System.out.println(dtoInfoBoard.getTitle());
//        System.out.println(dtoInfoBoard.getWriter());
//        System.out.println(dtoInfoBoard.getContent());
//        System.out.println(dtoInfoBoard.getBoardType());
        return serviceBoard.updateInfoBoard(dtoInfoBoard.getNo(), dtoInfoBoard.getTitle(), dtoInfoBoard.getWriter(), dtoInfoBoard.getContent(), dtoInfoBoard.getBoardType());
    }
    //삭제하기
    @RequestMapping(value="/deleteInfoBoard", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoInfoBoard> deleteInfoBoard(@RequestBody DtoInfoBoard dtoInfoBoard) {
//        System.out.println(dtoInfoBoard.getNo());
        return serviceBoard.deleteInfoBoard(dtoInfoBoard.getNo());
    }

    //게시판 조회수
    @RequestMapping(value="/infoReadCount", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoInfoBoard> infoReadCount(@RequestBody DtoInfoBoard dtoInfoBoard){
        System.out.println(dtoInfoBoard.getNo());
        return serviceBoard.infoReadCount(dtoInfoBoard.getNo());
    }
    //게시판 좋아요
    @RequestMapping(value="/infoLikeCount", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoInfoBoard> infoLikeCount(@RequestBody DtoInfoBoard dtoInfoBoard){
        System.out.println("infoLikeCount controller" +dtoInfoBoard.getNo());
        return serviceBoard.infoLikeCount(dtoInfoBoard.getNo());
    }


    ///////////////////
    ///////reply///////
    @RequestMapping(value="/replyList", method=RequestMethod.POST)
    @ResponseBody
    private List<DtoReply> replyList(@RequestBody DtoReply dtoReply) {
//        System.out.println(dtoReply.getNo());
        return serviceBoard.replyList(dtoReply.getNo());
    }
    //댓글쓰기
    @RequestMapping(value={"/insertReply"}, method = RequestMethod.POST)
    @ResponseBody
    private List<DtoReply> insertReply(@RequestBody DtoReply dtoReply){
//        System.out.println(dtoReply.getInfoNo());
//        System.out.println(dtoReply.getUserNo());
//        System.out.println(dtoReply.getContent());
        return serviceBoard.insertReply(dtoReply.getInfoNo(), dtoReply.getUserNo(), dtoReply.getContent());
    }
    //댓글수정
    @RequestMapping(value={"/updateReply"}, method = RequestMethod.POST)
    @ResponseBody
    private List<DtoReply> updateReply(@RequestBody DtoReply dtoReply){
//        System.out.println("update"+ dtoReply.getNo());
//        System.out.println(dtoReply.getContent());
        return serviceBoard.updateReply(dtoReply.getNo(), dtoReply.getContent());
    }
    //댓글삭제
    @RequestMapping(value={"/deleteReply"}, method = RequestMethod.POST)
    @ResponseBody
    private List<DtoReply> deleteReply(@RequestBody DtoReply dtoReply){
//        System.out.println("delete"+ dtoReply.getNo());
        return serviceBoard.deleteReply(dtoReply.getNo());
    }
}