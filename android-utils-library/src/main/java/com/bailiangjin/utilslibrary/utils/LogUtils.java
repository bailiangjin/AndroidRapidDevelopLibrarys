package com.bailiangjin.utilslibrary.utils;

import android.util.Log;

/**
 * @author blj
 */
public class LogUtils {
    private static String TAG = "IM_SDK";
    private static String INFO = "-->>";
    private static String INIO = "::";
    private static int DEBUG = 0;
    public final static int LOG_DEBUG = 0;
    public final static int LOG_INFO = LOG_DEBUG + 1;
    public final static int LOG_WARN = LOG_INFO + 1;
    public final static int LOG_ERROR = LOG_WARN + 1;

    public final static void e(String message) {
        if (DEBUG <= LOG_ERROR) {
            Log.e(TAG, getFormatString(message));
        }
    }

    public final static void i(String message) {
        if (DEBUG <= LOG_INFO) {
            Log.i(TAG, getFormatString(message));
        }
    }

    public final static void d(String message) {
        if (DEBUG <= LOG_DEBUG) {
            Log.d(TAG, getFormatString(message));
        }
    }

    public final static void w(String message) {
        if (DEBUG <= LOG_WARN) {
            Log.w(TAG, getFormatString(message));
        }
    }

    private static String getFormatString(String orgMsg) {
        StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
        String threadName = Thread.currentThread().getName();
        String className = stackTrace.getClassName();
        int lineNumber = stackTrace.getLineNumber();
        String methodName = stackTrace.getMethodName();

        StringBuffer sb = new StringBuffer();
        sb.append(threadName);
        sb.append( INIO);
        sb.append(className);
        sb.append(INIO);
        sb.append(lineNumber);
        sb.append(INIO);
        sb.append(methodName);
        sb.append(INFO);
        sb.append(orgMsg);
        return sb.toString();
    }
}
