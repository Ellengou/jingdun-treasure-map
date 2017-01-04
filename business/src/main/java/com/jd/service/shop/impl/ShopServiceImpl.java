package com.jd.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.ensure.Ensure;
import com.jd.dao.mapper.user.*;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.RolePermissionDto;
import com.jd.dtos.ShopDto;
import com.jd.dtos.TagDto;
import com.jd.entity.user.Shop;
import com.jd.entity.user.ShopTag;
import com.jd.entity.user.Tag;
import com.jd.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ellen on 2016/12/26.
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    TagMapperExt tagMapperExt;

    @Autowired
    ShopMapperExt shopMapperExt;

    @Autowired
    CurioCityMapperExt curioCityMapperExt;

    @Autowired
    ShopTagMapperExt shopTagMapper;

    @Autowired
    RolePermissionMapperExt rolePermissionMapperExt;

    @Autowired
    EvaluationMapperExt evaluationMapperExt;

    @Override
    public List<Tag> queryTagList(Pager pager, TagDto tag) {
        return tagMapperExt.queryTagList(tag);
    }

    @Override
    public Boolean delTagById(Long id) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setDeleted(Boolean.TRUE);
        return tagMapperExt.updateByPrimaryKeySelective(tag) > 0;
    }

    @Override
    public Boolean saveTag(Tag tag) {
        return tagMapperExt.insertSelective(tag) > 0;
    }

    @Override
    public Boolean updateTagById(Tag tag) {
        return tagMapperExt.updateByPrimaryKeySelective(tag) > 0;
    }

    @Override
    public PageInfo<ShopDto> queryShopList(Pager pager, ShopDto param) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<ShopDto> res = shopMapperExt.queryShopList(param);
        return new PageInfo<>(res);
    }

    @Override
    public List<ShopDto> queryList(ShopDto param) {
        return shopMapperExt.queryShopList(param);
    }

    @Override
    public Shop updateShop(Shop shop) {
        shopMapperExt.updateByPrimaryKeySelective(shop);
        return shopMapperExt.selectByPrimaryKey(shop.getId());
    }

    @Override
    public Shop saveShop(Shop shop) {
        Long id = shopMapperExt.insertSelective(shop);
        return shopMapperExt.selectByPrimaryKey(id);
    }

    @Override
    public Shop findShopById(Long id) {
        return shopMapperExt.selectByPrimaryKey(id);
    }

    @Override
    public ShopDto findShopInfo(Long id) {
        return shopMapperExt.findShopInfo(id);
    }

    @Override
    public Boolean saveShopTags(Long id, List<Long> tagIds) {
        for (Long tagId : tagIds) {
            ShopTag tag = new ShopTag();
            tag.setTagId(tagId);
            tag.setShopId(id);
            Ensure.that(shopTagMapper.insertSelective(tag)).isGt(0, "30004");
        }
        return true;
    }

    @Override
    public List<Tag> findTagListByShopId(Long id) {
        return tagMapperExt.findTagListByShopId(id);
    }

    @Override
    public Boolean delShopTagsById(Long id, List<Long> tagIds) {
        return shopTagMapper.delShopTags(id, tagIds) > 0;
    }

    @Override
    public List<RolePermissionDto> findResourceList(String ids,Long roleId) {
        return rolePermissionMapperExt.findResourceList(ids != null ? Arrays.asList(ids.split(",")) : null,roleId);
    }

    @Override
    public List<RolePermissionDto> findMenusList(String id, String level,Long roleId) {
        return rolePermissionMapperExt.findMenusList(id != null ? Arrays.asList(id.split(",")) : null, level != null ? Arrays.asList(level.split(",")) : null,roleId);
    }

    @Override
    public PageInfo<EvaluationDto> queryEvaluationListByShopId(Pager pager, EvaluationDto evaluationDto) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<EvaluationDto> list = evaluationMapperExt.queryEvaluationListByShopId(evaluationDto);
        return new PageInfo<>(list);
    }

    @Override
    public List<Tag> queryUserTagList(TagDto dto, Long loginUserId) {
        return tagMapperExt.queryUserTagList(dto, loginUserId);
    }

}
