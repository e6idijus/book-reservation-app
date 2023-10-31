package org.example;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavbarTest extends BaseTestPage {
    @Test
    void logoButtonTest(){
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogo();
        assertEquals("Welcome to Home!",driver.findElement(By.xpath("//div[@id='root']//div[.='Welcome to Home!']")).getText());
    }
    @Test
    void enterCategories(){
        MainPage mainPage = new MainPage(driver);
        mainPage.clickCategories();
        assertEquals("Current categories:", driver.findElement(By.cssSelector("label")).getText());

    }
}
