package com.example.common.util;

import javax.servlet.http.HttpServletRequest;

public class BrowserUtil {
    private BrowserUtil() {
        //default
    }

    public static boolean isIE(HttpServletRequest request) {
        return request.getHeader("USER-AGENT").toLowerCase().contains("msie")
                || request.getHeader("USER-AGENT").toLowerCase().contains("rv:11.0");
    }
}
