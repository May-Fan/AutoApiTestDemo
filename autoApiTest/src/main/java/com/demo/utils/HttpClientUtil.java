package com.demo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @program: AutoApiTestDemo
 * @description: 封装HttpClient中常用方法
 * @author: May
 * @create: 2020-03-03 11:25
 */
public class HttpClientUtil {
  private static CloseableHttpClient httpClient;
  static {
    httpClient = HttpClients.createDefault();
  }

  /**
   * 执行post方法，获取String类型的返回值
   * @param url api测试路径
   * @param jsonParams json字符格式的参数
   * @param headers 请求头
   * @return String类型返回值
   * @throws IOException
   */
  public static String doPost(String url,String jsonParams,
                              Map<String,? extends Object> headers) throws IOException {
    //1.添加请求url，声明post请求
    HttpPost post = new HttpPost(url);
    //2.处理传入的json字符串参数
    if(jsonParams == null) {
      jsonParams="";
    }
    StringEntity entity = new StringEntity(jsonParams,"utf-8");
    post.setEntity(entity);
    //3.配置传入的header头信息
    post.setHeader("Content-Type","application/json");
    if(headers != null) {
      for (Map.Entry<String, ? extends Object> header : headers.entrySet()) {
        if (header.getValue() != null) {
          post.addHeader(header.getKey(), header.getValue()
                  .toString());
        }
      }
    }
    //4.执行post方法
    HttpResponse httpResponse = httpClient.execute(post);
    //5.解析返回值
    HttpEntity respEntity = httpResponse.getEntity();
    String resultStr = null;
    if(respEntity != null) {
      resultStr = EntityUtils.toString(respEntity);
      EntityUtils.consume(respEntity);
    } else {
      resultStr = "HttpStatus:" +
              httpResponse.getStatusLine().getStatusCode();
    }
    return resultStr;
  }
  public static String doPost(String url,String jsonParams) throws IOException {
    return doPost(url,jsonParams,null);
  }

  /**
   * 执行get方法，获取String类型的返回值
   * @param url api测试路径
   * @param params json字符格式的参数
   * @param heasers 请求头
   * @return String类型返回值
   * @throws IOException
   */
  public static String doGet(String url,
                             Map<String,? extends Object> params,Map<String,Object> heasers) throws IOException {
    //1.根据参数形成尾部url，声明get请求并访问
    if(params != null) {
      url = url + getTailUrl(params);
      //System.out.println("临时数据：url=" + url);
    }
    HttpGet httpGet = new HttpGet(url);
    //2.设置header请求头信息
    if(heasers != null) {
      for(Map.Entry<String,Object> header:heasers.entrySet()) {
        httpGet.addHeader(header.getKey(),header.getValue().toString());
      }
    }
    //3.发送get请求
    HttpResponse response = httpClient.execute(httpGet);
    //4.解析返回值
    HttpEntity respEntity = response.getEntity();
    String resultStr = null;
    if(respEntity != null) {
      resultStr = EntityUtils.toString(respEntity);
      EntityUtils.consume(respEntity);
    } else {
      resultStr = "HttpStatus:" +
              response.getStatusLine().getStatusCode();
    }
    return resultStr;
  }
  public static String doGet(String url,
                             Map<String,? extends Object> params) throws IOException {
    return doGet(url,params,null);
  }
  /**
   * 执行patch方法，获取String类型的返回值
   * @param url 传入的url
   * @param jsonParam json类型字符串
   * @param headers 请求头
   * @return String类型返回值
   * @throws IOException
   */
  public static String doPatch(String url,String jsonParam,
                               Map<String,? extends Object> headers) throws IOException {
    String resultStr = "";
    //1.声明一个patch类型的对象，并传入url
    HttpPatch httpPatch = new HttpPatch(url);
    //2.处理传入的json字符串类型参数
    if(jsonParam == null) {
      jsonParam = "";
    }
    StringEntity entity = new StringEntity(jsonParam, "UTF-8");
    httpPatch.setEntity(entity);
    //3.配置header头信息
    httpPatch.setHeader("Content-Type","application/json");
    httpPatch.setHeader("Charset", "UTF-8");
    httpPatch.setHeader("Accept", "application/json");
    httpPatch.setHeader("Accept-Charset", "UTF-8");
    if (headers != null) {
      for (Map.Entry<String, ? extends Object> header : headers.entrySet()) {
        if (header.getValue() != null) {
          httpPatch.addHeader(header.getKey(), header.getValue().toString());
        }
      }
    }
    //4.执行patch方法
    HttpResponse httpResponse = httpClient.execute(httpPatch);
    //5.解析返回值
    HttpEntity respEntity = httpResponse.getEntity();
    if (respEntity != null) {
      resultStr = EntityUtils.toString(respEntity);
      EntityUtils.consume(respEntity);
    } else {
      resultStr = "HttpStatus:" +
              httpResponse.getStatusLine().getStatusCode();
    }
    return resultStr;
  }

  public static String doPatch(String url,String jsonParam) throws IOException {
    return doPatch(url,jsonParam,null);
  }

  /**
   * 执行delete方法，获取String类型的返回值
   * @param url 传入的url
   * @param params Map key-value键值对类型参数
   * @param headers 请求头
   * @return String类型返回值
   * @throws IOException
   */
  public static String doDelete(String url,Map<String,? extends Object> params,
                                Map<String,? extends Object> headers) throws IOException {
    String resultStr = null;
    //1.声明一个delete对象
    HttpDelete httpDelete = new HttpDelete(url);
    //2.配置传入的key-value类型参数
    if(params != null) {
      url = url + getTailUrl(params);
    }
    //3.配置header请求头
    if (headers != null) {
      for (Map.Entry<String, ? extends Object> header : headers.entrySet()) {
        if (header.getValue() != null) {
          httpDelete.addHeader(header.getKey(), header.getValue().toString());
        }
      }
    }
    //4.发送delete请求
    HttpResponse response = httpClient.execute(httpDelete);
    //5.解析返回值
    HttpEntity respEntity = response.getEntity();
    if(respEntity != null) {
      resultStr = EntityUtils.toString(respEntity);
      EntityUtils.consume(respEntity);
    } else {
      resultStr = "HttpStatus:" +
              response.getStatusLine().getStatusCode();
    }
    return resultStr;
  }
  public static String doDelete(String url,Map<String,? extends Object> params) throws IOException {
    return doDelete(url,params,null);
  }

  /**
   * 执行put方法，获取String类型的返回值
   * @param url
   * @param jsonParams
   * @param headers
   * @return
   * @throws IOException
   */
  public static String doPut(String url,String jsonParams,
                             Map<String, ? extends Object> headers) throws IOException {
    //1.添加请求url，声明post请求
    HttpPut put = new HttpPut(url);
    //2.处理传入的json字符串参数
    if(jsonParams == null) {
      jsonParams="";
    }
    StringEntity entity = new StringEntity(jsonParams,"utf-8");
    put.setEntity(entity);
    //3.配置传入的header头信息
    put.setHeader("Content-Type","application/json");
    if(headers != null) {
      for (Map.Entry<String, ? extends Object> header : headers.entrySet()) {
        if (header.getValue() != null) {
          put.addHeader(header.getKey(), header.getValue()
                  .toString());
        }
      }
    }
    //4.执行post方法
    HttpResponse httpResponse = httpClient.execute(put);
    //5.解析返回值
    HttpEntity respEntity = httpResponse.getEntity();
    String resultStr = null;
    if(respEntity != null) {
      resultStr = EntityUtils.toString(respEntity);
      EntityUtils.consume(respEntity);
    } else {
      resultStr = "HttpStatus:" +
              httpResponse.getStatusLine().getStatusCode();
    }
    return resultStr;
  }
  public static String doPut(String url,String jsonParams) throws IOException {
    return doPut(url, jsonParams,null);
  }
  /**
   * 根据传入的key-value键值对，拼接尾部url
   * @param params 传入的键值对
   * @return 返回字符类型的url
   */
  public static String getTailUrl(Map<String,? extends Object> params) {
    if(params==null || params.size()==0) {

      return "";
    }
    StringBuffer tailUrl = new StringBuffer("?");

    if(params!=null && !params.isEmpty()) {
      for(Map.Entry<String,? extends Object> entry : params.entrySet()) {
        String key = entry.getKey();
        Object value = entry.getValue();

        if(value == null) {
        } else if (!value.getClass().isArray()) {
          try {
            value = URLEncoder.encode(value.toString(),"UTF-8");
          } catch (UnsupportedEncodingException e) {
            throw  new RuntimeException(e);
          }
          tailUrl.append(key).append("=").append(value).append("&");
        } else {//如为数组，特殊处理
          //获取数组长度
          int len = Array.getLength(value);
          for(int i=0;i<len;i++) {
            Object element = Array.get(value,i);
            if(element != null) {
              tailUrl.append(new BasicNameValuePair(key,element.toString()));
            } else {
              tailUrl.append(new BasicNameValuePair(key,null));
            }
            tailUrl.append("&");
          }
        }
      }
    }
    String  paramStr = tailUrl.toString();
    //去除尾部的“&”
    paramStr = paramStr.substring(0,paramStr.length()-1);
    return paramStr;
  }

}
