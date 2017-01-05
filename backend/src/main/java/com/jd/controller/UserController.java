package com.jd.controller;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.UserDto;
import com.jd.entity.user.User;
import com.jd.face.JsonResult;
import com.jd.request.CommonRequest;
import com.jd.request.UserListRequest;
import com.jd.response.UserListResponse;
import com.jd.service.account.UserService;
import com.jd.utils.DozerUtils;
import com.jd.utils.MD5Utils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by renchao on 2017/1/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    UserService userService;

    @Value("${defeaut_pass}")
    String DEFEAUT_PASS;

    /**
     * 用户列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findUserPage(@RequestBody CommonRequest<UserListRequest> request) {
        Pager pager = request.getPager();
        UserListRequest userListRequest = request.getParam(UserListRequest.class);
        UserDto userDto = null;
        if (userListRequest != null)
            userDto = mapper.map(userListRequest, UserDto.class);
        PageInfo<UserDto> info = userService.queryUserListPage(pager, userDto);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), info.getTotal());
        return new JsonResult(pager, DozerUtils.maps(info.getList(), UserListResponse.class));
    }


    /**
     * 禁用 启用账户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}/enable/{enable}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult disableUserAccount(@PathVariable("id") Long userId, @PathVariable("enable") Boolean enable) {
        User user = userService.findUserById(userId);
        Ensure.that(user).isNotNull("USER_10001");
        UserDto res = new UserDto();
        res.setId(userId);
        res.setLocked(enable);
        res.setLockTime(new Date());
        if(enable)
            Ensure.that(userService.updateEnableUser(res)).isTrue("USER_1017");
        else
            Ensure.that(userService.updateEnableUser(res)).isTrue("USER_1018");
        return new JsonResult();
    }

    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteUserAccount(@PathVariable("id") Long userId) {
        User user = userService.findUserById(userId);
        Ensure.that(user).isNotNull("USER_10001");
        UserDto res = new UserDto();
        res.setId(userId);
        res.setDeleted(Boolean.TRUE);
        Ensure.that(userService.updateUserInfo(res)).isTrue("USER_1021");
        return new JsonResult();
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}/pwd", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult resetPassword(@PathVariable("id") Long userId) {
        User user = userService.findUserById(userId);
        Ensure.that(user).isNotNull("USER_10001");
        Ensure.that(userService.updatePassword(userId, MD5Utils.MD5(DEFEAUT_PASS))).isTrue("USER_1020");
        return new JsonResult();
    }

}
