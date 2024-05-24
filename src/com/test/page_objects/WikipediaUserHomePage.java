package com.test.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WikipediaUserHomePage {
    protected WebDriver driver;

    private By userDisplayNameBy = By.id("pt-userpage-2");
    private String userDisplayName;

    private By searchBarBy = By.id("searchInput");

    private By addToWatchListBy = By.id("ca-watch");
    private By watchListPageBy = By.linkText("Watchlist");

    public WikipediaUserHomePage(WebDriver driver, String userDisplayName) {
        this.driver = driver;
        this.userDisplayName = userDisplayName;
        if (!driver.findElement(userDisplayNameBy).getText().equals(userDisplayName)) {
            throw new IllegalStateException("This is not User Home Page," +
                    " current page is: " + this.userDisplayName);
        }
    }

    public void searchArticle(String searchString) {
        WebElement searchBar = driver.findElement(searchBarBy);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(searchBar));
        searchBar.sendKeys(searchString);
        searchBar.sendKeys(Keys.RETURN);
    }

    public void addArticleToWatchlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addToWatchListBy));
        driver.findElement(addToWatchListBy).click();
    }

    public WikipediaWatchlistPage navigateToWatchlistPage() {
        driver.findElement(watchListPageBy).click();
        return new WikipediaWatchlistPage(driver);
    }

}
