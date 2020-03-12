package com.demo.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.GetUserListCase;
import com.demo.bean.User;
import com.demo.utils.GetUrlUtil;
import com.demo.utils.DatabaseUtil;
import com.demo.utils.HttpClientUtil;
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
  @Test
  public void getUserList() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    //获取userListCase表中的测试数据和预期结果
    GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
    System.out.println(getUserListCase.toString());
    System.out.println(GetUrlUtil.getUserListUrl);
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
    //1.声明定义请求参数，为userListCase表中的测试数据
    JSONObject param = new JSONObject();
    param.put("username",getUserListCase.getUsername());
    param.put("email",getUserListCase.getEmail());
    //2.传入url，json字符型参数，获取响应
    String result = HttpClientUtil.doPost(GetUrlUtil.getUserListUrl,param.toString());
    //3.将其转换为json数组
    JSONArray jsonArray = JSONArray.parseArray(result);
    return jsonArray;
  }
}
