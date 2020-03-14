package com.demo.testdata;

import com.demo.bean.InterfaceName;
import com.demo.utils.ExcelUtil;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.List;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-03-12 09:48
 */
public class TestData {
  /**
   * 获取excel中测试数据
   * @param
   * @return
   * @throws IOException
   */
  @Deprecated
  public static Object[][] getParam(InterfaceName interfaceName) throws IOException {
    List<Object> li ;
    //从静态方法获取请求登录接口的请求数据
    List list = ExcelUtil.getApiData(interfaceName);
    Object[][] loginData = new Object[list.size()][];
    for(int i=0;i<list.size();i++) {
      li = (List) list.get(i);
      String info = (String) li.get(4);
      String[] a = info.split(",");
      loginData[i]  = new Object[a.length];
      for(int j=0;j<a.length;j++) {
        String value = a[j].split("=")[1];
        loginData[i][j] = value;
      }
    }
    return loginData;
  }

  /**
   * 获取接口参数、url、expect
   * @param interfaceName
   * @return
   * @throws IOException
   */
  public static Object[][] getTestData(InterfaceName interfaceName) {
    List<Object> li ;
    List list = ExcelUtil.getApiData(interfaceName);
    Object[][] testData = new Object[list.size()][3];
    for(int i=0;i<list.size();i++) {
      li = (List) list.get(i);
      String info = (String) li.get(4);
      String[] a = info.split(",");
      String[] param = new String[a.length];
      for(int j=0;j<a.length;j++) {
        param[j] = a[j].split("=")[1];
      }
      testData[i][0] = param;
      testData[i][1] = li.get(3);
      testData[i][2] = li.get(5);
    }
    return testData;
  }
}
