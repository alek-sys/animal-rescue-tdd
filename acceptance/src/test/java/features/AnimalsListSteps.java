package features;

import features.fixtures.HomePageFixture;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

public class AnimalsListSteps {

	private final HomePageFixture homePage;

	public AnimalsListSteps(HomePageFixture homePage) {
		this.homePage = homePage;
	}

	@Given("^I'm an anonymous user$")
	public void iMAnAnonymousUser() {
	}

	@When("^I navigate to the main page$")
	public void iNavigateToTheMainPage() {
		this.homePage.navigateTo();
	}

	@Then("I see {word}")
	public void iSee(String name) {
		this.homePage.verifyAnimalIsDisplayed(name);
	}

	@Then("I don't see {word}")
	public void iDontSee(String name) {
		this.homePage.verifyAnimalIsNotDisplayed(name);
	}

	@Then("I see {word} with description including {string} and rescue date {}")
	public void iSee(String name, String descriptionSubstring, String rescueDate) {
		this.homePage
			.verifyAnimalIsDisplayed(name)
			.andHasDescriptionIncluding(descriptionSubstring)
			.andHasRescueDate(rescueDate);
	}
}
