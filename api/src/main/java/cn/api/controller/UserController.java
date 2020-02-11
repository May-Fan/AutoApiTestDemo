package cn.api.controller;

import cn.api.bean.LoginUser;
import cn.api.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @program: AutoApiTestDemo
 * @description: 访问localhost:6868/swagger-ui.html
 * @author: May
 * @create: 2020-02-04 16:48
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Api(value = "v1",tags = "用户管理系统")
public class UserController {
  @Autowired
  private SqlSessionTemplate template;

  @PostMapping("/login")
  @ApiOperation(value = "登录接口",httpMethod = "POST")
  public Boolean login(HttpServletResponse response, @RequestBody LoginUser loginUser) {
    Integer isExist = template.selectOne("selectUserByName",loginUser);
    Cookie cookie = new Cookie("login","true");
    response.addCookie(cookie);
    if(!isExist.equals(0)) {
      log.info("可查询到当前用户名:"+loginUser.getUsername());
      return true;
    }
    return false;
  }
  @PostMapping("/addUser")
  @ApiOperation(value = "新增用户接口",httpMethod = "POST")
  public Boolean addUser(HttpServletRequest request,@RequestBody User user) {
    int result = 0;
    if(verifyCookies(request)==true) {
      result = template.insert("addUser",user);
    }
    if(result!=0) {
      log.info("新增用户："+user.getUsername()+"成功！");
      return true;
    }
    log.info("新增用户失败！");
    return false;
  }

  @PostMapping("/updateUserInfo")
  @ApiOperation(value = "更新用户接口",httpMethod = "POST")
  public Integer updateUser(HttpServletRequest request,@RequestBody User user) {
    int result = 0;
    if(verifyCookies(request)==true) {
      result = template.update("updateUser",user);
      log.info("更新成功，更新数据为"+result+"条");
      return result;
    }else {
      log.info("修改用户失败！");
      return null;
    }
  }

  @PostMapping("/getUserList")
  @ApiOperation(value = "获取用户列表接口",httpMethod = "POST")
  public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user) {
    if(verifyCookies(request)==true) {
      List<User> userList = template.selectList("getUserList",user);
      log.info("可查询到"+userList.size()+"个用户");
      return userList;
    }else {
      return null;
    }
  }

  public Boolean verifyCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if(Objects.isNull(cookies)) {
      log.info("cookies为空");
      return false;
    }
    for(Cookie c:cookies) {
      if(c.getName().equals("login")&&
              c.getValue().equals("true")) {
        log.info("cookies验证通过");
        return true;
      }
    }
    return false;
  }
}
