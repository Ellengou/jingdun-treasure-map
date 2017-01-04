package com.jd.dao.mapper.user;

import com.jd.base.BaseDao;
import com.jd.entity.user.AccountRole;

public interface AccountRoleMapper extends BaseDao {
    int insert(AccountRole record);

    int insertSelective(AccountRole record);
}