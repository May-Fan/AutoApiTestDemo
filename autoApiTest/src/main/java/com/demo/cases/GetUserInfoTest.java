package com.demo.cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.GetUserInfoCase;
import com.demo.bean.User;
import com.demo.config.TestConfig;
import com.demo.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: AutoApiTestDemo
 * @description: 获取用户信息接口测试用例
 * @author: May
 * @create: 2020-02-04 14:48
 */
public class GetUserInfoTest {
  @Test(dependsOnGroups = "login", description = "获取用户信息测试")
  public void getUserInfo() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    //获取userInfoCase表中的测试数据和预期结果
    GetUserInfoCase userInfoCase = session.selectOne("getUserInfoCase", 1);
    System.out.println(userInfoCase.toString());
    System.out.println(TestConfig.getUserInfoUrl);
    //1.传递userInfoCase表中的测试数据，获取接口的返回JSONArray数组
    JSONArray resultJsonArray = getJsonResult(userInfoCase);
    //2.1直接查询user表，获取预期结果
    User user = session.selectOne(userInfoCase.getExpected(),userInfoCase);
    //2.2将获取到的预期转化成JSONArray格式的字符串，并转化为JSONArray
    String str = "["+JSONObject.toJSONString(user)+"]";
    JSONArray expectedJsonArray = JSONArray.parseArray(str);
    //3.将预期和实际接口返回值作比较
    Assert.assertEquals(expectedJsonArray,resultJsonArray);

  }
  /**
   * 向接口发起请求，处理并返回response信息
   * @param userInfoCase 传递的测试数据
   * @return JSONArray格式的用户信息
   * @throws IOException
   */
  public JSONArray getJsonResult(GetUserInfoCase userInfoCase) throws IOException {
    //1.声明post请求，放请求url
    HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
    //2.1声明定义请求参数，为userInfoCase表中的测试数据
    JSONObject param = new JSONObject();
    param.put("id",userInfoCase.getUserId());
    //2.2设置请求头格式，将参数放入请求头
    post.setHeader("content-type","application/json");
    StringEntity entity = new StringEntity(param.toString(),"utf-8");
    post.setEntity(entity);
    //3.1发起post请求，获取响应
    HttpResponse response = TestConfig.httpClient.execute(post);
    //3.2从response请求头中，获取接口返回的内容
    String result = EntityUtils.toString(response.getEntity(),"utf-8");
//    List resultList = Arrays.asList(result);
//    JSONArray array = new JSONArray(resultList);
    //3.3根据json字符串的符号，自动划分数组元素，result="[{"id":1,"username":"彭于晏","password":"123456"}]"
    JSONArray array = JSONArray.parseArray(result);
    return array;
  }
}
