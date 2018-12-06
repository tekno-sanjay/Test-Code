package com.goemans.automation.functional.util;

import java.io.File;
//import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import javax.swing.Spring;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverException;

import com.goemans.automation.functional.base.Base;

public class Utility extends Base {

	/**
	 * This method will help to determine whether test case is runnable or not
	 * 
	 * @param testCase
	 * @param string 
	 * @param string 
	 * @return
	 */
	
	  
	public static boolean checkTestCaseRunmode(String testCase) {
		for (int i = 2; i <= datatable.getRowCount("Tests"); ++i) {
			if (datatable.getCellData("Tests", "Test Case ID", i).equalsIgnoreCase(testCase)) {
				return (datatable.getCellData("Tests", "Runmode", i).equalsIgnoreCase("Y"));
			}

		}

		datatable = null;
		return false;
	}


	  /*
	   * This function is used to get the complete data from the sheet into 2
	   * dimensional array
	   * 
	   * @param xls
	   *            instance of Test Case Excel file
	   * @param sheetName
	   *            Name of the sheet inside which Data is present
	   * @return Object[][] stores the data into 2D array
	   */
	public static Object[][] getData(String sheetName) {
		if (!(datatable.isSheetExist(sheetName))) {
			datatable = null;
			return new Object[1][0];
		}

		int rows = datatable.getRowCount(sheetName) - 1;
		if (rows <= 0) {
			Object[][] testData = new Object[1][0];
			return testData;
		}

		rows = datatable.getRowCount(sheetName);
		int cols = datatable.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; ++rowNum) {
			for (int colNum = 0; colNum < cols; ++colNum) {
				data[(rowNum - 2)][colNum] = datatable.getCellData(sheetName, colNum, rowNum);
			}
		}

		return data;
	}


	

	
	/**
	 * Generates file name with '.jpg' extension using the method argument as
	 * keyword. FORMAT - YEAR-MONTH-DAY-HOUR-MIN-SEC-filename.jpg
	 * 
	 * @param filename
	 *            keyword to be used during creation of file
	 * @return file name
	 */

	public static void screenshot(WebDriver driver, String SCNAME)

	{
		try
		{
			TakesScreenshot TS = (TakesScreenshot) driver;
			File source = TS.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./screenshots/" + SCNAME));
		}
		catch (Exception e) {
			System.out.println("Exception while taking screenshot" + e.getMessage());

		}
	}

	public static String generateRandomImageFilename(String SC) {
		Calendar c = Calendar.getInstance();
		SC = "" + c.get(Calendar.YEAR) + "_" + new SimpleDateFormat("MMM").format(c.getTime()) + "_" + c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.HOUR_OF_DAY) + "_" + c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND) + "-" + SC
				+ ".jpg";
		return SC;
	}

	/**
	 * This method will help to take test data from excel sheet
	 * 
	 * @param sheetName
	 * @return
	 */
		
/*		{

	datatable = null;
		//	return false;
		} */

	
	public static int getNumberFromString(String str) {

		String numberOnly = str.replaceAll("[^0-9]", "");
		return Integer.parseInt(numberOnly);
	}
	
	

}
