package com.jd.dao.mapper.user;

import com.jd.dtos.TagDto;
import com.jd.entity.user.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapperExt extends TagMapper {

    List<Tag> queryTagList(@Param("param") TagDto tag);

    List<Tag> findTagListByShopId(Long id);

    List<Tag> queryUserTagList(TagDto dto, Long loginUserId);
}