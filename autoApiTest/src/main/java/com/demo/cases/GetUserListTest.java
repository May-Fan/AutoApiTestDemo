package com.demo.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.GetUserListCase;
import com.demo.bean.UpdateUserInfoCase;
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
import java.util.List;

/**
 * @program: AutoApiTestDemo
 * @description: 获取用户列表接口测试用例
 * @author: May
 * @create: 2020-02-04 14:29
 */
public class GetUserListTest {
  @Test(dependsOnGroups = "login",description = "获取用户列表测试")
  public void getUserList() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    //获取userListCase表中的测试数据和预期结果
    GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
    System.out.println(getUserListCase.toString());
    System.out.println(TestConfig.getUserListUrl);
    //1.传递userListCase表中的测试数据，获取接口的返回JSONArray数组
    JSONArray resultJsonArray = getJsonResult(getUserListCase);
    //2.1直接查询user表，获取预期结果并打印
    List<User> expectedUserList = session.selectList(getUserListCase.getExpected(),getUserListCase);
    for(User user:expectedUserList) {
      System.out.println(user.toString());
    }
    //2.2将获取到的预期转化成JSONArray格式的字符串，并转化为JSONArray
    JSONArray expectedJsonArray = JSONArray.parseArray(JSON.toJSONString(expectedUserList));
    //3.1先比对两个JSON数组的大小
    Assert.assertEquals(expectedJsonArray.size(),resultJsonArray.size());
    //3.2遍历2个数组，将其中的元素一一比对
    for(int i=0;i<resultJsonArray.size();i++) {
      JSONObject expect = (JSONObject) resultJsonArray.get(i);
      JSONObject actual = (JSONObject) expectedJsonArray.get(i);
      Assert.assertEquals(expect.toString(),actual.toString());
    }
  }
  /**
   * 向接口发起请求，处理并返回response信息
   * @param getUserListCase 传递的测试数据
   * @return JSONArray格式的用户信息
   * @throws IOException
   */
  public JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
    //1.声明post请求，放请求url
    HttpPost post = new HttpPost(TestConfig.getUserListUrl);
    //2.1声明定义请求参数，为userListCase表中的测试数据
    JSONObject param = new JSONObject();
    param.put("username",getUserListCase.getUsername());
    param.put("email",getUserListCase.getEmail());
    //2.2设置请求头格式，将参数放入请求头
    post.setHeader("content-type","application/json");
    StringEntity entity = new StringEntity(param.toString(),"utf-8");
    post.setEntity(entity);
    //3.1发起post请求，获取响应
    HttpResponse response = TestConfig.httpClient.execute(post);
    //3.2从response请求头中，获取接口返回的内容
    String result = EntityUtils.toString(response.getEntity());
    JSONArray jsonArray = JSONArray.parseArray(result);
    return jsonArray;
  }
}
