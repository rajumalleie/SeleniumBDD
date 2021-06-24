package pages;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.DriverFactory;
import utilities.ReportConfiguration;


public class DuckGoPages {

    //Page Object  Model
    WebDriver driver;
    ReportConfiguration report=new ReportConfiguration();

    @FindBy(xpath="//*[@id=\"logo_homepage_link\"]")
    public static WebElement Logo;

    @FindBy(xpath="//*[@id=\"search_form_input_homepage\"]")
    public static WebElement SearchBox;

    @FindBy(id="search_button_homepage")
    public static WebElement SearchButton;

    public DuckGoPages(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void navigateToDuckGoApplication(String Url)  {
        try{
            DriverFactory.getDriver().get(Url);
            report.log("Url used->"+Url);
            Thread.sleep(3000);
            report.logResult("Navigated to Duck GO Page",LogStatus.PASS);
        }
        catch (Exception e){
            Assert.fail("Failed to navigate to Duck Go Application");
        }
    }

    public void verifyDuckGoLogoInHomePage() {
        try{
            if( Logo.isDisplayed()){
                report.takeScreenshot(driver,"Logo is available");
            }else{
                Assert.fail("Logo is not available");
            }
        }
        catch (Exception e){
            Assert.fail("Failed to verify Duck Go Logo in HomePage");
        }
    }

    public void searchItem(String itemName) {
        try{
            SearchBox.sendKeys(itemName);
            report.takeScreenshot(driver,"Searching Item");

            //Sample code to do actions with java script
           // JavascriptExecutor jse = (JavascriptExecutor)driver;
           // jse.executeScript("document.getElementById('search_button_homepage').click();");

            SearchButton.click();
            report.log("Item searched ->"+itemName);

            Thread.sleep(2000);
            report.takeScreenshot(driver,"Search Results Page");
            report.logResult("Item searched in Duck GO Page",LogStatus.PASS);
        }
        catch (Exception e){
            Assert.fail("Failed search Item Duck Go Application->"+e.getLocalizedMessage());
        }
    }


}
