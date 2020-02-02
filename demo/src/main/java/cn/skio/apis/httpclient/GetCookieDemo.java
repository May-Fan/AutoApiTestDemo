package cn.skio.apis.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @program: AutoApiTestDemo
 * @description: 该类演示了如何在Get方法中，携带和返回cookie信息
 * @author: May
 * @create: 2019-10-14 15:40
 */
public class GetCookieDemo {
  private String url;
  private ResourceBundle bundle;
  //全局变量，第一个Test方法获取到cookie后，后面的用例请求时会自动携带该cookie信息
  private CookieStore cookieStore = new BasicCookieStore();
  //创建一个HttpClient携带cookie信息的实例
  private CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

  @BeforeTest
  public String getUrl() {
    bundle = ResourceBundle.getBundle("application");
    //获取配置文件中的uri
    url = bundle.getString("url");
    return url;
  }

  /**
   * 使用get请求获取接口返回的cookie
   */
  @Test
  public void getCookies() throws IOException {
    HttpGet httpGet = new HttpGet(this.url+"/getCookies");
    HttpResponse response = httpClient.execute(httpGet);

    //获取响应的整体信息，将其转化成string格式并打印
    String result = EntityUtils.toString(response.getEntity(),"utf-8");
    System.out.println(result);

    List<Cookie> cookies = cookieStore.getCookies();
    //打印集合cookies里面的cookie字段名和值
    for(Cookie cookie:cookies) {
      String cookieName = cookie.getName();
      String cookieValue = cookie.getValue();
      System.out.println(cookieName+ " : " +cookieValue);
    }
  }

  /**
   * 使用Get方法，携带cookie信息发送请求
   * 依赖获取cookie的测试方法，通过类成员变量携带cookie信息
   * @throws IOException
   */
  @Test(dependsOnMethods = {"getCookies"})
  public void withCookies() throws IOException {
    //声明一个HttpGet对象，并配置接口url
    HttpGet httpGet_2 = new HttpGet(this.url + "/withCookies");
    //也可在底层请求中配置cookie信息，key-value对之间用;分隔
    //httpGet_2.setHeader("Cookie","age=17;name=1;sex=female");
    HttpResponse response2 = httpClient.execute(httpGet_2);

    //声明并获取返回信息中的状态码
    int statusCode = response2.getStatusLine().getStatusCode();

    //如果状态码为200，说明接口返回数据，打印返回信息
    if (statusCode == 200) {
      String result = EntityUtils.toString(response2.getEntity(), "utf-8");
      System.out.println(result);
    }else{
      System.out.println(statusCode);
    }
  }
}
