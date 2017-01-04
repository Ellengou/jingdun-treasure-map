package com.jd.core.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DatasourceContextHolder.getCustomerType();
        logger.debug("Thread:[{}]------Current datasource: [{}]",Thread.currentThread().getId(), datasource);

        DatasourceContextHolder.clearCustomerType();
        return datasource;
    }

}