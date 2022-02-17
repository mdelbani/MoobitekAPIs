package com.webui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class IlsLogin {


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



    }
}
