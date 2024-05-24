package com.test.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WikipediaLoginPage {
    protected WebDriver driver;

    private By usernameBy = By.id("wpName1");

    private By passwordBy = By.id("wpPassword1");

    private By loginButtonById = By.id("wpLoginAttempt");

    private final static String LOGINPAGE_TITLE = "Log in - Wikipedia";

    public WikipediaLoginPage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().equals(LOGINPAGE_TITLE)) {
            throw new IllegalStateException("This is not Log In Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public WikipediaUserHomePage loginUser(String userName, String password) {
        driver.findElement(usernameBy).sendKeys(userName);
        driver.findElement(passwordBy).sendKeys(password);
        driver.findElement(loginButtonById).click();
        return new WikipediaUserHomePage(driver, userName);
    }
}
