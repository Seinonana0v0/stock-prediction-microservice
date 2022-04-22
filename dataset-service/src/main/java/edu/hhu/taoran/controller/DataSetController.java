package edu.hhu.taoran.controller;

import edu.hhu.taoran.entity.Result;
import edu.hhu.taoran.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/dataset")
public class DataSetController {
    private final static String DATASET_PATH = "F:\\WorkSpace-PyCharm\\stock-prediction-python\\datasets";

    @Autowired
    private DataSetService dataSetService;
    /**
     * 上传csv
     * @param dataSet 数据集
     */
    @RequestMapping("/upload")
    public Result uploadDataSet(@RequestParam("dataSet") MultipartFile dataSet){
        try {
            System.out.println(dataSet.getOriginalFilename());
            dataSet.transferTo(new File(DATASET_PATH,dataSet.getOriginalFilename()));
            return new Result(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false);
        }

    }

    @RequestMapping("/uploadDataSetStatus")
    public Result uploadDataSetStatus(@RequestParam String stockId){
        try {
            dataSetService.uploadDataSet(stockId);
            return new Result(true);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false);
        }
    }

    /**
     * 导出csv
     * @param stockId 股票代码
     * @param response 响应
     */
    @RequestMapping("/download/{stockId}")
    public void downloadDataSet(@PathVariable String stockId, final HttpServletResponse response){
        String fileName = null;
        if(stockId.startsWith("6")){
            fileName = stockId+".sh.csv";
        }else if(stockId.startsWith("3")||stockId.startsWith("0")){
            fileName = stockId+".sz.csv";
        }

        try {
            // path是指想要下载的文件的路径
            File file = new File(DATASET_PATH,fileName);
            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
//            response.setContentType("application/x-msdownload");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/selectDataSetStatusById")
    public Integer selectDataSetStatusById(@RequestParam String stock_Id){
        return dataSetService.selectDataSetStatusById(stock_Id);
    }

    @RequestMapping("/insertWithOutDataSet")
    public void insertWithOutDataSet(@RequestParam String stockId) {
        dataSetService.insertWithOutDataSet(stockId);
    }

    @RequestMapping("/insertWithDataSet")
    public void insertWithDataSet(@RequestParam String stockId) {
        dataSetService.insertWithDataSet(stockId);
    }



    @Scheduled(fixedDelay = 1000*5)
    public void updateDateSets(){

    }

}
