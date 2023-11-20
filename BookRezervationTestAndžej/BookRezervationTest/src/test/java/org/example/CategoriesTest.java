package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriesTest extends BaseTestPageChromeDriver {


    @Test
    @Order(1)
    void addCategories() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoriesPage.valueOfCategory);
        categoriesPage.clickAddCategory();
        assertEquals("Category created!", driver.findElement(By.xpath("//div[@id='liveAlertPlaceholder']/div/div[.='Category created!']")).getText());


    }

    @Test
    @Order(2)
    void findCreatedCategory() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickSelectCategory();
        assertTrue(driver.findElement(By.cssSelector("select[name='category']")).getText().contains(categoriesPage.valueOfCategory));
    }

    @Test
    @Order(3)
    void addInvalidCategory() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory("geography");
        categoriesPage.clickAddCategory();
        assertEquals("Category name must start with an uppercase letter, followed by lowercase letters, without numbers, consecutive repeated characters, and a length between 5 and 50 characters", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());
    }

    @Test
    @Order(4)
    void editCategory() {
        String category = "Games";
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickSelectCategory();
        categoriesPage.selectValueOfCategories();
        categoriesPage.clickEditButton();
        categoriesPage.selectEditField();
        categoriesPage.clearEditField();
        categoriesPage.inputUpdatedCategory(category);
        categoriesPage.clickUpdateButton();
        assertEquals("Category updated!", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());
        categoriesPage.clickSelectCategory();
        assertTrue(driver.findElement(By.cssSelector("select[name='category']")).getText().contains(category));

    }

    @Test
    @Order(5)
    void cancelUpdate() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickSelectCategory();
        Select select = new Select(driver.findElement(By.cssSelector("select[name='category']")));
        select.selectByIndex(1);
        categoriesPage.clickSelectCategory();
        categoriesPage.clickEditButton();
        categoriesPage.clickCancelToUpdate();
        assertTrue(driver.findElement(By.xpath("/html//div[@id='root']//label[.='Current categories:']")).isDisplayed());

    }

    @Test
    @Order(6)
    void closeAlertWindow() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory("geography");
        categoriesPage.clickAddCategory();
        categoriesPage.closeAlertWindow();

        try {
            driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div"));

            assertFalse(true, "Element is present, but it should not be.");
        } catch (NoSuchElementException e) {

            assertFalse(false, "Element is not present, as expected.");
        }
    }

    @Test
    @Order(7)
    void deleteCategoryById() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoriesPage.valueOfCategory);
        categoriesPage.clickAddCategory();
        categoriesPage.clickSelectCategory();
        categoriesPage.selectValueOfCategories();
        categoriesPage.deleteCategory();
        categoriesPage.clickConfirmToDelete();
        assertEquals("Category deleted!", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());


        try {
            driver.findElement(By.cssSelector("[value='" + categoriesPage.valueOfCategory + "']"));

            assertFalse(true, "Element is present, but it should not be.");
        } catch (NoSuchElementException e) {

            assertFalse(false, "Element is not present, as expected.");
        }


    }

    @Test
    @Order(8)
    void undoDeleteCategoryById() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoriesPage.valueOfCategory);
        categoriesPage.clickAddCategory();
        categoriesPage.clickSelectCategory();
        categoriesPage.selectValueOfCategories();
        categoriesPage.deleteCategory();
        categoriesPage.clickUndoDelete();


        try {
            driver.findElement(By.cssSelector("[value='" + categoriesPage.valueOfCategory + "']"));

            assertFalse(false, "Element is present, as expected.");
        } catch (NoSuchElementException e) {

            assertFalse(true, "Element is not present, but should be.");
        }
    }

}