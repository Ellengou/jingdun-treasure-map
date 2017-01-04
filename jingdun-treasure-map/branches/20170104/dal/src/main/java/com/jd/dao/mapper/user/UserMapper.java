package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.User;

public interface UserMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}