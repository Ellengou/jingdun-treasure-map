package com.jd.mybatis;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

import java.util.ArrayList;
import java.util.List;

public class IntrospectedTableMyBatis3ImplExt extends IntrospectedTableMyBatis3Impl {

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            boolean mergeable = false; // 新生成的XML文件究竟是追加(true)还是覆盖(false)
            if ("true".equalsIgnoreCase(context.getProperty("mergeable"))) {
                mergeable = true;
            }
            GeneratedXmlFile gxf = new GeneratedXmlFile(document, getMyBatis3XmlMapperFileName(),
                                                        getMyBatis3XmlMapperPackage(),
                                                        context.getSqlMapGeneratorConfiguration().getTargetProject(),
                                                        mergeable, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }
        }

        return answer;
    }

}
