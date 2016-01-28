package com.testcases;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    CommonMethods methods = new CommonMethods( driver );
    
    List<Map<String, String>> tests = DataAddApp.getTestDataForPositiveTests();
    
    int testNumber = 1;
    for (Map<String, String> map : tests) {

      Logger.toLog( "Start Test: " + testNumber );
      
      Logger.toLog( "Test Data:" );
      for (String key : map.keySet()) {
        System.out.println(key + " => " + map.get(key));
      }
      
      // Open Add App form      
      methods.pressNewAppAndCheckModalDialog();
      
      // Fill App Form
      methods.fillAppForm(driver, map);
      
      // Click Save button
      driver.findElement(By.linkText("Save")).click();
           
      // Wait "add new app" form closed 
      (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-header")));
      
      // Check results
      this.checkResults(driver, map);
      
      Logger.toLog( "Test: " + testNumber + " complete" );
      
      // Sleep before start new test
      try { Thread.sleep( 1000 ); } 
      catch (InterruptedException e) { e.printStackTrace(); }   
      
      testNumber++;
    }
    
    Logger.toLog( "Test: testAddNewAppPositiveTests...complete" );
  }

  /**
  * Check test results
  * @param driver Web driver for browser
  * @param map test data
  */
  public void checkResults( WebDriver driver, Map<String, String> map )
  {
    String platformType = map.get("platformType");
    String name = map.get("name");
    String appIconFile = map.get("appIconFile");
    
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
  }
  
  @After
  public void tearDown() throws Exception {}
  
}