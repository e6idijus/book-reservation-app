package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class CategoriesPage {
    WebDriver driver;
    String valueOfCategory = "Films";
    By clickAddNewCategoryButton = By.cssSelector(".btn-success");
    By inputNewCategory = By.cssSelector("[type='text']");
    By clickAddCategory = By.cssSelector(".container [type='submit']");
    By clickSelectCategory = By.cssSelector("select[name='category']");
    By selectValueOfCategories = By.cssSelector("[value='" + valueOfCategory + "']");

    By clickEditButton = By.cssSelector(".btn-info");
    By selectEditField = By.cssSelector("form > input[name='category']");
    By clickUpdateButton = By.cssSelector("[class='container  col-12 col-sm-8 col-lg-4 mt-3 mb-3'] [type='submit']");
    By clickCancelToUpdate = By.cssSelector("form > .btn.btn-warning");
    By closeAlertWindow = By.cssSelector("[data-bs-dismiss]");
    By deleteCategory = By.xpath("/html//div[@id='root']/main/div/button[@type='button']");
    By clickConfirmToDelete = By.cssSelector(".btn.btn-danger.btn-primary");
    By clickUndoDelete = By.cssSelector(".modal-footer [data-bs-dismiss='modal']:nth-of-type(2)");

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAddNewCategoryButton() {
        driver.findElement(clickAddNewCategoryButton).click();
    }

    public void inputNewCategory(String name) {
        driver.findElement(inputNewCategory).sendKeys(name);
    }

    public void clickToEnterNewCategory() {
        driver.findElement(inputNewCategory).click();
    }

    public void clickAddCategory() {
        driver.findElement(clickAddCategory).click();
    }

    public void clickSelectCategory() {
        driver.findElement(clickSelectCategory).click();
    }

    public void selectValueOfCategories() {
        driver.findElement(selectValueOfCategories).click();
    }

    public void clickEditButton() {
        driver.findElement(clickEditButton).click();
    }

    public void selectEditField() {
        driver.findElement(selectEditField).click();
    }

    public void clearEditField() {
        driver.findElement(selectEditField).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }

    public void inputUpdatedCategory(String updatedCategory) {
        driver.findElement(selectEditField).sendKeys(updatedCategory);
    }

    public void clickUpdateButton() {
        driver.findElement(clickUpdateButton).click();
    }

    public void clickCancelToUpdate() {
        driver.findElement(clickCancelToUpdate).click();
    }

    public void closeAlertWindow() {
        driver.findElement(closeAlertWindow).click();
    }



    public void deleteCategory() {
        driver.findElement(deleteCategory).click();
    }
    public void clickConfirmToDelete(){
        driver.findElement(clickConfirmToDelete).click();
    }
    public void clickUndoDelete(){
        driver.findElement(clickUndoDelete).click();
    }

}

