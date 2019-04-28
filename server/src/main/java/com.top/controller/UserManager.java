package com.top.controller;

import com.top.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


//server自定义接口开发

@Log4j
@RestController
@Api(value = "v1",description = "用户相关接口")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response, @RequestParam User user){

        int i = template.selectOne("login",user);
        if (i==1){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            log.info("查询到的结果是" + user.getEmail());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "获取用户信息接口",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public String getUserInfo(HttpServletRequest request){
        Boolean x = verifyCookies(request);
        if (x==true){
            User result = template.selectOne("getUserInfo");
            log.info("获取用户信息成功");
            return result.toString();
        }
        return null;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            log.info("cookies为空");
            return false;
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("login") &&
            cookie.getValue().equals(true)){
                log.info("cookies验证通过");
                return true;
            }
        }
        return false;
    }
}
