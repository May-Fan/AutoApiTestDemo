package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.UpdateUserInfoCase;
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
 * @description: 更新用户数据接口测试用例
 * @author: May
 * @create: 2020-02-04 14:10
 */
public class UpdateUserInfoTest {
  @Test
  public void updateUserInfo() throws IOException {
    SqlSession session = DatabaseUtil.getSqlSession();
    UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
    System.out.println(updateUserInfoCase.toString());
    System.out.println(GetUrlUtil.updateUserInfoUrl);

    //1.传递updateUserCase表中的测试数据，获取接口返回的Integer字符串
    int result = setParams(updateUserInfoCase);
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
  public Integer setParams(UpdateUserInfoCase updateUserInfoCase) throws IOException {
    //1.声明定义请求参数，是updateUserCase表中的测试数据
    JSONObject param = new JSONObject(true);
    param.put("id",updateUserInfoCase.getUserId());
    param.put("password",updateUserInfoCase.getPassword());
    param.put("create_time",null);
    param.put("email",updateUserInfoCase.getEmail());
    param.put("username",updateUserInfoCase.getUsername());

    String result = HttpClientUtil.doPost(GetUrlUtil.updateUserInfoUrl,param.toString());
    return Integer.parseInt(result);
  }
}
