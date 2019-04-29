package com.top.testcases;


import com.top.config.TestConfig;
import com.top.model.InterfaceName;
import com.top.model.LoginCase;
import com.top.utils.ConfigFile;
import com.top.utils.DatabaseUtil;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取资源文件")
    public void beforeTest(){
        // 为url赋值
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);

        // 实例化httpClient
        TestConfig.defaultHttpClient = new DefaultHttpClient();

    }

    @Test(groups = "loginTrue",description = "登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
    }

    @Test(groups = "loginFalse",description = "登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginFalse = session.selectOne("loginCase",2);
        System.out.println(loginFalse.toString());
        System.out.println(TestConfig.loginUrl);
    }

}
