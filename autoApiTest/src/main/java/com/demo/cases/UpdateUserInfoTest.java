package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
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

/**
 * @program: AutoApiTestDemo
 * @description: 更新用户数据接口测试用例
 * @author: May
 * @create: 2020-02-04 14:10
 */
public class UpdateUserInfoTest {
  @Test(dependsOnGroups = "login",description = "更新用户信息测试")
  public void updateUserInfo() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
    System.out.println(updateUserInfoCase.toString());
    System.out.println(TestConfig.updateUserInfoUrl);

    //1.传递updateUserCase表中的测试数据，获取接口返回的Integer字符串
    int result = getResult(updateUserInfoCase);
    //2.根据传递给接口的对象，查询user表，获取该用例中接口更新的用户
    User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
    //3.判断预期和实际返回是否为空
    Assert.assertNotNull(user);
    Assert.assertNotNull(result);
  }

  /**
   * 向接口发起请求，处理并返回response信息
   * @param updateUserInfoCase 传递的测试数据
   * @return Integer类型，更新的数据条数
   * @throws IOException
   */
  public Integer getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
    //1.添加请求url，声明post请求
    HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
    //2.1声明定义请求参数，是updateUserCase表中的测试数据
    JSONObject param = new JSONObject(true);
    param.put("id",updateUserInfoCase.getUserId());
    param.put("password",updateUserInfoCase.getPassword());
    param.put("create_time",null);
    param.put("email",updateUserInfoCase.getEmail());
    param.put("username",updateUserInfoCase.getUsername());
    //2.2设置请求头格式，将参数放入请求头
    post.setHeader("content-type","application/json");
    StringEntity entity = new StringEntity(param.toString(),"utf-8");
    post.setEntity(entity);
    //3.1发起post请求，获取response响应
    HttpResponse response = TestConfig.httpClient.execute(post);
    //3.2从response请求头中，获取接口返回的内容
    String result = EntityUtils.toString(response.getEntity(),"utf-8");
    return Integer.parseInt(result);
  }
}