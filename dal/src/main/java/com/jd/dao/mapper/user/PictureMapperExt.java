package com.jd.dao.mapper.user;

import com.jd.entity.user.Picture;
import org.apache.ibatis.annotations.Param;

public interface PictureMapperExt extends PictureMapper {

    Picture selectByBusinessIdAndPicType(@Param("id") Long foreignId,@Param("type") String pictureType);
}