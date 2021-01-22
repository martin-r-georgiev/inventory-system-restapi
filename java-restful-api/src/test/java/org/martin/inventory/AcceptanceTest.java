package org.martin.inventory;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AcceptanceTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setupWebdriverChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15).getSeconds());
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void verifyApplicationUserLogin() {
        try {
            driver.get("http://localhost:3000/");
            //WebElement loginButton = driver.findElement(By.cssSelector("#navbarCollapse > ul > li:nth-child(1) > a"));
            WebElement loginButton = wait.until(presenceOfElementLocated(By.cssSelector("#navbarCollapse > ul > li:nth-child(1) > a")));
            Actions action = new Actions(driver);
            action.moveToElement(loginButton).click().perform();

            WebElement usernameTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-username")));
            usernameTextbox.sendKeys("admin");

            WebElement passwordTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-password")));
            passwordTextbox.sendKeys("admin");

            driver.findElement(By.id("login-submit")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div > div > div > div > img")));

            driver.navigate().refresh();
        } finally {
            teardown();
        }
    }

    @Test
    public void addItemToWarehouseTest() {
        try {
            driver.get("http://localhost:3000/");

            // Log in
            WebElement loginButton = wait.until(presenceOfElementLocated(By.cssSelector("#navbarCollapse > ul > li:nth-child(1) > a")));
            Actions action = new Actions(driver);
            action.moveToElement(loginButton).click().perform();

            WebElement usernameTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-username")));
            usernameTextbox.sendKeys("admin");

            WebElement passwordTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-password")));
            passwordTextbox.sendKeys("admin");

            driver.findElement(By.id("login-submit")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div > div > div > div > img")));

            driver.navigate().refresh();

            // Dashboard navigation

            wait.until(presenceOfElementLocated(By.id("dashboard-button"))).click();
            wait.until(presenceOfElementLocated(By.id("additem-button"))).click();

            WebElement nameTextbox = wait.until(presenceOfElementLocated(By.id("newitem-name")));
            nameTextbox.sendKeys("New Item");

            WebElement quantityTextbox = wait.until(presenceOfElementLocated(By.id("newitem-quantity")));
            quantityTextbox.sendKeys("50");

            driver.findElement(By.id("newitem-submit")).click();
        } finally {
            teardown();
        }
    }

    @Test
    public void editItemFromWarehouseTest() {
        try {
            driver.get("http://localhost:3000/");

            // Log in
            WebElement loginButton = wait.until(presenceOfElementLocated(By.cssSelector("#navbarCollapse > ul > li:nth-child(1) > a")));
            Actions action = new Actions(driver);
            action.moveToElement(loginButton).click().perform();

            WebElement usernameTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-username")));
            usernameTextbox.sendKeys("admin");

            WebElement passwordTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-password")));
            passwordTextbox.sendKeys("admin");

            driver.findElement(By.id("login-submit")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div > div > div > div > img")));

            driver.navigate().refresh();

            // Dashboard navigation

            wait.until(presenceOfElementLocated(By.id("dashboard-button"))).click();
            wait.until(presenceOfElementLocated(By.cssSelector("#root > div > section > div.container-fluid.m-3 > div > div > div:nth-child(1) > div > div > div.col-4.col-sm-3.d-flex.justify-content-end > button.btn.btn.btn-outline-info.btn-sm.mr-2"))).click();

            WebElement nameTextbox = wait.until(presenceOfElementLocated(By.id("edititem-name")));
            nameTextbox.sendKeys("Updated Item");

            WebElement quantityTextbox = wait.until(presenceOfElementLocated(By.id("edititem-quantity")));
            quantityTextbox.sendKeys("1234");

            driver.findElement(By.id("edititem-submit")).click();
        } finally {
            teardown();
        }
    }

    @Test
    public void deleteItemFromWarehouseTest() {
        try {
            driver.get("http://localhost:3000/");

            // Log in
            WebElement loginButton = wait.until(presenceOfElementLocated(By.cssSelector("#navbarCollapse > ul > li:nth-child(1) > a")));
            Actions action = new Actions(driver);
            action.moveToElement(loginButton).click().perform();

            WebElement usernameTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-username")));
            usernameTextbox.sendKeys("admin");

            WebElement passwordTextbox = wait.until(presenceOfElementLocated(By.id("login-textinput-password")));
            passwordTextbox.sendKeys("admin");

            driver.findElement(By.id("login-submit")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div > div > div > div > img")));

            driver.navigate().refresh();

            // Dashboard navigation

            wait.until(presenceOfElementLocated(By.id("dashboard-button"))).click();
            wait.until(presenceOfElementLocated(By.cssSelector("#root > div > section > div.container-fluid.m-3 > div > div > div:nth-child(1) > div > div > div.col-4.col-sm-3.d-flex.justify-content-end > button.btn.btn.btn-outline-danger.btn-sm"))).click();

            wait.until(presenceOfElementLocated(By.id("deleteitem-submit"))).click();
        } finally {
            teardown();
        }
    }
}
