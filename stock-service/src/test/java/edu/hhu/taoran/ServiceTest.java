package edu.hhu.taoran;

import edu.hhu.taoran.entity.PageResult;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(classes= StockServiceStater.class)
public class ServiceTest {
    @Autowired
    private StockService stockService;

    @Test
    public void findBySearchCondition(){
        SearchCondition sc = new SearchCondition(2,5,"","");
        PageResult pageResult = stockService.selectBySearchConditionWithDataSet(sc);
        System.out.println(pageResult.getTotal());
        System.out.println(pageResult.getRows());

    }
}
