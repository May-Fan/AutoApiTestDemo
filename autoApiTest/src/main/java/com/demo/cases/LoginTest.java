package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.InterfaceName;
import com.demo.bean.LoginCase;
import com.demo.config.TestConfig;
import com.demo.utils.ConfigFile;
import com.demo.utils.DatabaseUtil;
import com.demo.utils.HttpClientUtil;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: 登录接口测试用例
 * @author: May
 * @create: 2020-02-04 13:09
 */
public class LoginTest {
  //声明日志变量
  Logger logger = LogManager.getLogger(LoginTest.class.getName());
  /**
   * 前期测试准备
   */
  @Test(groups = "login",description = "测试准备工作")
  public void beforeTest() {
    TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
    TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
    TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
    TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
    TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);

    TestConfig.httpClient = HttpClients.createDefault();
  }

  @Test(dependsOnGroups = "login")
  public void loginTest() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    //获取loginCase表中的测试数据和预期结果
    LoginCase loginCase = session.selectOne("loginCase",1);
    System.out.println(loginCase.toString());
    System.out.println(TestConfig.loginUrl);
    //1.传递loginCase表中的测试数据，获取接口返回的String字符串
    String result = setParams(loginCase);
    logger.info("获取到预期值为："+loginCase.getExpected());
    logger.info("获取到接口实际返回值为："+result);
    //2.将loginCase表中的预期，和接口实际返回的结果比较
    Assert.assertEquals(loginCase.getExpected(),result);
  }

  /**
   * 向接口发起请求，处理并返回response信息
   * @param loginCase 传递的测试数据
   * @return 返回预期
   * @throws IOException
   */
  public String setParams(LoginCase loginCase) throws IOException {

    //1.声明定义请求参数，为loginCase表中的测试数据
    JSONObject param = new JSONObject();
    param.put("username",loginCase.getUsername());
    param.put("password",loginCase.getPassword());
    String result = HttpClientUtil.doPost(TestConfig.loginUrl,param.toString());

    //2.声明全局cookieStore，存放登录接口返回的cookie信息，供后面接口使用
    TestConfig.cookieStore = new BasicCookieStore();
    return result;
  }
}
