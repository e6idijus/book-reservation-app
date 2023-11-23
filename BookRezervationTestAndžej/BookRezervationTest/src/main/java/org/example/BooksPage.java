package org.example;

import org.openqa.selenium.*;

import java.time.LocalDate;

public class BooksPage {
    WebDriver driver;
    String valueOfCategory = "Films";

    By enterTitle = By.cssSelector("input#title");
    By enterAuthor = By.cssSelector("input#author");
    By clickToSelectCategory = By.cssSelector("select#category");
    By selectCategory = By.cssSelector("[value='" + valueOfCategory + "']");
    By enterDescription = By.cssSelector("textarea[name='description']");
    By enterPictureUrl = By.cssSelector("input#picture-url");
    By enterPages = By.cssSelector("input#pages");
    By enterIsbn = By.cssSelector("input#isbn");
    By selectDate = By.cssSelector("input#publication-date");
    By enterLanguage = By.cssSelector("input#language");
    By clickSubmit = By.cssSelector("[class='container col-12 col-sm-8 col-lg-4 mt-3 mb-3'] [type='submit']");
    By clickAddBookButton = By.cssSelector("[class='btn btn-warning mb-2']");

    public BooksPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterTitle(String title) {
        driver.findElement(enterTitle).sendKeys(title);

    }

    public void enterAuthor(String author) {
        driver.findElement(enterAuthor).sendKeys(author);
    }

    public void clickToSelectCategory() {
        driver.findElement(clickToSelectCategory).click();
    }

    public void selectCategory() {
        driver.findElement(selectCategory).click();
    }

    public void enterDescription(String description) {
        driver.findElement(enterDescription).sendKeys(description);
    }

    public void enterPictureUrl(String pictureUrl) {
        driver.findElement(enterPictureUrl).sendKeys(pictureUrl);
    }

    public void enterPages(String pages) {
        driver.findElement(enterPages).sendKeys(pages);
    }

    public void enterIsbn(String isbn) {
        driver.findElement(enterIsbn).sendKeys(isbn);
    }

    public void selectDate(String year, String month, String day) {
        WebElement dateInput = driver.findElement(selectDate);
        dateInput.sendKeys(year);
        dateInput.sendKeys(Keys.TAB);
        dateInput.sendKeys(month);
        dateInput.sendKeys(day);

    }
    public void enterLanguage(String language){
        driver.findElement(enterLanguage).sendKeys(language);
    }
    public void clickSubmit(){
        var element = driver.findElement(clickSubmit);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }
    public void clickAddBookButton(){
        driver.findElement(clickAddBookButton).click();

    }

}