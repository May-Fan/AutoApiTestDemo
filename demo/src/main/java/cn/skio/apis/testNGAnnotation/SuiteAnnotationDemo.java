package cn.skio.apis.testNGAnnotation;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2019-09-27 10:29
 */
public class SuiteConfig {

  @BeforeSuite
  public void beforeSuit () {
    System.out.println("beforeSuit运行");
  }

  @AfterSuite
  public void afterSuit () {
    System.out.println("afterSuit运行");
  }
}
