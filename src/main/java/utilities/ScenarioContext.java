package utilities;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;


public class ScenarioContext {
        static ExtentTest test;
        static ExtentReports report;
        static WebDriver driver;
        @Before
        public synchronized void setup(Scenario scenario){
            System.out.println("Scenario: " + scenario.getName());
        }
        @After
        public synchronized void closeDown(Scenario result)  {
            System.out.println("Finished scenario " + result.getName());
        }
}
