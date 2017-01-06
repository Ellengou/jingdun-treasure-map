package com.jd.dao.mapper.user;

import com.jd.dtos.ItemDto;
import com.jd.dtos.ShopDto;
import com.jd.entity.user.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapperExt extends ItemMapper {

    Item findByName(@Param("name") String name);

    Item findByCode(@Param("code") String code);

    List<ItemDto> queryItemList(@Param("param") ItemDto query);

    int delItem(@Param("idList") List<Long> idList);

    int marketAble(@Param("market") Boolean market, @Param("idList") List<Long> idList);

    ItemDto findDetailById(@Param("id") Long id);
}