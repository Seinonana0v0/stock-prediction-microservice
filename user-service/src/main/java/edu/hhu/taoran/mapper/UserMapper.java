package edu.hhu.taoran.mapper;

import edu.hhu.taoran.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from t_user where username=#{username} and password = #{password}")
    User checkLogin(@Param("username") String username, @Param("password") String password);
}
