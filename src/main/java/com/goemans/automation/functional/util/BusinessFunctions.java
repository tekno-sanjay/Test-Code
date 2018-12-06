package com.solocal.automation.functional.util;

import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.ExpectedConditions;

import com.solocal.automation.functional.base.Base;

public class BusinessFunctions extends Base {

	/**
	 * This method will login to the application
	 * 
	 * @param userName
	 * @param password
	 * @return true if login is successful else false
	 */
	public static boolean login(String userName, String password) {

		try {
			if (!loggedin) {
				// Enter text in [Email field] of "Login" popup
				WebUIAutomation.setText("TXT_Username_LOGINPAGE", userName);
				// driver.findElement(By.xpath("TXT-VALID-USERNAME")).sendKeys(userName);

				// Enter text in [Password field] of "Login" popup
				WebUIAutomation.setText("TXT_Password_LOGINPAGE", password);

				// driver.findElement(By.xpath("TXT-VALID-PASSWORD")).sendKeys(password);
				// Clicking on [Login] button
				WebUIAutomation.clickObj("BTN_Submit_LOGINPAGE");

				System.out.println("Logged in");

				loggedin = true;
			}

			return loggedin;

		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * This method will logout from application
	 * 
	 * @return true is logout is successful else false
	 */
	public static boolean logout() {

		try {

			driver.findElement(By.xpath("//a[text()='Logout']")).click();

			if (driver.findElement(By.xpath("//a[text()='Login']"))
					.isDisplayed()) {
				loggedin = false;
				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			return false;
		}
	}

}
