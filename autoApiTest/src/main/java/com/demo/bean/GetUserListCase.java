package com.demo.bean;

import lombok.Data;

/**
 * @program: AutoApiTestDemo
 * @description: 与localhost-apiTest中,表getUserListCase字段对应
 * @author: May
 * @create: 2020-02-04 11:43
 */
@Data
public class GetUserListCase {
  private Integer id;
  private String username;
  private String email;
  private String expected;
}
