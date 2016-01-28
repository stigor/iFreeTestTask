SET rootPath=D:\Selenium\workspace\iFreeTestTask

SET libPath=%rootPath%\lib

SET webdriverChromeDriver=%rootPath%\bin\chromedriver.exe

SET baseURL=http://ui.moneytapp.vas61t.test.i-free.ru

SET login=stigorv@gmail.com

SET password=iFree12345

rem webdriver options: chrome|firefox
SET webdriver=chrome

java -Dpassword=%password% -Dlogin=%login% -Dwebdriver=%webdriver% -DrootPath=%rootPath% -Dwebdriver.chrome.driver=%webdriverChromeDriver% -DbaseURL=%baseURL% -cp .;%libPath%\junit-4.12.jar;%libPath%\selenium-java-2.49.1.jar;%libPath%\selenium-server-standalone-2.49.1.jar org.junit.runner.JUnitCore com.testsuite.TestSuite > testResults.txt 2>&1