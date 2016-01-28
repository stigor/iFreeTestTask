package com.core;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonMethods {
  
  public WebDriver driver;
  
  public CommonMethods( WebDriver NewDriver )
  {
    this.driver = NewDriver;
    return;
  }
  
  /**
   * Open "Add New App" form
   */
  public void pressNewAppAndCheckModalDialog()
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
  
  /**
   * Get unique string (UUID)
   * @return String
   */
  public static String getUniqueString()
  {
    String uniqueString = "";
    //uniqueString += String.valueOf(System.currentTimeMillis()) + "-";
    uniqueString += UUID.randomUUID().toString();
    return uniqueString;
  }
 
  /**
   * Fill "Add New App" form
   * @param driver Web driver for browser
   * @param map test data for filling
   */
  public void fillAppForm(WebDriver driver, Map<String, String> map)
  {
    String platformType = map.get("platformType");
    String name = map.get("name");
    String description = map.get("description");
    String appIconFile = map.get("appIconFile");
    String category = map.get("category");
    
    Logger.toLog("Unique app name: " + name);
    
    // Fill platformType field
    if ( null != platformType )
    {
      new Select(driver.findElement(By.id("platformType"))).selectByVisibleText(platformType);
    }
    
    // Fill name field
    if ( null != name )
    {
      driver.findElement(By.id("name")).clear();
      driver.findElement(By.id("name")).sendKeys(name);
    }
    
    // Fill description field
    if ( null != description )
    {
      driver.findElement(By.id("description")).sendKeys(description);
    }
    
    // Fill app icon
    if ( null != appIconFile )
    {
      String pathToIcon = System.getProperty("rootPath") + File.separator + "src" 
                                                         + File.separator + "com"
                                                         + File.separator + "data"
                                                         + File.separator + "icons"
                                                         + File.separator + appIconFile;
      
      Logger.toLog("Icon path: " + pathToIcon);
      if ( System.getProperty("webdriver").equals("firefox"))
      {
        driver.findElement(By.name("appIconFile")).click();
        StringSelection stringSelection = new StringSelection(pathToIcon);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        Robot robot;
        try {
          robot = new Robot();
          robot.keyPress(KeyEvent.VK_CONTROL);
          robot.keyPress(KeyEvent.VK_V);
          robot.keyRelease(KeyEvent.VK_V);
          robot.keyRelease(KeyEvent.VK_CONTROL);
          try { Thread.sleep( 1000 ); } 
          catch (InterruptedException e) { e.printStackTrace(); }
          robot.keyPress(KeyEvent.VK_ENTER);
          robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
          e.printStackTrace();
        }

      }
      else
      {
        driver.findElement(By.name("appIconFile")).clear();
        driver.findElement(By.name("appIconFile")).sendKeys(pathToIcon);
      }
    }
    
    // Fill category
    if ( null != category )
    {
      List<WebElement> categoryElements = driver.findElements(By.cssSelector("span.tree-title"));
      for (WebElement oneCategory : categoryElements) {
        if ( oneCategory.getText().equals(category) )
        {
          WebElement parent = oneCategory.findElement(By.xpath(".."));
          parent.findElement(By.cssSelector("span.tree-checkbox")).click();
          break;
        }
      }
    }      
  }
}
