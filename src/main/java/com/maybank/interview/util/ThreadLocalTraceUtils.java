package com.maybank.interview.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public final class ThreadLocalTraceUtils {

    private ThreadLocalTraceUtils() {
    }

    public static void storeThreadAttribute(String key, Object obj) {
        RequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(key, obj, RequestAttributes.SCOPE_REQUEST);
        }
    }

    public static Object getThreadAttribute(String key) {
        RequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

    private static RequestAttributes getRequestAttributes() {
        try {
            return RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            return null;
        }
    }
}