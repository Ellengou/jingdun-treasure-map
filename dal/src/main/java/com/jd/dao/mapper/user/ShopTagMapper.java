package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.ShopTag;

public interface ShopTagMapper extends BaseDao {
    int insert(ShopTag record);

    int insertSelective(ShopTag record);
}