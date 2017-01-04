package com.jd.dao.mapper.user;

import com.jd.dtos.ShopDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopMapperExt extends ShopMapper {

    List<ShopDto> queryShopList(@Param("param") ShopDto param);

    ShopDto findShopInfo(@Param("id") Long id);
}