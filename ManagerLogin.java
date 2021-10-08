package Test.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ManagerLogin {

	public WebDriver driver;
	public WebElement element;
	Properties prop = new Properties();

	@BeforeMethod
	public void beforeMethod() throws IOException {

		try {
			FileReader reader = new FileReader("config.properties");

			prop.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void login() {
		try {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");
			
			driver = new ChromeDriver();

			driver.get("https://www.way2automation.com/angularjs-protractor/banking/#/login");

			driver.manage().window().maximize();

			driver.findElement(By.xpath("//button[contains(text(),'Bank Manager Login')]")).click();

			Thread.sleep(2000);

			driver.findElement(By.xpath("//button[contains(text(),'Add Customer')]")).click();

			WebDriverWait wait = new WebDriverWait(driver, 30);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']")))
					.sendKeys(prop.getProperty("FNAME"));

			// driver.findElement(By.xpath("//input[@placeholder='First
			// Name']")).sendKeys(prop.getProperty("FNAME"));

			driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(prop.getProperty("LNAME"));

			driver.findElement(By.xpath("//input[@placeholder='Post Code']")).sendKeys(prop.getProperty("PCODE"));

			driver.findElement(By.xpath("//button[@type='submit']")).click();

			Alert alert = driver.switchTo().alert();

			alert.accept();

			driver.findElement(By.xpath("//button[contains(text(),'Open Account')]")).click();

			Thread.sleep(3000);

			Select select = new Select(driver.findElement(By.id("userSelect")));

			List<WebElement> webElementList = select.getOptions();

			System.out.println("Available Options Size " + webElementList.size());

			if (webElementList.size() > 0)
				select.selectByIndex(webElementList.size() - 1);

			Select selectCurrency = new Select(driver.findElement(By.id("currency")));
			selectCurrency.selectByVisibleText("Rupee");

			driver.findElement(By.cssSelector("button[type=submit]")).click();
			driver.switchTo().alert().accept();

			driver.findElement(By.xpath("//button[@class='btn btn-lg tab'][2]")).click();

			Thread.sleep(2000);
			WebElement mytable = driver
					.findElement(By.xpath("//table[@class='table table-bordered table-striped']/tbody"));

			List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));

			int rows_count = rows_table.size();

			System.out.println("No of rows in a table : " + rows_count);

			String ofname = driver.findElement(By
					.xpath("//table[@class='table table-bordered table-striped']/tbody/tr[" + (rows_count) + "]/td[1]"))
					.getText();

			System.out.println("Inserted customer is " + ofname);
			String ExpectedText = prop.getProperty("FNAME");

			Assert.assertEquals(ExpectedText, ofname);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@AfterMethod
	public void closeBrowser()
	{
		
		driver.close();
		driver.quit();
	}

}
