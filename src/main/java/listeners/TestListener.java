package listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import reports.ExtentReportManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

	ScreenshotUtil screenshotUtil;

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentReportManager.createTest(testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReportManager.getTest().pass("Test Passed ✅");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ExtentReportManager.getTest().fail(result.getThrowable());
		String testName = result.getMethod().getMethodName();
		screenshotUtil = new ScreenshotUtil();

		try {
			screenshotUtil.takeScreenshot(testName,result);

			String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + testName + ".png";
			
            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
            
		} catch (IOException e) {
			ExtentReportManager.getTest().warning("Failed to attach screenshot: " + e.getMessage());
			System.err.println("ERROR: Failed to capture screenshot on test failure for " + testName);
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReportManager.getTest().skip("Test Skipped ⚠️");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentReportManager.initReports();

	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.flushReports();
		ExtentReportManager.unload();
	}

}
