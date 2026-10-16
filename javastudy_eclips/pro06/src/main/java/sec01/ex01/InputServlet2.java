package sec01.ex01;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InputServlet2
 */
@WebServlet("/Input2")
public class InputServlet2 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init 메서드 호출");
	}

	public void destroy() {
		System.out.println("destroy 메서드 호출");
	}

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException 
    {


        request.setCharacterEncoding("utf-8");
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
        	String name = (String) enu.nextElement();
        	String[] values = request.getParameterValues(name);
        	System.out.println("확인");
        	for(String value : values) {
        		System.out.println("name=" + name +", value="+value);
        	}
        }}
    }
