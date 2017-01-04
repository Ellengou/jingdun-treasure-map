package com.jd.dao.mapper.user;

import com.jd.dtos.AccountListDto;
import com.jd.entity.user.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ellengou on 2016/11/24.
 */
public interface AccountMapperExt extends AccountMapper {

    Account findByUsername(@Param("name") String username,@Param("password") String password);

    Account findDetailByUserId(@Param("userId") Long userId);

    List<AccountListDto> selectAccountList(@Param("param") Account account);

    Account findBusinessByAccount(@Param("name") String username);

}
