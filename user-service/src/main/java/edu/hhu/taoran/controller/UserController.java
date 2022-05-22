package edu.hhu.taoran.controller;

import edu.hhu.taoran.entity.Result;
import edu.hhu.taoran.pojo.User;
import edu.hhu.taoran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(@RequestBody Map<String,String> param, HttpServletRequest request, HttpServletResponse response){
        String username = param.get("username");
        String password = param.get("password");
        try {
            User user = userService.checkLogin(username, password);
            if(user==null){
                return new Result(false);
            }else {
                HttpSession session = request.getSession(true);
                session.setAttribute("user",user);
                User user1 = (User) session.getAttribute("user");

                String token = userService.getToken(user);
                Cookie cookie = new Cookie("token",token);
                cookie.setPath("/");
                return new Result(token,true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false);
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return new Result(true);
    }

    @RequestMapping("/selectById")
    public User selectById(@RequestParam Integer id){
        return userService.selectById(id);
    }

    @RequestMapping("/selectByUsername")
    public User selectById(@RequestParam String username){
        return userService.selectByUsername(username);
    }

}
