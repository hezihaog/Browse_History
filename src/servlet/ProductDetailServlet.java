package servlet;

import base.BaseHttpServlet;
import constant.ProjectConstant;
import dao.ProductDao;
import entity.Product;
import util.ParamsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品详情
 */
public class ProductDetailServlet extends BaseHttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        //取出前面商品列表跳转过来时传递的商品编号，用它来查找商品信息
        String id = ParamsUtil.getParameter(request, ProjectConstant.Params.ID);
        Product product = ProductDao.getInstance().findById(id);
        //显示到浏览器中
        PrintWriter writer = response.getWriter();
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<title>商品详情</title>");
        buffer.append("</head>");
        buffer.append("<body>");
        buffer.append("<table border='1' align='center' width='600px'>");
        //显示商品详情
        if (product != null) {
            buffer.append("<tr><th>编号：</th><td>" + product.getId() + "</td></tr>");
            buffer.append("<tr><th>商品名称：</th><td>" + product.getProductName() + "</td></tr>");
            buffer.append("<tr><th>商品型号：</th><td>" + product.getProductType() + "</td></tr>");
            buffer.append("<tr><th>商品价格：</th><td>" + product.getPrice() + "</td></tr>");
        }
        buffer.append("</table>");
        //返回按钮
        buffer.append("<center><a href='" + request.getContextPath() + "/list'>[返回列表]</a></center>");
        buffer.append("</body>");
        buffer.append("</html>");
        writer.write(buffer.toString());

        //添加浏览记录的Cookie
        Cookie cookie = new Cookie(ProjectConstant.CookieKey.BROWSE_HISTORY_ID_LIST, buildBrowseHistoryIdList(request, id));
        //设置过期时间，单位是秒
        cookie.setMaxAge(2 * 30 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    /**
     * 创建浏览记录Id列表
     *
     * @param request          请求对象
     * @param currentProductId 当前浏览的商品的Id
     */
    private String buildBrowseHistoryIdList(HttpServletRequest request, String currentProductId) {
        Cookie[] cookies = request.getCookies();
        String preBrowseHistoryIdList = null;
        //找出上一次的浏览记录
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ProjectConstant.CookieKey.BROWSE_HISTORY_ID_LIST)) {
                    preBrowseHistoryIdList = cookie.getValue();
                }
            }
        }
        //首次点击商品列表的条目，直接返回id即可
        if (cookies == null || preBrowseHistoryIdList == null) {
            return currentProductId;
        }
        //对Id列表进行拆分，拆分成多个id的数组
        String[] ids = preBrowseHistoryIdList.split(",");
        //使用链表集合保证有序并且能直接操作头尾
        LinkedList<String> idList = new LinkedList<String>(Arrays.asList(ids));
        //设定浏览记录只保留最近的3个
        if (idList.size() < 3) {
            //如果还未满限制，如果之前已经点过了，则移除掉，再重新添加到链表头部
            if (idList.contains(currentProductId)) {
                idList.remove();
                idList.addFirst(currentProductId);
            } else {
                //之前没有点过该商品，添加到顶部
                idList.addFirst(currentProductId);
            }
        } else {
            //到了限制了，如果之前已经点过了，先移除再添加到头部
            if (idList.contains(currentProductId)) {
                idList.remove(currentProductId);
                idList.addFirst(currentProductId);
            } else {
                //没有点过，由于满了限制了，先移除最后的，再添加id到最前面
                idList.removeLast();
                idList.addFirst(currentProductId);
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < idList.size(); i++) {
            //给每个id进行分割
            buffer.append(idList.get(i));
            buffer.append(",");
        }
        String result = buffer.toString();
        //删除最后一个逗号
        result = result.substring(0, result.length() - 1);
        return result;
    }
}