package com.clever.common.util;

public class ExceptionUtils {

    public static Throwable getRootCause(Throwable th) {
        Throwable root = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(th);
        root = root == null ? th : root;
        return root;
    }

    public static String getRootCauseMessage(Throwable th) {
        return getRootCause(th).getMessage();
    }
    
    public static String getStackTrace(Throwable th) {
    	return org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(th);
    }

}
