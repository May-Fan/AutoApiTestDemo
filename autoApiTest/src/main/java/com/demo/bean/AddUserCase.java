package com.demo.bean;

import lombok.Data;

/**
 * @program: AutoApiTestDemo
 * @description: 与localhost-apiTest中,表addUserCase字段对应
 * @author: May
 * @create: 2020-02-04 11:40
 */
@Data
public class AddUserCase {
  private Integer id;
  private Integer userId;
  private String username;
  private String password;
  private String email;
  private String create_time;
  private String expected;
}
