package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.InterfaceName;
import com.demo.testdata.TestData;
import com.demo.utils.GetUrlUtil;
import com.demo.utils.HttpClientUtil;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: 新增用户接口用例
 * @author: May
 * @create: 2020-02-04 12:37
 */
public class AddUserTest {
  //声明日志变量
  Logger logger = Logger.getLogger(AddUserTest.class);

  @Test(dataProvider = "addUserData")
  public void addUser(Object[] user,String url,String expect) throws IOException {
    //1.拼接配置文件中的地址和excel中api地址
    url = GetUrlUtil.hostname + url;
    //2.发送http请求
    String result = doRequest(user,url);
    logger.info("接口访问地址："+ url);
    logger.info("预期结果："+ expect);
    logger.info("实际返回："+result);
    //3.断言
    Assert.assertEquals(result,expect);

 }
  /**
   * 数据驱动模块
   * @return 返回测试数据
   */
  @DataProvider(name="addUserData")
  private Object[][] setParams() {
    Object[][] testData = TestData.getTestData(InterfaceName.ADDUSER);
    return testData;
  }

  /**
   * 配置参数，获取接口返回
   * @param user excel中新增的用户信息
   * @param url api路径
   * @return 接口返回值
   * @throws IOException
   */
  public String doRequest(Object[] user,String url) throws IOException {
    //1.声明定义请求参数，是addUserCase表中的测试数据
    JSONObject param = new JSONObject(true);
    param.put("id",user[0]);
    param.put("username",user[1]);
    param.put("password",user[2]);
    param.put("email",user[3]);
    param.put("create_time",user[4]);
    //2.传入url，json字符型参数，获取响应
    String result = HttpClientUtil.doPost(url,param.toString());
    return result;
  }
}