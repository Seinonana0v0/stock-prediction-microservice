package edu.hhu.taoran.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import edu.hhu.taoran.entity.PageResult;
import edu.hhu.taoran.entity.Result;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.pojo.Stock;
import edu.hhu.taoran.service.StockService;
import edu.hhu.taoran.utils.PythonHttpUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StockService stockService;

    /**
     * 条件查询
     * @param searchCondition 查询条件
     * @return
     */
    @RequestMapping("/selectStock")
    public PageResult selectBySearchCondition(@RequestBody SearchCondition searchCondition){
        return stockService.selectBySearchCondition(searchCondition);
    }

    /**
     * 带数据集状态条件查询
     * @param searchCondition
     * @return
     */
    @RequestMapping("/selectStockWithDataSet")
    public PageResult selectBySearchConditionWithDataSet(@RequestBody SearchCondition searchCondition){
        return stockService.selectBySearchConditionWithDataSet(searchCondition);
    }

    /**
     * 带模型状态条件查询
     * @param searchCondition
     * @return
     */
    @RequestMapping("/selectStockWithModel")
    public PageResult selectBySearchConditionWithModel(@RequestBody SearchCondition searchCondition){
        return stockService.selectBySearchConditionWithModel(searchCondition);
    }


    /**
     * 进行预测
     * @param id 股票代码
     * @param toPredict 要预测的数据
     * @return
     */
    @RequestMapping("/predict/{id}/{toPredict}")
    public Result predict(@PathVariable("id") String id, @PathVariable("toPredict") String toPredict){
        String redisKey = id+"_"+toPredict;
        String responseJson = null;
        if(!redisTemplate.hasKey(redisKey)){
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            String url = "http://127.0.0.1:5000/predict/"+id+"/"+toPredict;
            HttpGet httpGet = PythonHttpUtils.PythonHttpCreate(url);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
                responseJson = EntityUtils.toString(response.getEntity());
                JSONArray jsonArray = JSON.parseArray(responseJson);
                List<Float> list = jsonArray.toJavaList(Float.class);
                redisTemplate.boundValueOps(redisKey).set(responseJson,8, TimeUnit.HOURS);
                return new Result(list,true);
            }catch (IOException e) {
                e.printStackTrace();
                return new Result(false);
            }
        }else {
            responseJson = redisTemplate.boundValueOps(redisKey).get();
            JSONArray jsonArray = JSON.parseArray(responseJson);
            List<Float> list = jsonArray.toJavaList(Float.class);
            return new Result(list,true);
        }

    }



    @RequestMapping("/insertWithDataSet")
    public Result insertStockWithDataSet(@RequestBody Stock stock){
        try {
            stockService.insertStockWithDataSet(stock);
            return new Result(true);
        }catch (Exception e){
            return new Result(false);
        }
    }

    @RequestMapping("/insertWithoutDataSet")
    public Result insertStockWithoutDataSet(@RequestBody Stock stock){
        try {
            stockService.insertStockWithoutDataSet(stock);
            return new Result(true);
        }catch (Exception e){
            return new Result(false);
        }
    }

    @RequestMapping("/{id}")
    public Result selectById(@PathVariable("id")String id){
        Stock stock = stockService.selectById(id);
        if(stock==null){
            return new Result(false);
        }else {
            return new Result(stock,true);
        }
    }

    @RequestMapping("/insertToES")
    public Result insertToES(@RequestParam String stockId){
        try {
            stockService.insertToEs(stockId);
            return new Result(true);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false);
        }
    }

    @RequestMapping("/selectFromES")
    public PageResult selectFromES(@RequestBody SearchCondition searchCondition){
        try {
            return stockService.ESSearch(searchCondition);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("selectNameById")
    public String selectNameById(@RequestParam String id){
        return stockService.selectNameById(id);
    }

}
