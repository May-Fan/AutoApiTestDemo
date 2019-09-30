package cn.skio.apis;

import org.testng.annotations.*;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2019-09-27 10:33
 */
public class LoginTest {
  @BeforeTest
  public void beforeTest1() {
    System.out.println("beforeTest运行");
  }

  @BeforeClass
  public void beforeClass1() {
    System.out.println("beforeClass运行");
  }

  @BeforeMethod
  public void beforeMethod() {
    System.out.println("beforeMethod运行");
  }

  @BeforeGroups("client")
  public void beforeGroups() {
    System.out.println("beforeGroups运行");
  }

  @Test(timeOut = 3000)//超过指定运行时长，case运行失败
  public void login() throws InterruptedException {
    System.out.println("登录成功！");
    Thread.sleep(2000);
  }

  @Test(groups = "server")
  public void server1() {
    System.out.println("服务端测试用例1");
  }

  @Test(groups = "server")
  public void server2() {
    System.out.println("服务端测试用例2");
  }

  @Test(groups = "client")
  public void client1() {
    System.out.println("客户端测试用例1");
  }

  @Test(groups = "client")
  public void client2() {
    System.out.println("客户端测试用例2");
  }

  @AfterGroups("client")
  public void afterGroups() {
    System.out.println("afterGroups运行");
  }

  @AfterMethod
  public void afterMethod() {
    System.out.println("afterMethod运行");
  }

  @AfterClass
  public void afterClass1() {
    System.out.println("afterClass运行");
  }

  @AfterTest
  public void afterTest() {
    System.out.println("afterTest运行");
  }
}
