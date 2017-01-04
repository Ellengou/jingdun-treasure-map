package com.jd.dao.mapper.user;

import com.jd.dtos.CurioCityDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CurioCityMapperExt extends CurioCityMapper {

    List<CurioCityDto> queryCurioCityList(@Param("param") CurioCityDto curioCityDto);

    CurioCityDto findCurioCityInfo(@Param("id") Long id);
}