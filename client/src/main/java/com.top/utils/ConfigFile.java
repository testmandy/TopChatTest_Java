package com.top.utils;

/**用来拼接url的工具类
 *---- （接下来需要写一个公共的、用来读取数据库、执行sql语句的对象）
 * ----（每个case都获取一个对象太麻烦所以封装到公共方法中）
 */

import com.top.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {

    // 定义一个变量，用来读取配置文件数据，取到url
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    // 定义拼接url的方法
    public static String getUrl(InterfaceName name){
        String address = bundle.getString("test.url");
        String uri ="";

        // 判断请求的是哪个地址
        if (name == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");
        }
        if (name == InterfaceName.GETUSERINFO){
            uri = bundle.getString("getUserInfo.uri");
        }

        // 拼接地址
        String testUrl = address + uri;
        return testUrl;

    }
}
