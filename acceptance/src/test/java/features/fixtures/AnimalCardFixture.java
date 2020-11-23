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

	public AdoptionRequestWindowFixture andClickAddAdoptionRequest() {
		WebElement addButton = this.element.findElement(By.xpath(".//button[text()='Adopt']"));
		addButton.click();

		WebElement root = this.element.findElement(By.xpath("/*"));
		WebElement modalWindowElement = root.findElement(By.cssSelector(".modal"));

		return new AdoptionRequestWindowFixture(modalWindowElement);
	}

	public AdoptionRequestWindowFixture andClickEditAdoptionRequest() {
		WebElement addButton = this.element.findElement(By.xpath(".//button[text()='Edit Adoption Request']"));
		addButton.click();

		WebElement root = this.element.findElement(By.xpath("/*"));
		WebElement modalWindowElement = root.findElement(By.cssSelector(".modal"));

		return new AdoptionRequestWindowFixture(modalWindowElement);
	}

	public void andHasNoOfPendingRequests(int expectedRequests) {
		WebElement pendingRequests = this.element.findElement(By.cssSelector(".pending-number"));
		assertThat(pendingRequests.getText()).isEqualTo(String.valueOf(expectedRequests));
	}
}
