package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.UserShop;

public interface UserShopMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserShop record);

    int insertSelective(UserShop record);

    UserShop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserShop record);

    int updateByPrimaryKey(UserShop record);
}