package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage{
    WebDriver driver;

    By clickLogo = By.cssSelector("img[alt='Book Reservation App Logo']");
    By clickCategories = By.linkText("Categories");
    By clickNavBar = By.cssSelector(".navbar-toggler-icon");
    By clickBooks = By.linkText("Books");



    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    public void clickLogo(){
        driver.findElement(clickLogo).click();
    }
    public void clickCategories(){
        driver.findElement(clickCategories).click();
    }
    public void clickNavBar(){
        driver.findElement(clickNavBar).click();
    }
    public void clickBooks(){
        driver.findElement(clickBooks).click();
    }

}
