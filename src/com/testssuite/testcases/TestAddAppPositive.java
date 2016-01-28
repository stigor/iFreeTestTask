package com.testssuite.testcases;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.CommonMethods;
import com.core.Logger;
import com.data.DataAddApp;
import com.testsuite.TestSuite;


public class TestAddAppPositive {
  public WebDriver driver;
  
  @Before
  public void setUp() throws Exception { 
    driver = TestSuite.getDriver();
  }

  @Test
  public void testAddNewAppPositive() {
    Logger.toLog( "Test: testAddNewAppPositiveTests..." );
    
    List<Map<String, String>> tests = DataAddApp.getTestDataForPositiveTests();
    
    int testNumber = 1;
    for (Map<String, String> map : tests) {

      Logger.toLog( "Start Test: " + testNumber );
      
      Logger.toLog( "Test Data:" );
      for (String key : map.keySet()) {
        System.out.println(key + " => " + map.get(key));
      }
      
      // Open Add App form      
      CommonMethods.pressNewAppAndCheckModalDialog(driver);
           
      String platformType = map.get("platformType");
      String name = map.get("name");
      String description = map.get("description");
      String appIconFile = map.get("appIconFile");
      String category = map.get("category");
      
      // Get unique name
      if ( null != name && name.equals( "<get_unique_app_name>" ) )
      {
        name = CommonMethods.getUniqAppName();
        Logger.toLog("Unique app name: " + name);
      }
      
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
      
      // Click Save button
      driver.findElement(By.linkText("Save")).click();
      
      // Wait "add new app" form closed 
      (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-header")));
      
      // Check results
      Logger.toLog( "Check Logo..." );
      if ( null != appIconFile )
      {
        assertTrue( "Incorrect Icon", 
            driver.findElement(By.xpath("//img[contains(@src,'?appIconId')]")).isDisplayed() );
      }
      else if ( null != platformType && platformType.equals("iOS") )
      {
        assertTrue( "Incorrect Icon", 
            driver.findElement(By.xpath("//img[contains(@src,'app_ios.png')]")).isDisplayed() );        
      }
      else
      {
        assertTrue( "Incorrect Icon", 
            driver.findElement(By.xpath("//img[contains(@src,'app_android.png')]")).isDisplayed() );        
      }
      Logger.toLog( "Check Logo...OK" );
      
      Logger.toLog( "Check App name..." );
      String appNameAfterSave = driver.findElement(By.xpath("//div[contains(@class,'page-header')]/h1")).getText();
      assertEquals("Incorrect app name", appNameAfterSave, name);
      Logger.toLog( "Check App name...OK" );
      
      Logger.toLog( "Check Banner Tab..." );
      assertTrue( "Tab Banner doesn't show", 
          driver.findElement(By.xpath("//span[@alt-title='Banner']")).isDisplayed() );
      assertTrue( "Tab Banner doesn't enable", 
          driver.findElement(By.xpath("//span[@alt-title='Banner']")).isEnabled() );
      assertTrue( "Banner title doesn't show",
          driver.findElement(By.xpath("//span[contains(text(),'Banner')]")).isDisplayed() );
      Logger.toLog( "Check Banner Tab...OK" );
      
      Logger.toLog( "Check Fullscreen Tab..." );
      WebElement fullScreenTab = driver.findElement(By.xpath("//span[@alt-title='Fullscreen']")); 
      assertTrue( "Tab Fullscreen doesn't show", fullScreenTab.isDisplayed() );
      fullScreenTab.findElement(By.xpath("..")).click();
      assertTrue( "Tab Fullscreen doesn't enable", fullScreenTab.isEnabled() );
      assertTrue( "Fullscreen title doesn't show",
          driver.findElement(By.xpath("//span[contains(text(),'Fullscreen')]")).isDisplayed() );
      Logger.toLog( "Check Fullscreen Tab...OK" );
      
      Logger.toLog( "Check Video Tab..." );
      WebElement VideoTab = driver.findElement(By.xpath("//span[@alt-title='Video']")); 
      assertTrue( "Tab Video doesn't show", VideoTab.isDisplayed() );
      VideoTab.findElement(By.xpath("..")).click();
      assertTrue( "Tab Video doesn't enable", VideoTab.isEnabled() );
      assertTrue( "Video title doesn't show",
          driver.findElement(By.xpath("//span[contains(text(),'Video')]")).isDisplayed() );
      Logger.toLog( "Check Video Tab...OK" );
      
      // Sleep before start new test
      try { Thread.sleep( 1000 ); } 
      catch (InterruptedException e) { e.printStackTrace(); }   
      
      testNumber++;
    }
    
    Logger.toLog( "Test: testAddNewAppPositiveTests...complete" );
  }
  
  @After
  public void tearDown() throws Exception {}
  
}