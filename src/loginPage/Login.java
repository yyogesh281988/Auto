package loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import Excel.ReadWrite;

public class Login 
{
	WebDriver driver;
	Registration reg;
	ReadWrite excel;
	
	@Test(alwaysRun=true,priority=2)
	public void LoginInitialisation()
	{
		System.out.println("i m in contrustor");
		driver=Registration.driver;
		reg=new Registration();
		excel=new ReadWrite();
	}
	@Test(priority=3)
	public void logIn()
	{
		reg.login();
		
		int testdata=excel.columnCount("Registration Data",0);

		System.out.println(testdata);
		
		for(int exceldata=1;exceldata<testdata-2;exceldata++)
		{
			
				
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(excel.read("login",1,exceldata));
			
			driver.findElement(By.id("email")).sendKeys(excel.read("login",1,exceldata));
		
			driver.findElement(By.id("passwd")).sendKeys(excel.read("login",2,exceldata));
			
			driver.findElement(By.id("SubmitLogin")).click();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String username=excel.read("login",3,exceldata);
			
			System.out.println(username);
			
			String webname=driver.findElement(By.xpath(".//*[@class='account']/span")).getText();
			
			System.out.println(webname);
			
			Assert.assertEquals(true, webname.contains(username));
			reg.logout();
			
			}
			
			
			
		}
	
}
