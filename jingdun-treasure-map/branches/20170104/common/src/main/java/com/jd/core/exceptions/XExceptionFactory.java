package com.jd.core.exceptions;

import com.jd.core.context.ApplicationContextHelper;

/**
 * Created by Ellen on 2016/12/4.
 */
public class XExceptionFactory {

    private static ExceptionDefinitions exceptionDefinitions;

    public static XBusinessException create(String errorCode, String... args) {
        String exceptionPattern = getExceptionDefinitions().getExceptionMessage(errorCode);

        if (args.length > 0) {
            String errorMsg = String.format(exceptionPattern, args);
            return new XBusinessException(errorCode, errorMsg);
        }
        return new XBusinessException(errorCode, exceptionPattern);
    }

    private static ExceptionDefinitions getExceptionDefinitions() {
        if (exceptionDefinitions == null) {
            if (ApplicationContextHelper.getContext() == null)
                exceptionDefinitions = new ExceptionDefinitions();
            else
                exceptionDefinitions = ApplicationContextHelper.getContext().getBean(ExceptionDefinitions.class);
        }
        return exceptionDefinitions;
    }
}
