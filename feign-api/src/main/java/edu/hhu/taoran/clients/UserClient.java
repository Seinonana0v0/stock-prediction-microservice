package edu.hhu.taoran.clients;

import edu.hhu.taoran.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("userservice")
public interface UserClient {

    @RequestMapping("/user/selectByUsername")
    User selectByUsername(@RequestParam String username);

}
