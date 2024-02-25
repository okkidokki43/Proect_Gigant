package calendar;

import data.EventTypeData;
import driverfactory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CalendarEventsPage;

public class CalendarTest {
    private Logger logger = (Logger) LogManager.getLogger(CalendarTest.class);
    private WebDriver driver;
    private CalendarEventsPage calendarEventsPage;

    @BeforeEach
    public void init() {
        driver = new WebDriverFactory("--start-maximized").create();
        logger.info("Start driver");

        this.calendarEventsPage = new CalendarEventsPage(driver);
        calendarEventsPage.open("/events/near/");
    }

    @AfterEach
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Stop driver");
    }


    @Test
    public void validationDateEvents() {
        calendarEventsPage
                .checkVisibilityCardsEvent()
                .checkStartDateEvent();
    }

    @Test
    public void selectEventsOfType() {
        calendarEventsPage
                .selectSortedEventsType(EventTypeData.OPEN)
                .checkEventsType(EventTypeData.OPEN);
    }
}