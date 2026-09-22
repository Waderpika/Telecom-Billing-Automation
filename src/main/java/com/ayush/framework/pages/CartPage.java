package com.ayush.framework.pages;

import com.ayush.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * CartPage — Shopping cart page.
 */
public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css = ".title")
    private WebElement pageTitle;

    // ── Actions ──────────────────────────────────────────────────────────────

    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        waitForUrl("checkout-step-one");
        return new CheckoutPage();
    }

    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        waitForUrl("inventory");
        return new InventoryPage();
    }

    // ── Assertion helpers ─────────────────────────────────────────────────

    public int getCartItemCount() {
        return cartItems.size();
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    public String getFirstItemName() {
        return getText(itemNames.get(0));
    }

    public boolean isPageLoaded() {
        return isDisplayed(pageTitle);
    }
}
