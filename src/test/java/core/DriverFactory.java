package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.HashMap;

public class DriverFactory {

    
    public static WebDriver createDriver(String browserType, String mode) {
        switch (browserType.toLowerCase()) {
            case "chrome" -> {
                return createChromeDriver(mode);
            }
            case "firefox" -> {
                return createFirefoxDriver(mode);
            }
            default -> throw new IllegalArgumentException("Not a valid browser type: " + browserType);
        }
    }

    private static WebDriver createChromeDriver(String mode) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", chromePrefs);

        if ("headless".equalsIgnoreCase(mode)) {
            options.addArguments("--headless", "--disable-gpu");
        }

        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(String mode) {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        if ("headless".equalsIgnoreCase(mode)) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }
}
