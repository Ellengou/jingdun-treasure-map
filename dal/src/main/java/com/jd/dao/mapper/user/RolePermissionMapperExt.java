package com.jd.dao.mapper.user;

import com.jd.dtos.RolePermissionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapperExt extends RolePermissionMapper {

    List<RolePermissionDto> selectRolePermissionByRoleId(Long roleId);

    boolean deleteByRoleId(Long id);

    List<RolePermissionDto> findMenusAndResourceList(Long roleId);

    List<RolePermissionDto> findResourceList(@Param("ids") List<String> ids,@Param("roleId") Long roleId);

    List<RolePermissionDto> findMenusList(@Param("ids") List<String> idList,@Param("levels") List<String> level,@Param("roleId")Long roleId);
}