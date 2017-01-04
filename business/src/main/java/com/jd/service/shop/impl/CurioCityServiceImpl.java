package com.jd.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dao.mapper.user.CurioCityMapperExt;
import com.jd.dao.mapper.user.EvaluationMapperExt;
import com.jd.dtos.CurioCityDto;
import com.jd.dtos.EvaluationDto;
import com.jd.entity.user.CurioCity;
import com.jd.service.shop.CurioCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.service.shop.impl .by jingdun.tech.
 */
@Service
public class CurioCityServiceImpl implements CurioCityService {

    @Autowired
    CurioCityMapperExt curioCityMapperExt;

    @Autowired
    EvaluationMapperExt evaluationMapperExt;

    @Override
    public CurioCityDto findCurioCityInfo(Long id) {
        return curioCityMapperExt.findCurioCityInfo(id);
    }

    @Override
    public CurioCity findCurioCityById(Long id) {
        return curioCityMapperExt.selectByPrimaryKey(id);
    }

    @Override
    public CurioCity updateCurioCity(CurioCity curioCity) {
        int id = curioCityMapperExt.updateByPrimaryKeySelective(curioCity);
        if (id > 0)
            return curioCityMapperExt.selectByPrimaryKey(Long.valueOf(id));
        else
            return null;
    }

    @Override
    public CurioCity saveCurioCity(CurioCity curioCity) {
        int id = curioCityMapperExt.insertSelective(curioCity);
        if (id > 0)
            return curioCityMapperExt.selectByPrimaryKey(Long.valueOf(id));
        else
            return null;
    }

    @Override
    public PageInfo<CurioCityDto> queryCurioCityList(Pager pager, CurioCityDto curioCityDto) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<CurioCityDto> res = curioCityMapperExt.queryCurioCityList(curioCityDto);
        return new PageInfo<>(res);
    }

    @Override
    public List<CurioCityDto> queryList(CurioCityDto curioCityDto) {
        return curioCityMapperExt.queryCurioCityList(curioCityDto);
    }

    @Override
    public PageInfo<EvaluationDto> queryEvaluationListByCurioId(Pager pager, EvaluationDto evaluationDto) {
        if (pager != null)
            PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<EvaluationDto> list = evaluationMapperExt.queryEvaluationListByCurioId(evaluationDto);
        return new PageInfo<>(list);
    }

    @Override
    public List<EvaluationDto> queryListByCurioId(EvaluationDto evaluationDto) {
        return evaluationMapperExt.queryEvaluationListByCurioId(evaluationDto);
    }
}
