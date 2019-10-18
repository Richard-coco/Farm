package cn.farm.Util;


import cn.farm.Entity.User;

import javax.servlet.http.HttpSession;

public class UserUtil {

    //判断登录用户与传入用户是否一致 返回true 否则false
    public static boolean isSame(HttpSession session, int userID){
        User user = (User) session.getAttribute("user");
        if(user != null && user.getUserID() == userID){
            return true;
        }else{
            return false;
        }
    }

    //如果未登录返回-1
    public static int getUserID(HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user == null){
            return -1;
        }
        return user.getUserID();
    }

}
