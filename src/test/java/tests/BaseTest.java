package tests;


import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import core.DriverFactory;


public abstract class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    @BeforeMethod
    @Parameters({"browser", "SELENIUM_GRID_URL", "mode", "platform"})
    public void setUp(String browser,
                      String gridUrl,
                      @Optional("normal") String mode,
                      String platform) throws MalformedURLException {

        logger.info("Setting up browser: {}", browser);
        logger.info("Setting up grid: {}", gridUrl);
        logger.info("Setting up mode: {}", mode);

        WebDriver driver = DriverFactory.createDriver(browser, gridUrl, mode, platform);

        threadLocalDriver.set(driver);
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
        }
    }
}
