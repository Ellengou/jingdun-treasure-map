package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Tag;

public interface TagMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}