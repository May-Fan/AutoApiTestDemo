package cn.skio.apis;

import org.testng.annotations.Test;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2019-09-26 16:52
 */
public class GroupsTest {
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

}
