
package com.example.seleniumtest1;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocalLoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {

        // Configure Chrome for GitHub Actions
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        String page = new File("webpage/login.html")
                .getAbsoluteFile()
                .toURI()
                .toString();

        driver.get(page);
    }

    // Test Case 1: Valid Student Registration
    @Test
    public void validRegistrationTest() {

        driver.findElement(By.id("studentName"))
                .sendKeys("Jayanthi");

        driver.findElement(By.id("email"))
                .sendKeys("jayanthi@gmail.com");

        driver.findElement(By.id("usn"))
                .sendKeys("4VV23AI001");

        Select course = new Select(
                driver.findElement(By.id("course")));

        course.selectByValue("AIML");

        driver.findElement(By.id("registerButton")).click();

        String message = driver.findElement(By.id("message"))
                .getText();

        System.out.println("Message: " + message);

        Assert.assertEquals(message, "Registration Successful");
    }

    // Test Case 2: Empty Field Validation
    @Test
    public void emptyFieldsTest() {

        driver.findElement(By.id("registerButton")).click();

        Assert.assertEquals(
                driver.findElement(By.id("nameError")).getText(),
                "Name is required");

        Assert.assertEquals(
                driver.findElement(By.id("emailError")).getText(),
                "Email is required");

        Assert.assertEquals(
                driver.findElement(By.id("usnError")).getText(),
                "USN is required");

        Assert.assertEquals(
                driver.findElement(By.id("courseError")).getText(),
                "Please select a course");

        System.out.println("Empty field validation successful");
    }

    // Test Case 3: Invalid Email Validation
    @Test
    public void invalidEmailTest() {

        driver.findElement(By.id("studentName"))
                .sendKeys("Jayanthi");

        driver.findElement(By.id("email"))
                .sendKeys("jayanthi123");

        driver.findElement(By.id("usn"))
                .sendKeys("4VV23AI001");

        Select course = new Select(
                driver.findElement(By.id("course")));

        course.selectByValue("AIML");

        driver.findElement(By.id("registerButton")).click();

        String error = driver.findElement(By.id("emailError"))
                .getText();

        System.out.println("Error: " + error);

        Assert.assertEquals(
                error, "Enter a valid email address");
    }

    // Test Case 4: Registration Success Message
    @Test
    public void successMessageTest() {

        driver.findElement(By.id("studentName"))
                .sendKeys("Rahul");

        driver.findElement(By.id("email"))
                .sendKeys("rahul@gmail.com");

        driver.findElement(By.id("usn"))
                .sendKeys("4VV23AI002");

        Select course = new Select(
                driver.findElement(By.id("course")));

        course.selectByValue("CSE");

        driver.findElement(By.id("registerButton")).click();

        String message = driver.findElement(By.id("message"))
                .getText();

        System.out.println("Success Message: " + message);

        Assert.assertTrue(message.contains("Successful"));
    }

    // Test Case 5: Background Color Verification
    @Test
    public void checkBackgroundColor() {

        String bgColor = driver.findElement(By.id("registrationBox"))
                .getCssValue("background-color");

        System.out.println("Background Color: " + bgColor);

        Assert.assertEquals(bgColor, "rgba(173, 216, 230, 1)");
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

}
