package offersdataretrieval;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


public class CouponCodesMECodesData {

	public static void main(String[] args) throws CsvValidationException, IOException {

        String storeName = null;
        String storeURL = null;

        CSVReader reader = new CSVReader(new FileReader("CouponCodesME_Stores.csv"));

        String[] values;

        int totalStores = 0;
        int totalOffers = 0;

        while ((values = reader.readNext()) != null) {

            storeName = values[0];
            storeURL = values[1];

            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();

            try {

                driver.get(storeURL);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                List<WebElement> allOffers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'vouchersdata') and not(self::div[contains(@class,'blueborder')]) and not(preceding::h2)]")));

                if (!allOffers.isEmpty()) {

                    try (CSVWriter writer = new CSVWriter(new FileWriter("CouponCodesME_AllOffers.csv", true))) {

                        for (WebElement offer : allOffers) {

                            String offerTitle = offer.findElement(By.className("offer_title_main")).getText();

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
                                
                              	  String[] dataValues = {storeName, offerTitle, offerDescription, couponCode};
                                  writer.writeNext(dataValues);
                                }
                            }
                        }
                    }
                    
                    totalStores++;
                    totalOffers += allOffers.size();
                    
                } else {

                	continue;
                }

            } catch (TimeoutException e) {

                continue;

            } finally {

                driver.quit();
            }
        }

        reader.close();

        System.out.println("Competitor Name: CouponCodes ME");
        System.out.println("---------------------------");
        System.out.println("Total No. of Stores: " + totalStores);
        System.out.println("Total Coupons: " + totalOffers);
    }
}

