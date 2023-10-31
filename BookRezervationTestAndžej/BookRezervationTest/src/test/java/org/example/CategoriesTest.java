package org.example;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoriesTest extends BaseTestPage {

    String categoryName = "history";
    @Test
    void addCategories() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoryName);
        categoriesPage.clickAddCategory();
        assertEquals("Category created!", driver.findElement(By.xpath("//div[@id='liveAlertPlaceholder']/div/div[.='Category created!']")).getText());


    }

    @Test
    void findCreatedCategory(){
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoryName);
        categoriesPage.clickAddCategory();
        categoriesPage.clickSelectCategory();
        assertTrue(driver.findElement(By.cssSelector("select[name='category']")).getText().contains(categoryName));
    }
    @Test
    void addInvalidCategory(){
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoryName);
        categoriesPage.clickAddCategory();
        assertEquals("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());
    }
}
