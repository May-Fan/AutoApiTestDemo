package cn.skio.apis.threadDemo;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-01-10 11:03
 */
public class Account {
  private String car_no;
  private double balance;
  public String getCarNo() {
    return this.car_no;
  }
  public double getBalance() {
    return this.balance;
  }
  public void setBalance(double balance) {
    this.balance = balance;
  }

  public Account(String car_no,double balance) {
    this.car_no = car_no;
    this.balance = balance;
  }

  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(this != null && obj.getClass()==Account.class) {
      Account target = (Account) obj;
      return target.getCarNo().equals(car_no);
    }
    return false;
  }
}
