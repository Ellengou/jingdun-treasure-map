package com.jd.controller;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dtos.*;
import com.jd.entity.user.Account;
import com.jd.entity.user.Permission;
import com.jd.entity.user.Role;
import com.jd.entity.user.Tag;
import com.jd.face.JsonResult;
import com.jd.request.*;
import com.jd.response.*;
import com.jd.service.account.AccountService;
import com.jd.service.shop.PictureService;
import com.jd.service.shop.ShopService;
import com.jd.utils.CollectionUtils;
import com.jd.utils.DozerUtils;
import com.jd.utils.MD5Utils;
import com.jd.utils.StringUtil;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Value("${defeaut_pass}")
    String DEFEAUT_PASS;


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
        List<Role> list = accountService.selectAllRoleList();
        List<RoleListResponse> responses = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list))
            responses = DozerUtils.maps(list, RoleListResponse.class);
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
        List<Long>  resourceIds = roleRequest.getResourceIds();
        List<Long>  menuId = roleRequest.getMeunIds();
        Ensure.that(roleRequest).isNotNull("10000");
        List<Long> list = new ArrayList<>();
        if (com.jd.core.utils.CollectionUtils.isNotEmpty(resourceIds))
            list = new ArrayList<>(resourceIds);
        if (com.jd.core.utils.CollectionUtils.isNotEmpty(list))
            list.addAll(menuId);
        else
            list = new ArrayList<>(menuId);
        RoleDto role = mapper.map(roleRequest, RoleDto.class);
        role.setPermissionIds(list);
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
        RolesResponse response =  mapper.map(role, RolesResponse.class);
        List<RoleMenuRoleResourceResponse>  roleResourceResponses = getRoleResourceList(role.getId());
        List<Long> meuns = new ArrayList<>();
        List<Long> res = new ArrayList<>();
        if (com.jd.core.utils.CollectionUtils.isNotEmpty(roleResourceResponses)) {
            for (RoleMenuRoleResourceResponse resourceResponse : roleResourceResponses) {
                meuns.add(resourceResponse.getId());
                if (com.jd.core.utils.CollectionUtils.isNotEmpty(resourceResponse.getResource()))
                    for (ResourceResponse resDto : resourceResponse.getResource()){
                        res.add(resDto.getId());
                    }
            }
        }
        response.setResourceIds(res);
        response.setMeunIds(meuns);
        return new JsonResult(response);
    }

    /**
     * 角色删除 （标识删除）
     *
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
        AccountDto account = request.getParam(AccountListRequest.class) != null ? (mapper.map(request.getParam(AccountListRequest.class), AccountDto.class)) : null;
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
    public JsonResult saveAndUpdateAccount(@RequestBody CommonRequest<AccountSaveRequest> request) {
        AccountSaveRequest accountSaveRequest = request.getParam(AccountSaveRequest.class);
        Ensure.that(accountSaveRequest).isNotNull("10000");
        AccountDto account = mapper.map(accountSaveRequest,AccountDto.class);
        if (accountSaveRequest.getId() != null)
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
        AccountDto res = new AccountDto();
        String ids = null;
        res.setId(accountId);
        res.setDeleted(Boolean.TRUE);
        List<Role> roles = accountService.findRoleByAccountId(account.getId());
        if (com.jd.core.utils.CollectionUtils.isNotEmpty(roles))
            roles.forEach(role -> StringUtil.join(ids,role.getId()) );
        res.setRoleIds(ids);
        Ensure.that(accountService.updateAccount(res));
        return new JsonResult();
    }


    /**
     * 禁用 启用账户
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/{id}/disable/{enable}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult disableAccount(@PathVariable("id") Long accountId,@PathVariable("enable") Boolean enable) {
        Account account = accountService.findAccountById(accountId);
        Ensure.that(account).isNotNull("10000");
        AccountDto res = new AccountDto();
        res.setId(accountId);
        res.setEnable(enable);
        if(enable)
            Ensure.that(accountService.updateAccount(res)).isNotNull("1017");
        else
            Ensure.that(accountService.updateAccount(res)).isNotNull("1018");
        return new JsonResult();
    }

    /**
     * 重置密码
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/{id}/pwd", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult resetPassword(@PathVariable("id") Long accountId) {
        Account account = accountService.findAccountById(accountId);
        Ensure.that(account).isNotNull("1021");
        Ensure.that(accountService.updatePassword(accountId,MD5Utils.MD5(DEFEAUT_PASS))).isTrue("1020");
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
        AccountDetailResponse detailResponse = mapper.map(account,AccountDetailResponse.class);
        List<Role> roles = accountService.findRoleByAccountId(account.getId());
        if (com.jd.core.utils.CollectionUtils.isNotEmpty(roles))
            roles.forEach(role -> detailResponse.setRoleIds(String.valueOf(role.getId())));
        return new JsonResult(detailResponse);
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
        PageInfo<Tag> pageInfo = shopService.queryTagList(null, dto);
        return new JsonResult(pager, DozerUtils.maps(pageInfo.getList(), TagResponse.class));
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
        List<Tag> list = shopService.queryTagList( dto);
        if (CollectionUtils.isNotEmpty(list))
            return new JsonResult(DozerUtils.maps(list, TagResponse.class));
        else
            return new JsonResult();
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
        Ensure.that(tagRequest).isNotNull("10000");
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
        List<RoleMenuRoleResourceResponse>  roleResourceResponses = getRoleResourceList(null);
        return new JsonResult(DozerUtils.maps(roleResourceResponses,RoleMenuRoleResourceResponse.class));
    }

    /**
     *
     * @param roleId
     * @return
     */
    public  List<RoleMenuRoleResourceResponse>  getRoleResourceList(Long roleId){
        List<RolePermissionDto> reslist = shopService.findMenusList(roleId);
        List<RoleMenuRoleResourceResponse> roleResourceResponses = new ArrayList<>();
        List<ResourceResponse> resource = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(reslist))
            for (RolePermissionDto dto : reslist){
                RoleMenuRoleResourceResponse resourceResponse = new RoleMenuRoleResourceResponse();
                resourceResponse.setId(dto.getId());
                resourceResponse.setName(dto.getName());
                String bus =  dto.getButtonIds();
                if (StringUtil.trimToNull(bus)!=null){
                   String[] bts =  bus.split(",");
                   String[] btn =  dto.getButtonNames().split(",");
                   for (int i = 0;i<bts.length;i++){
                       ResourceResponse response = new ResourceResponse();
                       response.setName(btn[i]);
                       response.setId(Long.valueOf(bts[i]));
                       resource.add(response);
                   }
                }
                resourceResponse.setResource(resource);
                roleResourceResponses.add(resourceResponse);
            }
            return roleResourceResponses;
    }
}
