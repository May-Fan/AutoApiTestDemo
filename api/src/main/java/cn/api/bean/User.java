package cn.api.bean;

import lombok.Data;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-02-04 16:38
 */
@Data
public class User {
  private Integer id;
  private String username;
  private String password;
  private String email;
  private String create_time;
}
