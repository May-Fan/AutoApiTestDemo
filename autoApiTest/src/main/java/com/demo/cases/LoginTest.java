package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.InterfaceName;
import com.demo.bean.LoginCase;
import com.demo.testdata.ExcelUtil;
import com.demo.testdata.TestData;
import com.demo.utils.GetUrlUtil;
import com.demo.utils.ConfigFile;
import com.demo.utils.DatabaseUtil;
import com.demo.utils.HttpClientUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: 登录接口测试用例
 * @author: May
 * @create: 2020-02-04 13:09
 */
public class LoginTest {
  //声明日志变量
  Logger logger = Logger.getLogger(LoginTest.class);
  //声明全局cookieStore，存放登录接口返回的cookie信息，供后面接口使用
  CookieStore cookieStore = new BasicCookieStore();
  /**
   * 前期测试准备
   */
  @BeforeTest
  public void beforeTest() {
    GetUrlUtil.loginUrl = ConfigFile.getHostName(InterfaceName.LOGIN);
    GetUrlUtil.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
    GetUrlUtil.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
    GetUrlUtil.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
    GetUrlUtil.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
  }

  @Test(dataProvider = "loginData")
  public void loginTest(Object[] user,String url,String expect) throws IOException {
    //1.获取loginCase表中的测试数据和预期结果
    SqlSession session = DatabaseUtil.getSqlSession();
    LoginCase loginCase = session.selectOne("loginCase",1);
    logger.info("获取到接口地址："+url);
    logger.info("获取到预期值为："+loginCase.getExpected());

    //2.获取dataProvider中的测试数据，获取接口响应的String字符串
    String result = doRequest(user,GetUrlUtil.loginUrl+url);
//    logger.info("当前登录用户为："+ username);
    logger.info("获取到接口实际返回值为："+result);

    //3.将loginCase表中的预期，和接口实际返回的结果比较
    Assert.assertEquals(result,expect);
  }

  /**
   * 数据驱动模块
   * @return 返回测试数据
   */
  @DataProvider(name="loginData")
  private Object[][] setParams() throws IOException {
    Object[][] testData = TestData.getTestData("Login");
    return testData;
  }

  /**
   * 发送http post请求，获取接口响应值
   * @return String类型返回值
   * @throws IOException
   */
  private String doRequest(Object[] user,String url) throws IOException {
    JSONObject param = new JSONObject();
    param.put("username",user[0]);
    param.put("password",user[1]);
    String result = HttpClientUtil.doPost(url,param.toJSONString());
    return result;
  }
}
