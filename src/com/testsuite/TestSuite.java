package com.testsuite;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.core.Logger;
import com.core.MyWebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  com.testssuite.testcases.TestAddAppPositive.class,
  com.testssuite.testcases.TestAddAppNegative.class,
})

public class TestSuite {
  public static WebDriver driver;
  
  @BeforeClass
  public static void setUp() {
    Logger.toLog( "Start test suite" );
    
    /*
    // Use ONLY for debugging
    System.setProperty("webdriver", "firefox");
    System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\selenium-2.49.1\\chromedriver.exe");
    System.setProperty("baseURL", "http://ui.moneytapp.vas61t.test.i-free.ru");
    System.setProperty("rootPath", "D:\\Selenium\\workspace\\iFreeTestTask");
    System.setProperty("login", "stigorv@gmail.com");
    System.setProperty("password", "iFree12345");
    */
    
    // Setup driver, open first page and then login.
    driver = MyWebDriver.SelectDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    
    // Check login page was loaded
    WebElement loginElement, passwordElement, loginButton;
    driver.get( System.getProperty("baseURL") + "/webui-rc/apps/list" );
    
    try {
      loginElement = driver.findElement(By.id("j_username"));
      passwordElement = driver.findElement(By.id("j_password"));
      // TODO: Add name or id for button element
      loginButton = driver.findElement(By.cssSelector("input.force-right"));
      Logger.toLog( "Login form was opened" );
      if ( driver.getTitle().contains("MoneyTapp » Log in") && 
            loginElement.isDisplayed() && 
            passwordElement.isDisplayed() && 
            loginButton.isEnabled() )
      {
        loginElement.clear();
        loginElement.sendKeys( System.getProperty("login") );
        passwordElement.clear();
        passwordElement.sendKeys( System.getProperty("password") );
        loginButton.submit();
      }
      else
      {
        Logger.toLog( "Some elements have incorrect state" );
        assertTrue( "Login field doesn't show", loginElement.isDisplayed() );
        assertTrue( "Password field doesn't show", passwordElement.isDisplayed() );
        assertTrue( "Login button doesn't enable", loginButton.isEnabled() );
        assertTrue( "Incorrect Log in page title", driver.getTitle().contains("MoneyTapp » Log in") );
      }
    } catch (NoSuchElementException e)
    {
      Logger.toWarning( "Login form was NOT opened. Check Application List page was loaded" );
    }
      
    // Check loaded correct page
    assertTrue("Incorrect Application List page title", driver.getTitle().contains("MoneyTapp » Application List"));
    // TODO: Add name or id for button element
    assertTrue("Add App button doesn't show", driver.findElement(By.cssSelector("a[href='http://ui.moneytapp.vas61t.test.i-free.ru/webui-rc/apps/new']")).isDisplayed());
    Logger.toLog( "Application list page with \"New App\" button was loaded" );
  }
  
  public static WebDriver getDriver() { return driver; }
      
  @AfterClass
  public static void tearDown() {
    driver.quit();  
    Logger.toLog( "Test suite complete" );
  }
}