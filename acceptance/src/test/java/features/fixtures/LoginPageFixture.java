package features.fixtures;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPageFixture {

	private final WebDriver webDriver;

	public LoginPageFixture(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void loginWith(String username, String password) {
		this.webDriver.findElement(By.name("username")).sendKeys(username);
		this.webDriver.findElement(By.name("password")).sendKeys(password);
		this.webDriver.findElement(By.cssSelector(".btn-primary")).click();
	}

	public void verifyLoggedIn() {
		assertThat(this.webDriver.getCurrentUrl()).doesNotContain("login");
	}
}
