package Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.browsersrack.BasePackage.Utilities;
import com.browsersrack.ExtentReporting.ExtentReportingClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ListenerClass extends Utilities implements ITestListener {
    ExtentReports extentReports;
    ExtentTest extentTest;
    ExtentReportingClass erc = new ExtentReportingClass();
    String testName;
    String userDirPath = System.getProperty("user.dir").replace("\\", "/");


    @Override
    public void onStart(ITestContext context) {
        extentReports = erc.generateExtentReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        testName = result.getName();
        extentTest = extentReports.createTest(testName);
        extentTest.log(Status.INFO, testName + " Started Executing");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, testName + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = null;
        try {
            screenshotPath = takesScreenshot(testName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        extentTest.addScreenCaptureFromPath(screenshotPath);
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.FAIL, testName + " FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP, testName + " SKIPPED");

    }

    @Override
    public void onFinish(ITestContext context) {

        extentReports.flush();
        File file = new File(System.getProperty("user.dir") + "\\ExtentReports\\ExtentReport.html");


        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
