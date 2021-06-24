package stepdefs;

import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.DuckGoPages;
import utilities.DriverFactory;
import utilities.ReportConfiguration;

import pages.DuckGoPages.*;


public class DuckGoStepdefs {

    DuckGoPages duckGoPage;
    WebDriver driver;
    ReportConfiguration report=new ReportConfiguration();
    public DuckGoStepdefs(){
        driver= DriverFactory.getDriver();
        duckGoPage=new DuckGoPages(driver);
    }

    @Given("I navigate to DuckGO Application")
    public void iNavigateToDuckGOApplication() {
        String Url="https://start.duckduckgo.com";
        //duckGoPage.navigateToDuckGoApplication(Url);
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
    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {

    }
    @Then("I expect to see the duckduckgo logo on the page")
    public void iExpectToSeeTheDuckduckgoLogoOnThePage() {
        //duckGoPage.verifyDuckGoLogoInHomePage();
        try{
            if( DuckGoPages.Logo.isDisplayed()){
                report.takeScreenshot(driver,"Logo is available");
            }else{
                Assert.fail("Logo is not available");
            }
        }
        catch (Exception e){
            Assert.fail("Failed to verify Duck Go Logo in HomePage");
        }
    }
    @When("I type {string} into the search box")
    public void iTypeIntoTheSearchBox(String itemName) {
        //duckGoPage.searchItem(itemName);
        try{
            DuckGoPages.SearchBox.sendKeys(itemName);
            report.takeScreenshot(driver,"Searching Item");

            //Sample code to do actions with java script
            // JavascriptExecutor jse = (JavascriptExecutor)driver;
            // jse.executeScript("document.getElementById('search_button_homepage').click();");

            DuckGoPages.SearchButton.click();
            report.log("Item searched ->"+itemName);

            Thread.sleep(2000);
            report.takeScreenshot(driver,"Search Results Page");
            report.logResult("Item searched in Duck GO Page",LogStatus.PASS);
        }
        catch (Exception e){
            Assert.fail("Failed search Item Duck Go Application->"+e.getLocalizedMessage());
        }
    }
    @Then("I expect to see exactly {int} suggestions in the typeahead dropdown")
    public void iExpectToSeeExactlySuggestionsInTheTypeaheadDropdown(int arg0) {
        
    }

    @When("I go to the homepage and type Then click the magnifying glass")
    public void iGoToTheHomepageAndTypeThenClickTheMagnifyingGlass() {
        
    }

    @Then("I should get {int} results on the results page")
    public void iShouldGetResultsOnTheResultsPage(int arg0) {
    }
}
