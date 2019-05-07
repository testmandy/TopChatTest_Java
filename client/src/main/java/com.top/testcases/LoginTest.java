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
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigInteger;

public class LoginTest {

    String myToken;

    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取HttpClient对象")
    public void beforeTest(){
        // 为url赋值
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.sendLotteryUrl = ConfigFile.getUrl(InterfaceName.SENDLOTTERY);
        TestConfig.sendLotteryInfoUrl = ConfigFile.getUrl(InterfaceName.SENDLOTTERYINFO);
        TestConfig.bettingLotteryUrl = ConfigFile.getUrl(InterfaceName.BETTINGLOTTERY);
        TestConfig.getLottoryExpireTimeUrl = ConfigFile.getUrl(InterfaceName.GETLOTTORYEXPIRETIME);

        // 实例化httpClient
        TestConfig.defaultHttpClient = new DefaultHttpClient();



    }

    @Test(groups = "loginTrue",description = "登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",4);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        // 发请求，获取结果
        String result = getResult(loginCase);

        // 验证返回结果
        Assert.assertEquals(loginCase.getExpected(),result);

    }

    @Test(groups = "loginFalse",description = "登录失败接口测试",enabled = false)
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
        param.put("email",loginCase.getEmail());
        param.put("providerId",loginCase.getProviderId());
//        param.put("photoURL",loginCase.getPhotoURL());
//        param.put("accessToken",loginCase.getAccessToken());
//        param.put("federatedId",loginCase.getFederatedId());
//        param.put("code",loginCase.getCode());


        // 设置头信息
        post.setHeader("content-type","application/json");

        // 设置请求体
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

        System.out.println("返回的Json内容为：" + result);

        JSONObject reslutJson = new JSONObject(result);
        JSONObject data1 = reslutJson.getJSONObject("data");
        System.out.println("返回的data为：" + data1);
        String tokenValue = data1.getString("accesstoken");
        System.out.println("返回的accesstoken为：" + tokenValue);
        BigInteger uidValue = data1.getBigInteger("uid");
        System.out.println("返回的uid为：" + uidValue);
        JSONObject data2 = data1.getJSONObject("data");
        String addressValue = data2.getString("address");
        System.out.println("返回的address为：" + addressValue);


        TestConfig.token = tokenValue;
        TestConfig.uid = uidValue;
        TestConfig.address = addressValue;

//        post.setHeader("token",tokenValue);
//        System.out.println(post.getHeaders("token"));


        int errCode = reslutJson.getInt("ErrCode");
        if (errCode == 0) {
            return "true";
        }
        return "false";
    }
}
