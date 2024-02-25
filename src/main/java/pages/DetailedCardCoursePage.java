package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DetailedCardCoursePage extends AbsBasePage {
    String title = "//h1";
    String description = "//h1/following-sibling::div[text()]";
    String durationCourse = "//div/following-sibling::p[contains(text(), 'месяц')]";

    public DetailedCardCoursePage(WebDriver driver, String coursesPath) {
        super(driver, String.format("/lessons/%s", coursesPath));
    }

    public void checkTitleCourse() {
        Assertions.assertFalse(driver.findElement(By.xpath(title)).getText().isEmpty(), "Course name missing");
    }

    public void checkDescriptionCourse() {
        Assertions.assertFalse(driver.findElement(By.xpath(description)).getText().isEmpty(), "No course descriptions available");
    }

    public void checkCourseDuration() {
        Assertions.assertFalse(driver.findElement(By.xpath(durationCourse)).getText().isEmpty(), "There is no course duration");
    }
    public void checkCourseFormat(String format) {
        WebElement formatCourse = driver.findElement(By.xpath(String.format("//p[contains(text(), '%s')]", format)));
        Assertions.assertFalse(formatCourse.getText().isEmpty());
    }
}
