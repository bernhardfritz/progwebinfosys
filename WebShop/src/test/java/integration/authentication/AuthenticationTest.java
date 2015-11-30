package integration.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthenticationTest {
	
	private static WebDriver driver;
	
	@Before
	public void setUp() {
		driver = new FirefoxDriver();
		driver.get("http://localhost:8080/WebShop");
		driver.manage().window().maximize();
	}
	
	@After
	public void closeDown() {
		driver.close();
	}
	
	public void loginAsAdmin() {
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("secret");
		driver.findElement(By.id("signIn")).click();
	}
	
	public void logout() {
		driver.findElement(By.id("logout")).click();
	}

	@Test
	public void testSuccessfullLogin() {
		loginAsAdmin();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		
		assertEquals("Hello, admin!", driver.findElement(By.id("helloText")).getText());
	}
	
	@Test(expected = TimeoutException.class)
	public void testFailLogin() {
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("abc");
		driver.findElement(By.id("signIn")).click();
		
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
	}
	
	@Test
	public void testLogout() {
		loginAsAdmin();
		logout();
		
		assertTrue(driver.findElement(By.id("username")).isDisplayed());
	}
	
	@Test
	public void testRegister() {
		String username = "newTestUser";
		String password = "testPassword";
		
		driver.findElement(By.id("signUp")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("signUpModal")));
		driver.findElement(By.id("usernameSignup")).sendKeys(username);
		driver.findElement(By.id("passwordSignup")).sendKeys(password);
		driver.findElement(By.id("confirmpassword")).sendKeys(password);
		driver.findElement(By.id("confirmpassword")).submit();
		
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("signIn")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		
		assertEquals("Hello, " + username + "!", driver.findElement(By.id("helloText")).getText());
	}
}