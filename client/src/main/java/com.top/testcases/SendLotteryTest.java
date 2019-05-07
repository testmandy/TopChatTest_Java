package com.top.testcases;

import com.top.config.TestConfig;
import com.top.model.SendLotteryCase;
import com.top.utils.DatabaseUtil;
import com.top.utils.JwtToken;
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
        String address = TestConfig.address;
        String userId = String.valueOf(TestConfig.uid);
        String amount = sendLotteryCase.getAmount();
        String myClaim = "{\"address\":\"" + address + "\",\"userId\":" + userId + ",\"amount\":" + amount + "}";
        System.out.println("返回的加密内容为：" + myClaim);
        String myJwt = JwtToken.createToken(myClaim);
        param.put("jwt",myJwt);


        // 设置头信息
        post.setHeader("content-type","application/json");
        post.setHeader("token",TestConfig.token);
        System.out.println("返回的token为：" + TestConfig.token);
        post.setHeader("uid", String.valueOf(TestConfig.uid));
        System.out.println("返回的uid为：" + TestConfig.uid);

        // 设置请求体
        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);
        System.out.println(entity);

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

        JSONObject data1 = reslutJson.getJSONObject("data");
        System.out.println("返回的data为：" + data1);
        String lotteryAccount = data1.getString("lotteryAccount");
        System.out.println("返回的lotteryAccount为：" + lotteryAccount);

        TestConfig.lotteryAccount = lotteryAccount;


        if (errcodeValue == 0) {
            return "true";
        }
        return "false";
    }
}
