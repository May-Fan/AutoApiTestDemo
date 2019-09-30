package cn.skio.apis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2019-09-29 16:30
 */
public class DataProviderTest {
  @Test(dataProvider = "data",enabled = false)
  public void reciever1(String name,int age) {
    System.out.println("当前登录用户为"+name+"，年龄是"+age);
  }

  @Test(dataProvider = "data")
  public void reciever2(String name,int age) {
    Logger logger = LogManager.getLogger(DataProviderTest.class.getName());
//    指定日志在哪个类下面运行
    System.out.println("当前登录用户为"+name+"，年龄是"+age);
    logger.debug("第一次使用log4j2打印日志");
  }

  @DataProvider(name = "data")
  public Object[][] provider(Method method) {
    Object [][] obj = null;
    if(method.getName().equals("reciever1")) {
      obj = new Object[][] {
              {"张三",25},
              {"李四",42},
              {"王五",38}
      };
    } else if (method.getName().equals("reciever2")) {
      obj = new Object[][] {
              {"魏浥城",23},
              {"任然",53},
              {"许清焰",23}
      };
    }
    return obj;
  }
}
