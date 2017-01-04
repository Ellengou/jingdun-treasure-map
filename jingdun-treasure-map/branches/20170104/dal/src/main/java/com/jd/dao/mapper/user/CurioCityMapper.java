package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.CurioCity;

public interface CurioCityMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(CurioCity record);

    int insertSelective(CurioCity record);

    CurioCity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CurioCity record);

    int updateByPrimaryKey(CurioCity record);
}