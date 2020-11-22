package features.fixtures;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalCardFixture {

	private final WebElement element;

	public AnimalCardFixture(WebElement element) {
		this.element = element;
	}

	public AnimalCardFixture andHasDescriptionIncluding(String descriptionSubstring) {
		WebElement description = this.element.findElement(By.cssSelector(".description"));
		assertThat(description.getText()).contains(descriptionSubstring);
		return this;
	}

	public AnimalCardFixture andHasRescueDate(String rescueDate) {
		WebElement rescueDateEl = this.element.findElement(By.cssSelector(".date"));
		assertThat(rescueDateEl.getText()).contains(rescueDate);
		return this;
	}
}
