package org.example;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BooksTest extends BaseTestPageChromeDriver {
    @Test
    @Order(1)
    void addBook() {
        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.enterTitle("Harry Potter Film");
        booksPage.enterAuthor("David Yates");
        booksPage.clickToSelectCategory();
        booksPage.selectCategory();
        booksPage.enterDescription("Harry Potter is a film series based on the eponymous novels by British author J. K. Rowling. The series is produced and distributed by Warner Bros.");
        booksPage.enterPictureUrl("https://en.wikipedia.org/wiki/File:Daniel_Radcliffe,_Emma_Watson_%26_Rupert_Grint_colour.jpg");
        booksPage.enterPages("200");
        booksPage.enterIsbn("1-9028-9467-6");
        booksPage.selectDate("2000", "10", "05");
        booksPage.enterLanguage("English");
        booksPage.clickSubmit();
        assertEquals("Book created!", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());

    }

    @Test
    @Order(2)
    void addExistedBook() {
        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.enterTitle("Harry Potter Film");
        booksPage.enterAuthor("David Yates");
        booksPage.clickToSelectCategory();
        booksPage.selectCategory();
        booksPage.enterDescription("Harry Potter is a film series based on the eponymous novels by British author J. K. Rowling. The series is produced and distributed by Warner Bros.");
        booksPage.enterPictureUrl("https://en.wikipedia.org/wiki/File:Daniel_Radcliffe,_Emma_Watson_%26_Rupert_Grint_colour.jpg");
        booksPage.enterPages("200");
        booksPage.enterIsbn("1-9028-9468-6");
        booksPage.selectDate("2000", "10", "05");
        booksPage.enterLanguage("English");
        booksPage.clickSubmit();
        assertEquals("Title already exists", driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div")).getText());
    }


    @ParameterizedTest
    @Order(3)
    @CsvFileSource(files = "src/main/resources/Books.csv")
    void addBookWithOneEmptyField(String title, String author,String description, String pictureUrl, Integer pages, String years,String month, String day, String language, String isbn,String message) {
        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.enterTitle(title);
        booksPage.enterAuthor(author);
        booksPage.clickToSelectCategory();
        booksPage.selectCategory();
        booksPage.enterDescription(description);
        booksPage.enterPictureUrl(pictureUrl);
        booksPage.enterPages(String.valueOf(pages));
        booksPage.enterIsbn(isbn);
        booksPage.selectDate(years, month, day);
        booksPage.enterLanguage(language);
        booksPage.clickSubmit();

        assertTrue(driver.findElement(By.cssSelector("div#liveAlertPlaceholder > div > div"))
                .getText()
                .contains(message));
    }
}