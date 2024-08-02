package com.browsersrack.BasePackage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class Base {

    WebDriver driver;
    Properties prop = new Properties();
    File propertiesFile = new File("src/main/java/com/browsersrack/Configurations/Config.properties");
    ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public WebDriver initializeBrowser() throws Throwable {
        FileInputStream fis = new FileInputStream(propertiesFile);
        prop.load(fis);
        String browserName = prop.getProperty("BrowserName");

        if(browserName.equalsIgnoreCase("chrome")){
            driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }
        else if(browserName.equalsIgnoreCase("edge")){
            driver = new EdgeDriver();
        }
        else{
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get(prop.getProperty("BaseURL"));
        driverThreadLocal.set(driver);

        return driver;
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public void tearDown(){
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                throw e;
            } finally {
                driverThreadLocal.remove();
            }
        }
    }


}
