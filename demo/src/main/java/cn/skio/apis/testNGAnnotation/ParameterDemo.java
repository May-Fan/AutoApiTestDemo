package cn.skio.apis.testNGAnnotation;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @program: AutoApiTestDemo
 * @description: A demo for how to use parameter annotation
 * @author: May
 * @create: 2020-02-01 10:53
 */
public class ParameterDemo {
  /**
   * 参数来源于xml的使用方法
   * @param name xml文件中与class同级，定义的parameter标签，name="name"
   * @param age xml文件中与class同级定，义的parameter标签，name="age"
   */
  @Test
  @Parameters({"name","age"})
  public void param(String name,String age) {
    System.out.println(name+"的年龄是："+age);
  }
}
