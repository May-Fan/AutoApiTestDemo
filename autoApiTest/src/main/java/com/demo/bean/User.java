package com.demo.bean;
import lombok.Data;

/**
 * @program: AutoApiTestDemo
 * @description: 与localhost-apiTest中,表user字段对应
 * @author: May
 * @create: 2020-02-04 11:30
 */
@Data
public class User {
  private Integer id;
  private String username;
  private String password;
  private String email;
  private String create_time;

  @Override
  public String toString() {
    return "{"+
            "id:"+id+
            ",username:"+username+
            ",password:"+password+
            ",email:"+email+
            ",create_time:"+create_time+
            "}";
  }
}
