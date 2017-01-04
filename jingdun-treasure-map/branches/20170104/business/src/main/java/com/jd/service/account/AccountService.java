package com.jd.service.account;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dtos.AccountListDto;
import com.jd.dtos.RoleDto;
import com.jd.dtos.RolePermissionDto;
import com.jd.entity.user.*;

import java.util.List;

/**
 * Created by ellengou on 2016/11/23.
 */

public interface AccountService {

    public Account findByUsername(String username, String password);

    public Account findAccountByUserName(String username);

    public Account findAccountByUserId(Long userId);

    public Account updateAccount(Account account);

    public Account saveAccount(Account account);

    public List<Role> findRoleByAccountId(Long id);

    public List<String> findResourceByRoleIds(List<Long> ids);

    public UserBase findBaseByUserId(Long id);

    public List<Role> selectAllRoleList();

    public PageInfo<Role> selectAllRoles(Pager pager);

    public PageInfo<RolePermissionDto> selectRolePermissionByRoleId(Pager pager, Long roleId);

    public Role saveRole(RoleDto role);

    public UserBase saveUserBase(UserBase userBase);

    public Boolean saveAccountRole(AccountRole accountRole);

    public Permission savePermission(Permission resource);

    public Boolean saveRolePermission(RolePermission RolePermission);

    Role findRoleById(Long roleId);

    Boolean updateRole(RoleDto role);

    Account findAccountById(Long accountId);

    PageInfo<AccountListDto> selectAccountList(Pager pager, Account account);

    Boolean updatePassword(Long id, String password);

    List<String> queryAllPermission(Permission permission);

    boolean updatePermission(Permission permission);

    Permission findPermissionById(Long id);
}
