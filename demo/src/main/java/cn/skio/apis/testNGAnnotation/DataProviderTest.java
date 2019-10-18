package cn.skio.apis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * @program: AutoApiTestDemo
 * @description: 介绍了DataProvider标签的使用（用于配置/获取测试数据）
 * @author: May
 * @create: 2019-09-29 16:30
 */
public class DataProviderTest {

  //在名称为data的DataProvider注解的方法中，获取测试数据
  @Test(dataProvider = "data")
  public void dataReceiver_1(String name,int age) {
    System.out.println("当前登录用户："+name+"，年龄："+age);
  }

  @Test(dataProvider = "data")
  public void dataReceiver_2(String name,int age) {
    System.out.println("当前登录用户："+name+"，年龄："+age);

    //指定日志在哪个类下面运行
    Logger logger = LogManager.getLogger(DataProviderTest.class.getName());
    logger.debug("使用log4j2打印日志");
  }

  //配置名称为data的测试数据
  @DataProvider(name = "data")
  public Object[][] provider(Method method) {
    Object [][] obj = null;
    if(method.getName().equals("dataReceiver_1")) {
      obj = new Object[][] {
              {"张三",25},
              {"李四",42},
              {"王五",38}
      };
    } else if (method.getName().equals("dataReceiver_2")) {
      obj = new Object[][] {
              {"魏浥城",23},
              {"任然",53},
              {"许清焰",23}
      };
    }
    return obj;
  }
}
