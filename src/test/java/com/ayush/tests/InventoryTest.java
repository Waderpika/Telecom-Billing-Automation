package com.ayush.tests;

import com.ayush.framework.base.BaseTest;
import com.ayush.framework.pages.InventoryPage;
import com.ayush.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * InventoryTest — covers product listing and cart interaction scenarios.
 *
 * Test scenarios:
 *  1. Inventory page loads with products after login
 *  2. Add single item to cart — cart badge updates
 *  3. Add multiple items — cart count is correct
 *  4. Sort products by price low to high
 */
public class InventoryTest extends BaseTest {

    // Helper — logs in and returns InventoryPage
    private InventoryPage loginAndGetInventory() {
        return new LoginPage()
            .login(config.getValidUsername(), config.getValidPassword())
            .clickLoginAndExpectSuccess();
    }

    @Test(description = "Inventory page should display products after login")
    public void testInventoryPageLoads() {
        InventoryPage inventoryPage = loginAndGetInventory();

        Assert.assertTrue(inventoryPage.isPageLoaded(),
            "Inventory page should be loaded");
        Assert.assertTrue(inventoryPage.getProductCount() > 0,
            "At least one product should be displayed");
        Assert.assertEquals(inventoryPage.getProductCount(), 6,
            "SauceDemo should show 6 products");
    }

    @Test(description = "Adding item to cart should update cart badge")
    public void testAddSingleItemToCart() {
        InventoryPage inventoryPage = loginAndGetInventory();

        Assert.assertEquals(inventoryPage.getCartCount(), "0",
            "Cart should be empty initially");

        inventoryPage.addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartCount(), "1",
            "Cart badge should show 1 after adding one item");
    }

    @Test(description = "Adding multiple items should reflect correct cart count")
    public void testAddMultipleItemsToCart() {
        InventoryPage inventoryPage = loginAndGetInventory();

        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        inventoryPage.addItemToCartByIndex(2);

        Assert.assertEquals(inventoryPage.getCartCount(), "3",
            "Cart badge should show 3 after adding three items");
    }

    @Test(description = "Sorting by price low to high should reorder products")
    public void testSortByPriceLowToHigh() {
        InventoryPage inventoryPage = loginAndGetInventory();

        inventoryPage.sortBy("lohi");

        Assert.assertTrue(inventoryPage.areProductsSortedByPriceLowToHigh(),
            "Products should be sorted from lowest to highest price");
    }
}
