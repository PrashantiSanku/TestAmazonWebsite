package automationScripts;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonFeatureTests {
	
	ChromeDriver driver;
	
	@BeforeClass
	public void invokeBrowser()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Prashanti\\Desktop\\Prashu\\Softwares\\CD\\chromedriver.exe");
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(opt);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://www.amazon.com/");
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
	@Test(priority = 0)
	public void verifyTitleOfThePage()
	{
		String actualTitle = driver.getTitle();
		String expectedTitle = "Amazon.com. Spend less. Smile more.";
		
		Assert.assertEquals(actualTitle, expectedTitle);
		
	}
	
	@Test(priority = 10)
	public void searchProduct()
	{
		String productItem = "earphones";
		String category = "Electronics";
		
		WebElement selDropdown = driver.findElement(By.id("searchDropdownBox"));
		Select selCategory = new Select(selDropdown);
		selCategory.selectByVisibleText(category);
		
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productItem);
		driver.findElement(By.xpath("//input[@value='Go']")).click();
		
	}
	
	@Test(priority= 20)
	public void getNthProduct()
	{
		int productItemNumber = 3;
		
		String xpathExpression = String.format("//div[@data-component-type='s-search-result'][%d]", productItemNumber);
		WebElement nthProduct = driver.findElement(By.xpath(xpathExpression));
		String nthProductResult = nthProduct.getText();
		System.out.println(nthProductResult);
		
	}

	@Test(priority = 30, enabled = false)
	public void getAllProducts()
	{
		List<WebElement> allProducts = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		String productResult;
		for(WebElement product : allProducts)
		{
			productResult = product.getText();
			System.out.println(productResult);
			System.out.println("------------------------------");
			
			
		}
	}
	
	@Test(priority = 40)
	public void searchAllProductsViaScrollDown()
	{
		List<WebElement> allProducts = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		String productResult;
		Actions action = new Actions(driver);
		
		for(WebElement product : allProducts)
		{
			action.moveToElement(product).build().perform();
			productResult = product.getText();
			System.out.println(productResult);
			System.out.println("------------------------------");
			
			
		}
	}
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
}
