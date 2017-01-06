package com.jd.dao.mapper.user;

import com.jd.dtos.ItemTagDto;
import com.jd.entity.user.ItemTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemTagMapperExt extends ItemTagBaseMapper {

    List<ItemTag> queryTagList(@Param("param") ItemTagDto dto);
}