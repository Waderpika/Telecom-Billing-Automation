package com.ayush.framework.pages;

import com.ayush.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * InventoryPage — Product listing page after successful login.
 */
public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".title")
    private WebElement pageTitle;

    // ── Actions ──────────────────────────────────────────────────────────────

    public InventoryPage addFirstItemToCart() {
        click(addToCartButtons.get(0));
        return this;
    }

    public InventoryPage addItemToCartByIndex(int index) {
        if (index >= addToCartButtons.size())
            throw new IllegalArgumentException("Product index out of range: " + index);
        click(addToCartButtons.get(index));
        return this;
    }

    public CartPage goToCart() {
        click(cartIcon);
        waitForUrl("cart");
        return new CartPage();
    }

    public InventoryPage sortBy(String option) {
        // options: "az", "za", "lohi", "hilo"
        new Select(sortDropdown).selectByValue(option);
        return this;
    }

    // ── Assertion helpers ────────────────────────────────────────────────────

    public boolean isPageLoaded() {
        return isDisplayed(pageTitle) && productNames.size() > 0;
    }

    public int getProductCount() {
        return productNames.size();
    }

    public String getCartCount() {
        try {
            return getText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }

    public String getFirstProductName() {
        return getText(productNames.get(0));
    }

    public double getFirstProductPrice() {
        String price = getText(productPrices.get(0)).replace("$", "");
        return Double.parseDouble(price);
    }

    public boolean areProductsSortedByPriceLowToHigh() {
        List<Double> prices = productPrices.stream()
            .map(e -> Double.parseDouble(getText(e).replace("$", "")))
            .toList();
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) return false;
        }
        return true;
    }
}
