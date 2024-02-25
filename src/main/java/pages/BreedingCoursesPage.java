package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class BreedingCoursesPage extends AbsBasePage {
    public BreedingCoursesPage(WebDriver driver) {
        super(driver, "/catalog/courses");
    }
    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')][.//h6]")
    private List<WebElement> cardsCourses;

    public void cardsCoursesCountShouldBeSameAs(int count) {
        Assertions.assertEquals(
                count,
                cardsCourses.size(),
                String.format("Count cards courses should be %d", count));
    }

    public void clickRandomCardCourses() {
        faker.options().nextElement(cardsCourses).click();
    }
}
