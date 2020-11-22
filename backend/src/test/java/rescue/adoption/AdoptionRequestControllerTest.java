package rescue.adoption;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rescue.security.SecurityConfiguration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdoptionRequestController.class)
@Import(SecurityConfiguration.class)
class AdoptionRequestControllerTest {

	private final Integer TEST_ANIMAL_ID = 1;

	@MockBean
	AdoptionRequests adoptionRequests;

	@Autowired
	MockMvc mockMvc;
	private String TEST_USER_NAME = "test-user";

	@Test
	void shouldOnlyAllowAuthenticatedUsers() throws Exception {
		this.mockMvc.perform(post("/animals/{id}/adoption-requests/", TEST_ANIMAL_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", Matchers.endsWith("/login")));
	}

	@Test
	void shouldReturnCreatedStatus() throws Exception {
		sendAdoptionRequest()
			.andExpect(status().isCreated());
	}

	@Test
	void shouldPassAnimalId() throws Exception {
		sendAdoptionRequest();

		verify(adoptionRequests).adopt(eq(TEST_ANIMAL_ID), anyString(), anyString(), anyString());
	}

	@Test
	void shouldPassEmailAndNotes() throws Exception {
		sendAdoptionRequest();

		verify(adoptionRequests).adopt(any(), anyString(), eq("test@example.com"), eq("Test note"));
	}

	@Test
	void shouldRequireEmail() throws Exception {
		this.mockMvc.perform(
			post("/animals/{id}/adoption-requests/", TEST_ANIMAL_ID)
				.with(user("test-user"))
				.content("{ \"notes\": \"test\" }")
				.header("Content-Type", "application/json"))
			.andExpect(status().isBadRequest());

		verifyNoInteractions(adoptionRequests);
	}

	@Test
	void shouldPassUsername() throws Exception {
		sendAdoptionRequest();

		verify(adoptionRequests).adopt(anyInt(), eq(TEST_USER_NAME), anyString(), anyString());
	}

	private String testRequestJson() {
		//language=JSON
		return "{ \"email\": \"test@example.com\", \"notes\": \"Test note\" }";
	}

	private ResultActions sendAdoptionRequest() throws Exception {
		return this.mockMvc.perform(
			post("/animals/{id}/adoption-requests/", TEST_ANIMAL_ID)
				.with(user(TEST_USER_NAME))
				.content(testRequestJson())
				.header("Content-Type", "application/json"));
	}
}