package com.jd.dao.mapper.user;

import com.jd.dtos.EvaluationDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluationMapperExt extends EvaluationMapper {

    List<EvaluationDto> queryEvaluationListByCurioId(@Param("param") EvaluationDto evaluationDto);

    List<EvaluationDto> queryEvaluationListByItemIdOrUserId(@Param("param") EvaluationDto evaluationDto);

    List<EvaluationDto> queryEvaluationListByShopId(@Param("param") EvaluationDto evaluationDto);
}