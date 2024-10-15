package com.core;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	
	private static WebDriver driver;
	
	public static WebDriver getDriver() {
				 		
		if (driver == null) {
			
			String browser = GlobalProperty.getProperty("webdriver.driver");
			String path = GlobalProperty.getProperty("webdriver.path");
			
			if (browser.equals("Chrome")) {		
				System.setProperty("webdriver.chrome.driver", "/C:/driver/chromedriver/chromedriver.exe");			
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			}
			
			else if (browser.equals("Chrome-headless")) {
				System.setProperty("webdriver.chrome.driver", path + "chromedriver");
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--windows-size=1200x960");
				
				driver = new ChromeDriver(options);
			}
			
			else if (browser.equals("Firefox")) {
				System.setProperty("webdriver.geckodriver.driver", path + "geckodriver");
				driver = new FirefoxDriver();				
			}
			
			else if(browser.equals("Firefox-headless")) {
				System.setProperty("webdriver.geckodriver.driver", path+ "geckodriver");
				
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless");
				options.addArguments("--windows-size=1200x960");
				
				driver = new FirefoxDriver(options);
			}
			
			else {
				System.out.println("Driver não localizado");
			}
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));					
		}
		
		return driver;				
	}
	
	public static void killDriver() {		
		if (driver != null) {
			driver.quit();
			driver = null;
		}		
	}
}