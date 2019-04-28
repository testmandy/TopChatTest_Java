package com.top.testcases;


import com.top.config.TestConfig;
import com.top.model.InterfaceName;
import com.top.utils.ConfigFile;
import org.testng.annotations.BeforeTest;
import sun.net.www.http.HttpClient;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取")
    public void beforeTest(){
        // 为url赋值
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);

        // 定义共用的httpClient


    }
}
