package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.CityArea;

public interface CityAreaMapper extends BaseDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CityArea record);

    int insertSelective(CityArea record);

    CityArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CityArea record);

    int updateByPrimaryKey(CityArea record);
}