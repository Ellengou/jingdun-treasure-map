package com.jd.shiro;


import com.jd.core.ensure.Ensure;
import com.jd.entity.user.Account;
import com.jd.exception.AccountDeleteException;
import com.jd.service.account.AccountService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;


public class MyShiroRealm extends JdbcRealm {

    Logger logger = Logger.getLogger(MyShiroRealm.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    AccountService accountService;

    /**
     * 认证回调函数,登录时调用.用来检验登录的手机号和密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
;       String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        Account account = accountService.findAccountByUserName(username);
        Ensure.that(account).isNotNull("1016");
        Ensure.that(account.getPassword()).isEqualTo(password,"1001");
        Ensure.that(account.getDeleted()).isFalse("1007");
        Ensure.that(account.getEnable()).isTrue("1006");
        logger.debug(username + ":" + " 认证完成!");
        return new SimpleAuthenticationInfo(username, token.getPassword(), getName());
    }

}