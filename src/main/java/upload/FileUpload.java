package upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUpload {

	public static void main(String[] args) {
		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		List<String> fileList = new ArrayList<String>();
		fileList.add("C:/Users/Dell/Desktop/Dummy1.xlsx");
		fileList.add("C:/Users/Dell/Desktop/Dummy2.xlsx");
		fileList.add("C:/Users/Dell/Desktop/Dummy3.xlsx");
		fileList.add("C:/Users/Dell/Desktop/Dummy4.xlsx");
		fileList.add("C:/Users/Dell/Desktop/Dummy5.xlsx");
		Map<String, ArrayList<String>> mp = new HashMap<String, ArrayList<String>>();
		int count =1;
		for (Iterator iterator = fileList.iterator(); iterator.hasNext();) {
			String stringFilePath = (String) iterator.next();
			driver.get("https://the-internet.herokuapp.com/upload");
			driver.findElement(By.id("file-upload")).sendKeys(stringFilePath);
			driver.findElement(By.id("file-submit")).submit();
			if(driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText().contains("File Uploaded!")) {
				System.out.println("File Uploaded Succesfully");
			} else {
				System.out.println("File Not Uploaded");
			}
			ArrayList<String> request = new ArrayList<String>();
			request.add(driver.findElement(By.id("uploaded-files")).getText());
			request.add(driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText());
			
			mp.put("RequestId"+count, request);
			System.out.println(mp);
			sendemail(mp);
			count++;
			
		}
		
		driver.quit();

	}

	private static void sendemail(Map<String, ArrayList<String>> mp) {
		for (Entry<String, ArrayList<String>> entry : mp.entrySet()) {
			System.out.println(entry.getKey() +"_"+entry.getValue()); 
		} 
	}

}
