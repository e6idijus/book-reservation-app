package org.example;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavBarTestMobile extends BaseTestPageMobile{

    @Test
    void logoButtonTest(){
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogo();
        waiting();
        assertEquals("Welcome to Home!",driver.findElement(By.xpath("//div[@id='root']//div[.='Welcome to Home!']")).getText());
    }
    @Test
    void openNavBar(){
        MainPage mainPage = new MainPage(driver);
        mainPage.clickNavBar();
        waiting();
        assertEquals("Categories", driver.findElement(By.linkText("Categories")).getText());

    }

}
