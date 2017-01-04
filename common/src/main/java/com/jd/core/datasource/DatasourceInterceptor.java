package com.jd.core.datasource;

import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class DatasourceInterceptor {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    private Map<String, String> datasourceMapping;

    private String defaultDatasource;
    private ConcurrentMap<String, String> datasources = new ConcurrentHashMap<>();

    private ConcurrentMap<String, Pattern> patternMap = new ConcurrentHashMap<>();


    public void setDatasourceMapping(Map<String, String> datasourceMapping) {
        this.datasourceMapping = datasourceMapping;
    }

    public void setDefaultDatasource(String defaultDatasource) {
        this.defaultDatasource = defaultDatasource;
    }

    public void switchDatasource(JoinPoint jp) {

        //优先按className匹配
//        String className = jp.getSignature().getDeclaringTypeName();
//        String selectedDatasource = getDatasource(className);
//
//        if(selectedDatasource != null){
//            DatasourceContextHolder.setCustomerType(selectedDatasource);
//            return;
//        }

        //其次按packageName匹配
//        String packageName = jp.getSignature().getDeclaringType().getPackage().getName();
//        selectedDatasource = getDatasource(packageName);
//        if(datasources.containsKey(packageName)){
//            DatasourceContextHolder.setCustomerType(selectedDatasource);
//            return;
//        }

        String packageName = jp.getSignature().getDeclaringType().getPackage().getName();

        if(datasources.containsKey(packageName)){
            String targetDatasource = datasources.get(packageName);
            DatasourceContextHolder.setCustomerType(targetDatasource);

            logger.debug("thread:[{}]-----packageName:[{}]------datasource:[{}]",Thread.currentThread().getId(), packageName, targetDatasource);
            return;
        }

        for (String key : datasourceMapping.keySet()) {
            Pattern pattern = getPackagePattern(key);
            if (pattern.matcher(packageName).find()) {
                String targetDatasource = datasourceMapping.get(key);
                datasources.put(packageName, targetDatasource);
                DatasourceContextHolder.setCustomerType(targetDatasource);

                logger.debug("thread:[{}]-----packageName:[{}]------datasource:[{}]", Thread.currentThread().getId(), packageName, targetDatasource);
                return;
            }
        }

        if (StringUtils.isNotBlank(defaultDatasource)) {
            datasources.put(packageName, defaultDatasource);
            DatasourceContextHolder.setCustomerType(defaultDatasource);

            logger.debug("thread:[{}]-----packageName:[{}]------datasource:[{}]",Thread.currentThread().getId(), packageName, defaultDatasource);
            return;
        }

        throw XExceptionFactory.create("F_CORE_DS_1001");
    }

    private Pattern getPackagePattern(String patternStr) {
        if (patternMap.containsKey(patternStr)) {
            return patternMap.get(patternStr);
        }

        Pattern pattern = Pattern.compile(patternStr);
        patternMap.put(patternStr, pattern);
        return pattern;
    }

    private String getDatasource(String packageOrClassName) {
        if (datasources.containsKey(packageOrClassName)) {
            return datasources.get(packageOrClassName);
        }

        for (String key : datasourceMapping.keySet()) {
            Pattern pattern = getPackagePattern(key);
            if (pattern.matcher(packageOrClassName).find()) {
                String targetDatasource = datasourceMapping.get(key);
                datasources.put(packageOrClassName, targetDatasource);
                return targetDatasource;
            }
        }
        return null;
    }

}