package com.jd.service.basic.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.dao.mapper.user.CityAreaMapperEXT;
import com.jd.entity.user.CityArea;
import com.jd.service.basic.CityAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ellen on 2016/12/26.
 */
@Service
public class CityAreaServiceImpl implements CityAreaService {

    @Autowired
    CityAreaMapperEXT cityAreaMapperEXT;

    @Override
    public CityArea selectById(Integer id) {
        return cityAreaMapperEXT.selectByPrimaryKey(id);
    }

    @Override
    public List<CityArea> selectByCodeAndLevel(String code, int level) {
        return cityAreaMapperEXT.selectByCodeAndLevel(code, level);
    }

    @Override
    public List<CityArea> selectByCode(String code) {
        return cityAreaMapperEXT.selectByCode(code);
    }

    @Override
    public List<CityArea> selectByPCode(String code) {
        return cityAreaMapperEXT.selectByPCode(code);
    }

    @Override
    public PageInfo<CityArea> queryByAny(Page page, CityArea cityArea) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List sourceList = cityAreaMapperEXT.queryByAny(cityArea);
        return new PageInfo<CityArea>(sourceList);
    }

}
