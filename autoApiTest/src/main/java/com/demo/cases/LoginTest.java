package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.InterfaceName;
import com.demo.testdata.TestData;
import com.demo.utils.GetUrlUtil;
import com.demo.utils.ConfigFile;
import com.demo.utils.HttpClientUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: 登录接口用例
 * @author: May
 * @create: 2020-02-04 13:09
 */
public class LoginTest {
  //1.声明日志变量
  Logger logger = Logger.getLogger(LoginTest.class);
  //2.手动声明全局cookieStore，存放登录接口返回的cookie信息
//  CookieStore cookieStore = new BasicCookieStore();

  /**
   * 前期测试准备
   */
  @BeforeTest
  public void beforeTest() {
    //配置文件读取hostname
    GetUrlUtil.hostname = ConfigFile.getHostName();

    //从配置文件中获取其他接口url，保留，暂不删除
    GetUrlUtil.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
    GetUrlUtil.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
    GetUrlUtil.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
    GetUrlUtil.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
  }

  @Test(dataProvider = "loginData")
  public void loginTest(Object[] user,String url,String expect) throws IOException {
    //1.拼接配置文件中的地址和excel中api地址
    url = GetUrlUtil.hostname + url;
    //2.发送http请求
    String result = doRequest(user,url);
    logger.info("获取到接口地址："+ url);
    logger.info("获取到预期值为："+ expect);
    logger.info("获取到接口实际返回值为："+result);
    //3.断言
    Assert.assertEquals(result,expect);
  }

  /**
   * 数据驱动模块
   * @return 返回测试数据
   */
  @DataProvider(name="loginData")
  private Object[][] setParams() throws IOException {
    Object[][] testData = TestData.getTestData(InterfaceName.LOGIN);
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
    logger.info("获取到接口传参为：" + param.toJSONString());
    String result = HttpClientUtil.doPost(url,param.toJSONString());
    return result;
  }
}
