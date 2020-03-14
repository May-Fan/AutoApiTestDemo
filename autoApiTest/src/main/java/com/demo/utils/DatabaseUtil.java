package com.demo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-02-04 12:23
 */
public class DatabaseUtil {
  public static SqlSession getSqlSession() throws IOException {
    //1.获取配置的资源文件
    Reader reader = Resources.getResourceAsReader("databaseConfig-test.xml");
    //2.加载配置文件
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
    //sqlSession能执行配置文件中的sql语句
    SqlSession sqlSession = factory.openSession();
    return sqlSession;
  }
}
