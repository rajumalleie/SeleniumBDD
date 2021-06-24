package utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private static WebDriver driver;
    private DriverFactory(){
        System.out.println("Hello !! Inside of Driver Factory constructor");
    }

    public static WebDriver getDriver(){
        if(driver==null) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver_89");
            driver =new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void closeDriver(){
        driver.close();
        driver=null;
    }
}
