package com.jd.controller;

import com.jd.core.ensure.Ensure;
import com.jd.core.exceptions.XBusinessException;
import com.jd.entity.user.Account;
import com.jd.entity.user.Role;
import com.jd.exception.AccountDeleteException;
import com.jd.exception.AccountFrozenException;
import com.jd.exception.CodeErrorException;
import com.jd.face.JsonResult;
import com.jd.face.Result;
import com.jd.request.AccountRequest;
import com.jd.request.CommonRequest;
import com.jd.response.AccountResponse;
import com.jd.service.account.AccountService;
import com.jd.shiro.CacheSubject;
import com.jd.utils.CollectionUtils;
import com.jd.utils.StringUtil;
import com.jd.utils.StringUtils;
import com.jd.webkits.validatecode.RandomValidateCode;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by ellengou on 2016/11/18.
 */
@Controller
public class HomeController extends BaseController {

    Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    AccountService accountService;

    @Autowired
    Mapper mapper;

    @Value("${domain}")
    private String domain;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("login");
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model, String from) {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/password/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult updatePassword(Long id, String password) {
        Account account = getAccount();
        Ensure.that(String.valueOf(id)).isEqualTo(password, "1015");
        Ensure.that(accountService.updatePassword(id, password)).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 登录提交验证
     *
     * @throws
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@RequestBody CommonRequest<AccountRequest> request) {
        /**
         * 修改验证码（同一username10分钟内访问出错3次弹出验证码）
         */
        AccountRequest userForm = request.getParam(AccountRequest.class);
        Object count = SecurityUtils.getSubject().getSession().getAttribute("errCount");
        int errCount = 0;
        if (count != null)
            errCount = Integer.valueOf(count.toString());
        Ensure.that(errCount).isLt(3, "1004");
        Map<String, String> errMap = new HashMap();
        try {
            Ensure.that(userForm).isNotNull("1003");
            String image = userForm.getImageCode();
            Object object = SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.VALIDATE_CODE_NAME);
            String code = String.valueOf(object);
            if (object != null)
                Ensure.that(StringUtil.upperCase(image)).isEqualTo(StringUtil.upperCase(code), "1002");
            boolean userLoginSecurity = userLoginSecurity(userForm, errMap);
            Ensure.that(userLoginSecurity).isTrue("1008");
            CacheSubject.clearCache();
            //认证通过
            return new JsonResult(onAuthenticationSuccess(userForm.getUsername(), userForm.getPassword()));
        } catch (XBusinessException e) {
            return new JsonResult(e.getXCode(), e.getMessageWithoutCode(), new Result());
        } catch (Exception e) {
            return new JsonResult("-1", errMap.isEmpty() ? "验证失败" : errMap.get("errMsg"), new Result());
        }
    }

    private AccountResponse onAuthenticationSuccess(String username, String password) {
        com.jd.entity.user.Account account = accountService.findByUsername(username, password);
        Ensure.that(account).isNotNull("20002");
        List<Role> roles = accountService.findRoleByAccountId(account.getId());
        List<String> res = null;
        List<String> ros = new ArrayList();
        List<String> codes = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(roles)) {
//            List<Long> ids = new ArrayList();
//            roles.forEach(role -> ids.add(role.getId()));
//            roles.forEach(role -> ros.add(role.getValue()));
//            res = accountService.findResourceByRoleIds(ids);
//            codes = accountService.findResourceByRoleIds(ids);
//        }
        storeSession(com.jd.account.Account.SESSION_KEY, account);
        storeSession(com.jd.account.Account.SESSION_MENU_KEY, codes);
        storeSession(com.jd.account.Account.SESSION_RESOURCE_KEY, res);
        storeSession(com.jd.account.Account.SESSION_ROLE, CollectionUtils.isNotEmpty(roles)?roles.get(0):roles);
        storeSession(com.jd.account.Account.SESSION_USER_BASE, accountService.findBaseByUserId(account.getUserId()));
        AccountResponse response = mapper.map(account, AccountResponse.class);
        response.setCodes(codes);
        response.setResources(res);
        response.setRole(String.join(",", ros));
        return response;
    }

    /**
     * 登录验证
     *
     * @param userForm 包含用户名和登录密码(密码已加密)
     * @return
     */
    private boolean userLoginSecurity(AccountRequest userForm, Map<String, String> model) {
        UsernamePasswordToken token;
        Subject subject = SecurityUtils.getSubject();
        try {
            token = new UsernamePasswordToken(userForm.getUsername(), userForm.getPassword());
            subject.login(token);
            if (subject.isAuthenticated())
                return true;
        } catch (AccountFrozenException frozen) {
            Ensure.that(frozen.getClass()).isEqualTo(AccountFrozenException.class, "1004");
        } catch (CodeErrorException e) {
            Ensure.that(e.getClass()).isEqualTo(CodeErrorException.class, "1002");
        } catch (UnknownAccountException uae) {
            Ensure.that(uae.getClass()).isEqualTo(UnknownHostException.class, "1001");
        } catch (IncorrectCredentialsException ice) {
            Ensure.that(ice.getClass()).isEqualTo(IncorrectCredentialsException.class, "1005");
        } catch (LockedAccountException lae) {
            Ensure.that(lae.getClass()).isEqualTo(LockedAccountException.class, "1006");
        } catch (AccountDeleteException e) {
            Ensure.that(e.getClass()).isEqualTo(AccountDeleteException.class, "1007");
        } catch (AuthenticationException e) {
            Ensure.that(e.getClass()).isEqualTo(AuthenticationException.class, "1008");
        } catch (Exception e) {
            Ensure.that(e).isNotNull("1009");
        }
        return false;
    }

    /**
     * 注销
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult mLogout(HttpServletResponse response, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return new JsonResult();
    }

    @RequestMapping(value = "/image/code", method = RequestMethod.GET)
    public void getLoginCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 错误转向
     *
     * @throws
     */
    @RequestMapping(value = "/errors/{code}")
    public String login(@PathVariable("code") String code) {
        if (StringUtils.isNotEmpty(code))
            return "errors/" + code;
        return "error/404";
    }


}

