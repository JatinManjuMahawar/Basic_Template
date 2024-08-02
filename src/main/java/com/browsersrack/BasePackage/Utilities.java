package com.browsersrack.BasePackage;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;


public class Utilities extends Base {

    static WebDriverWait wait;
    WebDriver driver;
    public String takesScreenshot(String testName){
        driver = getDriver();
        String screenshotPath = System.getProperty("user.dir")+"\\test-outputs\\Screenshots\\"+testName+".PNG";

        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(screenshotFile, new File(screenshotPath));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return screenshotPath;
    }

    public static Object[][] getTestData(String sheetname, File excelFile) throws Exception {
        XSSFWorkbook workbook;
        XSSFSheet sheet;
        Object testData[][];
        try {
            FileInputStream fis = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetname);
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(rows).getLastCellNum();
            testData = new Object[rows][cols];
            for (int i = 0; i < rows; i++) {
                XSSFRow row = sheet.getRow(i+1);
                for (int j = 0; j < cols; j++) {
                    XSSFCell cell = row.getCell(j);
                    CellType cellType = cell.getCellType();
                    switch (cellType) {
                        case STRING:
                            testData[i][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                testData[i][j] = cell.getDateCellValue();
                                break;
                            } else {
                                double numericValue = cell.getNumericCellValue();
                                if (numericValue >= 1000000000 && numericValue <= 9999999999L) {
                                    testData[i][j] = (long) numericValue;
                                } else {
                                    testData[i][j] = Double.toString(numericValue);
                                }
                                break;
                            }
                    }
                }
            }

        } catch (Throwable t) {
            throw t;
        }

        return testData;
    }
    public static void waitUntilElementIsClickable(WebDriver driver, WebElement element, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilElementIsVisible(WebDriver driver, WebElement element, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilPresenceOfElementLocated(WebDriver driver, WebElement element, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilTextIsPresentInElement(WebDriver driver, WebElement element, String text, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void waitUntilElementIsSelected(WebDriver driver, WebElement element, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.elementToBeSelected(element));
    }

    public static void waitUntilTitleContains(WebDriver driver, String title, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.titleContains(title));
    }

    public static void waitUntilElementIsInvisible(WebDriver driver, WebElement element, int waitSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }


}
