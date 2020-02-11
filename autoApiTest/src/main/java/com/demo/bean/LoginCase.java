package com.demo.bean;

import lombok.Data;

/**
 * @program: AutoApiTestDemo
 * @description: 与localhost-apiTest中,表loginCase字段对应
 * @author: May
 * @create: 2020-02-04 13:04
 */
@Data
public class LoginCase {
  private Integer id;
  private String username;
  private String password;
  private String expected;
}
