package com.core;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonMethods {
  
  public static void pressNewAppAndCheckModalDialog( WebDriver driver )
  {
    // Open first page with AddApp button
    driver.get( System.getProperty("baseURL") + "/webui-rc/apps/list" );
    
    WebElement buttonNewApp = driver.findElement(By.cssSelector("a[href='" + System.getProperty("baseURL") + "/webui-rc/apps/new']"));
    assertTrue(buttonNewApp.isDisplayed());
    buttonNewApp.click();
    WebDriverWait waitAddNewAppForm = new WebDriverWait(driver, 10); 
    waitAddNewAppForm.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-header")));
  }
  
  // TODO: Fix this method. Checking unique name must be in database query
  public static String getUniqAppName()
  {
    String uniqueAppName = "AppName-";
    
    return uniqueAppName+getUniqueString();
  }
  
  public static String getUniqueString()
  {
    String uniqueString = "";
    //uniqueString += String.valueOf(System.currentTimeMillis()) + "-";
    uniqueString += UUID.randomUUID().toString();
    return uniqueString;
  }
  
}
