package com.jd.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.math.BigDecimal;
import java.sql.Types;

public class JavaTypeResolverSelfImpl extends JavaTypeResolverDefaultImpl {

    @Override
    public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
        String answer;
        JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case Types.DECIMAL:
                    answer = "DECIMAL"; //$NON-NLS-1$
                    break;
                case Types.NUMERIC:
                    answer = "NUMERIC"; //$NON-NLS-1$
                    break;
                case Types.DATE:
                    answer = "TIMESTAMP"; //$NON-NLS-1$
                    break;
                default:
                    answer = null;
                    break;
            }
            calculateJavaType(introspectedColumn);

        } else {
            if (jdbcTypeInformation.getJdbcTypeName().equals("DATE")) {
                answer = "TIMESTAMP";
            } else {
                answer = jdbcTypeInformation.getJdbcTypeName();
            }
        }

        return answer;
    }


    public FullyQualifiedJavaType calculateJavaType(
            IntrospectedColumn introspectedColumn) {
        // TODO Auto-generated method stub
        FullyQualifiedJavaType answer;

        JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case Types.DECIMAL:
                case Types.NUMERIC:
                    if (introspectedColumn.getScale() > 0) {//如果包含小数点则转换成float
                        answer = new FullyQualifiedJavaType(Float.class.getName());
                    } else {
                        if (introspectedColumn.getLength() > 18 || forceBigDecimals) {
                            answer = new FullyQualifiedJavaType(BigDecimal.class
                                    .getName());
                        } else if (introspectedColumn.getLength() > 9) {
                            answer = new FullyQualifiedJavaType(Long.class.getName());
                        } else if (introspectedColumn.getLength() > 4) {
                            answer = new FullyQualifiedJavaType(Integer.class.getName());
                        } else {
                            answer = new FullyQualifiedJavaType(Short.class.getName());
                        }
                    }
                    break;

                default:
                    answer = null;
                    break;
            }
            if (introspectedColumn.getJdbcType() == Types.BIT) {
                answer = new FullyQualifiedJavaType(Boolean.class.getName());
            }
        } else {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }

        return answer;
    }


}
