package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.ItemTag;

public interface ItemTagMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ItemTag record);

    int insertSelective(ItemTag record);

    ItemTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemTag record);

    int updateByPrimaryKey(ItemTag record);
}