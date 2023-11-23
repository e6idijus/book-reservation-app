package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class BooksTest extends BaseTestPageChromeDriver {
    @Test
    @Order(1)
    void addBook() {
        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.clickAddBookButton();
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
        assertEquals("Harry Potter Film", driver.findElement(By.cssSelector("[class] [class='col-md-6']:nth-of-type(2) [class='mb-2']")).getText());

    }

    @Test
    @Order(2)
    void addExistedBook() {
        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.clickAddBookButton();
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
    void addBookWithOneEmptyField(String title, String author, String description, String pictureUrl, Integer pages, String years, String month, String day, String language, String isbn, String message) {
        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.clickAddBookButton();
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

    @Test
    @Order(4)
    void checkFieldValidation() {

        MainPage mainPage = new MainPage(driver);
        BooksPage booksPage = new BooksPage(driver);
        mainPage.clickBooks();
        booksPage.clickAddBookButton();
        booksPage.clickSubmit();

        assertEquals("Book title must start with an uppercase letter, that can be followed by a mix of alphanumeric characters, spaces, and certain punctuation marks!", driver.findElement(By.cssSelector("[class] [class='mb-3']:nth-of-type(1) [role] div")).getText());
        assertEquals("Author's first and last name must start with an uppercase letter, that can be followed by one or more lowercase letters!", driver.findElement(By.cssSelector("[class] [class='mb-3']:nth-of-type(2) [role] div")).getText());
        assertEquals("Select one category at least!", driver.findElement(By.cssSelector("[class='mt-2'] [role] div")).getText());
        assertEquals("Description should start with a capital letter and is limited to a maximum of 300 characters!", driver.findElement(By.cssSelector("[class] [class='mb-3']:nth-of-type(4) [role] div")).getText());
        assertEquals("URl should start with either \"http://\" or \"https://\" and end with \".jpg\" or \".png!", driver.findElement(By.cssSelector("[class] [class='mb-3']:nth-of-type(5) [role] div")).getText());
        assertEquals("Pages field must have a value greater than 0!", driver.findElement(By.cssSelector("[class] [class='col-md-6 mb-2']:nth-of-type(1) [role] div")).getText());
        assertEquals("ISBN is incorrect", driver.findElement(By.cssSelector("[class] [class='col-md-6 mb-2']:nth-of-type(2) [role] div")).getText());
        assertEquals("Select a date, please!", driver.findElement(By.cssSelector("[class='col-md-6 mb-3'] [role] div")).getText());
        assertEquals("Language must start with an uppercase letter, that can be followed by one or more lowercase letters!", driver.findElement(By.cssSelector("[class='col-md-6'] [role] div")).getText());

    }
}