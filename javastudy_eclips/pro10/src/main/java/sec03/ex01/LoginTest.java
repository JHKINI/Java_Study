package sec03.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginTest
 */
@WebServlet("/LoginTest")
public class LoginTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;chars=utf-8");
		PrintWriter out = response.getWriter();
		String user_name = request.getParameter("user_name");
		String user_pw = request.getParameter("user_pw");
		out.println("<html><body>");
		out.println("이름은"+ user_name +"<br>");
		out.println("비밀번호는 "+user_pw +"<br>");
		out.println("</body></html>");
		
		
	}

}
