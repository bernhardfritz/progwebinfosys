package integration.shoppingCart;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import control.DBManager;
import model.Category;

public class ShoppingCartTest {
	
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
		for (Category category : DBManager.getInstance().getCategories()) {
			if (category.getName().equals("TestCategory") || category.getName().equals("TestCategory2")) {
				DBManager.getInstance().deleteCategory(category.getId());
			}
		}
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
	
	public void createTestItem(String categoryName, String itemName) {
		driver.findElement(By.id("createNewItem")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createItemModal")));
		driver.findElement(By.id("itemTitle")).sendKeys(itemName);
		driver.findElement(By.id("itemPrice")).sendKeys("1.11");
		driver.findElement(By.id("itemDescription")).sendKeys("ItemDesc");
		new Select(driver.findElement(By.id("itemCategory"))).selectByVisibleText(categoryName);
		driver.findElement(By.id("saveItem")).click();
	}
	
	public void fillShoppingCart(String itemName) {
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id(itemName + "ToShoppingCart")));
		driver.findElement(By.id(itemName + "ToShoppingCart")).click();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSuccessfulOrder() {
		loginAsAdmin();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createNewCategory")));
		createTestCategory("TestCategory");
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestCategory")));
		driver.findElement(By.id("categoryTestCategory")).click();
		createTestItem("TestCategory", "TestItem");
		fillShoppingCart("TestItem");
		
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("shoppingCartButton")));
		driver.findElement(By.id("shoppingCartButton")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("TestItemQuantity")));
		driver.findElement(By.id("TestItemQuantity")).clear();
		driver.findElement(By.id("TestItemQuantity")).sendKeys("2");
		driver.findElement(By.id("checkout")).click();
		
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("payWithPaypal")));
		driver.findElement(By.id("payWithPaypal")).click();
		
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("loadLogin")));
		driver.findElement(By.id("loadLogin")).click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("login_email")));
		driver.findElement(By.id("login_email")).sendKeys("g2-personal@webinfo.at");
		driver.findElement(By.id("login_password")).sendKeys("progwebinfosys");
		driver.findElement(By.id("submitLogin")).click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("continue")));
		driver.findElement(By.id("continue")).click();
		
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(By.id("statusText")));
		assertEquals("Payment successful!", driver.findElement(By.id("statusText")).getText());
	}
	
	@Test
	public void testCancelOrder() {
		loginAsAdmin();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("createNewCategory")));
		createTestCategory("TestCategory2");
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryTestCategory2")));
		driver.findElement(By.id("categoryTestCategory2")).click();
		createTestItem("TestCategory2", "TestItem2");
		fillShoppingCart("TestItem2");
		
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("shoppingCartButton")));
		driver.findElement(By.id("shoppingCartButton")).click();
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("TestItem2Quantity")));
		driver.findElement(By.id("TestItem2Quantity")).clear();
		driver.findElement(By.id("TestItem2Quantity")).sendKeys("2");
		driver.findElement(By.id("checkout")).click();
		
		new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.id("payWithPaypal")));
		driver.findElement(By.id("payWithPaypal")).click();
		
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("loadLogin")));
		driver.findElement(By.id("loadLogin")).click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("login_email")));
		driver.findElement(By.id("cancel_return")).click();
		
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(By.id("statusText")));
		assertEquals("Payment failed...", driver.findElement(By.id("statusText")).getText());
	}
}