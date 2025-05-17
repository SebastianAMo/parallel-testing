package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage {
    private final By resultItems = By.id("video-title");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public VideoPage clickFirstResult() {
        List<WebElement> videos = driver.findElements(resultItems);
        if (videos.isEmpty()) {
            throw new RuntimeException("No video results found!");
        }
        videos.get(0).click();
        return new VideoPage(driver);
    }
}
