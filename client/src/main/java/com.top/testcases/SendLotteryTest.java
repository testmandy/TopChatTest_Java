package com.top.testcases;

import com.top.config.TestConfig;
import com.top.model.SendLotteryCase;
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


public class SendLotteryTest {

    @Test(dependsOnGroups = "loginTrue",description = "发起彩票接口测试")
    public void sendLottery() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        SendLotteryCase sendLotteryCase = session.selectOne("sendLottery",1);
        System.out.println(TestConfig.sendLotteryUrl);

        // 发请求，获取结果
        String result = getResult(sendLotteryCase);

        // 验证返回结果
        Assert.assertEquals(sendLotteryCase.getExpected(),result);

    }

    private String getResult(SendLotteryCase sendLotteryCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.sendLotteryUrl);
        JSONObject param = new JSONObject();
        param.put("address",sendLotteryCase.getAddress());
        param.put("userId",sendLotteryCase.getUserId());
        param.put("amount",sendLotteryCase.getAmount());


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
        System.out.println("------------------发起彩票用例执行结果------------------");
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
