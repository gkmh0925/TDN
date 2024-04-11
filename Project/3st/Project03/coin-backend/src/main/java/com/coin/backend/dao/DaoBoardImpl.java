package com.coin.backend.dao;

import com.coin.backend.dto.DtoReply;
import com.coin.backend.dto.DtoInfoBoard;
import com.coin.backend.dto.DtoNoticeBoard;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DaoBoardImpl implements DaoBoard {
	private static final String NAME_SPACE = "com.coin.backend.mapper.BoardMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;

	///////////////////
	////NoticeBoard////
// 페이징, 검색 없는 boardList
//	@Override
//	public List<DtoNoticeBoard> noticeBoardList() {
//		return sqlSession.selectList(NAME_SPACE + ".noticeBoardList");
//	}

	//페이징, 검색기능 boardLis
	@Override
	public List<DtoNoticeBoard> noticeBoardList(int startRow, int num, String type, String keyword) {// int startRow, int num,
		Map<String, Object> map = new HashMap<>();
		map.put("startRow",  startRow); //map을 해줘야함
		map.put("num",  num);
		map.put("type", type);
		map.put("keyword", keyword);
//		System.out.println("DaoBoardImpl.noticeBoardList : "+map);
		return sqlSession.selectList(NAME_SPACE + ".noticeBoardList", map);
	}
	//전체개수
	@Override
	public int noticeBoardCount(String type, String keyword) {
		Map<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("keyword", keyword);
//		System.out.println(map);
		return sqlSession.selectOne(NAME_SPACE + ".noticeBoardCount", map);
	}

	//게시판 상세보기
	@Override
	public List<DtoNoticeBoard> noticeBoardDetail(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no); //map을 해줘야함
		return sqlSession.selectList(NAME_SPACE + ".noticeBoardDetail", map);
	}

	//게시판 글쓰기
	@Override
	public List<DtoNoticeBoard> insertNoticeBoard(String title, String writer, String content, String boardType) {
		Map<String, String> map = new HashMap<>();
		map.put("title", title); //map을 해줘야함
		map.put("writer", writer);
		map.put("content", content);
		map.put("boardTypeDB", boardType);
		return sqlSession.selectList(NAME_SPACE + ".insertNoticeBoard", map);
	}
	//게시판 수정
	@Override
	public List<DtoNoticeBoard> updateNoticeBoard(int no, String title, String writer, String content, String boardType) {
		Map<String, String> map = new HashMap<>();
		map.put("no", String.valueOf(no)); //map을 해줘야함
		map.put("title", title);
		map.put("writer", writer);
		map.put("content", content);
		map.put("boardType", boardType);
		return sqlSession.selectList(NAME_SPACE + ".updateNoticeBoard", map);
	}
	//게시판 삭제
	@Override
	public List<DtoNoticeBoard> deleteNoticeBoard(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
		return sqlSession.selectList(NAME_SPACE + ".deleteNoticeBoard", map);
	}
	//게시판 조회수
	@Override
	public List<DtoNoticeBoard> noticeReadCount(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no); //map을 해줘야함
		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".noticeReadCount", map);
	}
	//게시판 좋아요
	@Override
	public List<DtoNoticeBoard> noticeLikeCount(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no); //map을 해줘야함
		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".noticeLikeCount", map);
	}


	///////////////////
	/////InfoBoard/////
//	@Override
//	public List<DtoInfoBoard> infoBoardList() {
//		return sqlSession.selectList(NAME_SPACE + ".infoBoardList");
//	}

	//페이징, 검색기능 boardLis
	@Override
	public List<DtoInfoBoard> infoBoardList(int startRow, int num, String type, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow); //map을 해줘야함
		map.put("num",  num);
		map.put("type", type);
		map.put("keyword", keyword);
		System.out.println("DaoBoardImpl.infoBoardList : "+map);
		return sqlSession.selectList(NAME_SPACE + ".infoBoardList", map);
	}

	//전체개수
	@Override
	public int infoBoardCount(String type, String keyword) {
		Map<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("keyword", keyword);
		System.out.println(map);
		return sqlSession.selectOne(NAME_SPACE + ".infoBoardCount", map);
	}


	//게시판 상세보기
	@Override
	public List<DtoInfoBoard> infoBoardDetail(int no){
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
//		System.out.print(no);
		return sqlSession.selectList(NAME_SPACE + ".infoBoardDetail", map);
	}
	//게시판 글쓰기
	@Override
	public List<DtoInfoBoard> insertInfoBoard(String title, String writer, String content, String boardType, String url) {
		Map<String, String> map = new HashMap<>();
		map.put("title", title); //map을 해줘야함
		map.put("writer", writer);
		map.put("content", content);
		map.put("boardTypeDB", boardType);
		map.put("url", url);
		return sqlSession.selectList(NAME_SPACE + ".insertInfoBoard", map);
	}

	@Override
	public List<DtoInfoBoard> updateInfoBoard(int no, String title, String writer, String content, String boardType) {
		Map<String, String> map = new HashMap<>();
		map.put("no", String.valueOf(no));
		map.put("title", title);
		map.put("writer", writer);
		map.put("content", content);
		map.put("boardType", boardType);
		return sqlSession.selectList(NAME_SPACE + ".updateInfoBoard", map);
	}

	@Override
	public List<DtoInfoBoard> deleteInfoBoard(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
		return sqlSession.selectList(NAME_SPACE + ".deleteInfoBoard", map);
	}
	//게시판 조회수
	@Override
	public List<DtoInfoBoard> infoReadCount(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no); //map을 해줘야함
		return sqlSession.selectList(NAME_SPACE + ".infoReadCount", map);
	}
	//게시판 좋아요
	@Override
	public List<DtoInfoBoard> infoLikeCount(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no); //map을 해줘야함
		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".infoLikeCount", map);
	}

	///////////////////
	///////reply///////
	@Override
	public List<DtoReply> replyList(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
//		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".replyList", map);
	}

	@Override
	public List<DtoReply> insertReply(int infoNo, int userNo, String content) {
		Map<String, String> map = new HashMap<>();
		map.put("infoNo", String.valueOf(infoNo));
		map.put("userNo", String.valueOf(userNo));
		map.put("content", content);
//		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".insertReply", map);
	}

	@Override
	public List<DtoReply> updateReply(int no, String content) {
		Map<String, String> map = new HashMap<>();
		map.put("no", String.valueOf(no));
		map.put("content", content);
		//		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".updateReply", map);
	}

	@Override
	public List<DtoReply> deleteReply(int no) {
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
		//		System.out.println(map);
		return sqlSession.selectList(NAME_SPACE + ".deleteReply", map);
	}
}