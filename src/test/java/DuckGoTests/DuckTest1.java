package DuckGoTests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DuckGoPages;
import utilities.DriverFactory;

public class DuckTest1 {

    @Test
    public void searchItemInDuckTest(){
        try{
            WebDriver driver= DriverFactory.getDriver();
            DuckGoPages duckGoPages =new DuckGoPages(driver);
            String Url="https://start.duckduckgo.com";
            duckGoPages.navigateToDuckGoApplication(Url);
            duckGoPages.verifyDuckGoLogoInHomePage();
            duckGoPages.searchItem("IE Media");

        }catch (Exception e){
            System.out.println("Exception Occurred"+e.getLocalizedMessage());
            Assert.fail("Failed in search Item In DuckTest-"+e.getLocalizedMessage());
        }

    }
}
