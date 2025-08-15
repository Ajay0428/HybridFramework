package Tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import drivers.DriverFactory;

public class test extends BaseTest {
	WebDriver driver;

	@Test
	public void ABC() throws InterruptedException, IOException {

		Assert.assertFalse(true);
		System.out.println(DriverFactory.getDriver().getCurrentUrl());
	}

}
