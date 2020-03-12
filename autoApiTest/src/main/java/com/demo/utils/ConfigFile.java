package com.demo.utils;

import com.demo.bean.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-02-04 12:03
 */
public class ConfigFile {
  private static ResourceBundle bundle =
          ResourceBundle.getBundle("application", Locale.CHINA);

  public static String getUrl(InterfaceName interfaceName) {
    String url = bundle.getString("test.url");
    String uri="";
    if (interfaceName.equals(InterfaceName.LOGIN)) {
      uri = bundle.getString("login.uri");
    }else if(interfaceName.equals(InterfaceName.ADDUSER)) {
      uri = bundle.getString("addUser.uri");
    }else if(interfaceName.equals(InterfaceName.UPDATEUSERINFO)) {
      uri = bundle.getString("updateUserInfo.uri");
    } else if(interfaceName.equals(InterfaceName.GETUSERINFO)) {
      uri = bundle.getString("getUserInfo.uri");
    } else if(interfaceName.equals(InterfaceName.GETUSERLIST)) {
      uri = bundle.getString("getUserList.uri");
    }
    return url+uri;
  }
  public static String getHostName(InterfaceName interfaceName) {
    return bundle.getString("test.url");
  }
}
