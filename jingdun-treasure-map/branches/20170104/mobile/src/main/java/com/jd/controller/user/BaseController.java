package com.jd.controller.user;

import com.jd.account.Account;
import com.jd.core.ensure.Ensure;
import com.jd.entity.user.Role;
import com.jd.entity.user.User;
import com.jd.utils.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public abstract class BaseController {

    protected Log logger = LogFactory.getLog(getClass());


    public void storeSession(String key, Object value) {
        SecurityUtils.getSubject().getSession().setAttribute(key, value);
    }

    public void setMenu(Object value) {
        if (null != value) {
            SecurityUtils.getSubject().getSession().setAttribute("menu", value);
        }
    }

    public String getMenu() {
        Object menu = SecurityUtils.getSubject().getSession().getAttribute("menu");
        return menu == null ? "" : menu.toString();
    }


    public Account getAccount() {
        Object user = SecurityUtils.getSubject().getSession().getAttribute(Account.SESSION_KEY);
        Ensure.that(user).isNotNull("1014");
        Account account = (Account) user;
        return account;
    }

    /**
     * 检查单一角色
     * Check role.
     */
    protected boolean checkRole(Role role) {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.hasRole(role.toString());
    }


    /**
     * 检查当前用户是否具有任一角色
     *
     * @param roleNameArray 权限名称数组
     * @return
     */
    protected boolean checkRoleAny(Role... roleNameArray) {
        Subject subject = SecurityUtils.getSubject();
        for (Role role : roleNameArray) {
            if (subject.hasRole(role.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查当前用户是否具有所有角色
     *
     * @param roleNameArray 权限名称数组
     * @return
     */
    protected boolean checkRoleAll(Role... roleNameArray) {
        Subject subject = SecurityUtils.getSubject();
        if (ArrayUtils.isEmpty(roleNameArray)) {
            return false;
        }
        for (Role role : roleNameArray) {
            if (!subject.hasRole(role.toString())) {
                return false;
            }
        }
        return true;
    }

    public Long getLoginUserId() {
        Object user = SecurityUtils.getSubject().getSession().getAttribute(Account.SESSION_KEY);
        if (user == null)
            return null;
        else
            return ((User) user).getId();
    }

}
