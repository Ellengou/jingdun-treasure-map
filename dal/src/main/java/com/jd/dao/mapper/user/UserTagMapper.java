package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.UserTag;

public interface UserTagMapper extends BaseDao {
    int insert(UserTag record);

    int insertSelective(UserTag record);
}