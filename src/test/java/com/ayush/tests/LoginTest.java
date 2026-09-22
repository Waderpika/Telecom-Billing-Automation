package com.ayush.tests;

import com.ayush.framework.base.BaseTest;
import com.ayush.framework.pages.InventoryPage;
import com.ayush.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LoginTest — covers all login scenarios for SauceDemo application.
 *
 * Test scenarios:
 *  1. Valid credentials → successful login → inventory page loads
 *  2. Invalid password → error message displayed
 *  3. Empty username → error message displayed
 *  4. Locked out user → specific error message
 *  5. Login page elements are displayed on load
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials should navigate to inventory page")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page should be displayed");

        InventoryPage inventoryPage = loginPage
            .login(config.getValidUsername(), config.getValidPassword())
            .clickLoginAndExpectSuccess();

        Assert.assertTrue(inventoryPage.isPageLoaded(),
            "Inventory page should load after valid login");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),
            "URL should contain 'inventory' after login");
    }

    @Test(description = "Invalid password should show error message")
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage
            .login(config.getValidUsername(), "wrongpassword")
            .clickLoginAndExpectFailure();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error message should be displayed for invalid password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
            "Error message should indicate incorrect credentials");
    }

    @Test(description = "Empty username should show validation error")
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage
            .enterPassword(config.getValidPassword())
            .clickLoginAndExpectFailure();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error should show when username is empty");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
            "Error should specifically mention username required");
    }

    @Test(description = "Locked out user should see specific error message")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage();
        loginPage
            .login(config.getLockedUsername(), config.getValidPassword())
            .clickLoginAndExpectFailure();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "Error should display for locked out user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
            "Error should indicate user is locked out");
    }
}
