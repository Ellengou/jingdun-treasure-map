package com.jd.dao.mapper.user;

import com.jd.entity.user.AccountRole;

import java.util.List;

public interface AccountRoleMapperExt extends AccountRoleMapper {

    List<AccountRole> selectByAccountId(Long id);

    int insertObjects(List<Long> accountIds, List<Long> roleIds);

    void deleteAccountRoleByAccountIds(List<Long> accountIds);
}