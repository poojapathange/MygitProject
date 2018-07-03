package webAutoTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/*Author :Pooja ps
created date :10-6-18 version:Ecplise Oxygen,Selenium -3.11.0,Testng-6.13.1,Maven-3.7.0
Target - To verify all sites are up
*/

public class MainMenuLinksEnglishClass {
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;
	SendEMailcitruss objSendEMail = new SendEMailcitruss();  

	@BeforeTest
	public void startReport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/STMExtentReport.html", true);
		extent.addSystemInfo("Host Name", "citruss tv").addSystemInfo("Environment", "Automation Testing")
				.addSystemInfo("User Name", "Pooja PS");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
	}

	// This method is to capture the screenshot and return the path of the
	// screenshot.

	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@Test
	public void passTest() {
		logger = extent.startTest("passTest");
		Assert.assertTrue(true);
		logger.log(LogStatus.PASS, "Test Case Passed is passTest");
	}

	@Test
	public void failTest() throws InterruptedException {
		logger = extent.startTest("failTest");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\PoojaPatange\\Downloads\\workfolder\\chromedrive\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://en-ae.citrusstv.com");

		String EnglishMenu = MainMenuLinksEnglishClass.englishlinks(driver);
		if (EnglishMenu.equals("fail")) {
			Assert.assertEquals(EnglishMenu, "verifying English Main Menu links are up");
			logger.log(LogStatus.FAIL, "Test Case (failTest) Status is failed");
		}

	}

	@Test
	public static String englishlinks(WebDriver driver) throws InterruptedException {
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			Thread.sleep(9000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[1]/a/span"))
					.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[2]/a/span"))
					.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[3]/a/span"))
					.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[4]/a/span"))
					.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[5]/a/span"))
					.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[6]/a/span"))
					.click();
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath(
					"/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[7]/a/span"))
					.click();
			System.out.println("Verified All Main Menu Links Are Working For English");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}

		return "sucess";
	}

	/*
	 * @Test public void skipTest(){ logger = extent.startTest("skipTest"); throw
	 * new SkipException("Skipping - This is not ready for testing "); }
	 */

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println(" Main Menu Test Cases have been failed");
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			String screenshotPath = MainMenuLinksEnglishClass.getScreenhot(driver, result.getName());
			System.out.println("Taken screenshot");
			objSendEMail.emailsend(screenshotPath);// send email
			System.out.println("Sent To Mail ID");
			// To add it in the extent report
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		extent.endTest(logger);

	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
		driver.close();
	}

}