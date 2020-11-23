package features.fixtures;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class AdoptionRequestWindowFixture {

	private final WebElement element;

	public AdoptionRequestWindowFixture(WebElement element) {
		this.element = element;
	}

	public void typeEmail(String email) {
		WebElement emailEl = this.element.findElement(By.name("email"));
		emailEl.clear();
		emailEl.sendKeys(email);
	}

	public void typeNote(String note) {
		WebElement notesEl = this.element.findElement(By.name("notes"));
		notesEl.clear();
		notesEl.sendKeys(note);
	}

	public void clickAdd() {
		this.element.findElement(By.cssSelector(".actions"))
			.findElement(By.cssSelector(".green.button"))
			.click();
	}

	public void verifySuccessfullyClosed() {
		try {
			assertThat(this.element.isDisplayed()).isFalse();
		} catch (StaleElementReferenceException e) {
			// that's fine, element is removed so this means it is surely not visible
			return;
		}

		fail("Edit Adoption Request modal is still visible, but it shouldn't");
	}
}
