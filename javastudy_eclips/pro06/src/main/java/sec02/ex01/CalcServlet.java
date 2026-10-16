package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalcServlet
 */
@WebServlet("/Calc")
public class CalcServlet extends HttpServlet {

    private static final float USD_RATE = 1300f;
    private static final float JPY_RATE = 9.5f;
    private static final float CNY_RATE = 180f;
    private static final float GBP_RATE = 1650f;
    private static final float EUR_RATE = 1400f;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        PrintWriter pw = response.getWriter();

        String command = request.getParameter("command");
        String won = request.getParameter("won");
        String operator = request.getParameter("operator");

        if (command != null && command.equals("calculate")) {

            String result = calculate(Float.parseFloat(won), operator);

            pw.print("<html><body>");
            pw.print("<font size=10>변환 결과</font><br>");
            pw.print("<font size=10>" + result + "</font><br>");
            pw.print("<a href='/pro06/Calc'>돌아가기</a>");
            pw.print("</body></html>");
            return;
        }

        pw.print("<html><body>");
        pw.print("<form name='frmCalc' method='get' action='/pro06/Calc'>");

        pw.print("<font size=5>환율 계산기</font><br>");
        pw.print("원화: <input type='text' name='won' size='10' /><br>");

        pw.print("<select name='operator'>");
        pw.print("<option value='dollar'>달러</option>");
        pw.print("<option value='en'>엔화</option>");
        pw.print("<option value='wian'>위안</option>");
        pw.print("<option value='pound'>파운드</option>");
        pw.print("<option value='euro'>유로</option>");
        pw.print("</select><br>");

        pw.print("<input type='hidden' name='command' value='calculate' />");
        pw.print("<input type='submit' value='변환' />");

        pw.print("</form>");
        pw.print("</body></html>");

        pw.close();
    }

    private static String calculate(float won, String operator) {

        if (operator.equals("dollar")) {
            return String.format("%.2f", won / USD_RATE);
        } else if (operator.equals("en")) {
            return String.format("%.2f", won / JPY_RATE);
        } else if (operator.equals("wian")) {
            return String.format("%.2f", won / CNY_RATE);
        } else if (operator.equals("pound")) {
            return String.format("%.2f", won / GBP_RATE);
        } else if (operator.equals("euro")) {
            return String.format("%.2f", won / EUR_RATE);
        }

        return "error";
    }
}
			
