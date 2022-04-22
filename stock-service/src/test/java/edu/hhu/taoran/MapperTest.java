package edu.hhu.taoran;

import edu.hhu.taoran.entity.ModelStatus;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.mapper.StockMapper;
import edu.hhu.taoran.pojo.Stock;
import edu.hhu.taoran.pojo.StockDataSet;
import edu.hhu.taoran.pojo.StockModel;
import org.elasticsearch.client.ml.job.process.ModelSizeStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= StockServiceStater.class)
public class MapperTest {
    @Autowired
    private StockMapper stockMapper;

    @Test
    public void testSelectAll(){
        List<Stock> stockList = stockMapper.findAll();
        System.out.println(stockList);
    }








}
