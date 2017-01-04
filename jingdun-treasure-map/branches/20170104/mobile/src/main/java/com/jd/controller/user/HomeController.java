package com.jd.controller.user;

import com.jd.core.ensure.Ensure;
import com.jd.core.exceptions.XBusinessException;
import com.jd.entity.user.Account;
import com.jd.entity.user.User;
import com.jd.exception.AccountDeleteException;
import com.jd.exception.AccountFrozenException;
import com.jd.exception.CodeErrorException;
import com.jd.face.JsonResult;
import com.jd.face.Result;
import com.jd.request.UserRequest;
import com.jd.request.CommonRequest;
import com.jd.response.UserResponse;
import com.jd.service.account.UserService;
import com.jd.shiro.CacheSubject;
import com.jd.utils.*;
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
    UserService userService;

    @Autowired
    Mapper mapper;

    @Value("${domain}")
    private String domain;

    @RequestMapping(value = {"", "/", "/wap", "/index"}, method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("login");
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/wap/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model, String from) {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/wap/password/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult updatePassword(Long id, String password) {
        Account account = getAccount();
        Ensure.that(String.valueOf(id)).isEqualTo(String.valueOf(account.getId()), "1015");
        Ensure.that(userService.updatePassword(id, password)).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 登录提交验证
     *
     * @throws
     */
    @RequestMapping(value = "/wap/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@RequestBody CommonRequest<UserRequest> request) {
        /**
         * 修改验证码（同一username10分钟内访问出错3次弹出验证码）
         */
        UserRequest userForm = request.getParam(UserRequest.class);
        Object count = SecurityUtils.getSubject().getSession().getAttribute("errCount");
        int errCount = 0;
        if (count != null)
            errCount = Integer.valueOf(count.toString());
        Ensure.that(errCount).isLt(3, "1004");
        Map<String, String> errMap = new HashMap();
        try {
            Ensure.that(userForm).isNotNull("1003");
            String image = userForm.getImageCode();
            Ensure.that(image).isNotNull("1003");
            Object object = SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.VALIDATE_CODE_NAME);
            String code = String.valueOf(object);
            if (object != null)
                Ensure.that(StringUtil.upperCase(image)).isEqualTo(StringUtil.upperCase(code), "1002");
            boolean userLoginSecurity = userLoginSecurity(userForm, errMap);
            Ensure.that(userLoginSecurity).isTrue("1008");
            CacheSubject.clearCache();
            //认证通过
            return new JsonResult(onAuthenticationSuccess(userForm.getUserName(), userForm.getPassWord()));
        } catch (XBusinessException e) {
            return new JsonResult(e.getXCode(), e.getMessageWithoutCode(), new Result());
        } catch (Exception e) {
            return new JsonResult("-1", errMap.isEmpty() ? "验证失败" : errMap.get("errMsg"), new Result());
        }
    }

    private UserResponse onAuthenticationSuccess(String username, String password) {
        User user = userService.findUserByNameAndPassword(username, password);
        Ensure.that(user).isNotNull("20002");
        storeSession(com.jd.account.Account.SESSION_KEY,user);
        UserResponse response = mapper.map(user, UserResponse.class);
        response.setSessionId(SecurityUtils.getSubject().getSession().getId().toString());
        return response;
    }

    /**
     * 登录验证
     *
     * @param userForm 包含用户名和登录密码(密码已加密)
     * @return
     */
    private boolean userLoginSecurity(UserRequest userForm, Map<String, String> model) {
        UsernamePasswordToken token;
        Subject subject = SecurityUtils.getSubject();
        try {
            token = new UsernamePasswordToken(userForm.getUserName(), userForm.getPassWord());
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
    @RequestMapping(value = "/wap/logout", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult mLogout(HttpServletResponse response, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return new JsonResult();
    }

    @RequestMapping(value = "/wap/image/code", method = RequestMethod.GET)
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

}

