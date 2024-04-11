package com.coin.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coin.backend.dao.DaoBoard;
import com.coin.backend.dto.DtoReply;
import com.coin.backend.dto.DtoInfoBoard;
import com.coin.backend.dto.DtoNoticeBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceBoardImpl implements ServiceBoard {
	private static final int PAGE_SIZE = 10; //게시글 수
	private static final int PAGE_GROUP = 5; //페이지 수

	@Autowired
	private DaoBoard daoBoard;

	////NoticeBoard////
	//페이징, 검색 없는 boardList
/*	@Override
	public List<DtoNoticeBoard> noticeBoardList() {
		return daoBoard.noticeBoardList();
	}*/


	//전체개수
//	@Override
//	public int noticeBoardCount(String type, String keyword) {
//		return daoBoard.noticeBoardCount(type,keyword); //여기에 선언 안해도 될듯?
//	}

	@Override
	public Map<String, Object> noticeBoardList(int pageNum, String type, String keyword) { ///int startRow, int num,
		//현재 페이지
		int currentPage = pageNum;
		//pageNum이 1일 경우 0부터 시작, 2일 경우 10부터 시작
		int startRow = (currentPage - 1) * PAGE_SIZE; //1부터 시작하고 싶으면 +1 넣어주기
		//시작페이지
		int startPage = (currentPage-1)/PAGE_GROUP*PAGE_GROUP+1;
		//전체 공지사항 개수
		int listCount = daoBoard.noticeBoardCount(type,keyword);
		//10개씩 몇페이지인지, 페이지 개수
		int pageCount =
				listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1); // 총 페이지 개수 , 나머지가 0이 아니면 +1
//		System.out.println(listCount);
//		System.out.println(pageCount);

		// 현재 페이지 그룹의 마지막 페이지 : 10, 20, 30...
		int endPage = startPage + PAGE_GROUP - 1;
		if(endPage>pageCount) {
			endPage=pageCount;
		}

	// 이전 및 다음 블럭의 시작 페이지 번호 계산
		int prevPage=startPage-PAGE_GROUP;
		int nextPage=startPage+PAGE_GROUP;

//		System.out.println("listCount : " + listCount + ", type : " + type + ", pageNum : " + pageNum
//				+ ", keyword : " + keyword +  ", pageCount : " + pageCount);
		List<DtoNoticeBoard> noticeBoardList= daoBoard.noticeBoardList(startRow, PAGE_SIZE, type, keyword);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("noticeBoardList", noticeBoardList);
		modelMap.put("pageCount", pageCount);
		modelMap.put("startRow", startRow);
		modelMap.put("startPage", startPage);
		modelMap.put("endPage", endPage);
		modelMap.put("currentPage", currentPage);
		modelMap.put("listCount", listCount);
		modelMap.put("PAGE_SIZE", PAGE_SIZE);
		modelMap.put("pageGroup", PAGE_GROUP);
		modelMap.put("type", type);
		modelMap.put("keyword", keyword);
		modelMap.put("prevPage", prevPage);
		modelMap.put("nextPage", nextPage);

//		System.out.println("모델타입: "+ modelMap);

		return modelMap;
//		return daoBoard.noticeBoardList(pageNum, type, keyword);  // startRow, num,
	}

	//게시판 상세보기
	@Override
	public List<DtoNoticeBoard> noticeBoardDetail(int no) {
		return daoBoard.noticeBoardDetail(no);
	}
	//게시판 글쓰기
	@Override
	public List<DtoNoticeBoard> insertNoticeBoard(String title, String writer, String content, String boardType) {
		return daoBoard.insertNoticeBoard(title, writer, content, boardType);
	}
	//게시판 수정하기
	@Override
	public List<DtoNoticeBoard> updateNoticeBoard(int no, String title, String writer, String content, String boardType){
		return daoBoard.updateNoticeBoard(no, title, writer, content, boardType);
	}

	@Override
	public List<DtoNoticeBoard> deleteNoticeBoard(int no) {
		return daoBoard.deleteNoticeBoard(no);
	}
	//게시판 조회수
	@Override
	public List<DtoNoticeBoard> noticeReadCount(int no) {
		return daoBoard.noticeReadCount(no);
	}

	@Override
	public List<DtoNoticeBoard> noticeLikeCount(int no) {
		return daoBoard.noticeLikeCount(no);
	}


	/////InfoBoard/////
//	@Override
//	public List<DtoInfoBoard> infoBoardList() {
//		return daoBoard.infoBoardList();
//	}

	@Override
	public Map<String, Object> infoBoardList(int pageNum, String type, String keyword) {
		//현재 페이지
		int currentPage = pageNum;
		//pageNum이 1일 경우 0부터 시작, 2일 경우 10부터 시작
		int startRow = (currentPage - 1) * PAGE_SIZE; //1부터 시작하고 싶으면 +1 넣어주기
		//시작페이지
		int startPage = (currentPage-1)/PAGE_GROUP*PAGE_GROUP+1;
		//전체 공지사항 개수
		int listCount = daoBoard.infoBoardCount(type,keyword);
		//10개씩 몇페이지인지, 페이지 개수
		int pageCount =
				listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1); // 총 페이지 개수 , 나머지가 0이 아니면 +1
//		System.out.println(listCount);
//		System.out.println(pageCount);

		// 현재 페이지 그룹의 마지막 페이지 : 10, 20, 30...
		int endPage = startPage + PAGE_GROUP - 1;
		if(endPage>pageCount) {
			endPage=pageCount;
		}

		//		// 이전 및 다음 블럭의 시작 페이지 번호 계산
////		prevPage=startPage-PAGE_GROUP;
////		nextPage=startPage+PAGE_GROUP;

//		System.out.println("listCount : " + listCount + ", type : " + type + ", pageNum : " + pageNum
//				+ ", keyword : " + keyword +  ", pageCount : " + pageCount);

		List<DtoInfoBoard> infoBoardList= daoBoard.infoBoardList(startRow, PAGE_SIZE, type, keyword);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("infoBoardList", infoBoardList);
		modelMap.put("pageCount", pageCount);
		modelMap.put("startRow", startRow);
		modelMap.put("startPage", startPage);
		modelMap.put("endPage", endPage);
		modelMap.put("currentPage", currentPage);
		modelMap.put("listCount", listCount);
		modelMap.put("pageGroup", PAGE_GROUP);
		modelMap.put("type", type);
		modelMap.put("keyword", keyword);
//		System.out.println("info 모델타입: "+ modelMap);

		return modelMap;
	}

	//게시판 상세보기
	@Override
	public List<DtoInfoBoard> infoBoardDetail(int no) {
		return daoBoard.infoBoardDetail(no);
	}
	//게시판 글쓰기
	@Override
	public List<DtoInfoBoard> insertInfoBoard(String title, String writer, String content, String boardType, String url){
		return daoBoard.insertInfoBoard(title, writer, content, boardType, url);
	}
	//게시판 수정하기
	@Override
	public List<DtoInfoBoard> updateInfoBoard(int no, String title, String writer, String content, String boardType) {
		return daoBoard.updateInfoBoard(no, title, writer, content, boardType);
	}

	@Override
	public List<DtoInfoBoard> deleteInfoBoard(int no) {
		return daoBoard.deleteInfoBoard(no);
	}

	@Override
	public List<DtoInfoBoard> infoReadCount(int no) {
		return daoBoard.infoReadCount(no);
	}
	@Override
	public List<DtoInfoBoard> infoLikeCount(int no) {
		return daoBoard.infoLikeCount(no);
	}


	/////reply/////
	@Override
	public List<DtoReply> replyList(int no) {
		return daoBoard.replyList(no);
	}

	@Override
	public List<DtoReply> insertReply(int infoNo, int userNo, String content) {
		return daoBoard.insertReply(infoNo, userNo, content);
	}

	@Override
	public List<DtoReply> updateReply(int no, String content) {
		return daoBoard.updateReply(no, content);
	}

	@Override
	public List<DtoReply> deleteReply(int no) {
		return daoBoard.deleteReply(no);
	}


}
