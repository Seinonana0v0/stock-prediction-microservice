package edu.hhu.taoran.mapper;

import edu.hhu.taoran.entity.ModelStatus;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.pojo.Stock;
import edu.hhu.taoran.utils.StockSqlProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StockMapper {

    @Select("select * from t_stock")
    List<Stock> findAll();

    @Select("select * from t_stock where id = #{id}")
    Stock selectById(String id);



    @Select("select name from t_stock where id = #{id}")
    String selectNameById(String id);

    @SelectProvider(type = StockSqlProvider.class,method = "selectStockBySearchCondition")
    List<Stock> selectBySearchCondition(SearchCondition searchCondition);


    @Insert("insert into t_stock values(#{id},#{name},#{industry},#{introduction})")
    void insertStock(Stock stock);



}
