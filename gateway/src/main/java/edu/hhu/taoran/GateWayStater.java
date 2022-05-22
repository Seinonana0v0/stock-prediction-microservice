package edu.hhu.taoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientProperties;


@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignClientProperties.FeignClientConfiguration.class,
        basePackages = "edu.hhu.taoran.clients")
public class GateWayStater {
    public static void main(String[] args) {
        SpringApplication.run(GateWayStater.class,args);
    }
}
