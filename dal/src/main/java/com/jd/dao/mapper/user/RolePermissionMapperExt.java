package com.jd.dao.mapper.user;

import com.jd.dtos.RolePermissionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapperExt extends RolePermissionMapper {

    List<RolePermissionDto> selectRolePermissionByRoleId(Long roleId);

    int deleteByRoleId(@Param("roleId") Long id);

    List<RolePermissionDto> findMenusAndResourceList(Long roleId);

    List<RolePermissionDto> findResourceList(@Param("ids") List<String> ids,@Param("roleId") Long roleId);

    List<RolePermissionDto> findMenusList(@Param("roleId")Long roleId);
}