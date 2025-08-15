package drivers;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static WebDriver initializeDriver(String browserName) throws IOException {

		if (browserName == null || browserName.trim().isEmpty()) {
			throw new IllegalArgumentException("Browser name cannot be null or empty.");
		}

		browserName = browserName.trim().toLowerCase();

		try {
			switch (browserName) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();

				// Disable password leak detection prompt
				options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");
				options.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/chrome-profile");
				options.addArguments("--incognito");
				options.addArguments("--disable-sync");
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-save-password-bubble");

				driver.set(new ChromeDriver(options));
				break;

			case "edge":
				WebDriverManager.edgedriver().setup();
				// WebDriverManager.edgedriver().driverVersion("138.0.0").setup();
				driver.set(new EdgeDriver());
				// driver = new EdgeDriver();
				break;

			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver());
				break;

			default:
				throw new IllegalArgumentException("Unsupported browser: " + browserName);
			}

		} catch (IllegalStateException e) {
			// Specific WebDriverManager error for misconfigurations
			throw new IOException("Failed to set up WebDriver for browser: " + browserName, e);

		} catch (Exception e) {
			// Catch any unknown issues
			throw new IOException("Unexpected error while initializing WebDriver for browser: " + browserName, e);
		}

		WebDriver webDriver = getDriver(); // Avoid multiple method calls
		if (webDriver == null) {
			throw new IllegalStateException("WebDriver is null after initialization for browser: " + browserName);
		}

		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		return webDriver;
	}

	public void quitDriver() {
		WebDriver webDriver = getDriver();
		if (webDriver != null) {
			webDriver.quit();
			driver.remove();
		}
	}

	public static void main(String[] args) throws IOException {

		DriverFactory factory = new DriverFactory();
		ConfigReader reader = new ConfigReader();
		WebDriver driver = DriverFactory.initializeDriver(reader.getProperty("browser"));

		driver.get("https://www.google.com");
		System.out.println("Title: " + driver.getTitle());

		factory.quitDriver();
	}
}
