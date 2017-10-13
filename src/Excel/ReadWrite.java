package Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import loginPage.Registration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;




public class ReadWrite 

{
XSSFWorkbook workbook;
XSSFSheet sheet;
DataFormatter df;
File f;
File f1;
WebDriver driver;
	
	public ReadWrite() 
		{
	
		f=new File(".//Files/TestData.xlsx");
		f1=new File(".//Files/TestDataOutput.xlsx");
		
		try {
			FileUtils.copyFile(f,f1);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		FileInputStream fi;
		try {
			fi = new FileInputStream(f);
			workbook=new XSSFWorkbook(fi);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		df=new DataFormatter();
		}

	public String read(String sheetname,int row,int col)
	{
		sheet =workbook.getSheet(sheetname);
		String data=df.formatCellValue(sheet.getRow(row).getCell(col));
		return data;
	}

	public void write(String sheetname,int row,int col,String data)
	{
		
		sheet =workbook.getSheet(sheetname);
	
		try
		{
			sheet.getRow(row).createCell(col).setCellValue(data);
			
		}
		catch(Exception e)
		{
			sheet.createRow(row).createCell(col).setCellValue(data);
		}
		FileOutputStream fo;
		
		try {
			fo = new FileOutputStream(f1);
			workbook.write(fo);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}	

	public int rowCount(String sheetname)
	{
		sheet=workbook.getSheet(sheetname);
	
		return sheet.getLastRowNum();
	}
	
	public int columnCount(String sheetname,int row)
	{
		sheet=workbook.getSheet(sheetname);
		
		return sheet.getRow(row).getLastCellNum();
		
	}
	public void screenShots(String Page)
	{
		
		driver = Registration.driver;
		File src = ((TakesScreenshot)driver). getScreenshotAs(OutputType. FILE);
		try {
			FileUtils.copyFile(src, new File(".//Screenshots/"+Page+".png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
}
