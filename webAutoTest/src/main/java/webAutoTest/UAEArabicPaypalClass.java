package webAutoTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
created date :12-6-18 version:Ecplise Oxygen,Selenium -3.11.0,Testng-6.13.1,Maven-3.7.0
Target - To verify all sites are up
*/

public class UAEArabicPaypalClass {
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;
	SendEMailcitruss objSendEMail = new SendEMailcitruss();
	private static int invalidImageCount;

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
		driver.get("https://ar-ae.citrusstv.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(8000);
		String EnglishMenu = UAEArabicPaypalClass.UAEArabicPaypal(driver);
		if (EnglishMenu.equals("fail")) {
			Assert.assertEquals(EnglishMenu, "verifying English Main Stores are up");
			logger.log(LogStatus.FAIL, "Test Case (failTest) Status is failed");
		}

	}

	@Test
	public static String UAEArabicPaypal(WebDriver driver) throws InterruptedException {

		try {
			invalidImageCount = 0;
			List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			// System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyimageActive(imgElement);

				}
			}
			System.out.println("Total no. of invalid images are " + invalidImageCount);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		try {			
			driver.findElement(By.xpath("/html/body/div[3]/header/div[2]/div[2]/div[1]/div/div/div/div[2]/div/div/div/ul/li[1]/a/span")).click();
			driver.findElement(By.cssSelector(".action.tocart.primary.show-tooltip")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(3000);
			driver.findElement(By.xpath("html/body/div[3]/header/div[2]/div[1]/div/div[3]/div/div[1]/a/span[3]"))
					.click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(8000);
			driver.findElement(By.xpath("//*[@id='top-cart-btn-checkout']")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(8000);

			driver.findElement(By.xpath("//*[@id='email']")).clear();
			driver.findElement(By.xpath("//*[@id='email']")).sendKeys("ctv.testnew@ctv-it.net");
			driver.findElement(By.xpath("//*[@id='pass']")).clear();
			driver.findElement(By.xpath("//*[@id='pass']")).sendKeys("Test111111");
			driver.findElement(By.xpath("//*[@id='send2']")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(5000);

			WebElement scrolldown1 = driver.findElement(By.xpath("//*[@id='opc-shipping_method']/div/div[1]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrolldown1);
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			try {

				Boolean isPresent = driver.findElements(By.cssSelector(".button.action.continue.primary")).size() < 0;
				// System.out.println("address selected properly" +isPresent);

				if (isPresent == false) {
					driver.findElement(By.xpath("//*[@id='shipping-method-buttons-container']/div/button")).click();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(5000);

					Boolean isverified = driver
							.findElements(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
							.size() < 0;
					// System.out.println("SMS " +isverified);

					if (isverified == false) {
						driver.findElement(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span")).click();
					}

				} else {
					String shippingText = driver
							.findElement(By.xpath("//*[@id='checkout-step-shipping_method']/div/span")).getText();

					if (shippingText.equals("ع�?واً, لا تتوا�?ر أسعار لهذا الطلب �?ي الوقت الحالي ")) {
						driver.findElement(By.xpath("//*[@id='checkout-step-shipping']/div[1]/div/div/div[1]/button"))
								.click();
						WebElement scrolldown2 = driver
								.findElement(By.xpath("//*[@id='opc-shipping_method']/div/div[1]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrolldown2);
						Thread.sleep(3000);
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						driver.findElement(By.xpath("//*[@id='shipping-method-buttons-container']/div/button")).click();
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);

						Boolean phoneverify = driver
								.findElements(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
								.size() < 0;
						// System.out.println("SMS " + phoneverify);

						if (phoneverify == false) {
							driver.findElement(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
									.click();
						}

						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);

					} else if (shippingText.equals("ع�?واً, لا تتوا�?ر أسعار لهذا الطلب �?ي الوقت الحالي")) {
						driver.findElement(By.xpath("//*[@id='checkout-step-shipping']/div[1]/div/div/div[2]/button"))
								.click();
						WebElement scrolldown2 = driver
								.findElement(By.xpath("//*[@id='opc-shipping_method']/div/div[1]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrolldown2);
						Thread.sleep(3000);
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						driver.findElement(By.xpath("//*[@id='shipping-method-buttons-container']/div/button")).click();
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);

						Boolean phoneverify = driver
								.findElements(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
								.size() < 0;
						// System.out.println("SMS " + phoneverify);

						if (phoneverify == false) {
							driver.findElement(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
									.click();
						}

						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);

					} else if (shippingText.equals("ع�?واً, لا تتوا�?ر أسعار لهذا الطلب �?ي الوقت الحالي ")) {
						driver.findElement(By.xpath("//*[@id='checkout-step-shipping']/div[1]/div/div/div[3]/button"))
								.click();
						WebElement scrolldown3 = driver
								.findElement(By.xpath("//*[@id='opc-shipping_method']/div/div[1]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrolldown3);
						Thread.sleep(3000);
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						driver.findElement(By.xpath("//*[@id='shipping-method-buttons-container']/div/button")).click();
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);

						Boolean phoneverify = driver
								.findElements(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
								.size() < 0;
						// System.out.println("SMS " + phoneverify);

						if (phoneverify == false) {
							driver.findElement(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
									.click();
						}

						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);
					} else if (shippingText.equals("ع�?واً, لا تتوا�?ر أسعار لهذا الطلب �?ي الوقت الحالي ")) {
						driver.findElement(By.xpath("//*[@id='checkout-step-shipping']/div[1]/div/div/div[3]/button"))
								.click();
						WebElement scrolldown = driver
								.findElement(By.xpath("//*[@id='opc-shipping_method']/div/div[1]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrolldown);
						Thread.sleep(3000);
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						driver.findElement(By.xpath("//*[@id='shipping-method-buttons-container']/div/button")).click();
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);

						Boolean phoneverify = driver
								.findElements(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
								.size() < 0;
						// System.out.println("SMS " + phoneverify);

						if (phoneverify == false) {
							driver.findElement(By.xpath("//*[@id='ctv-sms-form-step-1']/div/div[2]/div[2]/a/span"))
									.click();
						}
						driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(6000);
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				e.printStackTrace();

			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(6000);
			driver.findElement(By.xpath("//*[@id='paypal_express']")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(3000);

			// driver.findElement(By.xpath("//*[@id='checkout-payment-method-load']/div/div[3]/div[2]/div[3]/div/button")).click();
			System.out.println(" UAE Arabic paypal payment order placed sucessfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
			return "fail";
		}
		return "sucess";
	}

	public static void verifyimageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			org.apache.http.HttpResponse response = client.execute(request);
			// verifying response code he HttpStatus should be 200 if not,
			// increment as invalid images count
			if (response.getStatusLine().getStatusCode() != 200)
				invalidImageCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			String screenshotPath = UAEArabicPaypalClass.getScreenhot(driver, result.getName());
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
		//driver.quit();
	}

}