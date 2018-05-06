package servlet;

import base.BaseHttpServlet;
import constant.ProjectConstant;
import dao.ProductDao;
import entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 商品列表
 */
public class ProductListServlet extends BaseHttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        //查询出所有的商品
        ProductDao productDao = ProductDao.getInstance();
        List<Product> productList = productDao.findAll();
        //将商品显示到浏览器中
        PrintWriter writer = response.getWriter();
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<title>商品列表</title>");
        buffer.append("</head>");
        buffer.append("<body>");
        buffer.append("<table border='1' align='center' width='600px'>");
        buffer.append("<tr>");
        buffer.append("<th>编号</th><th>商品名称</th><th>商品型号</th><th>商品价格</th>");
        buffer.append("</tr>");
        //遍历商品
        if (productList != null) {
            for (Product product : productList) {
                buffer.append("<tr>");
                buffer.append("<td>" + product.getId() + "</td>" + "<td><a href='" + request.getContextPath() + "/detail?" + ProjectConstant.Params.ID + "=" + product.getId() + "'>" + product.getProductName() + "</a></td>" + "<td>" + product.getProductType() + "</td>" + "<td>" + product.getPrice() + "</td>");
                buffer.append("</tr>");
            }
        }
        buffer.append("</table>");
        buffer.append("</body>");
        buffer.append("</html>");
        writer.write(buffer.toString());
        //显示上次浏览器过的商品
        showBroseHistory(request, response);
    }

    /**
     * 显示上次浏览器过的商品
     */
    private void showBroseHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        //显示标题后换行需要使用br标签而不是\n
        StringBuffer buffer = new StringBuffer("最近浏览过的商品：</br>");
        String idStr = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ProjectConstant.CookieKey.BROWSE_HISTORY_ID_LIST)) {
                    idStr = cookie.getValue();
                    break;
                }
            }
            if (idStr != null) {
                //拼接历史记录
                String[] ids = idStr.split(",");
                ProductDao productDao = ProductDao.getInstance();
                for (String id : ids) {
                    Product product = productDao.findById(id);
                    buffer.append(product.getId());
                    buffer.append(" ");
                    buffer.append(product.getProductName());
                    buffer.append(" ");
                    buffer.append(product.getProductType());
                    buffer.append(" ");
                    buffer.append(product.getPrice());
                    //浏览器中换行需要用br标签
                    buffer.append("</br>");
                }
            }
        }
        writer.write(buffer.toString());
    }
}
