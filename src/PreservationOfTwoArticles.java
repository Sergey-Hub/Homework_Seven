import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class PreservationOfTwoArticles {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "Users/user/Desktop/TestThree/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void preservationOfTwoArticles() {
        //Написать тест, который:
        //1. Сохраняет две статьи в одну папку
        //2. Удаляет одну из статей
        //3. Убеждается, что вторая осталась
        //4. Переходит в неё и убеждается, что title совпадает

        String list_name = "MyList";
        String first_article = "Java (programming language)";
        String second_article = "Island of Indonesia";
        int timeout = 10;

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                timeout
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_article + "']"),
                "can't find element by id",
                timeout
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "title of the page is not visible",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cant find element 'More options'",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cant find element 'Add to reading list'",
                timeout
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cant find onboarding button",
                timeout
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "No element to clear",
                timeout
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                list_name,
                "Cant find input for title",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cant find button OK to click",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find button Navigate up",
                timeout
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                timeout
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_article + "']"),
                "can't find element by id",
                timeout
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "title of the page is not visible",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cant find element 'More options'",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cant find element 'Add to reading list'",
                10
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + list_name + "']"),
                "Cant find list with name " + list_name,
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find button Navigate up",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cant find button My lists",
                timeout
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_image_container"),
                "Cant find articles list with name " + list_name,
                15
        );

        // Проверяю наличие статей на странице
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + first_article + "']"),
                "can't find first article on the list",
                20
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java']"),
                "can't find second article on the list",
                timeout
        );

        // Удаляю первую статью и проверяю отсуствие
        swipeElementToLeft(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='" + first_article + "']"),
                "Cant find article with text " + first_article
        );
        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='" + first_article + "']"),
                "Cant find article with text " + first_article,
                timeout
        );

        //Перехожу по оставшейся статье и проверяю заголовок
        String link_article_title = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java']"),
                "text",
                "cant find article to get its text attribute",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java']"),
                "can't find second article on the list",
                timeout
        );
        String article_title = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "cant find article to get its text attribute",
                timeout
        );
        Assert.assertEquals(
                "Article title does not match title of the link on the list",
                link_article_title,
                article_title
        );

}




    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String velue, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(velue);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear (By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected String waitForElementAndGetAttribute(By by, String attribute, String err_msg, int timeout) {
        WebElement element = waitForElementPresent(by, err_msg, timeout);
        return element.getAttribute(attribute);
    }

    protected void swipeUP(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y )
                .release()
                .perform();
    }

    protected void swipeUpQuick()
    {
        swipeUP(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){

            if (already_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }
}
