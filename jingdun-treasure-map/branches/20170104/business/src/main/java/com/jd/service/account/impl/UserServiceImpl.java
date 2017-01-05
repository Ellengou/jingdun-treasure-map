package com.jd.service.account.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dao.mapper.user.UserMapperExt;
import com.jd.dtos.AccountDto;
import com.jd.dtos.UserDto;
import com.jd.entity.user.User;
import com.jd.service.account.UserService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.service.account.impl .by jingdun.tech.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapperExt userMapperExt;

    @Autowired
    DozerBeanMapper mapper;

    @Override
    public Boolean updatePassword(Long id, String password) {
        return userMapperExt.updatePassword(id,password);
    }

    @Override
    public User findUserByNameAndPassword(String username, String password) {
        return userMapperExt.findUserByNameAndPassword(username,password);
    }

    @Override
    public User findAccountByUserName(String username) {
        return userMapperExt.findUserByName(username);
    }

    @Override
    public PageInfo<UserDto> queryUserListPage(Pager pager, UserDto userDto) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<UserDto> list = userMapperExt.queryUserListPage(userDto);
        return new PageInfo<>(list);
    }

    @Override
    public User findUserById(Long userId) {
        return userMapperExt.selectByPrimaryKey(userId);
    }

    @Override
    public Boolean updateUserInfo(UserDto userDto) {
        User user = mapper.map(userDto,User.class);
        return userMapperExt.updateByPrimaryKeySelective(user)>0;
    }

    @Override
    public Boolean updateEnableUser(UserDto userDto) {
        User user = mapper.map(userDto,User.class);
        return userMapperExt.updateByPrimaryKeySelective(user)>0;
    }
}
