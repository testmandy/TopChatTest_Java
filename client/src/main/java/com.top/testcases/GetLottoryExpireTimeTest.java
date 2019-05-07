package com.top.testcases;

import com.top.config.TestConfig;
import com.top.model.GetLottoryExpireTimeCase;
import com.top.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetLottoryExpireTimeTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取彩票的过期时间接口测试")
    public void getLottoryExpireTime() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetLottoryExpireTimeCase getLottoryExpireTimeCase = session.selectOne("getLottoryExpireTime",1);
        System.out.println(TestConfig.getLottoryExpireTimeUrl);

        // 发请求，获取结果
        String result = getResult(getLottoryExpireTimeCase);

        // 验证返回结果
        Assert.assertEquals(getLottoryExpireTimeCase.getExpected(),result);

    }

    private String getResult(GetLottoryExpireTimeCase getLottoryExpireTimeCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getLottoryExpireTimeUrl);
        JSONObject param = new JSONObject();
        param.put("lotteryAccount",TestConfig.lotteryAccount);

        // 设置头信息
        post.setHeader("content-type","application/json");
        post.setHeader("token",TestConfig.token);
        System.out.println(TestConfig.token);
        post.setHeader("uid", String.valueOf(TestConfig.uid));
        System.out.println(TestConfig.uid);

        // 设置请求体
        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);

        // 设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        // 声明result存放返回结果
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("------------------获取彩票的过期时间用例执行结果------------------");
        System.out.println("返回的statusline为:" + response.getStatusLine());
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
