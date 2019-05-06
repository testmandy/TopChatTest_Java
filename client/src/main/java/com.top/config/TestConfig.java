package com.top.config;


import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

/**用来装一些测试方法使用的变量
 * 比如每个接口对应的最终的请求url，写在统一的地方，方便取用和修改
 * ----------接下来需要写一个工具类，用来拼接url
 */
public class TestConfig {
    // 定义不同的url
    public static String loginUrl;
    public static String getUserInfoUrl;
    public static String sendLotteryUrl;
    public static String sendLotteryInfoUrl;
    public static String bettingLotteryUrl;
    public static String getLottoryExpireTimeUrl;


    // 定义共用的httpClient
    public static DefaultHttpClient defaultHttpClient;
    public static CookieStore store;
    public static String token;

}
