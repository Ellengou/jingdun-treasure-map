package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.operateLog;

public interface OperateLogMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(operateLog record);

    int insertSelective(operateLog record);

    operateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(operateLog record);

    int updateByPrimaryKey(operateLog record);
}