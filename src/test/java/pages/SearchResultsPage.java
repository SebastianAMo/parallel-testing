package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultsPage extends BasePage {
    private final By resultItems = By.id("video-title");
    private final By resultsLink = By.className("yt-core-attributed-string__link");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }


    public List<WebElement> resultItems() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultsLink));
    }
}
