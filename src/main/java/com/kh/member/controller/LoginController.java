package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/member/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("member-id");
		String memberPw = request.getParameter("member-pw");
		Member member = new Member(memberId, memberPw);
		// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
		MemberService service = new MemberService();
		Member mOne = service.selectCheckLogin(member);
		if(mOne != null) {
			HttpSession session = request.getSession();
			session.setAttribute("memberId", mOne.getMemberId());
			session.setAttribute("memberName", mOne.getMemberName());
			response.sendRedirect("/index.jsp");
		} else {
			request.setAttribute("msg", "존재하지 않는 회원정보입니다.");
			request.setAttribute("url", "/index.jsp");
			request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}
	}

}
