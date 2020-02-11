package com.demo.bean;

import lombok.Data;

/**
 * @program: AutoApiTestDemo
 * @description: 与localhost-apiTest中,表getUserInfoCase字段对应
 * @author: May
 * @create: 2020-02-04 11:42
 */
@Data
public class GetUserInfoCase {
  private Integer id;
  private Integer userId;
  private String expected;
}
