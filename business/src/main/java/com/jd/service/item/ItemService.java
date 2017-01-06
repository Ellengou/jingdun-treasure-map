package com.jd.service.item;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ItemDto;
import com.jd.dtos.ItemTagDto;
import com.jd.entity.user.Evaluation;
import com.jd.entity.user.Favorites;
import com.jd.entity.user.Item;
import com.jd.entity.user.ItemTag;

import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/27.
 * @since 1.0.
 * com.jd.service.item .by jingdun.tech.
 */
public interface ItemService {

    Item findById(Long id);

    ItemDto findDetailById(Long id);

    Item findByName(String name);

    Item findByCode(String code);

    Item saveItem(Item item);

    PageInfo<ItemDto> queryItemList(Pager pager, ItemDto query);

    Boolean delItem(Long... ids);

    Boolean marketAble(Boolean market, Long... ids);

    Item updateItem(Item item);

    PageInfo<EvaluationDto> queryEvaluationListByItemIdOrUserId(Pager pager, EvaluationDto evaluationDto);

    Evaluation saveEvaluation(Evaluation evaluation);

    /**
     * 商品标签查询
     * @param dto
     * @return
     */
    List<ItemTag> queryTagList(ItemTagDto dto);

    Favorites saveFavorites(Favorites favorites);
}
