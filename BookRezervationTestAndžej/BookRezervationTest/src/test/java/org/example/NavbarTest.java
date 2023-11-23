package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(3)
public class NavbarTest extends BaseTestPageChromeDriver {
    @Test
    void logoButtonTest(){
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogo();
        assertEquals("Book Reservation App",driver.findElement(By.cssSelector(".display-3.fw-bold")).getText());
    }
    @Test
    void enterCategories(){
        MainPage mainPage = new MainPage(driver);
        mainPage.clickCategories();
        assertEquals("Current categories:", driver.findElement(By.cssSelector("label")).getText());

    }
}
