package com.jd.service.account;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dtos.AccountDto;
import com.jd.dtos.UserDto;
import com.jd.entity.user.User;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.service.account .by jingdun.tech.
 */
public interface UserService {
    Boolean updatePassword(Long id, String password);

    User findUserByNameAndPassword(String username, String password);

    User findAccountByUserName(String username);

    PageInfo<UserDto> queryUserListPage(Pager pager, UserDto userDto);

    User findUserById(Long userId);

    Boolean updateUserInfo(UserDto res);

    Boolean updateEnableUser(UserDto res);
}
