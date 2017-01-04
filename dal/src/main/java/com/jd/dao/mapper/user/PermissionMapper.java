package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Permission;

public interface PermissionMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}