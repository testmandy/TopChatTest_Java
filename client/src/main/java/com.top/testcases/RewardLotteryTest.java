package com.top.testcases;

import com.top.config.TestConfig;
import com.top.model.InterfaceName;
import com.top.utils.ConfigFile;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @Author mandy
 * @Create 2019/5/8 18:04
 */
@Test(description = "彩票开奖接口用例")
public class RewardLotteryTest {
    public void rewardLottery() throws IOException {
        TestConfig.rewardLotteryUrl = ConfigFile.getUrl(InterfaceName.REWARDLOTTERY);
        System.out.println(TestConfig.rewardLotteryUrl);
        // 实例化httpClient
        TestConfig.defaultHttpClient = new DefaultHttpClient();

        // 发请求，获取结果
        String result = getResult();

        // 验证返回结果
        Assert.assertEquals("true",result);
    }

    private String getResult() throws IOException {
        HttpPost post = new HttpPost(TestConfig.rewardLotteryUrl);
        JSONObject param = new JSONObject();
        param.put("address",TestConfig.address);
        param.put("userId",TestConfig.uid);
        param.put("lotteryAccount",TestConfig.lotteryAccount);

        // 设置头信息
        post.setHeader("content-type","application/json");

        // 设置请求体
        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);

        // 声明result存放返回结果
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("------------------彩票开奖接口用例执行结果------------------");
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
