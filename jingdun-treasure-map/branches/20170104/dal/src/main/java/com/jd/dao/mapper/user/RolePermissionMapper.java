package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.RolePermission;

public interface RolePermissionMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
}