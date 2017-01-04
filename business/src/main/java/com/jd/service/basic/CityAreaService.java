package com.jd.service.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jd.entity.user.CityArea;

import java.util.List;

/**
 * Created by Ellen on 2016/11/30.
 * make by jingdun.com. roo
 * 城市区域服务
 */
public interface CityAreaService {

    public CityArea selectById(Integer id);

    public List<CityArea> selectByCodeAndLevel(String code, int level);

    public List<CityArea> selectByCode(String code);

    public List<CityArea> selectByPCode(String code);

    public PageInfo<CityArea> queryByAny(Page page, CityArea cityArea);

}
