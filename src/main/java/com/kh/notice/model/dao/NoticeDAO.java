package com.kh.notice.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.notice.model.vo.Notice;

public class NoticeDAO {

	public int insertNotice(SqlSession session, Notice notice) {
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	public int updateNotice(SqlSession session, Notice notice) {
		int result = session.update("NoticeMapper.updateNotice", notice);
		return result;
	}

	public int deleteNoticeByNo(SqlSession session, int noticeNo) {
		int result = session.delete("NoticeMapper.deleteNoticeByNo", noticeNo);
		return result;
	}

	public Notice selectOneByNo(SqlSession session, int noticeNo) {
		Notice notice = session.selectOne("NoticeMapper.selectOneByNo", noticeNo);
		return notice;
	}

	public List<Notice> selectNoticeList(SqlSession session, int currentPage) {
		int limit = 10;
		int offset = (currentPage-1)*limit;
		// RowBounds 는 쿼리문을 변경하지 않고 페이징을 처리할수 있게 해주는 클래스
		// limit값은 한 페이지당 보여주고 싶은 게시물의 갯수
		// offset값은 시작값, 변하는값
		// 1페이지에서는 0부터 시작해서 10개를 가져오고
		// 2페이지에서는 10부터 시작해서...
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = session.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}

	public String generatePageNavi(int currentPage) {
		int totalCount = 211;
		int recordCountPerPage = 10;
		int naviTotalCount = 0;
		if(totalCount % recordCountPerPage > 0) {
			naviTotalCount = totalCount/recordCountPerPage + 1;
		} else {
			naviTotalCount = totalCount/recordCountPerPage;
		}
		int naviCountPerPage = 10;
		int startNavi = ((currentPage-1)/naviCountPerPage) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == naviTotalCount) {
			needNext = false;
		}
		StringBuilder result = new StringBuilder();
		if(needPrev) {
			result.append("<a href='/notice/list.do?currentPage="+(startNavi-1)+"'>[이전]</a>&nbsp;");
		}
		for(int i = startNavi; i <= endNavi; i++) {
			result.append("<a href=\"/notice/list.do?currentPage="+ i +"\">"+ i +"</a>&nbsp;&nbsp;");
		}
		if(needNext) {
			result.append("<a href='/notice/list.do?currentPage="+(endNavi+1)+"'>[다음]</a>");
		}
		return result.toString();
	}

	

}
