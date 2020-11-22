package features.fixtures;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

	public AnimalCardFixture verifyAnimalIsDisplayed(String name) {
		try {
			WebElement header = this.webDriver.findElement(By.xpath("//div[text()='" + name + "']"));
			WebElement animalCard = header.findElement(By.xpath("./../.."));
			return new AnimalCardFixture(animalCard);
		} catch (NoSuchElementException e) {
			fail("An animal with name " + name + " is not found on the page");
		}

		return null;
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

	public void clickLogin() {
		this.webDriver.findElement(By.xpath("//div[text()='Sign in to adopt']/..")).click();
	}

	public void resetSession() {
		this.webDriver.manage().deleteAllCookies();
	}
}
