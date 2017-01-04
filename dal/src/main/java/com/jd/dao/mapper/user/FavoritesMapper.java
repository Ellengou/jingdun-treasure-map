package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Favorites;

public interface FavoritesMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);
}