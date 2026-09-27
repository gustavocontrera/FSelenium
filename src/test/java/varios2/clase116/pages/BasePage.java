package varios2.clase116.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {

    public WebDriver driver;

    public String getPageTitle(){
        return driver.getTitle();
    }

    public String getPageUrl(){
        return driver.getCurrentUrl();
    }

}
