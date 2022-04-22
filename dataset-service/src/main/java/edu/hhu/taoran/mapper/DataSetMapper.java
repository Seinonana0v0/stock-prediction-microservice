package edu.hhu.taoran.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DataSetMapper {
    @Select("select dataSetStatus from t_stock_dataset where stockId = #{stockId}")
    Integer selectDataSetStatusById(String stockId);

    @Insert("insert into t_stock_dataset values(#{stockId},0)")
    void insertWithOutDataSet(String stockId);

    @Insert("insert into t_stock_dataset values(#{stockId},1)")
    void insertWithDataSet(String stockId);

    @Update("update t_stock_dataset set dataSetStatus = 1 where stockId = #{stockId}")
    void uploadDataSet(String stockId);
}
