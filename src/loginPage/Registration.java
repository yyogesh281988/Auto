package loginPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Excel.ReadWrite;



public class Registration {
	
	public static WebDriver driver;
	ReadWrite excel;
	int fields,excel_row;
	WebElement register;
	int testdata_row,testdata_col;
	int flag=0;
	
	
	@Test
	public void initialisation()
	{
		excel=new ReadWrite();
		
		System.setProperty("webdriver.gecko.driver","./driver/geckodriver");
		
		driver= new FirefoxDriver();
		
		driver.get("http://automationpractice.com/index.php");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		excel.screenShots("Home page");
		
		login();
	}
	
	
	@Test(priority=1)
	public void register()
	{
		
		int regData=excel.columnCount("Registration Data",0);
		
		for(testdata_col=2;testdata_col<regData;testdata_col++)
		{
			
			try
			{
				System.out.println("sign in "+testdata_col);
				createAccount(testdata_col);
				
			
				for(testdata_row=1;testdata_row<excel.rowCount("Registration Data");testdata_row++)
				{
			
						if(flag<1)
						{
							System.out.println("registration "+testdata_col);
							registeration_fields_check();
			
						}
						
		
						excel.screenShots("Registration Page Test Data"+(testdata_col-1));
						// -----Title-------
		
												
							if(excel.read("Registration Data",2,testdata_col).equalsIgnoreCase("Male"))
			
								driver.findElement(By.id("uniform-id_gender1")).click();
																	
							else
								driver.findElement(By.id("uniform-id_gender2")).click();
			
				
						// -----First Name-------
		
		
						driver.findElement(By.id("customer_firstname")).sendKeys(excel.read("Registration Data",3,testdata_col));
		
						// -----Last Name-------
		
		
						driver.findElement(By.id("customer_lastname")).sendKeys(excel.read("Registration Data",4,testdata_col));
		
						// -----Password-------
		
		
						driver.findElement(By.id("passwd")).sendKeys(excel.read("Registration Data",5,testdata_col));
		
						// -----DOB-------
		
						String date= excel.read("Registration Data",6,testdata_col);
						String date_part[]=date.split("/");
						String day=date_part[0];
						String mon=date_part[1];
						String year=date_part[2];
						
						Select d=new Select(driver.findElement(By.id("days")));
						
						d.selectByValue(day);
				
						
						Select m=new Select(driver.findElement(By.id("months")));
						
						m.selectByValue(mon);
						
						
						Select y=new Select(driver.findElement(By.id("years")));
						
						y.selectByValue(year);
						
		
						// -----Sign up for our newsletter!-------
		
						if(excel.read("Registration Data",7,testdata_col).equalsIgnoreCase("Yes"))
						driver.findElement(By.id("newsletter")).click();
		
		
						// -----Receive special offers from our partners! -------
		
						if(excel.read("Registration Data",8,testdata_col).equalsIgnoreCase("Yes"))
							driver.findElement(By.id("optin")).click();
		
						// -----Company-------
		
		
						driver.findElement(By.id("company")).sendKeys(excel.read("Registration Data",9,testdata_col));
		
						// -----Address-------
						
		
						driver.findElement(By.id("address1")).sendKeys(excel.read("Registration Data",10,testdata_col));
						// -----Address (Line 2)-------
		
		
						driver.findElement(By.id("address2")).sendKeys(excel.read("Registration Data",11,testdata_col));
		
						// -----City-------
		
						driver.findElement(By.id("city")).sendKeys(excel.read("Registration Data",12,testdata_col));
		
		
						// -----State-------
							
						Select state=new Select(driver.findElement(By.id("id_state")));
						
						state.selectByVisibleText(excel.read("Registration Data",13,testdata_col));;
						
						
						// -----Zip/Postal Code-------
		
		
						driver.findElement(By.id("postcode")).sendKeys(excel.read("Registration Data",14,testdata_col));
		
		

						// -----Additional information-------
		
		
						driver.findElement(By.id("other")).sendKeys(excel.read("Registration Data",15,testdata_col));
		
		

						// -----Home phone-------
		
		
						driver.findElement(By.id("phone")).sendKeys(excel.read("Registration Data",16,testdata_col));
		

						// -----Mobile phone-------
		
		
						driver.findElement(By.id("phone_mobile")).sendKeys(excel.read("Registration Data",17,testdata_col));
		

						// -----Assign an address alias for future reference-------
		
						driver.findElement(By.id("alias")).clear();
						
						driver.findElement(By.id("alias")).sendKeys(excel.read("Registration Data",18,testdata_col));		
		
						driver.findElement(By.id("submitAccount")).click();
						
						Thread.sleep(3000);
						
						logout();
						
						Thread.sleep(3000);

			}
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
		
		
		}
	}
	
	
	@Test
	public void registeration_fields_check()
	{
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		
		//------Registration Form------------
		
		List<WebElement> list=driver.findElements(By.xpath("//label"));
		
		System.out.println(list.size());
		
		int res = 0;
		
		for(excel_row=1;excel_row<=excel.rowCount("Registration Data");excel_row++) 
		
		{
			
			String excelFields=excel.read("Registration Data", excel_row,0);
			
			
			for(fields =0;fields<list.size()-1;fields++)
			{
				String WebFields=list.get(fields).getText();	
				
				
				if(WebFields.equals("Mr.") || WebFields.equals("Mrs."))
				{
					excel.write("Registration Data",1,1,"Pass");
					continue;
				}
				else
				{
						if(WebFields.contains(excelFields))
						{
							System.out.println(excelFields);
							excel.write("Registration Data",excel_row,1,"Pass");
							System.out.println("Pass");
							res=0;
							break;
							
						}	
						else
						{
							res=1;
						}
				}
			}
			if(res==1)
			{
				System.out.println(excelFields);
				excel.write("Registration Data",excel_row,1,"Fail");
				System.out.println("Fail");
			}
			
		}
		flag=flag+1;		
	}
	
	public void login()
	{
		driver.findElement(By.className("login")).click();
	}
	
	public void logout()
	{
		WebDriverWait wait =new WebDriverWait(driver,10);
		
		WebElement exw= wait.until(ExpectedConditions.elementToBeClickable(By.className("logout")));
		
		exw.click();
	}
	
	public void createAccount(int cell)
	{	
		
		WebDriverWait wait =new WebDriverWait(driver,10);
		
		WebElement exw= wait.until(ExpectedConditions.elementToBeClickable(By.id("SubmitCreate")));
		
		driver.findElement(By.id("email_create")).clear();
		
		driver.findElement(By.id("email_create")).sendKeys(excel.read("Registration Data",1,cell));
		
		exw.click();
		
		excel.screenShots("Sign In"+cell);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
