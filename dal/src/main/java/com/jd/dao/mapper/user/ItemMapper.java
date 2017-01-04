package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Item;

public interface ItemMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}