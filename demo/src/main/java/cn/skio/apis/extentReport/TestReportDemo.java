package cn.skio.apis.extentReport;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2019-09-30 14:18
 */
public class TestReportDemo {
  @Test
  public void test1() {
    Assert.assertEquals(1,2);
  }

  @Test
  public void test2() {
    Assert.assertEquals(1,1);
  }

  @Test
  public void reportTest() {
    Reporter.log("这是testNG的原生日志");
    throw new RuntimeException("这是自己抛出的运行异常");
  }
}
