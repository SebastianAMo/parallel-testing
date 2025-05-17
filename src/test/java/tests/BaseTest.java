package tests;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import core.DriverFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public abstract class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    @BeforeMethod
    @Parameters({"browser", "SELENIUM_GRID_URL", "mode"})
    public void setUp(String browserType,
                      String gridUrl,
                      @Optional("normal") String mode) throws MalformedURLException {

        logger.info("Setting up browser: {}", browserType);
        logger.info("Setting up grid: {}", gridUrl);
        logger.info("Setting up mode: {}", mode);

        WebDriver driver = DriverFactory.createDriver(browserType, gridUrl, mode);

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
