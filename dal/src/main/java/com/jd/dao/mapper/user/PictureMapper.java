package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Picture;

public interface PictureMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);
}