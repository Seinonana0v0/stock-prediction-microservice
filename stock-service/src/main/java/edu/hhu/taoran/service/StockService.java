package edu.hhu.taoran.service;

import edu.hhu.taoran.entity.PageResult;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.pojo.Stock;

import java.io.IOException;
import java.util.List;

public interface  StockService {
    Stock selectById(String id);
    PageResult selectBySearchCondition(SearchCondition searchCondition);
    PageResult selectBySearchConditionWithDataSet(SearchCondition searchCondition);
    PageResult selectBySearchConditionWithModel(SearchCondition searchCondition);
    void insertStockWithDataSet(Stock stock);
    void insertStockWithoutDataSet(Stock stock);
    PageResult ESSearch(SearchCondition searchCondition) throws IOException;
    void insertToEs(String stockId) throws IOException;
    String selectNameById(String id);
    List<String> selectIndustrys();


}
