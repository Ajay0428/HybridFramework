package base;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import config.ConfigReader;
import drivers.DriverFactory;

public class BaseTest {

	protected WebDriver driver;
	DriverFactory driverFactory = new DriverFactory();
	ConfigReader config = new ConfigReader();

	@BeforeMethod
	public void setup(Method method, ITestContext context) throws IOException {

		String browser = config.getProperty("browser");
		if (browser == null || browser.isEmpty()) {
			browser = "chrome";
		}

		DriverFactory.initializeDriver(browser);
		driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
		DriverFactory.getDriver().get(config.getProperty("baseUrl"));
	}

	@AfterMethod
	public void teardown() {

		driverFactory.quitDriver();

	}
}
