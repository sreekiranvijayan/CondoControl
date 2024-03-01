import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class changeLanguage {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver (Here I'm using a package (bonigarcia) which enables to locate the correct chrome driver without downloading)
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Navigate to the login URL
        driver.get("https://qa.app.condocontrol.com:500/login");
        driver.manage().window().maximize();

        // Perform login
        driver.findElement(By.id("Username")).sendKeys("xxxx@user1.com");
        driver.findElement(By.id("Password")).sendKeys("xxxxx");
        driver.findElement(By.id("loginBtn")).click();

        // Adding wait time to make sure all items are loaded
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser window after each test
        driver.quit();
    }

    @Test
    public void changeLanguagePreference() {
        // Click on "My Account" in the left-hand menu
        driver.findElement(By.xpath("//*[@id=\"menuitem-nav_menu_my_account\"]/a/span")).click();

        // Wait for the "My Account" page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Locating the language preference edit button
        WebElement languagePreferenceElement = driver.findElement(By.id("openLanguagePreferences"));
        languagePreferenceElement.click();

        // Selecting a new language
        WebElement newOption = driver.findElement(By.xpath("//*[@id=\"LanguagePreference\"]/option[5]")); 
        newOption.click();
        //Saving to confirm the language change
        WebElement saveNewOption = driver.findElement(By.xpath("//*[@id=\"languagePreferencesForm\"]/div[2]/input"));;
        saveNewOption.click();

        // Check if the language preference is now set to Test English
        WebElement selectedLanguage = driver.findElement(By.xpath("//*[@id=\"tab-1\"]/div/div[1]/div[9]/div/p"));
        // Verifying if the language preference has been updated, if it's not "English test", the test will fail
        Assert.assertEquals(selectedLanguage.getText(), "English Test");

    }



}
