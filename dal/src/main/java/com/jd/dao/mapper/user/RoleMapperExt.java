package com.jd.dao.mapper.user;

import com.jd.entity.user.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapperExt extends RoleMapper {

    List<Role> findUserRoleByAccountId(@Param("accountId") Long aid);

    List<Role> selectAllRoles();
}