package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultsPage extends BasePage {
    private final By resultItems = By.xpath("//*[@class and contains(@class,'ytd-page-manager')]//*[@id='container']//*[@id='video-title']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }


    public List<WebElement> resultItems() {
        return fluentWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultItems));
    }

    public VideoPage selectResult(int index){

        List<WebElement> results = resultItems();
        results.get(index).click();

        return new VideoPage(driver);
    }
}
