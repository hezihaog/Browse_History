package util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 请求参数工具类
 */
public class ParamsUtil {
    /**
     * 解决get请求过来时的编码问题
     *
     * @param request   请求对象
     * @param paramsKey 请求参数的Key
     * @return 正常使用utf-8解码的参数值
     */
    public static String getParameter(HttpServletRequest request, String paramsKey) {
        String paramsValue;
        try {
            paramsValue = request.getParameter(paramsKey);
            paramsValue = URLEncoder.encode(paramsValue, "ISO-8859-1");
            paramsValue = URLDecoder.decode(paramsValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return paramsValue;
    }
}