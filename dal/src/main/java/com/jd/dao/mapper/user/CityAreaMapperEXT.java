package com.jd.dao.mapper.user;

import com.jd.entity.user.CityArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Ellen on 2016/11/30.
 * make by jingdun.com. roo
 */
public interface CityAreaMapperEXT extends CityAreaMapper {

    public List<CityArea> selectByCodeAndLevel(@Param("code") String code, @Param("level") int level);

    public List<CityArea> selectByCode(@Param("code") String code);

    public List<CityArea> selectByPCode(@Param("parentId") String code);

    public List<CityArea> queryByAny(@Param("param") CityArea param);
}
