package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Evaluation;

public interface EvaluationMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Evaluation record);

    Long insertSelective(Evaluation record);

    Evaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Evaluation record);

    int updateByPrimaryKey(Evaluation record);
}