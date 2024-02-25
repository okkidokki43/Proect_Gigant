package pages;

import data.EventTypeData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalendarEventsPage extends AbsBasePage {

    public CalendarEventsPage(WebDriver driver) {
        super(driver, "/events/near/");
    }

    @FindBy(css = ".dod_new-event")
    private List<WebElement> cardsEvent;
    @FindBy(css = ".dod_new-event__calendar-icon ~ .dod_new-event__date-text")
    private List<WebElement> dateEvents;
    @FindBy(css = ".dod_new-event .dod_new-type__text")
    private List<WebElement> eventsTypeIcon;

    private String dropdownSortingEventsListSelector = ".dod_new-events-dropdown";
    private String dropdownEventsListSelector = dropdownSortingEventsListSelector + " .dod_new-events-dropdown__list";
    private String dropdownSortingEventsItemTemplate = dropdownEventsListSelector + " [title='%s']";

    public CalendarEventsPage checkVisibilityCardsEvent() {
        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.visibilityOfAllElements(cardsEvent)));

        return this;
    }

    public CalendarEventsPage checkStartDateEvent() {
        for(WebElement dateEvent: dateEvents) {
            LocalDate currentDate = LocalDate.now();

            Pattern pattern = Pattern.compile("\\d+\\s+\\[а-яА-Я]+\\s+\\d{4}");
            String dateEventStr = dateEvent.getText();
            Matcher matcher = pattern.matcher(dateEvent.getText());
            if(!matcher.find()) {
                dateEventStr += String.format(" %d", currentDate.getYear());
            }
            LocalDate eventDate = LocalDate
                    .parse(dateEventStr, DateTimeFormatter.ofPattern("dd MMMM yyyy"));
            Assertions.assertTrue(
                    eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate),
                    "Date events are earlier than the current one");
        }
        return this;
    }

    private CalendarEventsPage dropdownSortingEventsShouldNotBeOpened() {
        Assertions.assertTrue(
                waitTools.waitForCondition(
                        ExpectedConditions.not(ExpectedConditions.attributeContains(
                                driver.findElement(By.cssSelector(dropdownSortingEventsListSelector)),
                                "class",
                                "dod_new-events-dropdown_opened"))
                )
        );
        return this;
    }

    private CalendarEventsPage dropdownSortingEventsShouldBeOpened() {
        Assertions.assertTrue(
                waitTools.waitForCondition((ExpectedConditions.attributeContains(
                        driver.findElement(By.cssSelector(dropdownSortingEventsListSelector)), "class", "dod_new-events-dropdown_opened"))
                )
        );
        return this;
    }

    private CalendarEventsPage openSortingEventsDropdown() {
        driver.findElement(By.cssSelector(dropdownSortingEventsListSelector)).click();

        return this;
    }

    private CalendarEventsPage sortingItemsShouldBeVisible() {
        Assertions.assertTrue(waitTools.waitElementVisible(driver.findElement(By.cssSelector(dropdownEventsListSelector))));

        return this;
    }

    private CalendarEventsPage clickSortingItem(EventTypeData eventSortedData) {
        driver.findElement(By.cssSelector(String.format(dropdownSortingEventsItemTemplate, eventSortedData.getName()))).click();        return this;
    }

    public CalendarEventsPage selectSortedEventsType(EventTypeData eventTypeData) {
        this.dropdownSortingEventsShouldNotBeOpened()
                .openSortingEventsDropdown()
                .dropdownSortingEventsShouldBeOpened()
                .sortingItemsShouldBeVisible()
                .clickSortingItem(eventTypeData);

        return this;
    }

    public CalendarEventsPage checkEventsType(EventTypeData eventTypeData) {
        for(WebElement element: eventsTypeIcon) {
            Assertions.assertEquals(eventTypeData.getName(), element.getText(),"Type event does not match");
        }
        return this;
    }


}