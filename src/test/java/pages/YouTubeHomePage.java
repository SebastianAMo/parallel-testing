package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YouTubeHomePage extends BasePage {
    private final By searchBox    = By.name("search_query");
    private final By searchButton = By.className("ytSearchboxComponentSearchButton");

    public YouTubeHomePage(WebDriver driver) {
        super(driver);
        driver.get("https://www.youtube.com");
    }

    public SearchResultsPage searchFor(String query) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(query);
        driver.findElement(searchButton).click();
        return new SearchResultsPage(driver);
    }
}
