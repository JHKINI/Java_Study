package com.spring.ex04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spring.ex01.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mem4.do")
public class MemberServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doHandle(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doHandle(request, response);

    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        response.setContentType("text/html; charset=utf-8");

        MemberDAO dao = new MemberDAO();

        String action = request.getParameter("action");

        String nextPage = "";

        if (action == null || action.equals("listMembers")) {

            List<MemberVO> membersList = dao.selectAllMemberList();

            request.setAttribute("membersList", membersList);

            nextPage = "test03/listMembers.jsp";

        } else if (action.equals("insertMember")) {

            String id = request.getParameter("id");
            String pwd = request.getParameter("pwd");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            MemberVO memberVO = new MemberVO();

            memberVO.setId(id);
            memberVO.setPwd(pwd);
            memberVO.setName(name);
            memberVO.setEmail(email);

            dao.insertMember(memberVO);

            nextPage = "mem4.do?action=listMembers";

        } else if (action.equals("updateMember")) {

            String id = request.getParameter("id");
            String pwd = request.getParameter("pwd");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            MemberVO memberVO = new MemberVO();

            memberVO.setId(id);
            memberVO.setPwd(pwd);
            memberVO.setName(name);
            memberVO.setEmail(email);

            dao.updateMember(memberVO);

            nextPage = "mem4.do?action=listMembers";

        } else if (action.equals("deleteMember")) {

            String id = request.getParameter("id");

            dao.deleteMember(id);

            nextPage = "mem4.do?action=listMembers";

        } else if (action.equals("searchMember")) {

            String name = request.getParameter("name");
            String email = request.getParameter("email");

            MemberVO memberVO = new MemberVO();

            memberVO.setName(name);
            memberVO.setEmail(email);

            List<MemberVO> membersList = dao.searchMember(memberVO);

            request.setAttribute("membersList", membersList);

            nextPage = "test03/listMembers.jsp";

        } else if (action.equals("foreachSelect")) {

            List<String> nameList = new ArrayList<String>();

            nameList.add("홍길동");
            nameList.add("이순신");
            nameList.add("김유신");

            List<MemberVO> membersList = dao.foreachSelect(nameList);

            request.setAttribute("membersList", membersList);

            nextPage = "test03/listMembers.jsp";
        } else if (action.equals("foreachInsert")) {
        	List<MemberVO> memList =new ArrayLsit();
        	memList.add(new MemberVO("m1","1234","박길동","m1@test.com"));
        	memList.add(new MemberVO("m2","1234","이길동","m2@test.com"));
        	memList.add(new MemberVO("m3","1234","김길동","m3@test.com"));
        	int result =dao.foreachInsert(memList);
        	nextPage = "/mem4.do?action=listMembers";
        }

        RequestDispatcher dispatch =
                request.getRequestDispatcher(nextPage);

        dispatch.forward(request, response);

    }

}