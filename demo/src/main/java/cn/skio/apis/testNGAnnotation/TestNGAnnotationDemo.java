package cn.skio.apis.testNGAnnotation;
import org.testng.annotations.*;

/**
 * @program: AutoApiTestDemo
 * @description: TestNG中常用注解的用法
 * @author: May
 * @create: 2019-09-27 10:33
 */
public class TestNGAnnotationDemo {

  @BeforeTest
  public void beforeTest() {
    System.out.println("BeforeTest注解后，方法在xml文件中的test组件前运行");
  }

  @BeforeClass
  public void beforeClass() {
    System.out.println("BeforeClass注解后，方法在当前所在类之前运行");
  }

  @BeforeMethod
  public void beforeMethod() {
    System.out.println("BeforeMethod注解后，该方法在当前类下所有注解了Test的测试方法之前运行一次");
  }

  @AfterMethod
  public void afterMethod() {
    System.out.println("AfterMethod注解后，该方法在当前类下所有注解了Test的测试方法之后运行一次");
  }

  /**
   * 括号里填写组的名称
   */
  @BeforeGroups("client")
  public void beforeClientGroups() {
    System.out.println("BeforeGroups注解后，该方法在指定了client组的测试方法之前运行");
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
  public void afterClientGroups() {
    System.out.println("AfterGroups注解后，该方法在指定了client组的测试方法之后运行");
  }

  @BeforeGroups("server")
  public void beforeServerGroups() {
    System.out.println("BeforeGroups注解后，该方法在指定了server组的测试方法之前运行");
  }

  @Test(groups = "server")
  public void server1() {
    System.out.println("服务端测试用例1");
  }

  @Test(groups = "server")
  public void server2() {
    System.out.println("服务端测试用例2");
  }

  @AfterGroups("server")
  public void afterServerGroups() {
    System.out.println("AfterGroups注解后，该方法在指定了server组的测试方法之后运行");
  }

  /**
   * 指定运行时长，超时后case运行失败
   */
  @Test(timeOut = 3000)
  public void login() throws InterruptedException {
    System.out.println("success！");
    Thread.sleep(2000);
  }

  /**
   * 忽略测试的注解
   */
  @Test(enabled = false)
  public void ignoreDemo() {
    System.out.println("enabled = false后，该测试方法被忽略，不会运行");
  }

  /**
   * 异常测试的注解：给定预期异常，即可正常往下执行，不会报错
   */
  @Test(expectedExceptions = RuntimeException.class)
  public void runTimeException() {
    System.out.println("异常测试：注解中给定预期异常，即可执行成功");
    throw new RuntimeException();
  }

  @AfterClass
  public void afterClass1() {
    System.out.println("AfterClass注解后，方法在当前所在类之后运行");
  }

  @AfterTest
  public void afterTest() {
    System.out.println("AfterTest注解后，方法在xml文件中的test组件后运行");
  }
}
