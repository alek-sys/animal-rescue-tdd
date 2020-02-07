package features;

import features.fixtures.HomePageFixture;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

	@Bean
	WebDriver webDriver() {
		return new FirefoxDriver(new FirefoxOptions().setHeadless(true));
	}

	@Bean
	HomePageFixture homePageFixture(WebDriver webDriver) {
		String envUrl = System.getenv().getOrDefault("ENV_URL", "http://localhost:3000/rescue");
		return new HomePageFixture(webDriver, envUrl);
	}
}
