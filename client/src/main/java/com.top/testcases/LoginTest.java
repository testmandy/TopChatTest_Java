package com.top.testcases;

import com.top.config.TestConfig;
import com.top.model.InterfaceName;
import com.top.model.LoginCase;
import com.top.utils.ConfigFile;
import com.top.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取HttpClient对象")
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

        // 发请求，获取结果
        String result = getResult(loginCase);

        // 验证返回结果
        Assert.assertEquals(loginCase.getExpected(),result);

    }

    @Test(groups = "loginFalse",description = "登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        // 发请求，获取结果
        String result = getResult(loginCase);

        // 验证返回结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }


    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("rawId",loginCase.getRawId());
        param.put("displayName",loginCase.getDisplayName());
        param.put("photoURL",loginCase.getPhotoURL());
        param.put("email",loginCase.getEmail());
        param.put("providerId",loginCase.getProviderId());
        param.put("accessToken",loginCase.getAccessToken());
        param.put("federatedId",loginCase.getFederatedId());
        param.put("code",loginCase.getCode());


        // 设置头信息
        post.setHeader("content-type","application/json");


        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);

        // 设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        // 声明result存放返回结果
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("------------------登录测试用例执行结果------------------");
        System.out.println("返回的statusline为:" + response.getStatusLine());

        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);

        System.out.println("返回的Json内容为：" + array);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return "true";
        }
        return "false";
    }
}
