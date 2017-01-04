package com.jd.service.shop;

import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.dtos.CurioCityDto;
import com.jd.dtos.EvaluationDto;
import com.jd.entity.user.CurioCity;

import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.service.shop .by jingdun.tech.
 */
public interface CurioCityService {

    CurioCityDto findCurioCityInfo(Long id);

    CurioCity findCurioCityById(Long id);

    CurioCity updateCurioCity(CurioCity curioCity);

    CurioCity saveCurioCity(CurioCity curioCity);

    PageInfo<CurioCityDto> queryCurioCityList(Pager pager, CurioCityDto curioCityDto);

    List<CurioCityDto> queryList(CurioCityDto curioCityDto);

    PageInfo<EvaluationDto> queryEvaluationListByCurioId(Pager pager, EvaluationDto evaluationDto);

    List<EvaluationDto> queryListByCurioId(EvaluationDto evaluationDto);
}
