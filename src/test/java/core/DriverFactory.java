package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;

public class DriverFactory {

    private static final String downloadFilepath = Paths.get(System.getProperty("user.home"), "Downloads").toString();

    public static WebDriver createDriver(String browserType, String gridUrl, String mode) throws MalformedURLException {
        switch (browserType.toLowerCase()) {
            case "chrome":
                return createChromeDriver(gridUrl, mode);
            case "firefox":
                return createFirefoxDriver(gridUrl, mode);
            default:
                throw new IllegalArgumentException("Not a valid browser type: " + browserType);
        }
    }

    private static WebDriver createChromeDriver(String gridUrl, String mode) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("safebrowsing.enabled", true);
        chromePrefs.put("download.default_directory", downloadFilepath);
        options.setExperimentalOption("prefs", chromePrefs);

        if ("headless".equalsIgnoreCase(mode)) {
            options.addArguments("--headless", "--disable-gpu");
        }

        options.addArguments("--start-maximized");
        return new RemoteWebDriver(new URL(gridUrl), options);
    }

    private static WebDriver createFirefoxDriver(String gridUrl, String mode) throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();

        if ("headless".equalsIgnoreCase(mode)) {
            options.addArguments("--headless");
        }

        return new RemoteWebDriver(new URL(gridUrl), options);
    }
}
