package utilities;


import com.relevantcodes.extentreports.ExtentReports;
import java.io.File;
import java.io.FileWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.testng.Assert;

public class ReportManager {
    private static ExtentReports extent;
    static String testSuiteName = "";
    static String workingDir;

    public ReportManager() {
    }

    public static void updateConfigFile(String suiteName, ExtentReports extent) {
        try {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(workingDir + "Reports" + File.separator + "report-config.xml");
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element configuration = rootNode.getChild("configuration");
            configuration.getChild("documentTitle").setText("Automated Testing Report - " + suiteName);
            configuration.getChild("reportName").setText(suiteName);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(xmlFile));
            extent.loadConfig(new File(workingDir + "Reports" + File.separator + "report-config.xml"));
        } catch (Exception var8) {
            Assert.fail("Test failed during report-config file creation");
        }

    }

    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(workingDir + "Reports" +
                    File.separator + "resultsReport.html", true);
        }
        return extent;
    }

    static {
        workingDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator;
        System.out.println("Working Directory -->"+workingDir);
    }
}
