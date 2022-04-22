package edu.hhu.taoran.utils;

import org.apache.http.client.methods.HttpGet;

public class PythonHttpUtils {
    public static HttpGet PythonHttpCreate(String url){
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.addHeader("Accept-Encoding0","gzip, deflate");
        httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        httpGet.addHeader("Cache-Control","max-age=0");
        httpGet.addHeader("Connection","keep-alive");
        httpGet.addHeader("Host","data.10jqka.com.cn");
        httpGet.addHeader("Upgrade-Insecure-Requests","1");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        return  httpGet;
    }
}
