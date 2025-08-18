package Tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.RetryAnalyzer;

public class test extends BaseTest {

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void ABC1() throws InterruptedException, IOException {

		Assert.assertFalse(false);

		driver.findElement(By.name("q")).sendKeys("Automation Testing");
		System.out.println("1");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class,groups = {"sanity"})
	public void ABC2() throws InterruptedException, IOException {

		Assert.assertFalse(false);

		driver.findElement(By.name("q")).sendKeys("Automation Testing");
		System.out.println("2");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void ABC3() throws InterruptedException, IOException {

		Assert.assertFalse(false);

		driver.findElement(By.name("q")).sendKeys("Automation Testing");
		System.out.println("3");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class,groups = {"sanity"})
	public void ABC4() throws InterruptedException, IOException {

		Assert.assertFalse(false);

		driver.findElement(By.name("q")).sendKeys("Automation Testing");
		System.out.println("4");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, groups = {"sanity"})
	public void ABC5() throws InterruptedException, IOException {

		Assert.assertFalse(false);

		driver.findElement(By.name("q")).sendKeys("Automation Testing");
		System.out.println("5");
	}

}
