package com.jd.common.config;

// import java.util.Properties;
//
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.BeanInitializationException;
// import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//
// import DesUtil;

public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    // private static final String KEY = "0001000200030004";
    //
    // @Override
    // protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
    // throws BeansException {
    // try {
    // String url = props.getProperty("oracle.jdbc.url");
    // if (url != null) {
    // String urlVal = DesUtil.Decrypt(url, DesUtil.hex2byte(KEY));
    // props.setProperty("oracle.jdbc.url", urlVal);
    // }
    // String username = props.getProperty("oracle.jdbc.username");
    // if (username != null) {
    // String usernameVal = DesUtil.Decrypt(username, DesUtil.hex2byte(KEY));
    // props.setProperty("oracle.jdbc.username", usernameVal);
    // }
    // String password = props.getProperty("oracle.jdbc.password");
    // if (password != null) {
    // String passwordVal = DesUtil.Decrypt(password, DesUtil.hex2byte(KEY));
    // props.setProperty("oracle.jdbc.password", passwordVal);
    // }
    // super.processProperties(beanFactory, props);
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new BeanInitializationException(e.getMessage());
    // }
    // }

}
