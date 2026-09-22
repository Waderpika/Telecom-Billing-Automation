package com.ayush.framework.pages;

import com.ayush.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage — Page Object for https://www.saucedemo.com (login screen)
 *
 * Uses @FindBy (PageFactory) for element location.
 * All interactions go through BasePage helpers (click, type, getText)
 * so waits are applied consistently.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".login_logo")
    private WebElement loginLogo;

    // ── Actions ──────────────────────────────────────────────────────────────

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public InventoryPage clickLoginAndExpectSuccess() {
        click(loginButton);
        waitForUrl("inventory");
        return new InventoryPage();
    }

    public LoginPage clickLoginAndExpectFailure() {
        click(loginButton);
        return this;
    }

    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return this;
    }

    // ── Assertions helpers ───────────────────────────────────────────────────

    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginLogo);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
