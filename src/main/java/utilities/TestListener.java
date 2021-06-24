package utilities;

import com.relevantcodes.extentreports.ExtentReports;
import org.testng.*;
import org.testng.xml.XmlSuite;
import java.util.List;

public class TestListener implements ITestListener, IReporter {
    ReportConfiguration report = new ReportConfiguration();

    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResult) {

    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {
        System.out.println("********* Started  " + iTestContext.getCurrentXmlTest().getName() + " Test  **********");
        ExtentReports extent = ReportManager.getReporter();
        ReportManager.updateConfigFile(iTestContext.getCurrentXmlTest().getSuite().getName(), extent);
        report.setExtentReportInstance(extent);
        report.startReportingPdf(iTestContext.getCurrentXmlTest().getName());
    }

    public void onFinish(ITestContext iTestContext) {
        System.out.println("********* Finished  " + iTestContext.getCurrentXmlTest().getName() + " Test  **********");
        DriverFactory.closeDriver();
        report.closeDocument();
        report.endTest();
        ReportManager.getReporter().flush();
    }

    @Override
    public void generateReport(List<XmlSuite> list, List<ISuite> list1, String s) {
        //Yet to implement this
    }
}
