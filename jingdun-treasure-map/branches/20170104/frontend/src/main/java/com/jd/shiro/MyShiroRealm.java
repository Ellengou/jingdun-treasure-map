package com.jd.shiro;


import com.jd.entity.user.Account;
import com.jd.service.account.AccountService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.jdbc.JdbcRealm;


public class MyShiroRealm extends JdbcRealm {

    Logger logger = Logger.getLogger(MyShiroRealm.class);

    AccountService accountService;

    /**
     * 认证回调函数,登录时调用.用来检验登录的手机号和密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Account account = accountService.findByUsername(username,String.valueOf(token.getPassword()));
        if (account == null)
            throw new AccountException("账号信息不存在!");
        return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), getName());
    }
}