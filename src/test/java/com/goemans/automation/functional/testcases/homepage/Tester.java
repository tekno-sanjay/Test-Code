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

public class Tester extends Base {

	@DataProvider(name = "getTestData")
	public static Object[][] testData() throws Exception {
		return Utility.getData("Tester");
	}

	@Test(dataProvider = "getTestData")
	public void tester(String scenario) throws Exception {

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
			List<WebElement> links = driver.findElements(By.tagName("img"));
			int brokenImagesCount=0;
			for (int i = 0; i < links.size(); i++)
			{
				System.out.println(links.get(i)); 
				String linkURL=links.get(i).getAttribute("src");
				System.out.println(links.get(i).getText());
				URL url = new URL(linkURL);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				http.setConnectTimeout(10000);
				http.setReadTimeout(20000);
				int statusCode=http.getResponseCode();
				if(statusCode==404||statusCode==500){ brokenImagesCount=brokenImagesCount+1;
				System.out.println(linkURL+"and its Status codes is:"+statusCode);
				}
				
			System.out.println("total number of broken images are: "+brokenImagesCount);
			}
		} catch (Exception e) {
		}
	}

}