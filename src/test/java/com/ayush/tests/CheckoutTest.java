package com.ayush.tests;

import com.ayush.framework.base.BaseTest;
import com.ayush.framework.pages.CartPage;
import com.ayush.framework.pages.CheckoutPage;
import com.ayush.framework.pages.InventoryPage;
import com.ayush.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CheckoutTest — End-to-end checkout flow scenarios.
 *
 * Test scenarios:
 *  1. Full E2E: login → add to cart → checkout → order confirmed
 *  2. Cart shows correct item after adding from inventory
 *  3. Checkout without filling details shows validation error
 */
public class CheckoutTest extends BaseTest {

    private InventoryPage loginAndGetInventory() {
        return new LoginPage()
            .login(config.getValidUsername(), config.getValidPassword())
            .clickLoginAndExpectSuccess();
    }

    @Test(description = "Full E2E: login, add item, checkout, confirm order")
    public void testFullCheckoutFlow() {
        // Login
        InventoryPage inventoryPage = loginAndGetInventory();
        String addedProduct = inventoryPage.getFirstProductName();

        // Add to cart
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartCount(), "1",
            "Cart should have 1 item");

        // Go to cart
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
            "Cart should display 1 item");
        Assert.assertEquals(cartPage.getFirstItemName(), addedProduct,
            "Cart should contain the item that was added");

        // Checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        checkoutPage.proceedToOverview("Ayush", "Kumar", "411001");

        // Verify order total is displayed
        Assert.assertFalse(checkoutPage.getOrderTotal().isEmpty(),
            "Order total should be displayed on overview page");

        // Finish order
        checkoutPage.clickFinish();

        // Confirm order
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
            "Order confirmation should be displayed");
        Assert.assertEquals(checkoutPage.getConfirmationHeader(), "Thank you for your order!",
            "Confirmation header should match expected text");
    }

    @Test(description = "Cart should display the correct item added from inventory")
    public void testCartShowsCorrectItem() {
        InventoryPage inventoryPage = loginAndGetInventory();
        String expectedProduct = inventoryPage.getFirstProductName();

        inventoryPage.addFirstItemToCart();
        CartPage cartPage = inventoryPage.goToCart();

        Assert.assertFalse(cartPage.isCartEmpty(),
            "Cart should not be empty after adding item");
        Assert.assertEquals(cartPage.getFirstItemName(), expectedProduct,
            "Item in cart should match what was added from inventory");
    }

    @Test(description = "Checkout with empty details should show validation error")
    public void testCheckoutValidationError() {
        InventoryPage inventoryPage = loginAndGetInventory();
        inventoryPage.addFirstItemToCart();

        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Click continue without filling details
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
            "Validation error should display when details are empty");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"),
            "Error should mention First Name is required");
    }
}
