package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter 0",urlPatterns = {"/*"})
public class Filter00_Encoding implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 0 - encoding begins");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (path.contains("/login") || path.contains("/myapp")){
            System.out.println("未设置字符编码格式");
        }else {
            System.out.println(method);
            response.setContentType("text/html;charset=UTF-8");
            System.out.println("设置响应对象字符编码格式为utf8");
            if (method.equals("POST")||method.equals("PUT")){
                request.setCharacterEncoding("UTF-8");
                System.out.println("设置请求对象字符编码格式为utf8");
            }
        }
        chain.doFilter(req,resp);
        System.out.println("Filter 0 - encoding ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
