package com.jd.service.item.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.core.utils.CollectionUtils;
import com.jd.dao.mapper.user.EvaluationMapperExt;
import com.jd.dao.mapper.user.FavoritesMapperExt;
import com.jd.dao.mapper.user.ItemMapperExt;
import com.jd.dao.mapper.user.ItemTagMapperExt;
import com.jd.dtos.EvaluationDto;
import com.jd.dtos.ItemDto;
import com.jd.dtos.ItemTagDto;
import com.jd.entity.user.Evaluation;
import com.jd.entity.user.Favorites;
import com.jd.entity.user.Item;
import com.jd.entity.user.ItemTag;
import com.jd.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/27.
 * @since 1.0.
 * com.jd.service.item.impl .by jingdun.tech.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapperExt itemMapperExt;

    @Autowired
    EvaluationMapperExt evaluationMapperExt;

    @Autowired
    ItemTagMapperExt itemTagMapperExt;

    @Autowired
    FavoritesMapperExt favoritesMapperExt;

    @Override
    public Item findById(Long id) {
        return itemMapperExt.selectByPrimaryKey(id);
    }

    @Override
    public ItemDto findDetailById(Long id) {
        return itemMapperExt.findDetailById(id);
    }

    @Override
    public Item findByName(String name) {
        return itemMapperExt.findByName(name);
    }

    @Override
    public Item findByCode(String code) {
        return itemMapperExt.findByCode(code);
    }

    @Override
    public Item saveItem(Item item) {
        int id = itemMapperExt.insertSelective(item);
        if (id > 0)
            return itemMapperExt.selectByPrimaryKey(Long.valueOf(id));
        else
            return null;
    }

    @Override
    public PageInfo<ItemDto> queryItemList(Pager pager, ItemDto query) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<ItemDto> dtos = itemMapperExt.queryItemList(query);
        return new PageInfo<>(dtos);
    }

    @Override
    public Boolean delItem(Long... ids) {
        List<Long> idList = Arrays.asList(ids);
        if (CollectionUtils.isNotEmpty(idList))
            return itemMapperExt.delItem(idList) > 0;
        return Boolean.FALSE;
    }

    @Override
    public Boolean marketAble(Boolean market, Long... ids) {
        List<Long> idList = Arrays.asList(ids);
        if (CollectionUtils.isNotEmpty(idList))
            return itemMapperExt.marketAble(market, idList) > 0;
        return Boolean.FALSE;
    }

    @Override
    public Item updateItem(Item item) {
        if (itemMapperExt.updateByPrimaryKeySelective(item) > 0)
            return itemMapperExt.selectByPrimaryKey(item.getId());
        else
            return null;
    }

    @Override
    public PageInfo<EvaluationDto> queryEvaluationListByItemIdOrUserId(Pager pager, EvaluationDto evaluationDto) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<EvaluationDto> list = evaluationMapperExt.queryEvaluationListByItemIdOrUserId(evaluationDto);
        return new PageInfo<>(list);
    }

    @Override
    public Evaluation saveEvaluation(Evaluation evaluation) {
        Long id = evaluationMapperExt.insertSelective(evaluation);
        if (id != null && id.intValue() > 0)
            return evaluationMapperExt.selectByPrimaryKey(id);
        return null;
    }

    @Override
    public List<ItemTag> queryTagList(ItemTagDto dto) {
        return itemTagMapperExt.queryTagList(dto);
    }

    @Override
    public Favorites saveFavorites(Favorites favorites) {
        int ok = favoritesMapperExt.insertSelective(favorites);
        return ok > 0 ? favorites : null;
    }

}
