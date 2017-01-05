package com.jd.dao.mapper.user;

import com.jd.entity.user.AccountRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountRoleMapperExt extends AccountRoleMapper {

    List<AccountRole> selectByAccountId(@Param("accountId") Long id);

    int insertAccountRole(@Param("accountId") Long accountId,@Param("roleId") Long roleId);

    void deleteAccountRoleByAccountIds(@Param("ids") List<Long> accountIds);
}