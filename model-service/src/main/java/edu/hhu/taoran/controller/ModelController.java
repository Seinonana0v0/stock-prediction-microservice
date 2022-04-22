package edu.hhu.taoran.controller;

import edu.hhu.taoran.entity.ModelStatus;
import edu.hhu.taoran.entity.Result;
import edu.hhu.taoran.service.ModelService;
import edu.hhu.taoran.utils.PythonHttpUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @RequestMapping("/selectModelStatusById")
    public ModelStatus selectModelStatusById(@RequestParam String stockId){
        return modelService.selectModelStatusById(stockId);
    }

    @RequestMapping("/train/{id}/{to_train}")
    public Result train(@PathVariable("id")String id,@PathVariable("to_train") String to_train){
        String url = "http://127.0.0.1:5000/train/"+id+"/"+to_train;
        HttpGet httpGet = PythonHttpUtils.PythonHttpCreate(url);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            String resMessage = EntityUtils.toString(response.getEntity());
            if(resMessage.equals("success")){
                if(to_train.equals("close")){
                    modelService.updateCloseStatus(id);
                }
                if(to_train.equals("open")){
                    modelService.updateOpenStatus(id);
                }

                return new Result(true);
            }else {
                return new Result(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false);
        }
    }

    @RequestMapping("/insertModel")
    public void insertModel(@RequestParam String stockId){
        modelService.insertModel(stockId);
    }
}
