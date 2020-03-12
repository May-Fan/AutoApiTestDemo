package com.demo.testdata;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: AutoApiTestDemo
 * @description: 获取excel表中测试数据
 * @author: May
 * @create: 2020-03-09 18:30
 */
public class ExcelUtil {
  /**
   * 根据接口名称
   * @param apiName
   * @return
   * @throws IOException
   */
  public static List getApiData(String apiName) throws IOException {
    List list = getExcelData("/Users/fanlilin/workspace/autoApiTest/AutoApiTestDemo/autoApiTest/src/main/java/com/demo/testdata","数据驱动测试用例.xlsx");
    List<Object> li;
    List loginList =  new ArrayList();
    List addUserList = new ArrayList();
    List updateList = new ArrayList();
    List getUserList = new ArrayList();
    for(Object obj:list) {
      li = (List) obj;
      if(li.get(1).equals("Login")) {
        loginList.add(li);
      } else if(li.get(1).equals("AddUser")) {
        addUserList.add(li);
      } else if(li.get(1).equals("UpdateUserInfo")) {
        updateList.add(li);
      }else if(li.get(1).equals("GetUserList")) {
        getUserList.add(li);
      }
    }

    switch (apiName) {
      case "Login":
        return loginList;
      case "AddUser":
        return addUserList;
      case "UpdateUserInfo":
        return updateList;
      case "GetUserList":
        return getUserList;
      default:
        return null;
    }

  }

  /**
   * 获取excel中的数据，储存至List二维集合并返回
   * @param filePath 文件绝对路径
   * @param fileName 文件名称
   * @return List二维集合
   * @throws IOException
   */
  public static List getExcelData(String filePath,String fileName) throws IOException {
    Workbook workbook = null;
    Sheet sheet;
    Row row;
    Cell cell;
    String str;
    List list = new ArrayList();
    File file = new File(filePath + "/" + fileName);
    //1.根据后缀名，获取工作表
    FileInputStream inStr = new FileInputStream(file);
    try {
      workbook = getWorkbook(inStr,fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //2.解析工作表中的内容，添加至集合；
    for (int i=0;i<workbook.getNumberOfSheets();i++) {
      sheet = workbook.getSheetAt(i);
      if(sheet == null) {
        continue;
      }
      for(int j=sheet.getFirstRowNum()+1;j<sheet.getLastRowNum()+1;j++) {
        row = sheet.getRow(j);
        if(row == null) {
          continue;
        }
        List<Object> li = new ArrayList<>();
        for(int x = row.getFirstCellNum();x<row.getLastCellNum();x++) {
          cell = row.getCell(x);
          if(cell.getCellType()==CellType.STRING||cell.getCellType()==CellType.BLANK) {
            str = cell.getStringCellValue();
            if(str.equals("")) {
              str = null;
            }
          }else {
            str = null;
          }
          li.add(str);
        }
        list.add(li);
      }
    }
    workbook.close();
    return list;
  }

  /**
   * 根据后缀名判断excel版本，使用不同的工具类
   * @param inStr    工作流
   * @param fileName 文件名
   * @return 返回解析后的工作表
   * @throws Exception 抛出文件格式错误异常
   */
  private static Workbook getWorkbook(FileInputStream inStr, String fileName)  {
    String fileType = fileName.substring(fileName.lastIndexOf("."));
    Workbook workbook = null;
    if (fileType.equals(".xls")) {
      try {
        workbook = new HSSFWorkbook(inStr);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (fileType.equals(".xlsx")) {
      try {
        workbook = new XSSFWorkbook(inStr);
      }catch (Exception e) {
        e.printStackTrace();
      }
    } else {
//      logger.error("文件后缀名错误！");
      System.out.println("后缀名错误，请上传excel格式的文件！");
      return null;
    }
    if (workbook == null) {
      System.out.println("excel内容不能为空！");
      return null;
    }
    return workbook;
  }
}
