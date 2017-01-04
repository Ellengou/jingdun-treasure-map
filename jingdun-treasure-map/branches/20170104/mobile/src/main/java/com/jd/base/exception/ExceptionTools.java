package com.jd.base.exception;

public class ExceptionTools {

    public static String getExceptionName(Exception ex) {
        return ex.getClass().getSimpleName();
    }

    public static StringBuffer getExceptionDetails(Exception ex) {
        StackTraceElement[] messages = ex.getStackTrace();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < messages.length; i++) {
            String className = messages[i].getClassName();
            sb.append(className + "." + messages[i].getMethodName());
            sb.append("(");
            sb.append(messages[i].getFileName() + ":" + messages[i].getLineNumber());
            sb.append(")");
            sb.append("\n");
        }
        return sb;
    }
}
