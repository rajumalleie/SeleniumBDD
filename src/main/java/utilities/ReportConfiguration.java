package utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
public class ReportConfiguration {

    static ThreadLocal<Document> documentInstance = new ThreadLocal();
    public static ThreadLocal<Integer> stepCounter = new ThreadLocal();
    public static int count;

    public static Map extentTestMap = new HashMap();
    public static ExtentReports extentReports;
    public static ExtentTest test;

    public ReportConfiguration(){
       count=1;
    }

    public void setExtentReportInstance(ExtentReports extent){
        extentReports=extent;
    }
    public static synchronized ExtentTest getTest() {
        return (ExtentTest)extentTestMap.get((int)Thread.currentThread().getId());
    }

    public synchronized void endTest() {
        extentReports.endTest((ExtentTest)extentTestMap.get((int)Thread.currentThread().getId()));
        extentReports.flush();
    }

    public synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extentReports.startTest(testName, desc);
        extentTestMap.put((int)Thread.currentThread().getId(), test);
        return test;
    }

    public synchronized Document startReportingPdf(String testname) {
        Document document =
                new Document(PageSize.A4, 50.0F, 50.0F, 50.0F, 50.0F);
        try {
            test = startTest(testname,"Duck GO Tests");
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator
                            + "main" + File.separator + "resources" + File.separator + "pdfReports" + File.separator + testname + ".pdf");
            PdfWriter.getInstance(document,fos );
            stepCounter.set(1);
        } catch (DocumentException | FileNotFoundException var4) {
            var4.printStackTrace();
        }
        document.open();
        try {
            addTitlePage(document, testname);
        } catch (DocumentException var3) {
            var3.printStackTrace();
        }
        setDocument(document);
        return documentInstance.get();
    }

    public  synchronized void setDocument(Document document) {
        documentInstance.set(document);
    }

    public synchronized void closeDocument() {
        if (!documentInstance.get().isOpen()) {
            documentInstance.get().open();
        }
        documentInstance.get().close();
    }

    private  synchronized void addTitlePage(Document document, String TestTitle) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("This document contains the test results for the TestCase:- " + TestTitle));
        preface.add("Time of execution: "+java.time.LocalDateTime.now());
        addEmptyLine(preface, 2);
        preface.add(new Paragraph("This document is a automatically generated version post running " +
                "the above mentioned test case"));
        document.add(preface);
    }

    private  void addEmptyLine(Paragraph paragraph, int number) {
        for(int i = 0; i < number; ++i) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void takeScreenshot(WebDriver driver, String stepDescription) {
        try {
            documentInstance.get().newPage();
            documentInstance.get().addTitle(stepDescription);
            documentInstance.get().addSubject(stepDescription);
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFilePath = new File("src/main/resources/Reports/image" +count + ".jpg");
            count++;
            FileUtils.copyFile(screenshot, destinationFilePath);
            Image image=Image.getInstance(destinationFilePath.getAbsolutePath());
            image.scaleAbsolute(500.0F, 500.0F);
            documentInstance.get().add(image);
            System.out.println(stepDescription+"-added to pdf");
            test.log(LogStatus.PASS, stepDescription + test.addScreenCapture(
                            addScreenshot(destinationFilePath.getAbsolutePath())));

            destinationFilePath.delete();
            logResult("Take screenshot: Page Name->"+stepDescription, LogStatus.PASS);
        } catch (Exception e) {
            logResult("Failed to takeScreenshot"+e.getLocalizedMessage(), LogStatus.FAIL);
            Assert.fail("Failed to takeScreenshot");
        }
    }

    public static String addScreenshot(String ScreenshotFilepath) {
        File scrFile = new File(ScreenshotFilepath);
        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int)scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
        } catch (IOException var5) {
            var5.printStackTrace();
        }
        return "data:image/png;base64," + encodedBase64;
    }

    public void log(String logMessage){
        try {
            System.out.println(logMessage);
            test.log(LogStatus.PASS,logMessage);
        } catch (Exception e) {
            test.log(LogStatus.FAIL,"Failed to log Result"+e.getLocalizedMessage());
            Assert.fail("Failed to log Result");
        }
    }

    public void logResult(String logMessage, LogStatus status){
        try {
            System.out.println(logMessage);
            if(status.equals(LogStatus.FAIL)){
                test.log(LogStatus.FAIL,logMessage);
                Assert.fail(logMessage);
            }else {
                test.log(LogStatus.PASS,logMessage);
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL,"Failed to log Result"+e.getLocalizedMessage());
            Assert.fail("Failed to log Result");
        }
    }
}
