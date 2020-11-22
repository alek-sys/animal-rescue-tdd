package features.fixtures;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AdoptionRequestWindowFixture {

	private final WebElement element;

	public AdoptionRequestWindowFixture(WebElement element) {
		this.element = element;
	}

	public void typeEmail(String email) {
		this.element.findElement(By.name("email")).sendKeys(email);
	}

	public void typeNote(String note) {
		this.element.findElement(By.name("notes")).sendKeys(note);
	}

	public void clickAdd() {
		this.element.findElement(By.cssSelector(".actions"))
			.findElement(By.cssSelector(".green.button"))
			.click();
	}
}
