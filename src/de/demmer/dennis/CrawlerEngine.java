package de.demmer.dennis;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class CrawlerEngine {



	private WebDriver driver;

	public CrawlerEngine(){
	    // PhantomJS wird geladen
        System.setProperty("phantomjs.binary.path", "executables\\phantomjs.exe");

        driver = new PhantomJSDriver();

    }


	public void stop() {
		
		driver.quit();

	}

	public WebDriver getDriver() {

		return driver;
	}

}
