package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FirstLab {
    private WebDriver chromeDriver;
    private static final String baseUrl = "https://www.nmu.org.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }

    @BeforeMethod
    public void preconditions() {
//        chromeDriver.get(baseUrl);
    }

    @Test
    public void testFooterExists() {
        WebElement content = chromeDriver.findElement(org.openqa.selenium.By.id("content"));
        org.testng.Assert.assertNotNull(content);
    }

    @Test
    public void testGoogleMapsSearch() {
        chromeDriver.get("https://www.google.com/maps");

        WebElement searchField = chromeDriver.findElement(org.openqa.selenium.By.name("q"));
        searchField.click();

        String searchQuery = "Дніпро";
        searchField.sendKeys(searchQuery);

        org.testng.Assert.assertEquals(searchField.getAttribute("value"), searchQuery);

        WebElement searchButton = chromeDriver.findElement(org.openqa.selenium.By.xpath("//button[@aria-label='Шукати']"));
        searchButton.click();

        org.testng.Assert.assertNotNull(searchButton);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (chromeDriver != null) {
            chromeDriver.quit();
        }
    }
}