package edu.hhu.taoran.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("datasetservice")
public interface DataSetClient {

    @RequestMapping("/dataset/selectDataSetStatusById")
    Integer selectDataSetStatusById(@RequestParam String stock_Id);

    @RequestMapping("/dataset/insertWithOutDataSet")
    void insertWithOutDataSet(@RequestParam String stockId);


    @RequestMapping("/dataset/insertWithOutDataSet")
    void insertWithDataSet(@RequestParam String stockId);
}

