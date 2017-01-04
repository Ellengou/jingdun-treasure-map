package com.jd.service.shop;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.RolePermissionDto;
import com.jd.dtos.ShopDto;
import com.jd.dtos.TagDto;
import com.jd.entity.user.Shop;
import com.jd.entity.user.Tag;

import java.util.List;

/**
 * Created by ellen on 2016/12/26.
 */
public interface ShopService {

    List<Tag> queryTagList(Pager pager, TagDto tag);

    Boolean delTagById(Long id);

    Boolean saveTag(Tag tag);

    Boolean updateTagById(Tag tag);

    PageInfo<ShopDto> queryShopList(Pager pager, ShopDto param);

    List<ShopDto> queryList(ShopDto param);

    Shop updateShop(Shop shop);

    Shop saveShop(Shop shop);

    Shop findShopById(Long id);

    ShopDto findShopInfo(Long id);

    Boolean saveShopTags(Long id, List<Long> tagIds);

    List<Tag> findTagListByShopId(Long id);

    Boolean delShopTagsById(Long id, List<Long> tagIds);

    List<RolePermissionDto> findResourceList(String ids,Long roleId);

    List<RolePermissionDto> findMenusList(String id, String level,Long roleId);

    PageInfo<EvaluationDto> queryEvaluationListByShopId(Pager pager, EvaluationDto evaluationDto);

    List<Tag> queryUserTagList(TagDto dto, Long loginUserId);
}
