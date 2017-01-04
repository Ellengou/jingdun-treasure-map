package com.jd.dao.mapper.user;

import com.jd.entity.user.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapperExt extends UserMapper {

    Boolean updatePassword(@Param("id") Long id,@Param("password") String password);

    User findUserByNameAndPassword(@Param("name") String username, @Param("password") String password);

    User findUserByName(@Param("name") String username);
}