package com.jd.dao.mapper.user;

import com.jd.entity.user.Permission;

import java.util.List;

public interface PermissionMapperExt extends PermissionMapper {

    List<Permission> findPermissionByRoleIds(List<Long> ids);

    List<String> queryAllPermission(Permission permission);
}