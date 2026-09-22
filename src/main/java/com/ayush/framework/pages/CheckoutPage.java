package com.ayush.framework.pages;

import com.ayush.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * CheckoutPage — covers checkout step 1 (details), step 2 (overview), and confirmation.
 */
public class CheckoutPage extends BasePage {

    // Step 1 — Customer details
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // Step 2 — Overview
    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // Confirmation
    @FindBy(css = ".complete-header")
    private WebElement confirmationHeader;

    @FindBy(css = ".complete-text")
    private WebElement confirmationText;

    // ── Step 1 Actions ────────────────────────────────────────────────────────

    public CheckoutPage fillDetails(String firstName, String lastName, String postalCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        return this;
    }

    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }

    public CheckoutPage proceedToOverview(String firstName, String lastName, String postal) {
        fillDetails(firstName, lastName, postal);
        click(continueButton);
        waitForUrl("checkout-step-two");
        return this;
    }

    // ── Step 2 Actions ────────────────────────────────────────────────────────

    public CheckoutPage clickFinish() {
        click(finishButton);
        waitForUrl("checkout-complete");
        return this;
    }

    // ── Assertion helpers ─────────────────────────────────────────────────────

    public String getOrderTotal() {
        return getText(totalLabel);
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public boolean isOrderConfirmed() {
        return isDisplayed(confirmationHeader) &&
               getText(confirmationHeader).toLowerCase().contains("thank you");
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
