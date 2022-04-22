package edu.hhu.taoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignClientProperties.FeignClientConfiguration.class,
        basePackages = "edu.hhu.taoran.clients")
@EnableScheduling
@EnableTransactionManagement
public class StockServiceStater {
    public static void main(String[] args) {
        SpringApplication.run(StockServiceStater.class,args);
    }
}
