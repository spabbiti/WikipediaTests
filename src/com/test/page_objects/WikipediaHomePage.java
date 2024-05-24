package com.test.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WikipediaHomePage {
    protected WebDriver driver;
    private By loginButtonById = By.id("pt-login-2");
    private final static String HOMEPAGE_TITLE = "Wikipedia, the free encyclopedia";

    private final static String WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/Main_Page";

    public WikipediaHomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(WIKIPEDIA_URL);
        if (!driver.getTitle().equals(HOMEPAGE_TITLE)) {
            throw new IllegalStateException("This is not Home Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public WikipediaLoginPage navigateToLoginPage() {
        driver.findElement(loginButtonById).click();
        return new WikipediaLoginPage(driver);
    }

}
