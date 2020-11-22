package rescue.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = WhoAmIController.class)
@Import(SecurityConfiguration.class)
class WhoAmIControllerTest {

	private static final String TEST_USER_NAME = "joanne";
	private static final String TEST_USER_PASSWORD = "joanne1975";

	private WebDriver webDriver;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.webDriver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc).build();
	}

	@Test
	void shouldNotAllowAccessForUnauthenticatedUsers() {
		webDriver.navigate().to("http://localhost/whoami");
		assertThat(webDriver.getCurrentUrl()).endsWith("/login");
	}

	@Test
	void shouldDisplayUsernameAfterSuccessfulLogin() {
		webDriver.navigate().to("http://localhost/whoami");
		webDriver.findElement(By.name("username")).sendKeys(TEST_USER_NAME);
		webDriver.findElement(By.name("password")).sendKeys(TEST_USER_PASSWORD);
		webDriver.findElement(By.className("btn-primary")).click();

		assertThat(webDriver.getPageSource()).contains(TEST_USER_NAME);
	}

	@Test
	void shouldDisplayErrorForFailedLogin() {
		webDriver.navigate().to("http://localhost/whoami");
		webDriver.findElement(By.name("username")).sendKeys(TEST_USER_NAME);
		webDriver.findElement(By.name("password")).sendKeys("incorrect password");
		webDriver.findElement(By.className("btn-primary")).click();

		assertThat(webDriver.getPageSource()).contains("Bad credentials");
	}

}