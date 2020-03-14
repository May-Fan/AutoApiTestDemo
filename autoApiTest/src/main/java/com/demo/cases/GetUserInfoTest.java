package com.demo.cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.GetUserInfoCase;
import com.demo.bean.User;
import com.demo.utils.GetUrlUtil;
import com.demo.utils.DatabaseUtil;
import com.demo.utils.HttpClientUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: 获取用户信息接口测试用例
 * @author: May
 * @create: 2020-02-04 14:48
 */
public class GetUserInfoTest {
  @Test
  public void getUserInfo() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    //获取userInfoCase表中的测试数据和预期结果
    GetUserInfoCase userInfoCase = session.selectOne("getUserInfoCase", 1);
    System.out.println(userInfoCase.toString());
    System.out.println(GetUrlUtil.getUserInfoUrl);
    //1.传递userInfoCase表中的测试数据，获取接口的返回JSONArray数组
    JSONArray resultJsonArray = setParams(userInfoCase);
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
  public JSONArray setParams(GetUserInfoCase userInfoCase) throws IOException {
    //1.声明定义请求参数，为userInfoCase表中的测试数据
    JSONObject param = new JSONObject();
    param.put("id",userInfoCase.getUserId());
    //2.传入url，json字符型参数，获取响应
    String result = HttpClientUtil.doPost(GetUrlUtil.getUserInfoUrl,param.toString());
    //3.根据json字符串的符号，自动划分数组元素，result="[{"id":1,"username":"彭于晏","password":"123456"}]"
    JSONArray array = JSONArray.parseArray(result);
    return array;
  }
}
