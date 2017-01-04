package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.Account;

public interface AccountMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}