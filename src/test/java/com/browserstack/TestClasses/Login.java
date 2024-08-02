package com.browserstack.TestClasses;

import com.browsersrack.BasePackage.Base;
import org.apache.poi.xssf.binary.XSSFBUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login extends Base {
    WebDriver driver;
    ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
    @BeforeMethod
    public void setup() throws Throwable {
        driver = initializeBrowser();
        threadLocal.set(driver);
    }
    @AfterMethod
    public void tearDown(){
        super.tearDown();
    }

    @Test
    public void test1(){
        System.out.println("Run 1st Test");
    }
    @Test
    public void test2(){
        System.out.println("Run 2nd Test");
    }

}
