package sec03.ex01;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class EncoderFilter extends HttpFilter implements Filter {

    ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("utf-8 인코딩...");
        context = fConfig.getServletContext();
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
    	long begin = System.currentTimeMillis();
        System.out.println("doFilter 호출");

        request.setCharacterEncoding("utf-8");

        String contextPath =
                ((HttpServletRequest) request).getContextPath();

        String pathInfo =
                ((HttpServletRequest) request).getRequestURI();

        String realPath =
                context.getRealPath(pathInfo);

        String mesg =
                "Context : " + contextPath +
                "\nURI 정보 : " + pathInfo +
                "\n물리적 경로 : " + realPath;

        System.out.println(mesg);

        chain.doFilter(request, response);
        long end = System.currentTimeMillis();
        System.out.println("작업 시간:"+(end-begin)+"ms");
    }

    public void destroy() {
        System.out.println("destroy 호출");
    }
}