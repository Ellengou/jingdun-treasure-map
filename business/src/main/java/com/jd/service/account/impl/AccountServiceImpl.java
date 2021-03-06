package com.jd.service.account.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.core.utils.CollectionUtils;
import com.jd.dao.mapper.user.*;
import com.jd.dtos.AccountDto;
import com.jd.dtos.AccountListDto;
import com.jd.dtos.RoleDto;
import com.jd.dtos.RolePermissionDto;
import com.jd.entity.user.*;
import com.jd.service.account.AccountService;
import com.jd.utils.StringUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellengou on 2016/11/23.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapperExt accountMapper;

    @Autowired
    RoleMapperExt roleMapperExt;

    @Autowired
    UserBaseMapperExt userBaseMapperExt;

    @Autowired
    AccountRoleMapperExt accountRoleMapperExt;

    @Autowired
    PermissionMapperExt permissionMapperExt;

    @Autowired
    RolePermissionMapperExt rolePermissionMapperExt;

    @Autowired
    DozerBeanMapper mapper;

    @Override
    public Account findByUsername(String username, String password) {
        return accountMapper.findByUsername(username, password);
    }

    @Override
    public Account findAccountByUserName(String username) {
        return accountMapper.findBusinessByAccount(username);
    }

    @Override
    public Account findAccountByUserId(Long userId) {
        return accountMapper.findDetailByUserId(userId);
    }

    @Override
    public Account updateAccount(AccountDto account) {
        Account a = mapper.map(account,Account.class);
       int ok = accountMapper.updateByPrimaryKeySelective(a);
        List<Long> ids = new ArrayList<>();
        ids.add(a.getId());
        accountRoleMapperExt.deleteAccountRoleByAccountIds(ids);
        String role = account.getRoleIds();
        if (StringUtil.isNotBlank(role)) {
            AccountRole accountRole = new AccountRole();
            accountRole.setRoleId(Long.valueOf(role));
            accountRole.setAccountId(a.getId());
            accountRoleMapperExt.insertSelective(accountRole);
        }
        return  ok > 0 ? a : null;
    }

    @Override
    public Boolean enableAccount(AccountDto account) {
        Account a = mapper.map(account,Account.class);
        int ok = accountMapper.updateByPrimaryKeySelective(a);
        return  ok > 0 ;
    }

    @Override
    public Boolean disableAccount(AccountDto account) {
        Account a = mapper.map(account,Account.class);
        int ok = accountMapper.updateByPrimaryKeySelective(a);
        return  ok > 0 ;
    }

    @Override
    public Account saveAccount(AccountDto account) {
        Account a = mapper.map(account,Account.class);
        int ok =accountMapper.insertSelective(a);
        String role = account.getRoleIds();
        AccountRole accountRole = new AccountRole();
        accountRole.setRoleId(Long.valueOf(role));
        accountRole.setAccountId(a.getId());
        accountRoleMapperExt.insertSelective(accountRole);
        return ok > 0 ? a : null;
    }

    @Override
    public List<Role> findRoleByAccountId(Long id) {
        return roleMapperExt.findUserRoleByAccountId(id);
    }

    @Override
    public List<String>  findResourceByRoleIds(List<Long> ids) {
        List<Permission> permissions = permissionMapperExt.findPermissionByRoleIds(ids);
        if (permissions == null)
            return null;
        List<String> codes = new ArrayList<>();
        permissions.forEach(permission -> codes.add(permission.getCode()));
        return codes;
    }

    @Override
    public UserBase findBaseByUserId(Long id) {
        Ensure.that(id).isNotNull("10000");
        return userBaseMapperExt.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Role> selectAllRoles(Pager pager) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<Role> list = roleMapperExt.selectAllRoles();
        return new PageInfo<Role>(list);
    }

    public List<Role> selectAllRoleList(){
      return   roleMapperExt.selectAllRoles();
    }

    @Override
    public PageInfo<RolePermissionDto> selectRolePermissionByRoleId(Pager pager, Long roleId) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<RolePermissionDto> resourceDtos = rolePermissionMapperExt.selectRolePermissionByRoleId(roleId);
        PageInfo<RolePermissionDto> pageInfo = new PageInfo<>(resourceDtos);
        return pageInfo;
    }

    @Override
    public Role saveRole(RoleDto dto) {
        Role role = mapper.map(dto, Role.class);
        Long ok = roleMapperExt.insertSelective(role);
        List<Long> resources = dto.getPermissionIds();
        for (int i = 0; i < resources.size(); i++) {
            RolePermission roleResource = new RolePermission();
            roleResource.setPermissionId(resources.get(i));
            roleResource.setRoleId(role.getId());
            rolePermissionMapperExt.insertSelective(roleResource);
        }

        return ok > 0 ? role : null;
    }

    @Override
    public Permission savePermission(Permission resource) {
        int ok = permissionMapperExt.insertSelective(resource);
        return ok > 0 ? resource : null;
    }

    @Override
    public UserBase saveUserBase(UserBase userBase) {
        int ok = userBaseMapperExt.insertSelective(userBase);
        return ok > 0 ? userBase : null;
    }

    @Override
    public Boolean saveAccountRole(AccountRole accountRole) {
        return accountRoleMapperExt.insertSelective(accountRole) > 0;
    }

    @Override
    public Boolean saveRolePermission(RolePermission roleResource) {
        return rolePermissionMapperExt.insertSelective(roleResource) > 0;
    }

    @Override
    public Role findRoleById(Long roleId) {
        return roleMapperExt.selectByPrimaryKey(roleId);
    }

    @Override
    public Boolean updateRole(RoleDto dto) {
        Role role = mapper.map(dto, Role.class);
        List<Long> resources = dto.getPermissionIds();
        if (CollectionUtils.isNotEmpty(resources)) {
            rolePermissionMapperExt.deleteByRoleId(dto.getId());
            for (int i = 0; i < resources.size(); i++) {
                RolePermission roleResource = new RolePermission();
                roleResource.setPermissionId(resources.get(i));
                roleResource.setRoleId(dto.getId());
                rolePermissionMapperExt.insertSelective(roleResource);
            }
        }
        return roleMapperExt.updateByPrimaryKeySelective(role) > 0;
    }

    @Override
    public Account findAccountById(Long accountId) {
        return accountMapper.selectByPrimaryKey(accountId);
    }

    @Override
    public PageInfo<AccountListDto> selectAccountList(Pager pager, AccountDto account) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<AccountListDto> accountList = accountMapper.selectAccountList(account);
        PageInfo<AccountListDto> pageInfo = new PageInfo<>(accountList);
        return pageInfo;
    }


    @Override
    public Boolean updatePassword(Long id, String password) {
        Account account = new Account();
        account.setId(id);
        account.setPassword(password);
        account.setInitPassword(Boolean.FALSE);
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    @Override
    public List<String> queryAllPermission(Permission permission) {
        return permissionMapperExt.queryAllPermission(permission);
    }

    @Override
    public boolean updatePermission(Permission permission) {
        return permissionMapperExt.updateByPrimaryKeySelective(permission)>0;
    }

    @Override
    public Permission findPermissionById(Long id) {
        return permissionMapperExt.selectByPrimaryKey(id);
    }

}
