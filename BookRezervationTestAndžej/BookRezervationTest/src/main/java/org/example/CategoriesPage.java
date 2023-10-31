package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoriesPage {
    WebDriver driver;

    By clickAddNewCategoryButton = By.cssSelector(".btn-success");
    By inputNewCategory = By.cssSelector("[type='text']");
    By clickAddCategory = By.cssSelector(".container [type='submit']");
    By clickSelectCategory = By.cssSelector("select[name='category']");

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
    }
    public void clickAddNewCategoryButton(){
        driver.findElement(clickAddNewCategoryButton).click();
    }
    public void inputNewCategory(String name){
        driver.findElement(inputNewCategory).sendKeys(name);
    }
    public void clickToEnterNewCategory(){
        driver.findElement(inputNewCategory).click();
    }
    public void clickAddCategory(){
        driver.findElement(clickAddCategory).click();
    }
    public void clickSelectCategory(){
        driver.findElement(clickSelectCategory).click();
    }

}

