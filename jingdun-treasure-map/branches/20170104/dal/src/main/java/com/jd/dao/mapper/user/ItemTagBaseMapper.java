package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.ItemTagBase;

public interface ItemTagBaseMapper extends BaseDao {
    int insert(ItemTagBase record);

    int insertSelective(ItemTagBase record);
}