package cn.skio.apis;

import org.testng.annotations.Test;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2019-09-27 10:33
 */
public class PayTest {
  @Test(enabled = false)
//  忽略测试
  public void pay () {
    System.out.println("微信支付成功！");
  }
}
