package com.jd.dao.mapper.user;

import java.util.List;

public interface ShopTagMapperExt extends ShopTagMapper {

    int delShopTags(Long id, List<Long> tagIds);
}