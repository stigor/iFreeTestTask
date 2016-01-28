package com.testcases;

import java.util.List;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.CommonMethods;
import com.core.Logger;
import com.data.DataAddApp;
import com.testsuite.TestSuite;


public class TestAddAppNegative {
  
  public WebDriver driver;
  
  @Before
  public void setUp() throws Exception {
    driver = TestSuite.getDriver();
  }
  
  @Test
  public void testAddNewAppNegative() {
      Logger.toLog( "Test: testAddNewAppNegativeTests..." );
      CommonMethods methods = new CommonMethods( driver );
      
      List<Map<String, String>> tests = DataAddApp.getTestDataForNegativeTests();
      
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
       
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-header")));
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("validationModal")));

        // Check results
        this.checkResults(driver, map);
        
        driver.findElement(By.linkText("OK")).click();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-header")));
               
        driver.findElement(By.cssSelector("button.close")).click();
        // Wait "add new app" form closed 
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-header")));
        
        // Sleep before start new test
        try { Thread.sleep( 1000 ); } 
        catch (InterruptedException e) { e.printStackTrace(); }   
        
        testNumber++;
      }
      
      Logger.toLog( "Test: testAddNewAppNegativeTests...complete" );
    }

  /**
  * Check test results
  * @param driver Web driver for browser
  * @param map test data
  */
  public void checkResults( WebDriver driver, Map<String, String> map )
  {
    String name = map.get("name");
    String appIconFile = map.get("appIconFile");
    String category = map.get("category");
  
    if ( null == name || null == category )
    {
      Logger.toLog( "Check 'Please fill in these fields' message..." );
      assertTrue( "No 'Please fill in these fields' message",
        driver.findElement(By.xpath("//b[contains(text(),'Please fill in these fields:')]")).isDisplayed() );
      Logger.toLog( "Check 'Please fill in these fields' message...OK" );
    }
    
    if ( null == name )
    {
      Logger.toLog( "Check empty App name..." );
      assertTrue( "No error message about empty name field",
          driver.findElement(By.xpath("//li[contains(text(),'Empty adPlace name field!')]")).isDisplayed() );
      Logger.toLog( "Check empty App name...OK" );
    }
    
    if ( null == category )
    {
      Logger.toLog( "Check empty category..." );
      assertTrue( "No error message about empty category field",
          driver.findElement(By.xpath("//li[contains(text(),'Empty category field!')]")).isDisplayed() );
      Logger.toLog( "Check empty category...OK" );
    }
    
    if ( null != appIconFile && appIconFile.equals("icon256x256.png") )
    {
      Logger.toLog( "Check incorrect image message..." );
      assertTrue( "No error message about incorrect image size",
          driver.findElement(By.xpath("//div[contains(text(),'Image must have size up to 200x200px')]")).isDisplayed() );
      Logger.toLog( "Check incorrect image message...OK" );
    }
  }
  
  @After
  public void tearDown() throws Exception {
    
  }
}