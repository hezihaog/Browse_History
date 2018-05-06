package base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * HttpServlet基类，处理了请求、响应的编码、解码
 */
public class BaseHttpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCharsetEncoding(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCharsetEncoding(req, resp);
    }

    protected void setCharsetEncoding(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //解决post请求过来时的编码问题
        request.setCharacterEncoding("utf-8");
        //解决响应到浏览器的编码问题
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
    }
}