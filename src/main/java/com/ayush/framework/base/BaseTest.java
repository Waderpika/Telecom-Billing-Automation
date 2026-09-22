package com.ayush.framework.base;

import com.ayush.framework.utils.ConfigReader;
import com.ayush.framework.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest — parent class for all test classes.
 *
 * Responsibilities:
 *  - Initialize and tear down WebDriver for each test method
 *  - Navigate to base URL before each test
 *  - Provide getDriver() helper to test classes
 *
 * Why per-method (not per-class) setup?
 *  Each test method gets a clean browser state — avoids test pollution,
 *  critical for parallel execution where shared state causes flaky tests.
 */
public class BaseTest {

    protected ConfigReader config = ConfigReader.getInstance();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
        DriverManager.getDriver().get(config.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
