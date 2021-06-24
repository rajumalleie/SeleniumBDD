package DuckGoTests;

import com.itextpdf.text.Document;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import utilities.DriverFactory;
import utilities.ReportConfiguration;

import java.io.File;

public class SampleTest {

    static ExtentTest test;
    static ExtentReports extentReports;
    static WebDriver driver;
    ReportConfiguration report=new ReportConfiguration();
    Document document;
    @BeforeClass
    public void beforeTest(){
        try{
            document = report.startReportingPdf(getClass().getName());
            extentReports = new ExtentReports(System.getProperty("user.dir") +
                    File.separator + "src" + File.separator + "main" + File.separator + "resources" +
                    File.separator + "Reports" + File.separator + getClass().getName()+".html");
            test = extentReports.startTest("SampleTest");
            System.out.println("Inside of Before Test");
            driver=DriverFactory.getDriver();
            test.log(LogStatus.PASS,"Driver launched");
        }catch(Exception e){
            System.out.println("Exception->"+e.getMessage());
            test.log(LogStatus.FAIL,"Failed to launch browser");
            Assert.fail("Failed in Before Duck Go Tests Test");
        }
    }

    @Test
    public void sampleTest(){
        try{
            System.out.println("Inside of Test");
            driver.get("https://start.duckduckgo.com/");
            Thread.sleep(2000);
            test.log(LogStatus.PASS,"Navigated to Duck GO Page");
            report.takeScreenshot(driver,"Duck Go HOme Page");
            System.out.println("Navigated to Duck home screen");

            driver.findElement(By.xpath("//*[@id=\"search_form_input_homepage\"]")).sendKeys("Hello");
            Thread.sleep(2000);
            report.takeScreenshot(driver,"Searching Item");
            driver.findElement(By.id("search_button_homepage")).click();
            Thread.sleep(2000);
            report.takeScreenshot(driver,"Search Results Page");
            test.log(LogStatus.PASS,"Item searched in Duck GO Page");

        }catch(Exception e){
            System.out.println("Exception->"+e.getMessage());
            test.log(LogStatus.FAIL,"Failed to search item  Duck GO Page");
            Assert.fail("Failed inside of test for navigating to URL");
        }
    }


    @AfterClass
    public void afterTest(){
        try{
            System.out.println("Inside of After Test");
            DriverFactory.closeDriver();
            extentReports.endTest(test);
            extentReports.flush();
            report.closeDocument();
        }
       catch (Exception e){
           test.log(LogStatus.PASS,"Failed to close driver");
           Assert.fail("Failed in Before DuckGoTests.Test");
       }

    }

}
