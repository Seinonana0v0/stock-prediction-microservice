package edu.hhu.taoran.clients;

import edu.hhu.taoran.entity.ModelStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("modelservice")
public interface ModelClient {

    @RequestMapping("/model/selectModelStatusById")
    ModelStatus selectModelStatusById(@RequestParam String stockId);

    @RequestMapping("/model/insertModel")
    void insertModel(@RequestParam String stockId);
}
