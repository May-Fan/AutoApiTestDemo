package com.demo.cases;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.AddUserCase;
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
 * @description: 新增用户接口测试用例
 * @author: May
 * @create: 2020-02-04 12:37
 */
public class AddUserTest {

  @Test(dependsOnGroups = "login",description = "添加用户接口测试")
  public void addUser() throws IOException, InterruptedException {
    SqlSession session = DatabaseUtil.getSqlSession();
    AddUserCase addUserCase = session.selectOne("addUserCase",1);
    System.out.println(addUserCase.toString());
    System.out.println(TestConfig.addUserUrl);

    //1.传递addUserCase表中的测试数据，获取接口返回的String字符串
    String result = getResult(addUserCase);
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
  public String getResult(AddUserCase addUserCase) throws IOException {
    //1.声明post请求，放请求url
    HttpPost post = new HttpPost(TestConfig.addUserUrl);
    //2.1声明定义请求参数，是addUserCase表中的测试数据
    JSONObject param = new JSONObject(true);
    param.put("id",addUserCase.getUserId());
    param.put("username",addUserCase.getUsername());
    param.put("password",addUserCase.getPassword());
    param.put("email",addUserCase.getEmail());
    param.put("create_time",addUserCase.getCreate_time());

    //2.2设置请求头格式，将参数放入请求头
    post.setHeader("content-type","application/json");
    StringEntity entity = new StringEntity(param.toString(),"utf-8");
    post.setEntity(entity);
    //配置cookies
//    CloseableHttpClient httpClient = HttpClients.custom()
//            .setDefaultCookieStore(TestConfig.cookieStore).build();

    //3.1发起post请求，获取response响应
    HttpResponse response = TestConfig.httpClient.execute(post);
    //3.2从response请求头中，获取接口返回的内容
    String result = EntityUtils.toString(response.getEntity(),"utf-8");
    return result;
  }

}