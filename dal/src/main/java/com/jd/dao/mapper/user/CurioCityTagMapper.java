package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.CurioCityTag;

public interface CurioCityTagMapper extends BaseDao {
    int insert(CurioCityTag record);

    int insertSelective(CurioCityTag record);
}