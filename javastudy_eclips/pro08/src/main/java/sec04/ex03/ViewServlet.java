package sec04.ex03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewMembers")
public class ViewServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        List membersList = (List) request.getAttribute("membersList");

        out.print("<html><body>");
        out.print("<table border='1'>");
        out.print("<tr align='center' bgcolor='lightgreen'>");
        out.print("<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td><td>삭제</td>");
        out.print("</tr>");

        for (int i = 0; i < membersList.size(); i++) {
            MemberVO memberVO = (MemberVO) membersList.get(i);

            String id = memberVO.getId();
            String pwd = memberVO.getPwd();
            String name = memberVO.getName();
            String email = memberVO.getEmail();
            Date joinDate = memberVO.getJoinDate();

            out.print("<tr>");
            out.print("<td>" + id + "</td>");
            out.print("<td>" + pwd + "</td>");
            out.print("<td>" + name + "</td>");
            out.print("<td>" + email + "</td>");
            out.print("<td>" + joinDate + "</td>");
            out.print("<td><a href='/pro09/member3?command=delMember&id=" + id + "'>삭제</a></td>");
            out.print("</tr>");
        }

        out.print("</table>");
        out.print("<br>");
        out.print("<a href='/pro09/memberForm.html'>새 회원 등록하기</a>");
        out.print("</body></html>");
    }
}