package cn.skio.apis.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @program: AutoApiTestDemo
 * @description: A demo for using http client.
 * @author: May
 * @create: 2020-02-01 21:23
 */
public class HttpDemo {
  @Test
  public void get() throws IOException {
    HttpGet httpGet = new HttpGet("http://www.baidu.com");
    HttpClient httpClient = HttpClients.createDefault();
    HttpResponse response = httpClient.execute(httpGet);
    //将响应体的entity部分转化为String
    String result = EntityUtils.toString(response.getEntity(),"utf-8");
    System.out.println(result);
  }
}
