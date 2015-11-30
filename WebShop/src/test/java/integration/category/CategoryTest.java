package integration.category;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryTest {
	
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
	
	@AfterClass
	public static void clean() {
		driver = new FirefoxDriver();
		driver.get("http://localhost:8080/WebShop");
		driver.manage().window().maximize();
		
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("secret");
		driver.findElement(By.id("signIn")).click();
		
		try {
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestNeu")));
			driver.findElement(By.id("categoryTestNeu")).click();
			driver.findElement(By.id("deleteCategory")).click();
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCategoryModal")));
			driver.findElement(By.id("deleteCategorySubmit")).click();
		}
		catch(NoSuchElementException e) {
			System.err.println("Cannot delete category TestNeu.");
		}
		
		try {
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestEditNeu")));
			driver.findElement(By.id("categoryTestEditNeu")).click();
			driver.findElement(By.id("deleteCategory")).click();
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCategoryModal")));
			driver.findElement(By.id("deleteCategorySubmit")).click();
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		}
		catch(NoSuchElementException e) {
			System.err.println("Cannot delete category TestEditNeu.");
		}
		
		driver.close();
	}
	
	public void loginAsAdmin() {
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("secret");
		driver.findElement(By.id("signIn")).click();
	}
	
	public void createTestCategory(String name) {
		loginAsAdmin();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createNewCategory")));
		driver.findElement(By.id("createNewCategory")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createCategoryModal")));
		driver.findElement(By.id("categoryName")).sendKeys(name);
		driver.findElement(By.id("categoryDescription")).sendKeys("Desc");
		driver.findElement(By.id("saveCategory")).click();
	}
	
	@Test
	public void testCreateNewCategory() {
		createTestCategory("TestNeu");
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestNeu")));
		
		assertEquals("TestNeu", driver.findElement(By.id("categoryTestNeu")).getText());
	}
	
	@Test
	public void testEditCategory() {
		createTestCategory("TestEdit");
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestEdit")));
		driver.findElement(By.id("categoryTestEdit")).click();
		driver.findElement(By.id("editCategory")).click();
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.id("editCategoryModal")));
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("TestEditNeu");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("DescEditNeu");
		driver.findElement(By.id("saveEditedCategory")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		
		assertEquals("TestEditNeu", driver.findElement(By.id("categoryTestEditNeu")).getText());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testDeleteCategory() {
		createTestCategory("TestDelete");
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestDelete")));
		driver.findElement(By.id("categoryTestDelete")).click();
		driver.findElement(By.id("deleteCategory")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCategoryModal")));
		driver.findElement(By.id("deleteCategorySubmit")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("helloText")));
		driver.findElement(By.id("categoryTestDelete"));
	}
}