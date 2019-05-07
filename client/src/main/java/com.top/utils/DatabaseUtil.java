package com.top.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**用来读取数据库、执行sql语句的对象
 * （每个case都获取一个对象太麻烦所以封装到公共方法中）
 *
 * @Author mandy
 * @Create 2019/5/7 14:10
 */

public class DatabaseUtil {

    public static SqlSession getSqlSession() throws IOException {
        // 获取配置的资源文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");

        // 加载资源
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);

        // 返回SqlSession
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }
}
