package com.webui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class IlsLogin {

    String toastMessage;

    @BeforeClass
    public void beforeClass(){

    }

    @Test
    public void Login() throws InterruptedException {

        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.get("http://172.16.3.33:8090");
        driver.findElement(By.name("username")).sendKeys("mdelbani");
        driver.findElement(By.name("password")).sendKeys("MDelb@n1");
        driver.findElement(By.cssSelector(".mat-button-wrapper")).click();
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector("ils-games.ng-star-inserted > button:nth-child(1) > span:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".mat-select-value-text > span:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#mat-option-2 > span:nth-child(1)")).click();
        driver.findElement(By.name("name")).sendKeys("Automated Pick3 Game");
        driver.findElement(By.cssSelector(".mat-raised-button > span:nth-child(1)")).click();
        toastMessage = String.valueOf(driver.findElement(By.xpath("//*[@id=\"toast-container\"]")));
//        assert toastMessage == "Game created Game Automated Pick3 Game has been created";
//        System.out.println("Test Passed");

    }
}
