package integration.simulation;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimulationTest {
	
	private WebDriver driver;
	
	@Before
	public void setUp() {
		driver = new FirefoxDriver();
		driver.get("http://localhost:8081/WebShop");
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
	public void testLogin() {
		loginAsAdmin();
		
		assertEquals("Hello, admin!", driver.findElement(By.id("helloText")).getText());
	}
	
	@Test
	public void testLogout() {
		loginAsAdmin();
		logout();
		
		assertEquals(true, driver.findElement(By.id("username")).isDisplayed());
	}
	
	public void createTestCategory() {
		loginAsAdmin();
		driver.findElement(By.id("createNewCategory")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryModal")));
		driver.findElement(By.id("categoryName")).sendKeys("Test");
		driver.findElement(By.id("categoryDescription")).sendKeys("Desc");
		driver.findElement(By.id("saveCategory")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
	}
	
	@Test
	public void testCreateNewCategory() {
		createTestCategory();
		
		assertEquals("Test", driver.findElement(By.id("categoryTest")).getText());
	}
	
	@Test
	public void testEditCategory() {
		createTestCategory();
		driver.findElement(By.id("categoryTest")).click();
		driver.findElement(By.id("editCategory")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("editCategoryModal")));
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("TestNeu");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("DescNeu");
		driver.findElement(By.id("saveEditedCategory")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		
		assertEquals("TestNeu", driver.findElement(By.id("categoryTestNeu")).getText());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testDeleteCategory() {
		createTestCategory();
		driver.findElement(By.id("categoryTest")).click();
		driver.findElement(By.id("deleteCategory")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCategoryModal")));
		driver.findElement(By.id("deleteCategorySubmit")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		driver.findElement(By.id("categoryTest"));
	}
}