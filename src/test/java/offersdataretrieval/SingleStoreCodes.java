package offersdataretrieval;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.opencsv.CSVWriter;

public class SingleStoreCodes {

	@Test
    public void scrapeSingleStore() throws IOException {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://www.couponcodesme.com/ae/carrefour");
		
		List<WebElement> allOffers = driver.findElements(By.xpath("//div[contains(@class,'vouchersdata') and not(self::div[contains(@class,'blueborder')]) and not(preceding::h2)]"));
		
		 try (CSVWriter writer = new CSVWriter(new FileWriter("singleStoreData.csv", true))) {
			 
		if(!allOffers.isEmpty()) {
	
		for(WebElement offer : allOffers) {
			
			String offerTitle = offer.findElement(By.xpath(".//h3")).getText();
			
			String offerDescription = "";
			
			List<WebElement> offerDescriptionElements = offer.findElements(By.xpath(".//div[contains(@class,'vouchdescription')]"));
			 if (!offerDescriptionElements.isEmpty()) {
				 offerDescription = offerDescriptionElements.get(0).getText();
	            }
						
			String couponCode = "";
			
			List<WebElement> couponCodeElements = offer.findElements(By.xpath(".//div[@title='Click to Copy Code']"));
			
          if (!couponCodeElements.isEmpty()) {
        	  
              couponCode = couponCodeElements.get(0).getText();
              
              if(!couponCode.equalsIgnoreCase("CCME")) {
              
            	  System.out.println(offerTitle);
            	  System.out.println(offerDescription);
            	  System.out.println(couponCode);
              }
          }
		}
	}
}
		driver.quit();
	}
}
