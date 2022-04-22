package edu.hhu.taoran.mapper;

import edu.hhu.taoran.entity.ModelStatus;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@Mapper
public interface ModelMapper {

    @Select("select openStatus,closeStatus from t_stock_model where stockId = #{stockId}")
    ModelStatus selectModelStatusById(String stockId);

    @Insert("insert into t_stock_model values(#{stockId},0,0)")
    void insertModel(String stockId);

    @Update("update t_stock_model set closeStatus = 1 where stockId = #{stockId}")
    void updateCloseStatus(@Param("stockId") String stockId);

    @Update("update t_stock_model set openStatus = 1 where stockId = #{stockId}")
    void updateOpenStatus(@Param("stockId") String stockId);
}
