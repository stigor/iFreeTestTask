package com.testssuite.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
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
  
// Search using keyword through Google search

  @Test
  public void testAddNewAppNegative() {
      Logger.toLog( "Test: testAddNewAppNegativeTests..." );
      
      List<Map<String, String>> tests = DataAddApp.getTestDataForNegativeTests();
      
      int testNumber = 1;
      for (Map<String, String> map : tests) {

        Logger.toLog( "Start Test: " + testNumber );
        
        Logger.toLog( "Test Data:" );
        for (String key : map.keySet()) {
          System.out.println(key + " => " + map.get(key));
        }
        
        // Open Add App form      
        CommonMethods.pressNewAppAndCheckModalDialog(driver);

        String name = map.get("name");
        String appIconFile = map.get("appIconFile");
        String category = map.get("category");
        
        // Get unique name
        if ( null != name && name.equals( "<get_unique_app_name>" ) )
        {
          name = CommonMethods.getUniqAppName();
          Logger.toLog("Unique app name: " + name);
        }
        
        // Fill name field
        if ( null != name )
        {
          driver.findElement(By.id("name")).clear();
          driver.findElement(By.id("name")).sendKeys(name);
        }
        
        // Fill app icon
        if ( null != appIconFile )
        {
          String pathToIcon = System.getProperty("rootPath")  + File.separator + "src" 
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

        // Check results
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-header")));
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("validationModal")));        
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

  @After
  public void tearDown() throws Exception {
    
  }
}