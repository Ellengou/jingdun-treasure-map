package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Shop;

public interface ShopMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Shop record);

    Long insertSelective(Shop record);

    Shop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}