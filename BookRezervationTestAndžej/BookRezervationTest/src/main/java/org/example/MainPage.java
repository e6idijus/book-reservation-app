package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage{
    WebDriver driver;

    By clickLogo = By.cssSelector("img[alt='Book Reservation App Logo']");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    public void clickLogo(){
        driver.findElement(clickLogo).click();
    }

}
