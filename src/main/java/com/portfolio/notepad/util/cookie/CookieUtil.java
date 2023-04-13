package com.portfolio.notepad.util.cookie;

import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void cookieCreate(CookieInfo info, HttpServletResponse response){
        String saveCookie = info.getName() + "=" + info.getValue() + ";domain=" + info.getDomain() + ";" + "max-age=" + 100000 + ";path=/;SameSite=None;secure;";
        response.addHeader("Set-Cookie", saveCookie);
    }

    public static void cookieDelete(CookieInfo info, HttpServletResponse response){
        String saveCookie = info.getName() + "=" + null + ";domain=" + info.getDomain() + ";" + "max-age=" + 0 + ";path=/;SameSite=None;secure;";
        response.addHeader("Set-Cookie", saveCookie);
    }


}

