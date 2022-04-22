package edu.hhu.taoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignClientProperties.FeignClientConfiguration.class,
        basePackages = "edu.hhu.taoran.clients")
@EnableTransactionManagement
public class UserServiceStater {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceStater.class,args);
    }
}
