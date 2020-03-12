package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.AddUserCase;
import com.demo.utils.GetUrlUtil;
import com.demo.utils.DatabaseUtil;
import com.demo.utils.HttpClientUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: 新增用户接口测试用例
 * @author: May
 * @create: 2020-02-04 12:37
 */
public class AddUserTest {
  //声明日志变量
  Logger logger = Logger.getLogger(AddUserTest.class);

  @Test
  public void addUser() throws IOException, InterruptedException {

    //1.获取addUserCase表中的测试数据和预期结果
    SqlSession session = DatabaseUtil.getSqlSession();
    AddUserCase addUserCase = session.selectOne("addUserCase",2);
    logger.info("获取到接口地址：" + GetUrlUtil.addUserUrl);
    logger.info("获取到预期值为：" + addUserCase.getExpected());

    //1.传递addUserCase表中的测试数据，获取接口返回的String字符串
    String result = doRequest(addUserCase);
    //1--->2获取新增结果时，接口线程和接口测试线程抢占资源，等待接口线程执行完毕后再判断
    Thread.sleep(3000);
    //2.根据传递给接口的对象，查询user表，获取并打印接口新增的用户
    //User user = session.selectOne("getAddUser",addUserCase);
    //System.out.println(user.toString());
    //3.将预期和接口实际返回值作比较
    Assert.assertEquals(addUserCase.getExpected(),result);
 }
  /**
   * 向接口发起请求，处理并返回response信息
   * @param addUserCase 传递的测试数据
   * @return 返回预期
   * @throws IOException
   */
  public String doRequest(AddUserCase addUserCase) throws IOException {
    //1.声明定义请求参数，是addUserCase表中的测试数据
    JSONObject param = new JSONObject(true);
    param.put("id",addUserCase.getUserId());
    param.put("username",addUserCase.getUsername());
    param.put("password",addUserCase.getPassword());
    param.put("email",addUserCase.getEmail());
    param.put("create_time",addUserCase.getCreate_time());
    //2.传入url，json字符型参数，获取响应
    String result = HttpClientUtil.doPost(GetUrlUtil.addUserUrl,param.toString());
    return result;
  }
}