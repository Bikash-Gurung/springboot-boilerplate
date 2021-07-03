package com.example.boilerplate.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {
    public static String getReferenceToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader.replace("Bearer", "").trim();
    }
}
