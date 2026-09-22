package com.ayush.framework.listeners;

import com.ayush.framework.utils.DriverManager;
import com.ayush.framework.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener — TestNG listener for screenshot capture and logging.
 * Registered in testng.xml so it applies to all tests automatically.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n▶ Starting: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ FAILED: " + result.getMethod().getMethodName());
        System.out.println("   Reason: " + result.getThrowable().getMessage());

        // Capture screenshot on failure
        try {
            String screenshotPath = ScreenshotUtil.capture(
                DriverManager.getDriver(),
                result.getMethod().getMethodName()
            );
            if (screenshotPath != null) {
                System.out.println("   Screenshot: " + screenshotPath);
            }
        } catch (Exception e) {
            System.out.println("   Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭ SKIPPED: " + result.getMethod().getMethodName());
    }
}
