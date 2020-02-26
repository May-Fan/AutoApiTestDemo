package cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-02-04 16:36
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan("cn.api")
public class Application {
  private static ConfigurableApplicationContext context;

  public static void main(String[] args) {
    Application.context =
            SpringApplication.run(Application.class, args);
  }

  @PreDestroy
  public void close() {
    Application.context.close();
  }
}
