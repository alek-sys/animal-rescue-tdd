package features.fixtures;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.fail;

public class HomePageFixture {

	private final WebDriver webDriver;
	private final String baseUrl;

	public HomePageFixture(WebDriver webDriver, String baseUrl) {
		this.webDriver = webDriver;
		this.baseUrl = baseUrl;
	}

	public void navigateTo() {
		this.webDriver.navigate().to(this.baseUrl);
	}

	public void verifyAnimalIsDisplayed(String name) {
		try {
			this.webDriver.findElement(By.xpath("//div[text()='" + name + "']"));
		} catch (NoSuchElementException e) {
			fail("An animal with name " + name + " is not found on the page");
		}
	}

	public void verifyAnimalIsNotDisplayed(String name) {
		try {
			this.webDriver.findElement(By.xpath("//div[text()='" + name + "']"));
		} catch (NoSuchElementException e) {
			// fine, no element is what we need
			return;
		}

		fail("An animal with name " + name + " is displayed on the page but it shouldn't be");
	}
}
