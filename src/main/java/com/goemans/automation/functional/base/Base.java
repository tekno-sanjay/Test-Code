package com.goemans.automation.functional.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.goemans.automation.functional.util.MSExcelAutomation;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Base {

	public String testURL = "";
	public String userName = "";
	public String password = "";
	public static boolean loggedin = false;

	public static ExtentReports report;
	public static ExtentTest logger;

	public static WebDriver driver = null;
	public static WebDriverWait wait = null;

	public static MSExcelAutomation datatable = null;
	public static String executionBrowser = null;
	public static Properties config = null;
	public static Properties objectRepo = null;
	 
	//protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@BeforeSuite
	public void beforeSuite() throws IOException {
		// Report Directory and Report Name
		report = new ExtentReports(
				System.getProperty("user.dir") + File.separator + "report" + File.separator + "execution_report.html",
				true);

		// Supporting File for Extent Reporting
		// report.loadConfig(new File("extent-config.xml"));

		// Provide Execution Machine Information
		report.addSystemInfo("Environment", "QA-Sanity");

		datatable = new MSExcelAutomation(
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
						+ "resources" + File.separator + "testdata" + File.separator + "Controller.xls");

		// Load configuration
		config = new Properties();
		FileInputStream fp = new FileInputStream(System.getProperty("user.dir") + File.separator + "src"
				+ File.separator + "test" + File.separator + "resources" + File.separator + "config.properties");
		config.load(fp);

		// Load configuration
		objectRepo = new Properties();
		FileInputStream fip = new FileInputStream(System.getProperty("user.dir") + File.separator + "src"
				+ File.separator + "test" + File.separator + "resources" + File.separator + "ObjectRepo.properties");
		objectRepo.load(fip);
	}

	//@Parameters("browser")
	@BeforeTest
	public void setExecutionBrowser() {

		// executionBrowser = browser;
		executionBrowser = config.getProperty("browser");
		System.out.println("Execution Browser:" + executionBrowser);
	}

	public void setUp() {

		// String executionBrowser = System.getProperty("BROWSER");
		String os = System.getProperty("os.name");

		System.out.println("Before Test::" + this.getClass().getName());

		try {

			if (executionBrowser.equalsIgnoreCase("firefox")) {

				if (os.equals("Linux")) {

					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + File.separator + "lib" + File.separator + "geckodriver");

				} else if (os.startsWith("Windows")) {

					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "lib"
							+ File.separator + "geckodriver.exe");

				}

				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				// capabilities.setCapability("marionette", true);

				driver = new FirefoxDriver(capabilities);
				driver.manage().window().maximize();

			} else if (executionBrowser.equalsIgnoreCase("chrome")) {

				if (os.equals("Linux")) {

					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + File.separator + "lib" + File.separator + "chromedriver");

				} else if (os.startsWith("Windows")) {

					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator
							+ "lib" + File.separator + "chromedriver.exe");

				}

				ChromeOptions options = new ChromeOptions();

				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);

				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.addArguments("start-maximized");
				options.addArguments("--disable-notifications");

				options.addArguments("disable-infobars"); // Not good to use

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(capabilities);

			} else if (executionBrowser.equalsIgnoreCase("ie")) {

				// DesiredCapabilities ieCapabilities =
				// DesiredCapabilities.internetExplorer();
				// ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				// true);
				// driver = new InternetExplorerDriver(ieCapabilities);

			}

			// Set Implicit Wait
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			// Navigate to application URL
			driver.get(config.getProperty("testSiteURL"));

			// Thread.sleep(3000);
			wait = new WebDriverWait(driver, 20);

		} catch (SkipException e) {

			// driver = null;

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@AfterTest
	public void closeBrowser() {
		loggedin = false;
		if (driver != null) {
			driver.quit();
		}

	}
	
	@AfterSuite
	public void afterSuite() {
		System.out.println("in afterSuite");
		report.close();
	}

}
