package org.example;

import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriesTest extends BaseTestPage {

    String categoryName = "Cats";

    @Test
    @Order(1)
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
    @Order(2)
    void findCreatedCategory() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickSelectCategory();
        assertTrue(driver.findElement(By.cssSelector("[name='category mt-3']")).getText().contains(categoryName));
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
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickSelectCategory();
        categoriesPage.selectValueOfCategories();
        categoriesPage.clickEditButton();
        categoriesPage.selectEditField();
        categoriesPage.clearEditField();
        categoriesPage.inputUpdatedCategory("s Games");
        categoriesPage.clickUpdateButton();
        assertEquals("Category updated!", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());
        categoriesPage.clickSelectCategory();
        assertTrue(driver.findElement(By.cssSelector("[name='category mt-3']")).getText().contains("Cats Games"));

    }

    @Test
    @Order(5)
    void cancelUpdate() {
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickSelectCategory();
        Select select = new Select(driver.findElement(By.xpath("//div[@id='root']//select[@name='category mt-3']")));
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
    void deleteCategoryById(){
        MainPage mainPage = new MainPage(driver);
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        mainPage.clickCategories();
        categoriesPage.clickAddNewCategoryButton();
        categoriesPage.clickToEnterNewCategory();
        categoriesPage.inputNewCategory(categoriesPage.valueOfCategory);
        categoriesPage.clickAddCategory();
        categoriesPage.clickSelectCategory();
        categoriesPage.selectCategoryToDelete();
        categoriesPage.deleteCategory();
        categoriesPage.clickConfirmToDelete();

        try {
            driver.findElement(By.cssSelector("[value='" + categoriesPage.valueOfCategory + "']"));

            assertFalse(true, "Element is present, but it should not be.");
        } catch (NoSuchElementException e) {

            assertFalse(false, "Element is not present, as expected.");
        }




    }


}

