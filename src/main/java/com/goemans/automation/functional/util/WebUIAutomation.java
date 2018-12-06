package com.goemans.automation.functional.util;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.goemans.automation.functional.base.Base;

public class WebUIAutomation extends Base {

	/**
	 * This method will return locator of web element
	 * 
	 * @param strElement
	 * @param prop
	 * @return By locator
	 * @throws Exception
	 */
	private static By getLocator(String strElement, Properties prop) throws Exception {

		// retrieve the specified object from the object list
		String locator = prop.getProperty(strElement).trim();

		String locatorType = "";
		String locatorValue = "";

		if (locator.contains(">")) {
			// extract the locator type and value from the object
			locatorType = locator.substring(1, locator.indexOf(">")).trim();
			locatorValue = locator.substring(locatorType.length() + 2).trim();

		} else {
			locatorType = "xpath";
			locatorValue = locator;

		}

		// for testing and debugging purposes
		System.out.println(
				"Retrieving object of type '" + locatorType + "' and value '" + locatorValue + "' from the object map");

		// return a instance of the By class based on the type of the locator
		// this By can be used by the browser object in the actual test
		switch (locatorType.toLowerCase().trim()) {
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
		case "class":
			return By.className(locatorValue);
		case "tagname":
		case "tag":
			return By.className(locatorValue);
		case "linktext":
		case "link":
			return By.linkText(locatorValue);
		case "partiallinktext":
			return By.partialLinkText(locatorValue);
		case "cssselector":
		case "css":
			return By.cssSelector(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		default:
			throw new Exception("Unknown locator type '" + locatorType + "'");
		}
	}

	/**
	 * This function is used to identify the object on the Application
	 * 
	 * @param xpathKey
	 *            - unique sudo name which we have kept for every object on the web
	 *            page
	 * @return WebElement
	 */
	public static WebElement getObject(String locatorKey) {

		WebElement obj = null;

		try {
			By locator = getLocator(locatorKey, objectRepo);
			obj = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			obj = null;
		}
		return obj;
	}
	
	public static void Upload(String filename) {
	String FilePath1 = System.getProperty("user.dir") + File.separator + "DCO" + File.separator
			+ filename;
	StringSelection ss1 = new StringSelection(FilePath1);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss1, null); // copy the above string to clip
																				// board
	Robot robot;
	try {
		robot = new Robot();
		robot.delay(3000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL); // paste the copied string into the dialog box
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(2000);
		
	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
}

	/**
	 * This is a overloaded function is used to identify the object on the
	 * Application
	 * 
	 * @param pathKey
	 * @param prop
	 * @return
	 */
	public static WebElement getObject(String locatorKey, Properties prop) {

		WebElement obj = null;

		try {
			obj = driver.findElement(getLocator(locatorKey, prop));
		} catch (Exception e) {
			obj = null;
		}
		return obj;
	}

	/**
	 * This function is used to identify the objects on the Application
	 * 
	 * @author
	 * @param xpathKeyOfElements
	 * @return List<WebElement>
	 */
	public static List<WebElement> getObjects(String locatorKeyOfElements) {

		List<WebElement> obj;

		try {
			By locator = getLocator(locatorKeyOfElements, objectRepo);
			obj = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (Exception e) {

			obj = null;
		}

		return obj;

	}

	/**
	 * This is an overloaded function which is used to check that Web Element exist
	 * on the page or not
	 * 
	 * @param xpathKey
	 * @return boolean true/false
	 */
	public static boolean isElementPresent(String locatorKey) {

		try {
			WebElement webElem = getObject(locatorKey);
			if (!(webElem == null)) {

				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * This method will check WebElement is displayed or not
	 * 
	 * @param locatorKey
	 * @return boolean true if WebElement is displayed on webpage else boolean false
	 */
	public static boolean isElementDisplayed(String locatorKey) {

		try {
			WebElement obj = null;
			By locator = getLocator(locatorKey, objectRepo);
			obj = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return obj.isDisplayed();

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This is an overloaded function which is used to click elements on the web
	 * page
	 * 
	 * @param xpathKey
	 * @return boolean - true/false
	 */
	public static boolean clickObj(String locatorKey) {

		try {

			By locator = getLocator(locatorKey, objectRepo);

			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

			// WebElement webElem = getObject(locatorKey);
			// webElem.click();
			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This is an overloaded function which is used to enter value in the text box
	 * 
	 * @param xpathKey
	 * @param value
	 *            which needs to enter
	 * @return boolean - true/false
	 */
	public static boolean setText(String locatorKey, String value) {

		try {

			WebElement webElem = getObject(locatorKey);

			if (!(webElem == null)) {

				// Clearing the text box before typing value
				webElem.clear();

				Thread.sleep(500);

				// typing the value in the text box
				webElem.sendKeys(value);

				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This is an overloaded function used to wait for the element till it loads
	 * completely
	 * 
	 * @param xpathKey
	 * @param maxTime
	 *            maximum time in seconds for which we want the web driver to wait
	 *            for the element
	 * @return boolean - true/false
	 */
	public static boolean isObjPresent(String locatorKey, int maxTime) {

		try {
			for (int i = 0; i <= maxTime; i++) {

				if (isElementPresent(locatorKey)) {

					return true;
				}

				Thread.sleep(1000);
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

		return false;
	}

	/**
	 * This is an overloaded function which is used to select value from drop down
	 * 
	 * @param driver
	 *            instance
	 * @param xpathKey
	 * @param strValue
	 *            value which needs to be selected from drop down
	 * @return boolean - true/false
	 */
	public static boolean selectValueFromDrpDwn(String locatorKey, String strValue) {

		try {
			Select element;
			if (!strValue.contains("")) {

				element = new Select(getObject(locatorKey));
				element.selectByVisibleText(strValue);
			}

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method will select from dropdown which is not a select box.
	 * 
	 * @param containerKey
	 * @param optionsKey
	 * @param valueToSelect
	 * @return
	 */
	public static boolean selectOptionFromDrpDwn(String containerKey, String optionsKey, String valueToSelect) {

		try {

			// Click on container
			getObject(containerKey).click();

			// Select Options
			List<WebElement> options = getObjects(optionsKey);
			for (WebElement wbel : options) {

				if ((wbel.getText().trim()).equalsIgnoreCase(valueToSelect)) {

					return true;
				}

			}

			return false;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
	


	/**
	 * This method will select value from dropdown
	 * 
	 * @param containerKey
	 * @param valueToSelect
	 * @return true if element is selected successfully or else false
	 */
	public static boolean selectOptionFromDrpDwn(String containerKey, String valueToSelect) {

		try {

			// Click on container
			getObject(containerKey).click();

			Thread.sleep(2000);

			List<WebElement> options = new ArrayList<>();

			// retrieve the specified object from the object list
			String locator = objectRepo.getProperty(containerKey).trim();

			if (locator.contains("md-select")) {

				options = driver.findElements(By.xpath("//md-option"));

			} else if (locator.contains("input")) {

				options = driver.findElements(By.xpath("//ng2-menu-item"));
			}

			// System.out.println(options.size());

			for (WebElement wbel : options) {

				if ((wbel.getText().trim()).equalsIgnoreCase(valueToSelect)) {
					wbel.click();
					return true;
				}

			}
			getObject(containerKey).click();
			return false;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
	
	
	
	

	/**
	 * This method will select value from dropdown
	 * 
	 * @param containerKey
	 * @param valueToSelect
	 * @return true if element is selected successfully or else false
	 */
	public static boolean selectOptionFromDrpDwnByIndex(String containerKey, int index) {

		try {

			// Click on container
			getObject(containerKey).click();

			Thread.sleep(2000);

			List<WebElement> options = new ArrayList<>();

			// retrieve the specified object from the object list
			String locator = objectRepo.getProperty(containerKey).trim();

			if (locator.contains("md-select")) {

				options = driver.findElements(By.xpath("//md-option"));

			} else if (locator.contains("input")) {

				options = driver.findElements(By.xpath("//ng2-menu-item"));

			}

			Actions action = new Actions(driver);

			// Select Option
			action.click(options.get(index)).build().perform();
			action.release().build().perform();

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param searchText
	 * @param containerKey
	 * @param valueToSelect
	 * @return
	 */
	public static boolean searchAndselectOption(String searchText, String inputContainerKey,
			String dropdownContainerKey, String valueToSelect) {

		try {

			WebUIAutomation.setText(inputContainerKey, searchText);

			Thread.sleep(2000);

			List<WebElement> options = new ArrayList<>();

			// retrieve the specified object from the object list
			String locator = objectRepo.getProperty(dropdownContainerKey).trim();

			if (locator.contains("md-select")) {

				options = driver.findElements(By.xpath("//md-option"));

			} else if (locator.contains("input")) {

				options = driver.findElements(By.xpath("//ng2-menu-item"));
			}

			// System.out.println(options.size());

			System.out.println(options.get(0).getText().trim());

			for (int i = 0; i < options.size(); i++) {

				if ((options.get(i).getText().trim()).equalsIgnoreCase(valueToSelect)) {
					options.get(i).click();
					return true;
				}

			}
			getObject(inputContainerKey).click();
			return false;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

}
