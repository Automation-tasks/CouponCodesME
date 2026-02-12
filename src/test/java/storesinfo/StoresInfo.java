package storesinfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.opencsv.CSVWriter;

public class StoresInfo {

	public static void main(String[] args) throws IOException {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();	
		driver.get("https://www.couponcodesme.com/ae/sitemap/all");
		
		String storeName = null;
		String storeURL = null;
		
		List<WebElement> allStores = driver.findElements(By.xpath("//div[@class='sitemapList']//a"));
		
		try (CSVWriter writer = new CSVWriter(new FileWriter("CouponCodesME_Stores.csv", true))) {
		
		for(WebElement store : allStores) {
			
			storeName = store.getText();
            storeURL = store.getDomAttribute("href");
            
            String[] values = {storeName, storeURL};
            writer.writeNext(values);
		}
	}
		
		driver.quit();
	}
}

