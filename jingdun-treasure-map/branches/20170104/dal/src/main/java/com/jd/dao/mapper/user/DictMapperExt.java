package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Dict;

public interface DictMapperExt extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}