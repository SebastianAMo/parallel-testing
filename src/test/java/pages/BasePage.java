package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;

    protected WebDriverWait wait;
    protected WebDriverWait longWait;
    protected FluentWait<WebDriver> fluentWait;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .ignoring(org.openqa.selenium.StaleElementReferenceException.class);
        this.actions = new Actions(driver);
    }
}