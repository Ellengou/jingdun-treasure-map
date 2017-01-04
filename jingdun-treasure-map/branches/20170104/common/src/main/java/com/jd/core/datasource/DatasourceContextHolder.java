package com.jd.core.datasource;

public class DatasourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setCustomerType(String customerType) {
        clearCustomerType();
        contextHolder.set(customerType);
    }

    public static String getCustomerType() {
        return contextHolder.get();
    }

    public static void clearCustomerType() {
        if(contextHolder.get() != null) {
            contextHolder.remove();
        }
    }
}