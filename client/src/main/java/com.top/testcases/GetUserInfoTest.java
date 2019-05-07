package com.top.testcases;

import com.top.config.TestConfig;
import com.top.model.GetUserInfoCase;
import com.top.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @Author mandy
 * @Create 2019/5/7 14:10
 */
public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户信息接口测试",invocationCount = 1)
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfo",2);
        System.out.println(TestConfig.getUserInfoUrl);

        // 发请求，获取结果
        String result = getResult(getUserInfoCase);

        // 验证返回结果
        Assert.assertEquals(getUserInfoCase.getExpected(),result);

    }

    private String getResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);

        // 设置头信息
        post.setHeader("content-type","application/json");
        post.setHeader("token",TestConfig.token);
        System.out.println(TestConfig.token);
        post.setHeader("uid", String.valueOf(TestConfig.uid));
        System.out.println("获取的uid为：" + TestConfig.uid);


        // 设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        // 声明result存放返回结果
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("------------------获取用户信息用例执行结果------------------");
        System.out.println("返回的Json内容为：" + result);


        JSONObject reslutJson = new JSONObject(result);
        int errcodeValue = reslutJson.getInt("ErrCode");
        System.out.println("返回的ErrCode为：" + errcodeValue);
        String reasonValue = reslutJson.getString("Reason");
        System.out.println("返回的Reason为：" + reasonValue);

        if (errcodeValue == 0) {
            return "true";
        }
        return "false";
    }
}
