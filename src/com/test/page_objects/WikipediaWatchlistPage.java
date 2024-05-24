package com.test.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class WikipediaWatchlistPage {
    protected WebDriver driver;

    private final static String WATCH_LIST_PAGE_TITLE = "Watchlist - Wikipedia";

    private By viewAndEditWatchListBy = By.id("ca-special-specialAssociatedNavigationLinks-link-1");
    private By removeTitlesButtonBy = By.cssSelector("button[value='Remove titles']");

    private By clearWatchListBy = By.id("ca-special-specialAssociatedNavigationLinks-link-3");
    private By clearWatchListButtonBy = By.cssSelector("button[value='Clear the watchlist (This is permanent!)']");

    private By watchListPageBy = By.linkText("Watchlist");


    public WikipediaWatchlistPage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().contains(WATCH_LIST_PAGE_TITLE)) {
            throw new IllegalStateException("This is not WatchList Page," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void removeArticleFromWatchList(String articleName) {
        String articleCheckboxString = String.format("input[value='%s']", articleName);

        WebElement checkbox = driver.findElement(By.cssSelector(articleCheckboxString));
        getWait(driver).until(ExpectedConditions.attributeContains(checkbox, "value", articleName));
        checkbox.click();
        driver.findElement(removeTitlesButtonBy).click();
    }

    public void assertWatchListContains(String articleName) {
        String articleCheckboxString = String.format("input[value='%s']", articleName);
        driver.findElement(viewAndEditWatchListBy).click();
        WebElement checkbox = driver.findElement(By.cssSelector(articleCheckboxString));
        getWait(driver).until(ExpectedConditions.attributeContains(checkbox, "value", articleName));
        Assert.assertEquals(checkbox.getAttribute("value"), articleName);
    }

    public void openArticle(String articleName) {
        driver.findElement(By.linkText(articleName)).click();
        Assert.assertEquals(driver.getTitle(), articleName + " - Wikipedia");
    }

    public void clearWatchList() {
        driver.findElement(watchListPageBy).click();
        driver.findElement(clearWatchListBy).click();
        getWait(driver).until(ExpectedConditions.elementToBeClickable(clearWatchListButtonBy));
        driver.findElement(clearWatchListButtonBy).click();
    }

    private WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

}
