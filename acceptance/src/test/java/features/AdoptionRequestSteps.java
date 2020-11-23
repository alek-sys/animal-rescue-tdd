package features;

import features.fixtures.AdoptionRequestWindowFixture;
import features.fixtures.HomePageFixture;
import features.fixtures.LoginPageFixture;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class AdoptionRequestSteps {

	private final HomePageFixture homePageFixture;
	private final LoginPageFixture loginPageFixture;

	private AdoptionRequestWindowFixture adoptionRequestWindow;

	public AdoptionRequestSteps(HomePageFixture homePageFixture, LoginPageFixture loginPageFixture) {
		this.homePageFixture = homePageFixture;
		this.loginPageFixture = loginPageFixture;
	}

	@Given("I'm signed up with username {string} and password {string}")
	public void iMSignedUpWithUsernameAndPassword(String username, String password) {
		// here we either need to have pre-configured test user and take name / password from settings
		// or have a way to create a test user on the fly. It is quite often possible in case of OAuth2 login,
		// many identity providers provide this functionality.

		// For this test, we assume user already exists.

		this.homePageFixture.navigateTo();
		this.homePageFixture.resetSession();
	}

	@And("I log in with username {string} and password {string}")
	public void iLogInWithUsernameAndPassword(String username, String password) {
		this.homePageFixture.clickLogin();
		this.loginPageFixture.loginWith(username, password);
		this.loginPageFixture.verifyLoggedIn();
	}

	@And("I click \"Add adoption request\" on {string} card")
	public void iClickAddRequest(String name) {
		this.adoptionRequestWindow = this.homePageFixture
			.verifyAnimalIsDisplayed(name)
			.andClickAddAdoptionRequest();
	}

	@And("I click \"Edit adoption request\" on {string} card")
	public void iClickEditRequest(String name) {
		this.adoptionRequestWindow = this.homePageFixture
			.verifyAnimalIsDisplayed(name)
			.andClickEditAdoptionRequest();
	}

	@And("I specify my email {string} and add note {string}")
	public void iSpecifyMyEmailAndAddNote(String email, String note) {
		this.adoptionRequestWindow.typeEmail(email);
		this.adoptionRequestWindow.typeNote(note);
		this.adoptionRequestWindow.clickAdd();
	}

	@Then("I see {int} pending adoption request\\(s) for {string}")
	public void iSee(int expectedRequests, String name) {
		this.homePageFixture
			.verifyAnimalIsDisplayed(name)
			.andHasNoOfPendingRequests(expectedRequests);

		this.adoptionRequestWindow.verifySuccessfullyClosed();
	}

	@And("I have created adoption request for {string} with email {string} and note {string}")
	public void iHaveCreatedAdoptionRequestFor(String name, String email, String note) {
		this.iClickAddRequest(name);
		this.adoptionRequestWindow.typeEmail(email);
		this.adoptionRequestWindow.typeNote(note);
		this.adoptionRequestWindow.clickAdd();
	}

	@And("I click \"Delete\"")
	public void iClick() {
		this.adoptionRequestWindow.clickDelete();
	}
}
