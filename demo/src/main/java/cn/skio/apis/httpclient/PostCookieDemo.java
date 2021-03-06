package cn.skio.apis.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @program: AutoApiTestDemo
 * @description: 该类演示了如何在Post方法中，携带和返回cookie信息
 * @author: May
 * @create: 2019-10-16 09:47
 */
public class PostCookieDemo {
  private String url;
  private ResourceBundle bundle;
  private CookieStore cookieStore = new BasicCookieStore();
  //创建一个HttpClient携带cookie信息的实例
  private CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

  @BeforeTest
  public void getUrl() {
    bundle = ResourceBundle.getBundle("application", Locale.CHINA);
    //获取配置文件中的uri
    url = bundle.getString("url");
  }
  /**
   * 使用get请求获取接口返回的cookie
   */
  @Test
  public void getCookies() throws IOException {
    HttpGet httpGet = new HttpGet(this.url+bundle.getString("getCookies"));
    HttpResponse response = httpClient.execute(httpGet);

    //获取响应的整体信息，将其转化成string格式并打印
    String result = EntityUtils.toString(response.getEntity(),"utf-8");
    System.out.println(result);
    List<Cookie> cookies = cookieStore.getCookies();
  }

  @Test(dependsOnMethods = {"getCookies"})
  public void withCookiesPost () throws IOException {
    //声明一个HttpPost对象
    HttpPost httpPost = new HttpPost(url + "/withCookiesPost");
    //声明JSONObject对象并添加参数
    JSONObject param = new JSONObject();
    param.put("car_no", "A");
    param.put("vin", "LDP");
    StringEntity entity = new StringEntity(param.toString(), "utf-8");
    httpPost.setEntity(entity);
    //为HttpPost对象设置请求头信息
    httpPost.setHeader("content-type", "application/json");
    //执行post方法并打印返回信息
    HttpResponse httpResponse = httpClient.execute(httpPost);

    String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
    System.out.println(result);

    JSONObject resultJson =new JSONObject(result);
    String msg = resultJson.getString("msg");
    Assert.assertEquals(msg,"success");
  }

}
