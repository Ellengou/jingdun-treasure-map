package com.jd.webkits.converters;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.StringFieldDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ellen
 * @since 2015-07-20 11:26
 */
public class XStringDeserializer extends StringFieldDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(XStringDeserializer.class);

    //public static final XStringDeserializer INSTANCE = new XStringDeserializer();

    public XStringDeserializer(ParserConfig config, Class<?> clazz, FieldInfo fieldInfo) {
        super(config, clazz, fieldInfo);
    }

    /*@Override
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        T objValue = super.deserialze(parser, clazz, fieldName);
        if (objValue == null) {
            return null;
        }
        String value = (String) objValue;
        logger.info("value1:"+value);
        //xss过滤
        value = StringEscapeUtils.escapeHtml4(value);
        logger.info("value2:"+value);
        return null;
    }
*/

    public static void main(String[] args) {
        String name = "张三<a>www.baidu.com</a>";
        name = StringEscapeUtils.escapeHtml4(name);
        System.out.println("name:"+name);
    }
}
