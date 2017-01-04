package com.jd.controller;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.AccountListDto;
import com.jd.dtos.RoleDto;
import com.jd.dtos.RolePermissionDto;
import com.jd.dtos.TagDto;
import com.jd.entity.user.Account;
import com.jd.entity.user.Permission;
import com.jd.entity.user.Role;
import com.jd.entity.user.Tag;
import com.jd.face.JsonResult;
import com.jd.request.AccountListRequest;
import com.jd.request.CommonRequest;
import com.jd.request.RoleRequest;
import com.jd.request.TagRequest;
import com.jd.response.*;
import com.jd.service.account.AccountService;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ellen.
 * @date 2016/12/22.
 * @since 1.0.
 * com.jd.controller .by jingdun.tech.
 */
@Controller
@RequestMapping("/basic")
public class SystemController {

    @Autowired
    AccountService accountService;

    @Autowired
    DozerBeanMapper mapper;

    @Autowired
    PictureService pictureService;

    @Autowired
    ShopService shopService;


    /**
     * 角色管理页面列表 分页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findRoles(@RequestBody CommonRequest<Role> request) {
        Pager pager = request.getPager();
        PageInfo<Role> pageInfo = accountService.selectAllRoles(pager);
        List<RolesResponse> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pageInfo.getList()))
            responses = DozerUtils.maps(pageInfo.getList(), RolesResponse.class);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), pageInfo.getTotal());
        return new JsonResult(pager, responses);
    }

    /**
     * 角色下拉列表 不分页
     *
     * @return
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findAllRoles() {
        PageInfo<Role> pageInfo = accountService.selectAllRoles(null);
        List<RoleListResponse> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pageInfo.getList()))
            responses = DozerUtils.maps(pageInfo.getList(), RoleListResponse.class);
        return new JsonResult(responses);
    }

    /**
     * 角色新增 、修改 依据为 角色ID字段值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult asaveAndUpdateRole(@RequestBody CommonRequest<RoleRequest> request) {
        RoleRequest roleRequest = request.getParam(RoleRequest.class);
        Long[] resourceIds = roleRequest.getResourceIds();
        Long[] menuId = roleRequest.getMeunIds();
        Ensure.that(roleRequest).isNotNull("10000");
        RoleDto role = mapper.map(roleRequest, RoleDto.class);
        if (role.getId() != null)
            accountService.updateRole(role);
        else
            accountService.saveRole(role);
        return new JsonResult();
    }

    /**
     * 角色信息
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult addRole(@PathVariable("id") Long roleId) {
        Role role = accountService.findRoleById(roleId);
        Ensure.that(role).isNotNull("1013");
        return new JsonResult(mapper.map(role, RolesResponse.class));
    }

    /**
     * 角色删除 （标识删除）
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteRole(@PathVariable("id") Long roleId) {
        Role role = accountService.findRoleById(roleId);
        Ensure.that(role).isNotNull("1013");
        RoleDto param = new RoleDto();
        param.setId(roleId);
        param.setDeleted(true);
        Boolean res = accountService.updateRole(param);
        Ensure.that(res).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 账户列表  分页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findAccount(@RequestBody CommonRequest<AccountListRequest> request) {
        Pager pager = request.getPager();
        Account account = request.getParam(AccountListRequest.class) != null ? (mapper.map(request.getParam(AccountListRequest.class), Account.class)) : null;
        PageInfo<AccountListDto> pageInfo = accountService.selectAccountList(pager, account);
        List<AccountResponse> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pageInfo.getList()))
            responses = DozerUtils.maps(pageInfo.getList(), AccountListResponse.class);
        if (pager != null)
            pager = new Pager(pager.getPageNum(), pager.getPageSize(), pageInfo.getTotal());
        return new JsonResult(pager, responses);
    }

    /**
     * 账户新增 修改  依据账户ID字段
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveAndUpdateAccount(@RequestBody CommonRequest<Account> request) {
        Account account = request.getParam(Account.class);
        Ensure.that(account).isNotNull("10000");
        if (account.getId() != null)
            Ensure.that(accountService.updateAccount(account)).isNotNull("20001");
        else
            Ensure.that(accountService.saveAccount(account)).isNotNull("20001");
        return new JsonResult();
    }

    /**
     * 账户删除 标识删除
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteAccount(@PathVariable("id") Long accountId) {
        Account account = accountService.findAccountById(accountId);
        Ensure.that(account).isNotNull("10000");
        Account res = new Account();
        res.setId(accountId);
        res.setDeleted(Boolean.TRUE);
        Ensure.that(accountService.updateAccount(account));
        return new JsonResult();
    }

    /**
     * 账户详情
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findAccount(@PathVariable("id") Long accountId) {
        Account account = accountService.findAccountById(accountId);
        Ensure.that(account).isNotNull("10000");
        return new JsonResult(account);
    }


    /**
     * 权限列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/permission/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getMenuList(@RequestBody CommonRequest<Permission> request) {
        Permission permission = request.getParam(Permission.class);
        Ensure.that(permission).isNotNull("10000");
        List<String> menus = accountService.queryAllPermission(permission);
        return new JsonResult(menus);
    }

    /**
     * 权限新增 修改  依据ID字段
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/permission/save-update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveAndUpdateMenu(@RequestBody CommonRequest<Permission> request) {
        Permission permission = request.getParam(Permission.class);
        Ensure.that(permission).isNotNull("10000");
        if (permission.getId() != null)
            Ensure.that(accountService.updatePermission(permission)).isTrue("20001");
        else
            Ensure.that(accountService.savePermission(permission)).isNotNull("20001");
        return new JsonResult();
    }

    /**
     * 权限刪除 依据ID字段
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult deleteMenu(@PathVariable("id") Long id) {
        Permission permission = accountService.findPermissionById(id);
        Ensure.that(permission).isNotNull("20001");
        Permission menu1 = new Permission();
        menu1.setId(permission.getId());
        menu1.setDeleted(Boolean.TRUE);
        Ensure.that(accountService.updatePermission(menu1)).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 標簽列表 分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/page", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getTagPage(@RequestBody CommonRequest<TagRequest> request) {
        Pager pager;
        pager = request.getPager();
        TagRequest tagRequest = request.getParam(TagRequest.class);
        TagDto dto = null;
        if (tagRequest != null)
            dto = mapper.map(tagRequest, TagDto.class);
        return new JsonResult(pager, DozerUtils.maps(shopService.queryTagList(pager, dto), TagResponse.class));
    }


    /**
     * 標簽列表 不分頁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getTagList(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tagRequest = request.getParam(TagRequest.class);
        TagDto dto = null;
        if (tagRequest != null)
            dto = mapper.map(tagRequest, TagDto.class);
        return new JsonResult(DozerUtils.maps(shopService.queryTagList(null, dto), TagResponse.class));
    }

    /**
     * 新增标签 修改標簽
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/add-update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveOrUpdateTag(@RequestBody CommonRequest<TagRequest> request) {
        TagRequest tagRequest = request.getParam(TagRequest.class);
        Ensure.that(tagRequest).isNotNull("");
        Tag tag = mapper.map(tagRequest, Tag.class);
        Ensure.that(tag).isNotNull("10000");
        Boolean res;
        if (tag.getId() != null)
            res = shopService.updateTagById(tag);
        else
            res = shopService.saveTag(tag);
        Ensure.that(res).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/tag/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delTag(@PathVariable("id") Long id) {
        Ensure.that(shopService.delTagById(id)).isTrue("20001");
        return new JsonResult();
    }

    /**
     * 权限列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/role-resource/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult roleResourceList() {
        //一级菜单
        List<RolePermissionDto> oneLevelMenusList = shopService.findMenusList(null, "1");
        List<RolePermissionDto> twoAndThreeLevelMenusList;
        List<RolePermissionDto> roleResourceDtos;
        List<RoleMenuRoleResourceResponse> roleResourceResponses = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(oneLevelMenusList))
            for (RolePermissionDto dto : oneLevelMenusList) {
                String resourceQueryStr = dto.getMenuIds();
                String mids = dto.getMenuIds();
                String mnames = dto.getMenuNames();
                List<ResourceResponse> resourceResponses = new ArrayList<>();
                //2 3 级菜单
                twoAndThreeLevelMenusList = shopService.findMenusList(mids, "2,3");
                for (RolePermissionDto domain : twoAndThreeLevelMenusList) {
                    Set<String> ids;
                    ids = new HashSet<>();
                    Set<String> names = new HashSet<>();
                    CollectionUtils.addAll(ids, domain.getMenuIds().split(","));
                    CollectionUtils.addAll(names, domain.getMenuNames().split(","));
                    String[] id = new String[names.size()];
                    names.toArray(id);
                    int i = 0;
                    for (String sid : ids) {
                        ResourceResponse resourceResponse;
                        resourceResponse = new ResourceResponse();
                        resourceResponse.setId(Long.valueOf(sid));
                        resourceResponse.setName(id[i]);
                        resourceResponses.add(resourceResponse);
                        i++;
                    }
                }
                twoAndThreeLevelMenusList.forEach(rolePermissionDto -> resourceQueryStr.concat(rolePermissionDto.getMenuIds()));
                //当前菜单下所有权限集合
                roleResourceDtos = shopService.findResourceList(resourceQueryStr);
                for (RolePermissionDto permissionDto : roleResourceDtos) {
                    Set<String> ids = new HashSet<>();
                    Set<String> names = new HashSet<>();
                    CollectionUtils.addAll(ids, permissionDto.getButtonIds().split(","));
                    CollectionUtils.addAll(names, permissionDto.getButtonNames().split(","));
                    String[] id = new String[names.size()];
                    names.toArray(id);
                    int i = 0;
                    for (String sid : ids) {
                        ResourceResponse resourceResponse = new ResourceResponse();
                        resourceResponse.setId(Long.valueOf(sid));
                        resourceResponse.setName(id[i]);
                        resourceResponses.add(resourceResponse);
                        i++;
                    }

                }
                RoleMenuRoleResourceResponse roleMenuRoleResourceResponse = mapper.map(dto, RoleMenuRoleResourceResponse.class);
                roleMenuRoleResourceResponse.setResource(resourceResponses);
                roleResourceResponses.add(roleMenuRoleResourceResponse);
            }

        return new JsonResult(roleResourceResponses);
    }

}
