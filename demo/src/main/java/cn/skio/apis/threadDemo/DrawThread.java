package cn.skio.apis.threadDemo;

import org.testng.annotations.Test;

import java.util.*;

/**
 * @program: AutoApiTestDemo
 * @description:
 * @author: May
 * @create: 2020-01-10 11:06
 */
public class DrawThread{

  public static void main(String[] args) {
    Integer[] arr = {1,3,2,3,4,7,8,4,5,9,7,6,9,8,0};
    List<Integer> list =new ArrayList<>();
    Integer j = arr.length;
    for(int i=0;i<arr.length;i++) {
      if(!list.contains(arr[i])) {
        list.add(arr[i]);
      }
    }
    System.out.println(list);
  }
  @Test
  public void test() {
    List<String> list = new ArrayList<>();
    list.add("锦");
    list.add("衣");
    list.add("之");
    list.add("下");
    Iterator it = list.iterator();
    while(it.hasNext()) {
      System.out.println((String) it.next());
      it.remove();
    }
  }
  @Test
  public void transfer() {
    String[] strArr = {"锦","衣","之","下"};
    List<String> list = Arrays.asList(strArr);
    System.out.println(list);//集合可以直接打印，打印时调用toString方法

    String[] strArr2 = list.toArray(new String[list.size()]);
    System.out.println(Arrays.toString(strArr2));//数组打印的是hashCode，Object未重写toString方法
  }
}
