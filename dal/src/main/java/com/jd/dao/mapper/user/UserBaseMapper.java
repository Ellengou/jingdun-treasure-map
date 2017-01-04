package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.UserBase;

public interface UserBaseMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserBase record);

    int insertSelective(UserBase record);

    UserBase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBase record);

    int updateByPrimaryKey(UserBase record);
}