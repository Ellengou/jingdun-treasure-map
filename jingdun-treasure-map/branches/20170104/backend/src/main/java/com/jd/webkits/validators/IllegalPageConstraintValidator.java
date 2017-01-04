package com.jd.webkits.validators;

import com.jd.core.exceptions.XExceptionFactory;
import com.jd.webkits.context.XContext;
import com.jd.webkits.validators.annotations.IllegalPage;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by nt on 2015-06-19.
 */
public class IllegalPageConstraintValidator implements ConstraintValidator<IllegalPage, Object> {

    @Override
    public void initialize(IllegalPage illegalPage) {

    }

    @Override
    public boolean isValid(Object s, ConstraintValidatorContext constraintValidatorContext) {
        XContext context = XContext.getCurrentContext();

        String currentPage = context.getParameter("currentPage");
        String pageSize = context.getParameter("pageSize");

        validateCurrentPage(currentPage);
        validatePageSize(pageSize);

        return true;
    }

    /**
     * validate page size is illegal
     *
     * @param pageSize
     */
    private void validatePageSize(String pageSize) {
        if (StringUtils.isEmpty(pageSize)) {
            throw XExceptionFactory.create("F_WEBKITS_PAGE_1004");
        }
        if (!StringUtils.isNumeric(pageSize)) {
            throw XExceptionFactory.create("F_WEBKITS_PAGE_1005");
        }
        if (Integer.valueOf(pageSize) < 1) {
            throw XExceptionFactory.create("F_WEBKITS_PAGE_1006");
        }
    }

    /**
     * validate current is illegal
     *
     * @param currentPage
     */
    private void validateCurrentPage(String currentPage) {
        if (StringUtils.isEmpty(currentPage)) {
            throw XExceptionFactory.create("F_WEBKITS_PAGE_1001");
        }
        if (!StringUtils.isNumeric(currentPage)) {
            throw XExceptionFactory.create("F_WEBKITS_PAGE_1002");
        }
        if (Integer.valueOf(currentPage) < 1) {
            throw XExceptionFactory.create("F_WEBKITS_PAGE_1003");
        }
    }

}
