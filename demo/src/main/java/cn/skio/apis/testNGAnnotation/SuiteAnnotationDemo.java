package cn.skio.apis.testNGAnnotation;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * @program: AutoApiTestDemo
 * @description: Suite的注解分别代表需要在整个测试套件前和测试套件后运行
 * @author: May
 * @create: 2019-09-27 10:29
 */
public class SuiteAnnotationDemo {

  @BeforeSuite
  public void beforeSuit () {
    System.out.println("BeforeSuit注解后，方法在xml文件中的整个测试套件前运行");
  }

  @AfterSuite
  public void afterSuit () {
    System.out.println("AfterSuit注解后，方法在xml文件中的整个测试套件后运行");
  }
}
