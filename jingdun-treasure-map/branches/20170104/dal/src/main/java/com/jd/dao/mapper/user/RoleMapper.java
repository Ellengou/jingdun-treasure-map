package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Role;

public interface RoleMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Long insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}