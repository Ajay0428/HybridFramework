package reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	// Initializes ExtentReports only once (Singleton pattern)
	public static void initReports() {
		
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReports/Report_" + timeStamp
					+ ".html";
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			
			sparkReporter.config().setReportName("Automation test results");
			sparkReporter.config().setDocumentTitle("Test Results");
			
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Tester", "Ajay Namala");
		}
	}

	public static synchronized ExtentTest createTest(String testName) {
		ExtentTest extentTest = extent.createTest(testName);
		test.set(extentTest);
		return extentTest;
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void flushReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	public static void unload() {
		test.remove();
	}
}
