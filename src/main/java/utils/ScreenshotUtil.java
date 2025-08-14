package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import drivers.DriverFactory;

public class ScreenshotUtil {

	DriverFactory driverFactory = new DriverFactory();
	WebDriver driver;
	String timeStamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss_a").format(new Date());

	public void takeScreenshot(String testName, ITestResult result) throws IOException {

		if (testName == null || testName.trim().isEmpty()) {
			System.err.println("ERROR: Screenshot name (testName) cannot be null or empty.");
			return;
		}

		driver = DriverFactory.getDriver();
		if (driver == null) {
			System.err.println("ERROR: WebDriver instance is null. Screenshot cannot be taken.");
			return;
		}

		// Take the screenshot
		File srcFile;

		try {
			srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		} catch (Exception e) {
			System.err.println("ERROR: Failed to capture screenshot for test: " + testName);
			e.printStackTrace();
			return;
		}

		// Build destination folder and file
		String destDirPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator
				+ "screenshots";

		File destDir = new File(destDirPath);

		if (!destDir.exists()) {
			boolean created = destDir.mkdirs();
			if (!created) {
				System.err.println("ERROR: Could not create screenshot directory at: " + destDirPath);
				return;
			}
		}

		File destFile = new File(destDirPath + File.separator + testName + "- " + timeStamp + ".png");

		// Copy screenshot to destination
		try {
			FileUtils.copyFile(srcFile, destFile);
			System.out.println("INFO: Screenshot saved at: " + destFile.getAbsolutePath());

		} catch (IOException e) {

			System.err.println("ERROR: Unable to save screenshot to " + destFile.getAbsolutePath());
			throw e;
		}
	}
}
