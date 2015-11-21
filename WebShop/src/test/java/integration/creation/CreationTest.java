package integration.creation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreationTest {
	
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
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestCategory")));
			driver.findElement(By.id("categoryTestCategory")).click();
			driver.findElement(By.id("deleteCategory")).click();
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCategoryModal")));
			driver.findElement(By.id("deleteCategorySubmit")).click();
		}
		catch(NoSuchElementException e) {
			System.err.println("Cannot delete category TestCategory.");
		}
		
		driver.close();
	}
	
	public void loginAsAdmin() {
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("secret");
		driver.findElement(By.id("signIn")).click();
	}
	
	public void createTestCategory(String name) {
		driver.findElement(By.id("createNewCategory")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createCategoryModal")));
		driver.findElement(By.id("categoryName")).sendKeys(name);
		driver.findElement(By.id("categoryDescription")).sendKeys("Desc");
		driver.findElement(By.id("saveCategory")).click();
	}
	
	public void createTestItem() {
		driver.findElement(By.id("createNewItem")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createItemModal")));
		driver.findElement(By.id("itemTitle")).sendKeys("TestItem");
		driver.findElement(By.id("itemPrice")).sendKeys("11.11");
		driver.findElement(By.id("itemDescription")).sendKeys("ItemDesc");
		new Select(driver.findElement(By.id("itemCategory"))).selectByVisibleText("TestCategory");
		driver.findElement(By.id("saveItem")).click();
	}
	
	public void createTestComment() {
		driver.findElement(By.id("createComment")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createCommentModal")));
		driver.findElement(By.id("message-text")).sendKeys("TestComment");
		driver.findElement(By.id("rating4")).click();
		driver.findElement(By.id("message-text")).submit();
	}
	
	@Test
	public void testCreateAll() {
		loginAsAdmin();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createNewCategory")));
		createTestCategory("TestCategory");
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestCategory")));
		assertEquals("TestCategory", driver.findElement(By.id("categoryTestCategory")).getText());
		
		driver.findElement(By.id("categoryTestCategory")).click();
		createTestItem();
		assertEquals("http://localhost:8080/WebShop/item.jsp?itemId=", driver.getCurrentUrl().substring(0, 46));
		
		createTestComment();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("commentTestComment")));
		assertEquals("TestComment", driver.findElement(By.id("commentTestComment")).getText());
	}
}