package com.browsersrack.ExtentReporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


import java.io.File;

public class ExtentReportingClass {
    public ExtentReports generateExtentReports(){
        ExtentReports extentReports = new ExtentReports();
        File extentReportFile = new File("ExtentReports/ExtentReport.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        sparkReporter.config().setReportName("TutorialsNinjaTestNG Extent Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Extent Report");
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy");
        extentReports.setSystemInfo("OS", "Windows");
        extentReports.setSystemInfo("Application Build No", "1.2");
        extentReports.attachReporter(sparkReporter);

        return extentReports;
    }


}
