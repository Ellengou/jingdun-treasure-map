package com.jd.service.account.impl;

import com.jd.dao.mapper.user.UserMapperExt;
import com.jd.entity.user.User;
import com.jd.service.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
