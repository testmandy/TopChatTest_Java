package com.top.testcases;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.top.config.TestConfig;
import com.top.model.GetUserInfoCase;
import com.top.utils.DatabaseUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户信息接口测试")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfo",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        // 发请求，获取结果
        String result = getResult(getUserInfoCase);

        // 验证返回结果
        Assert.assertEquals(getUserInfoCase.getExpected(),result);


    }

    private String getResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("uid",getUserInfoCase.getUserId());

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

        System.out.println(result);
        return result;
    }
}
