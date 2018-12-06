package com.goemans.automation.functional.testcases.homepage;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.solocal.automation.functional.base.Base;
import com.solocal.automation.functional.util.Utility;

public class Link extends Base {

	@DataProvider(name = "getTestData")
	public static Object[][] testData() throws Exception {
		return Utility.getData("Link");
	}

	@Test(dataProvider = "getTestData")
	public void link(String scenario) throws Exception {

		// Test Case Start
		logger = report.startTest(
				(this.getClass().getSimpleName() + " :: " + Thread.currentThread().getStackTrace()[1].getMethodName()));

		// Test Execution Status
		logger.getRunStatus();

		if (!(Utility.checkTestCaseRunmode(this.getClass().getSimpleName()))) {

			logger.log(LogStatus.SKIP, "Runmode set to No");
			throw new SkipException("Runmode set to No");
		}
		try {

			if (driver == null || driver.toString().contains("null")) {
				setUp();
			} else {
				// driver.navigate().refresh();
			}

		//	driver.get("https://www.goemans.com/home/");
			// Wait for 5 seconds
			Thread.sleep(5000);

			// Used tagName method to collect the list of items with tagName "a"
			// findElements - to find all the elements with in the current page. It returns
			// a list of all webelements or an empty list if nothing matches
			List<WebElement> links = driver.findElements(By.tagName("a"));
			// To print the total number of links
			System.out.println("Total links are " + links.size());
			// used for loop to
			for (int i = 0; i < links.size(); i++) {
				WebElement element = links.get(i);
				// By using "href" attribute, we could get the url of the requried link
				String url = element.getAttribute("href");
				// calling verifyLink() method here. Passing the parameter as url which we
				// collected in the above link
				// See the detailed functionality of the verifyLink(url) method below
				verifyLink(url);
			}
		} catch (Exception e) {
		}
	}

	// The below function verifyLink(String urlLink) verifies any broken links and
	// return the server status.
	public static void verifyLink(String urlLink) {
		// Sometimes we may face exception "java.net.MalformedURLException". Keep the
		// code in try catch block to continue the broken link analysis
		try {
			// Use URL Class - Create object of the URL Class and pass the urlLink as
			// parameter
			URL link = new URL(urlLink);
			// Create a connection using URL object (i.e., link)
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(2000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
			}
			if (httpConn.getResponseCode() == 404) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
			}
		}
		// getResponseCode method returns = IOException - if an error occurred
		// connecting to the server.
		catch (Exception e) {
			// e.printStackTrace();
		}
	}
}