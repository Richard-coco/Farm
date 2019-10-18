package cn.farm.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SessionUtil {

    //consumer JSESSIONID
    public static String getJSessionId(HttpServletRequest request){
        List<String> cookieList = HttpUtil.getCookieList(request);
        String jsessionID = null;
        String[] target;
        if(cookieList != null){
            for (String s : cookieList){
                target = s.split("=");
                jsessionID = target[1];
            }
        }else{
            throw new RuntimeException();
        }
        return jsessionID;
    }


}
