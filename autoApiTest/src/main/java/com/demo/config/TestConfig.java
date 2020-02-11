package com.demo.config;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-02-04 11:56
 */
public class TestConfig {
  public static String loginUrl;
  public static String addUserUrl;
  public static String updateUserInfoUrl;
  public static String getUserInfoUrl;
  public static String getUserListUrl;

  public static CloseableHttpClient httpClient;
  public static CookieStore cookieStore;
}
